/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.serialize;

import cz.colormemory.json.JSONArray;
import cz.colormemory.json.JSONException;
import cz.colormemory.json.JSONObject;
import static cz.colormemory.kdysem.data.TextConstants.ACTIONS_CREATING_SYNTAX_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.ACTIONS_DOES_NOT_EXIST;
import static cz.colormemory.kdysem.data.TextConstants.AREA_CANT_CREATE;
import static cz.colormemory.kdysem.data.TextConstants.CONFIG_CREATING_SYNTAX_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.GAMEOBJECT_CANT_ADD;
import static cz.colormemory.kdysem.data.TextConstants.INSTANCES_CREATING_SYNTAX_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.ROOM_CANT_CREATE;
import cz.colormemory.kdysem.game.exceptions.GameControlException;
import cz.colormemory.kdysem.game.exceptions.GameInitializeException;
import cz.colormemory.kdysem.game.logic.Game;
import cz.colormemory.kdysem.game.logic.HardConstructor;
import java.awt.Point;
import java.io.File;

/*******************************************************************************
 * Instance of class {@code InitializeManager} represents 
 * 
 * @author Avatar
 * @version 1.00 - 05/15
 */
public class InitializeManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /** Odkaz na hru */
    private final Game GAME;
    
    private final FileManager FM;
    
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================


    /***************************************************************************
     * Implicitní konstruktor
     */
    public InitializeManager(Game game, FileManager fm){
        this.GAME = game;
        this.FM = fm;
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
    
    /***************************************************************************
     * Neinicializuje pomocí soiuborů, ale natvrdo příámo z třídy {@link HardConstructor}. 
     * Používá se pro testování.
     * 
     * @return Úspěšnost inicializace. True pouze v případě, že se vše inicializovalo správně
     * @throws GameInitializeException Při jakémkoliv problému s inicializací. Více info ve zprávě výjimky.
     */
    public boolean initializeHard() throws GameInitializeException{
        String configFolder = GAME.getProperty("folder.config");
        
        //Inicialoizuje herní konfiguraci ze souboru
        initializeConfigData(configFolder);
        
        new HardConstructor();
        
        return true;
    }
    
    /***************************************************************************
     * Hlavní inicicalizační metoda. Vytvoří herní konfiguraci, základní místnosti 
     * hry i s obsahem, obsah inventáře a vzájemné propojení jednoltivých herních 
     * objektů (Akce)
     * 
     * @return Úspěšnost inicializace. True pouze v případě, že se vše inicializovalo správně
     * @throws GameInitializeException Při jakémkoliv problému s inicializací. Více info ve zprávě výjimky.
     */
    public boolean initialize() throws GameInitializeException 
    {
        //Jediná předem uložená vlastnost v konfiguraci
        String configFolder = GAME.getProperty("folder.config");
        
        //Inicializuje herní konfiguraci ze souboru
        initializeConfigData(configFolder);
        
        //inicializuje herní objekty v mísnostech aktuální lokace
        initializeRooms(configFolder,
                        GAME.getProperty("actualArea"));
        
        // Inicialuzuje objekty v inventáři
        initializeInventoryObjects(configFolder);
        
        // Inicializuje závoslostní akce jednotlivých objektů
        initializeObjectActions(configFolder,
                        GAME.getProperty("actualArea"));
 
        return true;
    }
    
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Načte konfigurační soubor a všechnu jeho konfiguraci přehraje herní konfigurace.
     * 
     * @param configFolderPath Cesta ke konfiguračnímu souboru.
     * @throws GameInitializeException V případě, kdy nebyl soubor nalezen,
     * nepodařil přečíst nebo obsahoval nevalidní JSON a nešel přeparsovat.
     * @return Úspěšnost inicializace.
     */
    private boolean initializeConfigData(String configFolderPath) throws GameInitializeException {
        JSONObject config = FM.read(configFolderPath,"game.config");

        /* Proiteruje konfigurační JSON a všechnu konfiguraci nastaví samostné hře */
        for (int i = 0; i < config.names().length(); i++) {
            try {
                String key = config.names().get(i).toString();
                GAME.setProperty(key,config.get(key).toString());
                 
            } catch (JSONException ex) {
                throw new GameInitializeException(CONFIG_CREATING_SYNTAX_ERROR + ex.getMessage(),ex);
            }
        }      
        
        return true;
    }
    
    
    /***************************************************************************
     * Znicicializuje místnosti aktuální lokace a všechny předměty v nich
     * 
     * @param configFolderPath Cesta ke konfiguračnímu souboru.
     * @param areaName Název aktuální lokace.
     * @return Úspěšnost incializace true poue v případě, kdy se skutečně správně inicializovaly-
     * @throws GameInitializeException Při fatálním inicializačním problému.
     */
    private boolean initializeRooms(String configFolderPath, String areaName) throws GameInitializeException {
 
        if(!GAME.setActualArea(areaName)){
            throw new GameInitializeException(String.format(AREA_CANT_CREATE,areaName));
        }
        
        // Obsah načte do JSONObjectů
        JSONObject roomsJSON = FM.read(configFolderPath + File.separator + "init" + File.separator + areaName, areaName + ".rooms");
        JSONObject gameObjectsJSON = FM.read(configFolderPath + File.separator + "init" + File.separator + areaName, areaName + ".objects");

        try {            
            // Projede všechny místnosti v lokaci
            for (int i = 0; i < roomsJSON.names().length(); i++) {
                String roomId = roomsJSON.names().get(i).toString();
                JSONObject room = roomsJSON.getJSONObject(roomId);   
                
                
                /* Vytvoří místnost. V případě že má být aktuální to nastaví a 
                 * očistí její ID od označovacího prefixu, který vrátí */
                roomId = createRoom(roomId, room, areaName);
                
                //Podle klíče mísnosti si vytáhne i JSON všech herních objektů v mísnosti z druhého souboru
                JSONObject roomObjects = gameObjectsJSON.getJSONObject(roomId);
                
                // vytvoří instance všech obejktů, které přidá do mísnosti
                createObjects(roomId, room, roomObjects); 
            }
        } catch (JSONException ex) {
            throw new GameInitializeException(INSTANCES_CREATING_SYNTAX_ERROR + ex.getMessage(),ex);
        }
        
        return true;
    }
    
    
    /***************************************************************************
     * Podle zadaného JSONu vytvoří mísnost, včetna nastavení aktuálnosti.
     * 
     * @param roomId Identifikátor místnosti, používá se mimo jiné k označení, 
     * zda je místnost aktuální. pokud ano je uvozená @
     * @param room JSONObject s informacemi o místnosti.
     * @param areaName Název aktuální lokace
     * @return Očištěné roomId, pokud obsahovalo @ jako ukazatel aktuální místnosti.
     * @throws JSONException Při chybě čtení JSONu
     * @throws GameInitializeException Pokud se mísnost nepodařila vytvořit.
     */
    private String createRoom(String roomId, JSONObject room, String areaName) throws JSONException, GameInitializeException {
        
        boolean current = false;
        
        //Odstraní zavináč jako znak výchozí místností a nastaví pomocnou proměnnou
        if(roomId.matches("^@(.*)")){
            roomId = roomId.substring(1);
            current = true;
        }

        
        if(!GAME.createRoom(
                roomId,
                room.getString("name"),
                room.getString("description"),
                areaName,
                new Point(
                        (int)room.getJSONArray("playerPosition").get(0),
                        (int)room.getJSONArray("playerPosition").get(1)
                ),
                current
        )){
            throw new GameInitializeException(String.format(ROOM_CANT_CREATE,room.getString("name")));
        }
        
        return roomId;
    }

    
    /***************************************************************************
     * Podle zadaného JSONu vytvoří všechny herní objekty, které přiřadí do zadané mísnosti.
     * 
     * @param roomId ID mínosti
     * @param room JSON samostné místnosti, ze které získá ID herních objektů
     * @param gameObjectsJSON JSON objektů zadané mísnosti.
     * @throws JSONException Při chybě čtení JSONu
     * @throws GameInitializeException Pokud se objekt nepodařil vytvořit.
     */
    private void createObjects(String roomId, JSONObject room, JSONObject roomObjects) throws JSONException, GameInitializeException {

        //Vytahá si ID Herních objektů v místnosti
        JSONArray personIds = room.getJSONArray("persons");
        JSONArray transporterIds = room.getJSONArray("transporters");
        JSONArray itemIds = room.getJSONArray("items");
        
        
        // Proiteruje Osoby a vytvoří jejich instance
        createGameObject("person", personIds, roomObjects, roomId);
        
        // Proiteruje Transportery a vytvoří jejich instance
        createGameObject("transporter", transporterIds, roomObjects, roomId, "target", "locked");
        
        // Proiteruje itemy a vytvoří jejich instance
        createGameObject("item", itemIds, roomObjects, roomId, "pickability", "usability","interactability");
    }
    
    
    /***************************************************************************
     * Inicializuje herní objekty v inventáři
     * 
     * @param configFolderPath Cesta ke konfiguračnímu souboru.
     * @throws GameInitializeException Pokud se objekt nepodařil vytvořit.
     */
    private boolean initializeInventoryObjects(String configFolderPath) throws GameInitializeException {
        
        // Obsah načte do JSONObjectu
        JSONObject inventoryJSON = FM.read(configFolderPath + File.separator + "init", 
                                           "inventory.objects");
        
        try {
            // Proiteruje itemy a vytvoří jejich instance
            createGameObject("item", inventoryJSON.names(), inventoryJSON, "inventory", "pickability", "usability","interactability");
        
        } catch (JSONException ex) {
            throw new GameInitializeException(INSTANCES_CREATING_SYNTAX_ERROR + ex.getMessage(),ex);
        }
        
        return true;
    }
    
    
    /***************************************************************************
     * Proiteruje klíče získané ze zadaného JSON pole v zadaném JSON objektu. 
     * Následně z nich vytvoří instance herních objektů dle zadaného v parametru
     * type a rovnou přidá do zadané místnosti.
     * 
     * @param type Typ heního objektu (item, transporter, person)
     * @param itemIds JSONpole a klíči podle kterého se bude procházet JSON objekt, aby se nemusle iterovat celý
     * @param roomObjects Procházený JSON objekt, ve kterém jsou uložený informace o objektech
     * @param roomId ID mísnosti, kams e mají objekty přidat
     * @param params Volitelné parametry. Každý datový typ je má v jiném složení.
     * @throws JSONException Při chybě parsování objektů
     * @throws GameInitializeException Pokud se nepodaří vytvořit objekt
     */
    private void createGameObject(String type, JSONArray itemIds, JSONObject roomObjects, String roomId, String ... params) throws JSONException, GameInitializeException {
        
        // Proiteruje itemy a vytvoří jejich instance
        for(int y=0; y < itemIds.length(); y++){
            JSONObject itemJSON = roomObjects.getJSONObject(itemIds.getString(y));
            
            Object[] properties;
            
            /* Pokud není nastaven žádný parametr, musí stejně vytvořit pole s 
             * hodnotou null, v opačném případě vytvoří pole a nasype do něj 
             * hodnoty parametrů */
            if(params.length > 0){
                properties = new Object[params.length];
            
                for(int i = 0; i < params.length; i++){
                    properties[i] = itemJSON.get(params[i]);
                }
            }
            else {
                properties = new Object[1];
                properties[0] = null;
            }
            
            
            if(!GAME.createGameObject(
                    type,
                    roomId,
                    itemIds.getString(y),
                    itemJSON.getString("name"),
                    createDescriptions(itemJSON.getJSONArray("description")),
                    itemJSON.getInt("priority"),
                    new Point(
                            Integer.parseInt(itemJSON.getJSONArray("position").getString(0)),
                            Integer.parseInt(itemJSON.getJSONArray("position").getString(1))
                    ),
                    properties
            )){
                throw new GameInitializeException(String.format(GAMEOBJECT_CANT_ADD,roomId,itemJSON.getString("name")));
            }
        }
    }
    
    
    /***************************************************************************
     * Inicializuje závislosti mezi objekty (akce).
     * 
     * @param configFolderPath Cesta ke konfiguračnímu souboru.
     * @param areaName Název aktuální lokace.
     * @throws GameInitializeException Pokud se akce nepodařila přiřadit nebo přečíst
     */
    private void initializeObjectActions(String configFolderPath, String areaName) throws GameInitializeException{
        
        JSONObject actionItems = FM.read(configFolderPath + File.separator + "init" + File.separator + areaName, areaName+".actions");
        
        try {
            for(int i = 0; i < actionItems.names().length();i++){
                String itemId = actionItems.names().get(i).toString();
                JSONObject itemJSON = actionItems.getJSONObject(itemId);
                
                for(int z = 0; z < itemJSON.names().length(); z++){
                    String triggerId = itemJSON.names().getString(z);
                    JSONObject actionPair = itemJSON.getJSONObject(triggerId);
                    
                    for(int a = 0; a < actionPair.names().length();a++){
                        String actionName = actionPair.names().getString(a);
                        String targetId = actionPair.getString(actionName);
                        
                        GAME.addInteractAction(itemId, triggerId, actionName, targetId); 
                    }
                }            
            }
        } catch (JSONException ex) {
            throw new GameInitializeException(ACTIONS_CREATING_SYNTAX_ERROR + ex.getMessage(),ex);
        } catch (GameControlException ex) {
            throw new GameInitializeException(ACTIONS_DOES_NOT_EXIST + ex.getMessage(),ex);
        }
    }
    
    
    /***************************************************************************
     * Ze zadaného JSON pole vytvoří stringové pole
     * 
     * @param descriptionJSONArray JSON pole s uloženými popisky
     * @return Stringové pole s uloženými popisky
     * @throws JSONException Pokud bude v JSON chyba
     */
    private String[] createDescriptions(JSONArray descriptionJSONArray) throws JSONException {
        String[] description = new String[descriptionJSONArray.length()];
        
        for(int i = 0; i < descriptionJSONArray.length(); i++){
            description[i] = descriptionJSONArray.getString(i);
        }
        
        return description;
    }
    
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        InitializeManager inst = new InitializeManager();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




