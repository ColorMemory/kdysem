/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;





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
    private final Collection<Room> ROOM_LIST = new ArrayList<>();

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
     * @param name název místnosti
     * @param description popis místnosti
     * @param area lokace místnosti
     * @param defaultPlayerPosition Výchozí pozice hráče po vstupu do místnosti
     * @return úspěšnost vytvoření
     */
    public Room createRoom(String name, String description, Area area,
                              Point defaultPlayerPosition)
    {
        Room newRoom = new Room(name, description, area, defaultPlayerPosition);
        addRoomToList(newRoom);
        return newRoom;
    }


    /***************************************************************************
     * Přidá místnost do kolekce všech místností. A také do kolekce své lokace.
     *
     * @param room nová místnost
     */
    public void addRoomToList(Room room)
    {
        Area area = room.getArea();
        area.addRoomToArea(room);
        
        this.ROOM_LIST.add(room);
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
                touchObject =  object;
                break;
            }
        }

        return touchObject;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
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
//        RoomManager inst = new RoomManager();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
