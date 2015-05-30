/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.json.JSONException;
import cz.colormemory.json.JSONObject;
import cz.colormemory.kdysem.framework.IGame;
import cz.colormemory.kdysem.framework.IListener;
import cz.colormemory.kdysem.game.commands.ActionList;
import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.entities.Area;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.entities.Room;
import cz.colormemory.kdysem.game.exceptions.GameControlException;
import cz.colormemory.kdysem.game.serialize.SerializeManager;
import cz.colormemory.kdysem.game.support.IInteractable;
import java.awt.Point;





/*******************************************************************************
 * Třída {@code Game} je jedináček a představuje základní funkčnost celé hry.
 * Je vlastně jejím správcem. Ostatní přímo nesouvisející třídy se u
 * ní mohou přihlásit jako posluchači grafiky, zvuku apod. Tyto přímo 
 * nesouvisející třídy, by s ní měli pracovat pouze jako s rozhraním {@link IGame}
 *
 * @author  André HELLER
 * @version 1.5 — 05/2015
 */
public class Game implements IGame
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final Game SINGLETON = new Game();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Vysílač zpráv o změnách pro posluchače */
    private final Broadcaster BROADCASTER = new Broadcaster();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
    
    /** Odkaz na správce místností */
    private RoomManager roomManager;
    
    /** Odkaz na správce objektů */
    private GameObjectManager gameObjectManager;
    
    /** Odkaz na Správce herních stavů */
    private StateManager stateManager;
    
    /** Odkaz na executora - vykonávací třída, zajištuje herní běh */
    private Executor executor;

    /** Odkaz na herní správce ukládání */
    private SerializeManager serializeManager;
    
    /** Odkaz na inventář */
    private Inventory inventory;
    
    /** Pomocná proměná, které kontroluje, zda-li již hra byla inicializována, 
     * pokud se snažím zavolat metodu, který inicializaci požaduje */
    private boolean initialized = false;
    
    
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda vrátí odkaz na jedináčka.
     *
     * @return odkaz na jedináčka
     */
    public static Game getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Privátní konstruktor zabraňující vytvoření instance
     */
    private Game() {/* ... */}


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí odkaz na aktuální lokaci
     * 
     * @return 
     */
    public Area getActualArea(){
       return Area.getActualArea();
    }
    
    /***************************************************************************
     * Nastaví aktuální lokaci dle jejího názvu.
     * 
     * @param areaName název lokace
     * @return false pokud lokace neexistuje a nejde tudíž nastavit.
     */
    public boolean setActualArea(String areaName){
        return Area.setActualArea(Area.valueOf(areaName));
    }
    
    /***************************************************************************
     * Vrátí správce místností, který má přehled o jednotlivých místnostech a 
     * jejich stavech.
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     * 
     * @return Správce místností
     */
    public RoomManager getRoomManager() {
        if(!initialized){
            initialize();
        }
        return roomManager;
    }

    
    /***************************************************************************
     * Vrátí správce objektů. Třída která má u sebe uložené všechny herní 
     * objekty {@link AGameObject}. Slouží k rychlú úprave atributí jejich
     * instancí, jinak se s nimi manimupule v rámci jednotlivých místností.
     * 
     * Vhodné pro získání odkazu na instanci nezávisle na mísnosti nebo rychlé 
     * úpravě objektu mimo aktuální mísnost. Toho typicky využívají herní akce 
     * {@link ActionList}
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     * 
     * @return Správce objektů
     */
    public GameObjectManager getGameObjectManager() {
        if(!initialized){
            initialize();
        }
        return gameObjectManager;
    }
    
    
    /***************************************************************************
     * Vrátí konfigurační hodnotu na základě jejího názvu v parametru.
     * 
     * @param propertyName název konfigurační vlastnosti
     * @return hodnotu konfigurační vlastnoti
     */
    public String getProperty(String propertyName) {
        return stateManager.getProperty(propertyName);
    }
    
    
    /***************************************************************************
     * Nastaví novou konfigurační vlastnost, případně přepíše existující hodnotu.
     * 
     * @param key název konfigurační vlastnosti
     * @param value konfigurační hodnota
     */
    public void setProperty(String key, String value){
        stateManager.setProperty(key, value);
    }
    
    /***************************************************************************
     * Vrátí Executora. Executor, je hlavní reakční třída, která vykonává a řídí
     * běh hry. Zpracovává dotek, na základě kterého následně spustí adekvátní 
     * příkaz.
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     * 
     * @return Executor.
     */
    public Executor getExecutor() {
        if(!initialized){
            initialize();
        }
        return executor;
    }
    
    /***************************************************************************
     * Vrátí odkaz na Správce "serializace". Hra se přímo neserializuje v pravém
     * slovasmyslu, ale místo toho se ukládá do souborů jako JSON objekty.
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     * 
     * @return SerializeManager
     */
    public SerializeManager getSerializeManager(){
        if(!initialized){
            initialize();
        }
        return serializeManager;
    }
    
    
    /***************************************************************************
     * Vrátí odkaz na inventář. Třída inventáře je zároveň jeho správcem a 
     * stará se o jeho obsah.
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     * 
     * @return odkaz na inventář
     */
    public Inventory getInventory() {
        if(!initialized){
            initialize();
        }
        return inventory;
    }
    
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Metoda zinicializuje konfiguraci, herní objetky a všechny místnosti 
     * aktuální nastavené lokace. Tuto metodu je nutné zavolat před začátkem 
     * samostné hry. Pokud to nebude udělané explicitně, stejně ji zavolají 
     * ostatní metody třídy.
     * 
     * @return Úspěšnost inicializace true/false
     */
    @Override
    public boolean initialize()
    {
        //Zabrání opětovné inicializaci
        if(initialized){
            return true;
        }
        
        /* Pomocná kontrolní proměnná. Zbaraňuje volání ostatních příkazu, dokud
         * se nespustí tato metoda. Současně se ale nemůže změnit až na konci 
         * metody, protože by nefunugovaly inicializace podobjektů.
         * 
         * Přepokládáme, že doběhne v pořádku.
         */
        initialized = true;
        
        /* Inicializuje ostatní třídy, které potřebují již existující instaci 
         * game, nutné v tomto pořadí! */
        roomManager = new RoomManager();
        gameObjectManager = new GameObjectManager();
        serializeManager = new SerializeManager();
        stateManager = new StateManager();
        executor = new Executor();
        inventory = new Inventory();
        
        
        // inicializuje konfiguraci dle konfiguračního souboru
        if(!serializeManager.initialize()){
                        
            // Zruší všechny odkazy na instance
            initialized = false;
            roomManager = null;
            gameObjectManager = null;
            stateManager = null;
            executor = null;
            serializeManager = null;
            inventory = null;
        }
        
        return initialized;
    }
    
    
    /***************************************************************************
     * Uloží aktuální stav hry do souborů pro uložení.
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     */
    @Override
    public boolean save() {
        if(!initialized){
            if(!initialize()){
                return false;
            }
        }
        
        if(!serializeManager.save()){
            return false;
        }
        return true;
    }

    
    /***************************************************************************
     * Zpracuje souřadnice dotyku na displayi. Deleguje na executora 
     * {@link Executor}, který zajistí další potřebné volání
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     *
     * @param point souřadnice dotyku
     * @return úspěšnost vykonání příkazu true = byl nalezen předmět a příkaz 
     * se vykonal.
     */
    @Override
    public boolean processTouch(Point point)
    {
        if(!initialized){
            initialize();
        }
        return executor.processTouch(point);
    }
    
    
    /***************************************************************************
     * Přihlásí posluchače k vysílačí zpráv.
     *
     * @param listener posluchač, který má být přihlášen
     */
    @Override
    public void addListener(IListener listener)
    {
        BROADCASTER.addListener(listener);
    }


    /***************************************************************************
     * Odhlásí posluchače od vysílače
     *
     * @param listener posluchač, který má být odstraněn
     */
    @Override
    public void removeListener(IListener listener)
    {
        BROADCASTER.removeListener(listener);
    }


    /***************************************************************************
     * Informuje posluchače o změně
     */
    @Override
    public void notifyListeners()
    {
        BROADCASTER.notifyListeners();
    }
    
    
    /***************************************************************************
     * Továrna, která vytvoří novou místnost a pridá jí do mapy místností a
     * mapy lokace.
     * 
     * @param key klíč, pod kterým se mísnost uloží v hashmapě
     * @param name název místnosti
     * @param description popis místnosti
     * @param areaName název lokace místnosti
     * @param defaultPlayerPosition Výchozí pozice hráče po vstupu do místnosti
     * @param current ukazatel, zda má tato místnost být aktuální
     * @return úspěšnost vytvoření
     */
    public boolean createRoom(String key, String name, String description, String areaName,
                              Point defaultPlayerPosition, boolean current){
        
        Room newRoom = roomManager.createRoom(key, name, description, Area.valueOf(areaName),
                                              defaultPlayerPosition);
                
        //nastaví výchozí místnost
        if(current){
            roomManager.setCurrentRoom(newRoom);
        }
        
        return true;
    }
    
    
    /***************************************************************************
     * Přidá do kolekce objektů v místnosti podle parametru objekt v parametru.
     *
     * @param roomId Identifikátor místnosti
     * @param gameObject herní objekt, který se má přidat
     * @return úspěšnost vložení -> true/false
     */
    public boolean addObjectToRoom(String roomId, AGameObject gameObject){
        Room room = roomManager.getRoom(roomId);
        return room.addObjectToRoom(gameObject);
    }
    
    
    /***************************************************************************
     * Přiřadí k objektu nějaký závislostní vztah definovaný třídou {@link ActionList}
     * 
     * @param itemId Identifikátor prvku, kterému náleží závislost
     * @param triggerId Identifikátor prvku, který je aktivátorem závislostní akce
     * @param actionName Závislostní akce
     * @param targetId Identifikátor předmětu, na který se má aplikovat uvedená akce
     * @return úspěšnost přidání
     * @throws GameControlException Pokud definovaná akce neexistuje.
     */
    public boolean addInteractAction(String itemId, String triggerId, String actionName, String targetId) throws GameControlException{
        IInteractable interactableObject = (IInteractable) gameObjectManager.getGameObject(itemId);
        interactableObject.addInteractAction(triggerId, actionName, targetId);
        
        return true;
    }
    
    
    /***************************************************************************
     * Vytvoří instanci herního objektu na základě jeho typu definovaho parametrem.
     * Následně ji přidá do definované místnosti.
     * 
     * @param type Typ herního objektu.
     * @param roomId Identifikátor mísnosti, kam se má objekt přidat
     * @param key Identifikátor objektu
     * @param name Název objektu
     * @param description Popis objektu
     * @param priority Priorita zobrazení více info ve tříde {@link Placement}
     * @param position Pozice zobrazení více info ve tříde {@link Placement}
     * @param params Doplňují parametry daného datového typu.
     * @return úspěšnost vytvoření
     */
    public boolean createGameObject(String type, String roomId, String key, String name, 
                                    String[] description, int priority, Point position,
                                    Object ... params){
              
        AGameObject gameObject = gameObjectManager.createObject(type, key, name, description, priority, position, params);
        
        
        if(!roomId.equals("inventory")){
            return addObjectToRoom(roomId, gameObject);
        }
        else{
            return inventory.addItem((Item) gameObject);
        }
    }

    
    /**************************************************************************
     * 
     * 
     * @return 
     */
    public JSONObject toJSON() throws JSONException{
       return getActualArea().toJSON().put("inventory", inventory.toJSON());
    }
    
//== TEMPORARY HELP METHODS ====================================================
    
    /***************************************************************************
     * Dočasná metoda, která "otevírá" inventář, pro zjednodušení do doby, než 
     * bude udělané UI
     * 
     * @todo FUTURE - domyslet při dělání UI
     */
    public void openInventory(){
        inventory.setActive(true);
        System.out.println(">>> OPEN INVENTORY");
    }
    
    
    /***************************************************************************
     * Dočasná metoda, která "zavírá" inventář, pro zjednodušení do doby, než 
     * bude udělané UI
     * 
     * @todo FUTURE - domyslet při dělání UI
     */
    public void closeInventory(){
        inventory.setActive(false);
        System.out.println(">>> CLOSE INVENTORY");
    }
    
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        Game inst = new Game();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
