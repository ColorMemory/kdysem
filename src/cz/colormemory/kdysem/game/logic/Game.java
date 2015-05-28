/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.framework.IGame;
import cz.colormemory.kdysem.framework.IListener;
import cz.colormemory.kdysem.game.exceptions.GameControlException;
import cz.colormemory.kdysem.game.exceptions.GameInitializeException;
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
     * Vrátí správce herních stavů. Třída, je de facto konfigurací celé hry. 
     * Je potomkem třídy Properties a uchovává veškeré nastavení hry.
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     * 
     * @return Správce herních stavů
     */
    public StateManager getStateManager() {
        if(!initialized){
            initialize();
        }
        return stateManager;
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
        stateManager = new StateManager();
        executor = new Executor();
        serializeManager = new SerializeManager();
        inventory = new Inventory();
        
        
        try {
            // inicializuje konfiguraci dle konfiguračního souboru
            serializeManager.initialize();
        } catch (GameInitializeException ex) {
            /* Pokud se při inicializaci stane chyba vyhodí inicializační vyjímku
             * @todo Nutno ještě nějak doošetřit, zatím prostě vypisuje do konzole
             */
            ex.printStackTrace();
            
            // Pomocnou proměnnou zase zruší
            initialized = false;
        }
        
        return initialized;
    }
    
    
    /***************************************************************************
     * Uloží aktuální stav hry do souborů pro uložení.
     * 
     * Pokud hra ještě nebyla zinicializována, tak ji zinicializuje.
     */
    @Override
    public void save() {
        if(!initialized){
            initialize();
        }
        
        try {
            serializeManager.save();
        } catch (GameControlException ex) {
            // V případě problému vypíše výjimku
            //@todo Dodělt výjimky alespoň v inicializaci zatím.
            ex.printStackTrace();
        }
    }
    
    
    /***************************************************************************
     * Naloaduje hru z uložených souborů.
     * 
     * @todo FUTURE zatím není dotažené.
     */
    public void load(){
        if(!initialized){
            initialize();
        }
//        try {
//            serializeManager.load();
//        } catch (GameInitializeException ex) {
//            System.out.println("==============================================="
//                    + "\nException: " + ex.getMessage());
//            ex.printStackTrace();
//        }
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

    
//== TEMPORARY HELP METHODS ====================================================
    
    /***************************************************************************
     * Dočasná metoda, která "otevírá" inventář, pro zjednodušení do doby, než 
     * bude udělané UI
     * 
     * @todo FUTURE - domyslet při dělání UI
     */
    public void openInventory(){
        inventory.setActive(true);
    }
    
    
    /***************************************************************************
     * Dočasná metoda, která "zavírá" inventář, pro zjednodušení do doby, než 
     * bude udělané UI
     * 
     * @todo FUTURE - domyslet při dělání UI
     */
    public void closeInventory(){
        inventory.setActive(false);
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
