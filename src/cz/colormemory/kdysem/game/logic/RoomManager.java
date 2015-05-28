/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.entities.Area;
import cz.colormemory.kdysem.game.entities.Room;
import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;





/*******************************************************************************
 * Třída {@code RoomManager} představuje správce herních místností {@link Room}.
 * Má k dispozici jejich kolekce vždy pro aktuální lokaci. Použíévá se pro 
 * přístup k místnostem a hlavně jejich objektům, takže dokáže vracet objekt na 
 * zadaných mapových souradnicích. Mimo to si pamatuje, která místnost je aktuální.
 *
 * @author  André HELLER
 * @version 1.6 — 05/2015
 */
public class RoomManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Mapa všech herních místností */
    private final Map<String,Room> ROOM_LIST = new HashMap<>();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Aktuální místnost ve hře */
    private Room currentRoom;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Privátní konstruktor zabraňující vytvoření instancí.
     */
    public RoomManager(){/* ... */}


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
     * Továrna, která vytvoří novou místnost a pridá jí do mapy místností a
     * mapy lokace.
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
     * Vrátí herní objekt{@link AGameObject} na zadaných souřadnicích v 
     * aktuální místnoti.
     *
     * @param point smapové ouřadnice
     * @return object na zadaných souřadnicích nebo null.
     * @throws NullPointerException pokud se nenašel žádný objekt.
     */
    public AGameObject getRoomObjectOn(Point point)
    {
        return this.getRoomObjectOn(currentRoom, point);
    }


    /***************************************************************************
     * Vrátí objekt na zadaných souřadnicích.
     * 
     * @todo FUTURE Nebere ohled na klikání na podlahu
     *
     * @param targetRoom cílové místnost, ze které má vrátit objekt
     * @param point mapové souřadnice místnosti
     * @return odkaz na herní objekt
     * @throws NullPointerException pokud se nenašel žádný objekt,
     */
    public AGameObject getRoomObjectOn(Room targetRoom, Point point) throws NullPointerException
    {
        // Získá kolekci objektů z místnosti a deklaruje proměnné
        Collection<AGameObject> roomObjects = targetRoom.getRoomObjects();
        AGameObject touchObject = null;

        // Projede všechny objekty v mísnotsti
        for(AGameObject object : roomObjects){
            // Přeuloží si jejich souřadnice do lokálních proměnných
            Point objectPosition = object.getPosition();
            Point objectScale = object.getScale();

            // Přepočítá, zdali byl dotek v míste nějakého objektu na základě Position, Scale
            if(point.x >= objectPosition.x && point.x <= objectPosition.x + objectScale.x &&
               point.y >= objectPosition.y && point.y <= objectPosition.y + objectScale.y){
                // Pokud už předtím nějaký objekt naše provná prioritu a uloží do proměnné
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
    

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Přidá místnost do kolekce všech místností. A také do kolekce své lokace.
     *
     * @param key Klíč nové místnosti
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
