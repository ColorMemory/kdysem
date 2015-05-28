/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;


import cz.colormemory.json.JSONArray;
import cz.colormemory.json.JSONException;
import cz.colormemory.json.JSONObject;
import static cz.colormemory.kdysem.data.TextConstants.AREA_DOES_NOT_EXIST;
import static cz.colormemory.kdysem.data.TextConstants.AREA_FILE_IO_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.AREA_FILE_WRONG_SYNTAX;
import static cz.colormemory.kdysem.data.TextConstants.AREA_JSON_IS_NULL;
import static cz.colormemory.kdysem.data.TextConstants.CONF_FILE_IO_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.CONF_FILE_NOT_FOUND;
import static cz.colormemory.kdysem.data.TextConstants.CONF_FILE_WRONG_SYNTAX;
import static cz.colormemory.kdysem.data.TextConstants.INSTANCES_CREATING_SYNTAX_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.SAVE_FILE_CREATE_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.SAVE_FILE_WRONG_SYNTAX;
import cz.colormemory.kdysem.game.commands.ActionList;
import cz.colormemory.kdysem.game.entities.Area;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.entities.Person;
import cz.colormemory.kdysem.game.entities.Placement;
import cz.colormemory.kdysem.game.entities.Room;
import cz.colormemory.kdysem.game.entities.Transporter;
import cz.colormemory.kdysem.game.exceptions.GameControlException;
import cz.colormemory.kdysem.game.exceptions.GameInitializeException;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



//@todo výhledově je nutné tuhle třídu pročistit, asi rozdělit do více samostaných tříd, včetně nějaké file\Managaeru a celkově trochu víc domysle, momentálně to strukturální fungje, ale je to příšerný guláš a je to celé poměrně hnusné.


/*******************************************************************************
 * Třída {@code SerializeManager} představuje správce pro ukládání a 
 * incicialozování hry
 * Zajistí správné vytvoření všech objektů a funkčností.
 *
 * @author  André HELLER
 * @version 1.00 — 05/2015
 */
public class SerializeManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na správce místností */
    private final RoomManager ROOM_MANAGER;
    
    /** Odkaz na správce objektů */
    private final GameObjectManager GAME_OBJECT_MANAGER;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Implicitní konstruktor
     */
    public SerializeManager()
    {
        ROOM_MANAGER = Game.getInstance().getRoomManager();
        GAME_OBJECT_MANAGER = Game.getInstance().getGameObjectManager();
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlavní inicicalizační metoda. Vytvoří základní místnosti hry i s obsahem.
     * 
     * @throws cz.colormemory.kdysem.game.exceptions.GameInitializeException
     */
    public void initialize() throws GameInitializeException 
    {
        Game game = Game.getInstance();
        StateManager sm = game.getStateManager();
        Inventory inv = game.getInventory();
        
        //inicializuje herní konfiguraci natvrdo přímo v javě
        initializeConfigData(sm);
        
//        HardConstructor hard = new HardConstructor();

        //inicializuje herní instance pro aktuální lokaci
        initializeRooms(sm.getProperty("folder.config") + File.separator + "init",
                        sm.getProperty("actualArea"));
        
        // Inicialuzuje objekty v inventáři
        initializeInventoryObjects(sm.getProperty("folder.config") + File.separator + "init", inv);
        
    }
    
    
    /***************************************************************************
     * Uloží aktuální hru
     * 
     * @throws cz.colormemory.kdysem.game.exceptions.GameControlException
     */
    public void save() throws GameControlException{
        /* @todo Save inventory - nebere vpotaz inventář, vůbec ho neukládá a 
         * rovněž neukládá konfiguraci*/
            
        Game game = Game.getInstance();
        StateManager sm = game.getStateManager();
        Area actualArea = Area.getActualArea();
        
        String saveFolderPath = sm.getProperty("folder.config") + File.separator + sm.getProperty("folder.save");
        
        saveRooms(saveFolderPath, actualArea);
        saveInventory(saveFolderPath, game);
        
        
    }
    
    
    /***************************************************************************
     * Inicializuje objekty hry z uložených souborů pro aktuální lokaci
     * @throws cz.colormemory.kdysem.game.exceptions.GameInitializeException
     */
    public void load() throws GameInitializeException{
        StateManager sm = Game.getInstance().getStateManager();
        load(sm.getProperty("actualArea"));
    }
    
    
    /***************************************************************************
     * Inicializuje objekty hry z uložených souborů pro zadanou lokaci.
     * @param areaName
     * @throws cz.colormemory.kdysem.game.exceptions.GameInitializeException
     */
    public void load(String areaName) throws GameInitializeException{
        StateManager sm = Game.getInstance().getStateManager();
        Inventory inv = Game.getInstance().getInventory();
        
        initializeRooms(sm.getProperty("folder.config") + File.separator + "saves",
                    areaName);
        
        initializeInventoryObjects(sm.getProperty("folder.config") + File.separator + "saves", inv);
    }

    
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Načte konfigurační soubor a všechnu jeho konfiguraci přehraje do 
     * properties State Manageru.
     * 
     * @param sm State manageru, kterému přehraje properties
     * @throws GameInitializeException Pokud nebyl nalezen konfigurační soubor
     */
    private void initializeConfigData(StateManager sm) throws GameInitializeException {
        // Otevře konfigurační soubor
        File configFile = new File(sm.getProperty("folder.config"),"game.config");
        
        //pokud neexistuje vyhodí vyjímku
        if(!configFile.exists()){
            throw new GameInitializeException(CONF_FILE_NOT_FOUND);
        }
        
        try {
            JSONObject config = new JSONObject(readAllLines(configFile));
            
            /* Přečte konfigurační soubor a všechnu konfiguraci nahraje jako 
             * jednotlivé property do StateManageru */
            for (int i = 0; i < config.names().length(); i++) {
                String key = config.names().get(i).toString();
                sm.setProperty(key,config.get(key).toString());
            }
            
        } catch (JSONException ex) {
            throw new GameInitializeException(CONF_FILE_WRONG_SYNTAX + ex.getMessage(),ex);
        } catch (IOException ex) {
            throw new GameInitializeException(CONF_FILE_IO_ERROR + ex.getMessage(), ex);
        }
    }
    
    
    /***************************************************************************
     * Znicicializuje místnosti aktuální lokace
     * 
     * @param sm StateManager, ze kterého tahá některé properties
     * @throws GameInitializeException Při fatálním inicializačním problému
     */
    private void initializeRooms(String initFolderPath, String areaName) throws GameInitializeException {
       
        // Nastaví zadanou lokaci jako aktuální
        for (Area area : Area.values()) {
            if (area.getName().equals(areaName)) {
                Area.setActualArea(area);
                break;
            }
        }
        
        Area actualArea = Area.getActualArea();

        // pokud neexistuje vyhodí vyjímku
        if(actualArea == null){
            throw new GameInitializeException(String.format(AREA_DOES_NOT_EXIST, initFolderPath));
        }
        
        
        /* ==================================================================
        * Načte kofigurační soubory s lokací a příslušnými objekty lokace
        * ================================================================== */
        
        //Načte konfgiurace aktuální lokace (config/areas/???.area) a převede na JSONObject
        File areaFile = new File(initFolderPath + File.separator +
                actualArea.getName() + File.separator +
                actualArea.getName() + ".rooms");
        
        //Načte herní objekty aktuální lokace (config/gameObjects/???.objects) a převede na JSONObject
        File gameObjectsFile = new File(initFolderPath + File.separator +
                actualArea.getName() + File.separator +
                actualArea.getName() + ".objects");
        
        // Obsah načte do JSONObjectů
        JSONObject areasJSON = null;
        JSONObject gameObjectsJSON = null; 
        try {
            
            areasJSON = new JSONObject(readAllLines(areaFile));
            gameObjectsJSON = new JSONObject(readAllLines(gameObjectsFile));
            
        } catch (IOException ex) {
            throw new GameInitializeException(String.format(AREA_FILE_IO_ERROR,actualArea) + ex.getMessage(),ex);
        } catch (JSONException ex) {
            throw new GameInitializeException(String.format(AREA_FILE_WRONG_SYNTAX,actualArea) + ex.getMessage(), ex);
        }
        
        
        if(areasJSON == null || gameObjectsJSON == null){
            throw new GameInitializeException(AREA_JSON_IS_NULL);
        }
        
        
        try {            
            // Projede všechny místnosti v lokaci
            for (int i = 0; i < areasJSON.names().length(); i++) {
                
                String key = areasJSON.names().get(i).toString();
                JSONObject room = areasJSON.getJSONObject(key);                
                boolean current = false;
                
                //Odstraní zavináč jako znak výchozí místností
                if(key.matches("^@(.*)")){
                    key = key.substring(1);
                    current = true;
                }
                
                //Vytvoří mísnost, ta se sama zařadí do kolekce správce místností
                Room newRoom = ROOM_MANAGER.createRoom(
                        key,
                        room.getString("name"),
                        room.getString("description"),
                        actualArea,
                        new Point(
                                (int)room.getJSONArray("playerPosition").get(0),
                                (int)room.getJSONArray("playerPosition").get(1)
                        )
                );
                
                //nastaví výchozí místnost
                if(current){
                    ROOM_MANAGER.setCurrentRoom(newRoom);
                }
                
                
                JSONObject roomObjects = gameObjectsJSON.getJSONObject(key);
                
                
                //Vytahá si ID Herních objektů v místnosti
                JSONArray itemIds = room.getJSONArray("items");
                JSONArray personIds = room.getJSONArray("persons");
                JSONArray transporterIds = room.getJSONArray("transporters");
                
                // Proiteruje itemy a vytvoří jejich instance
                for(int y=0; y < itemIds.length(); y++){
                    JSONObject itemJSON = roomObjects.getJSONObject(itemIds.getString(y));
                    
                    Item item = GAME_OBJECT_MANAGER.createItem(
                            itemIds.getString(y),
                            itemJSON.getString("name"),
                            createDescriptions(itemJSON),
                            createPlacement(itemJSON),
                            itemJSON.getBoolean("pickability"),
                            itemJSON.getBoolean("usability"),
                            itemJSON.getBoolean("interactability")
                    );
                    
                    newRoom.addObjectToRoom(item);
                    
                    JSONObject actions = itemJSON.getJSONObject("actions");
                    
                    if(actions.names() == null){
                        continue;
                    }
                    
                    for(int z = 0; z < actions.names().length(); z++){
                        String triggerId = actions.names().getString(z);
                        JSONObject actionPair = actions.getJSONObject(triggerId);
                        
                        for(int a = 0; a < actionPair.names().length();a++){
                            String actionName = actionPair.names().getString(a);
                            String targetId = actionPair.getString(actionName);
                            
                            item.addInteractAction(triggerId, ActionList.getAction(actionName), targetId);
                        }
                        
                    }
                }
                
                // Proiteruje Osoby a vytvoří jejich instance
                for(int y=0; y < personIds.length(); y++){
                    JSONObject personJSON = roomObjects.getJSONObject(personIds.getString(y));
                    
                    //Zatím požaduje Dialog, doplnit až vlastně budeme vědět co to bude.
                    Person person = GAME_OBJECT_MANAGER.createPerson(
                            personIds.getString(y),
                            personJSON.getString("name"),
                            createDescriptions(personJSON),
                            createPlacement(personJSON),
                            null);
                    
                    newRoom.addObjectToRoom(person);
                }
                
                // Proiteruje Transportery a vytvoří jejich instance
                for(int y=0; y < transporterIds.length(); y++){
                    JSONObject transporterJSON = roomObjects.getJSONObject(transporterIds.getString(y));
                            
                    Transporter transporter = GAME_OBJECT_MANAGER.createTransporter(
                            transporterIds.getString(y),
                            transporterJSON.getString("name"),
                            createDescriptions(transporterJSON),
                            createPlacement(transporterJSON),
                            transporterJSON.getString("target"),
                            transporterJSON.getBoolean("locked")
                    );
                    
                    newRoom.addObjectToRoom(transporter);
                }
            }
        } catch (JSONException ex) {
            throw new GameInitializeException(INSTANCES_CREATING_SYNTAX_ERROR + ex.getMessage());
        } 
    }
    
    
    /***************************************************************************
     * Inicializuje  herní objekty v inventáři
     * 
     * @param game hra
     * @param initFolderPath cesta inicializační složky
     * @throws NumberFormatException 
     * @throws GameInitializeException 
     */
    private void initializeInventoryObjects(String initFolderPath, Inventory inv) throws GameInitializeException {
               
        //Načte inicializační soubor invetáře
        File inventoryFile = new File(initFolderPath + File.separator +
                "inventory.objects");
        
        
        // Obsah načte do JSONObjectů
        JSONObject inventoryJSON = null;
        try {
            
            inventoryJSON = new JSONObject(readAllLines(inventoryFile));
            
        } catch (IOException ex) {
            throw new GameInitializeException(String.format(AREA_FILE_IO_ERROR,"Inventář") + ex.getMessage(),ex);
        } catch (JSONException ex) {
            throw new GameInitializeException(String.format(AREA_FILE_WRONG_SYNTAX,"Inventář") + ex.getMessage(), ex);
        }
        
        
        if(inventoryJSON == null){
            throw new GameInitializeException(AREA_JSON_IS_NULL);
        }
        
        try {
            for (int i = 0; i < inventoryJSON.names().length(); i++) {            
                String itemId = inventoryJSON.names().get(i).toString();
                JSONObject itemJSON = inventoryJSON.getJSONObject(itemId);
                
                inv.addItem(new Item(
                        itemId,
                        itemJSON.getString("name"),
                        createDescriptions(itemJSON),
                        createPlacement(itemJSON),
                        itemJSON.getBoolean("pickability"),
                        itemJSON.getBoolean("usability"),
                        itemJSON.getBoolean("interactability")
                ));
            }
        
        } catch (JSONException ex) {
            throw new GameInitializeException("ssss");
        }
    }

    
    /***************************************************************************
     * Uloží aktuální lokaci do souborů.
     */
    private void saveRooms(String saveFolderPath, Area actualArea) throws GameControlException {
        File areaSaveFolder = new File(saveFolderPath + File.separator + actualArea.getName());
        if(!areaSaveFolder.exists()){
            areaSaveFolder.mkdirs();
        }
        
        BufferedWriter out = null;
        
        try {
            File areaFile = new File(areaSaveFolder,actualArea.getName() + ".rooms");
            if(!areaFile.exists()){
                areaFile.createNewFile();
            }

            
            File gameObjectsFile = new File(areaSaveFolder,actualArea.getName() + ".objects");
            if(!gameObjectsFile.exists()){
                gameObjectsFile.createNewFile();
            }
            
        
            //Vytvoří writer pro soubor místností
            out = new BufferedWriter(new FileWriter(areaFile));
            
            out.write(actualArea.toJSON().getJSONObject("rooms").toString(2));
//            actualArea.toJSON().getJSONObject("rooms").write(out);
            
            out.flush();
            
            //Vytvoří writer pro soubor herních objektů
            out = new BufferedWriter(new FileWriter(gameObjectsFile));
            
            out.write(actualArea.toJSON().getJSONObject("objects").toString(2));
//            actualArea.toJSON().getJSONObject("objects").write(out);
            
            
        } catch (IOException ex) {
            throw new GameControlException(SAVE_FILE_CREATE_ERROR + ex.getMessage(), ex);
        } catch (JSONException ex) {
            throw new GameControlException(SAVE_FILE_WRONG_SYNTAX + ex.getMessage(), ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(SerializeManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /***************************************************************************
     * Uloží do souboru inventář
     * 
     * @param saveFolderPath cesta k saves složce
     * @param game hra
     * @throws GameControlException 
     */
    private void saveInventory(String saveFolderPath, Game game) throws GameControlException {
        Inventory inv = game.getInventory();
        
        File saveFolder = new File(saveFolderPath);
        if(!saveFolder.exists()){
            saveFolder.mkdirs();
        }
        
        BufferedWriter out = null;  
        
        try {
            //Uloží konfiguraci inventáře
            File inventoryFile = new File(saveFolder,"inventory.objects");
            if(!inventoryFile.exists()){
                inventoryFile.createNewFile();
            }
            
            
            //Vytvoří writer pro soubor inventáře
            out = new BufferedWriter(new FileWriter(inventoryFile));

            out.write(inv.toJSON().toString(2));

            out.close();
        
        } catch (IOException ex) {
            throw new GameControlException(SAVE_FILE_CREATE_ERROR + ex.getMessage(), ex);
        } catch (JSONException ex) {
            throw new GameControlException(SAVE_FILE_WRONG_SYNTAX + ex.getMessage(), ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(SerializeManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /***************************************************************************
     * Dostane BurreferedReader, který už v sobě má naštený soubor a proiteruje 
     * všechny jeho řádky, které pomocí String Builderu narve do stringu, 
     * který vrátí.
     * 
     * @param reader BufferedReader obsahující Filereader s požadovaným souborem.
     * @return String celého souboru.
     * @throws IOException pokud nemůže přečíst řádek.
     */
    private String readAllLines(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        StringBuilder sb =  new StringBuilder();
        
        // Proiteruje soubor a vytvoří z něj dlouhý string
        while(true){
            String line = in.readLine();
            
            if(line == null){
                break;
            }
            
            sb.append(line).append('\n');
        }
        
        String result = sb.toString();
        
        /* Z nějakého důvodu, je občas prvním znakem nějaký bordel, zatím 
         * nevím proč. Každopádně, pokud první znak není počáteční JSON "{", 
         * zjistí kde je a string ořízne */
        if(!result.startsWith("{")){
           result = result.substring(result.indexOf("{"));
        }
        
        return result;
    }
    
    
    /***************************************************************************
     * Ze zadaného jasonu vytahá hodnoty pro Placement, který níásledně vytvoří a vrátí
     * 
     * @param transporterJSON JSON s herním objektem
     * @return Vytvoření Placement
     * @throws JSONException Při JSON chybě
     * @throws NumberFormatException Při posraném přetypování
     */
    private Placement createPlacement(JSONObject gameObejctJSON) throws JSONException, NumberFormatException {
        Placement placement =
                new Placement(
                        gameObejctJSON.getInt("priority"),
                        new Point(Integer.parseInt(gameObejctJSON.getJSONArray("position").getString(0)),
                                  Integer.parseInt(gameObejctJSON.getJSONArray("position").getString(1))
                        )
                );
        return placement;
    }
    
    
    /***************************************************************************
     * Ze zadaného JSON objektu vytáhá description a převede na stringové pole
     * 
     * @param gameobjectJSON JSON herního objektu
     * @return Stringové pole s uloženými popisky
     * @throws JSONException Pokud bude v JSON chyba
     */
    private String[] createDescriptions(JSONObject gameObjectJSON) throws JSONException {
        JSONArray descriptionJSONArray = gameObjectJSON.getJSONArray("description");
        String[] description = new String[descriptionJSONArray.length()];
        for(int z = 0; z < descriptionJSONArray.length(); z++){
            description[z] = descriptionJSONArray.getString(z);
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
//        SerializeManager inst = new SerializeManager();
//        
//            inst.initialize();
//        
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
