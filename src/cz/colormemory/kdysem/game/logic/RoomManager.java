/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.entities.Room;
import cz.colormemory.kdysem.game.entities.Area;
import cz.colormemory.kdysem.game.entities.AGameObject;
import java.awt.Point;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;





/*******************************************************************************
 * Třída {@code RoomManager} je jedináček představující
 * správce herních místností {@link Room}.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class RoomManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final RoomManager SINGLETON = new RoomManager();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Kolekce, všech herních místností */
    private final Map<String,Room> ROOM_LIST = new HashMap<>();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Aktuální místnost ve hře */
    private Room currentRoom;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda, vrací odkaz na jedináčka.
     *
     * @return  odkaz na jedináčka
     */
    public static RoomManager getInstance(){
        return SINGLETON;
    }


    /***************************************************************************
     * Privátní konstruktor zabraňující vytvoření instancí.
     */
    private RoomManager(){/* ... */}


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí místnost na základě klíče
     * 
     * @param key klíč místnosti
     * @return místnost
     */
    public Room getRoom(String key){
        return  ROOM_LIST.get(key);
    }
    
    /***************************************************************************
     * Vrátí aktuální mistnost
     *
     * @return aktuální mistnost
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }


    /***************************************************************************
     * Nastaví aktuální místnost.
     *
     * @param room aktuální místnost
     */
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;
    }


    /***************************************************************************
     * Vytvoří novou místnost a pridá jí do kolekce místností.
     * 
     * 
     * @param key klíč, pod kterým se mísnost uloží v hashmapě
     * @param name název místnosti
     * @param description popis místnosti
     * @param area lokace místnosti
     * @param defaultPlayerPosition Výchozí pozice hráče po vstupu do místnosti
     * @return odkaz na místnost
     */
    public Room createRoom(String key, String name, String description, Area area,
                              Point defaultPlayerPosition)
    {
        Room newRoom = new Room(key, name, description, area, defaultPlayerPosition);
        addRoomToList(key, newRoom);
        return newRoom;
    }


    /***************************************************************************
     * Vrátí objekt na zadaných souřadnicích v aktuální místnoti
     *
     * @param point souřadnice
     * @return object na zadaných souřadnicích nebo null.
     * @throws NullPointerException
     */
    public AGameObject getRoomObjectOn(Point point)
    {
        return this.getRoomObjectOn(currentRoom, point);
    }



    /***************************************************************************
     * Vrátí objekt na zadaných souřadnicích. Pokud nic nenalezne vrátí null.
     *
     * DOPSAT !!!!  nebere v potaz jestli je otevrenej inventář nebo ne!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * 
     * Nebere ohled na klikání na podlahu
     *
     * @param targetRoom
     * @param point
     * @return
     * @throws NullPointerException
     */
    public AGameObject getRoomObjectOn(Room targetRoom, Point point)
    {
        Collection<AGameObject> roomObjects = targetRoom.getRoomObjects();
        AGameObject touchObject = null;

        for(AGameObject object : roomObjects){
            Point objectPosition = object.getPosition();
            Point objectScale = object.getScale();

            if(point.x >= objectPosition.x && point.x <= objectPosition.x + objectScale.x &&
               point.y >= objectPosition.y && point.y <= objectPosition.y + objectScale.y){
                if(touchObject != null){
                    touchObject = touchObject.getPriority() < object.getPriority() ? object : touchObject;
                    
                    //@todo shoda Priorit: nebere v potaz, pokud jsou na stejných souřadnicich dva objekty se stejnou prioritou, nutno někde ošetřit, ale asi budou lepší místa, až se bdue vykraslovat grafika, možná v inicializaci.
                }
                else {
                    touchObject = object;
                }
            }
        }

        return touchObject;
    }
    
    
    /***************************************************************************
     * Vrátí kolekci všech místností
     * 
     * @return kolekce bšech místností
     */
    public Map<String, Room> getAllRooms(){
        return Collections.unmodifiableMap(ROOM_LIST);
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Přidá místnost do kolekce všech místností. A také do kolekce své lokace.
     *
     * @param room nová místnost
     */
    private void addRoomToList(String key, Room room)
    {
        Area area = room.getArea();
        area.addRoomToArea(room);
        
        this.ROOM_LIST.put(key,room);
    }
    
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        RoomManager inst = new RoomManager();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
