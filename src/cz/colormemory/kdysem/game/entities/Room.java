/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.entities;

import cz.colormemory.json.JSONConstructor;
import cz.colormemory.json.JSONException;
import cz.colormemory.json.JSONObject;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;





/*******************************************************************************
 * Instance třídy {@code Room} představují místnosti ve hře.
 * Každá místnost je klíčovou složkou hry, neboť má většinu informací
 * o ostaních objektech.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Room
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Kolekce předmětů a lidí uvnitř mísnosti */
    private final Collection<AGameObject> ROOM_OBJECT_LIST = new ArrayList<>();

    private final String KEY;
    
    /** Podlaha místnosti */
    private final Floor FLOOR;

    /** Název místnosti */
    private final String NAME;

    /** Popis místnosti */
    private final String DESCRIPTION;

    /** Lokace místnosti */
    private final Area AREA;

    /** Defaultní pozice, kde se hráč objeví při vstupu do místnosti */
    private final Point DEFAULT_PLAYER_POSITION;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Pozice, kde se hráč zobrazí při příchodu do místnosti */
    private Point activeTransportPosition = null;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Konstruktor vytvoří místnost s jménem, popisem, lokací, výchozí pozici
     * a podlahou {@link Floor}. Místnost sama se pak přidá správci místností
     * a díky němu i své lokaci do kolekcí místností
     *
     * @param id identifikátor používaný ostatními objekty
     * @param name název místnosti
     * @param description popis místnosti
     * @param area lokace
     * @param defaultPlayerPosition výchozí souřadnice pro hráče
     */
    public Room(String key, String name, String description, Area area, Point defaultPlayerPosition)
    {
        this.KEY = key;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.AREA = area;
        this.FLOOR = new Floor(new int[0]);
        this.DEFAULT_PLAYER_POSITION = defaultPlayerPosition;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí odkaz na kolekci s objekty ve místnosti.
     *
     * @return kolekce s objekty v místnosti
     */
    public Collection<AGameObject> getRoomObjects()
    {
        return ROOM_OBJECT_LIST;
    }


    /***************************************************************************
     * Vrátí odkaz na podlahu.
     *
     * @return Podlaha místnosti
     */
    public String getKey()
    {
        return KEY;
    }
    
    
    /***************************************************************************
     * Vrátí odkaz na podlahu.
     *
     * @return Podlaha místnosti
     */
    public Floor getFloor()
    {
        return FLOOR;
    }


    /***************************************************************************
     * Vrátí název místnosti.
     *
     * @return název místnosti
     */
    public String getName()
    {
        return NAME;
    }


    /***************************************************************************
     * Vrátí popis místnosti.
     *
     * @return popis místnosti
     */
    public String getDescription()
    {
        return DESCRIPTION;
    }


    /***************************************************************************
     * Vrátí lokaci místnosti.
     *
     * @return lokace místnosti
     */
    public Area getArea()
    {
        return AREA;
    }


    /***************************************************************************
     * Vrátí defaultní pozici, kde se hráč objeví při vstupu do místnosti.
     *
     * @return defaultní pozice pro hráče
     */
    public Point getDefaultPlayerPosition()
    {
        return DEFAULT_PLAYER_POSITION;
    }


    /***************************************************************************
     * Vrátí pozici v mísnosti, kde se má hráč aktuálně zobrazit po přechodu
     * z jiné místnosti.
     *
     * @return startovní pozice v místnosti pro hráče
     */
    public Point getActiveTransportPosition()
    {
        return activeTransportPosition;
    }


    /***************************************************************************
     * Nastaví pozici v mísnosti, kde se má hráč aktuálně zobrazit po přechodu
     * z jiné místnosti.
     *
     * @param position pozice, kde se má hráč zobrazit při příchodu
     */
    public void setActiveTransportPosition(Point position)
    {
        this.activeTransportPosition = position;
    }


//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přidá do kolekce svých objektů objekt v parametru.
     *
     * @param roomObject objekt, který se vloží do kolekce místnosti
     * @return úspěšnost vložení -> true/false
     */
    public boolean addObjectToRoom(AGameObject roomObject)
    {
        return ROOM_OBJECT_LIST.add(roomObject);
    }


    /***************************************************************************
     * Odebere z kolekce svých objektů objekt v parametru
     *
     * @param roomObject objekt, který se odebere z kolekce místnosti
     * @return úspěšnost odebrání -> true/false
     */
    public boolean removeObjectFromRoom(AGameObject roomObject)
    {
        return ROOM_OBJECT_LIST.remove(roomObject);
    }
    
    
    /***************************************************************************
     * Vrátí JSON reprezentující instanci. Vhodné pro použití při serializaci.
     * 
     * 
     * @return 
     * @throws cz.colormemory.json.JSONException v případě chybné syntaxe JSONU
     */
    public JSONObject toJSON() throws JSONException {
        JSONConstructor roomJSON = new JSONConstructor();
        JSONConstructor objectsJSON = new JSONConstructor();
        
        roomJSON.object()
            .key("name").value(NAME)
            .key("description").value(DESCRIPTION);


            // @todo podlaha ještě není domyšlená jajk se bude používat a ukládat a inicializovat
            roomJSON.key("floor")
                .array()
                    .value(0).value(0)
                    .value(150).value(0)
                    .value(150).value(150)
                    .value(0).value(150)
                .endArray();

            roomJSON.key("playerPosition")
                .array();

                if(activeTransportPosition != null){            
                    roomJSON.value(activeTransportPosition.x)
                        .value(activeTransportPosition.y);
                }
                else {
                    roomJSON.value(DEFAULT_PLAYER_POSITION.x)
                        .value(DEFAULT_PLAYER_POSITION.y); 
                }
                roomJSON.endArray();
        
        
        objectsJSON.object();
////    roomObjects
        //vytvoří pole s ID GameObjectů plus je nasází do samostatného JSONU
        addGameObjectsToJSON("transporters", Transporter.class ,roomJSON, objectsJSON);
        addGameObjectsToJSON("items", Item.class ,roomJSON, objectsJSON);
        addGameObjectsToJSON("persons", Person.class ,roomJSON, objectsJSON);
        
        
        
        objectsJSON.endObject();
        roomJSON.endObject();

        
        return new JSONObject(new JSONConstructor()
                .object()
                    .key("info").value(new JSONObject(roomJSON.toString()))
                    .key("objects").value(new JSONObject(objectsJSON.toString()))
                .endObject().toString());
    }

//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Přidá do JSONu mísnosti pole s jednotlivýma ID GameObjectů. Současně do 
     * druhého JSONu přidá jejich detailní informace
     * 
     * @param arrayName Název přidávaného pole
     * @param classReference Třída objektů, které se mají serializovat do pole
     * @param roomJSON Odkaz na JSON místnosti
     * @param objectsJSON Oskaz na JSON game objektů
     * @throws JSONException V případě chybné syntaxe JSONu
     */
    private void addGameObjectsToJSON(String arrayName, 
                    Class<? extends AGameObject> classReference, 
                    JSONConstructor roomJSON, 
                    JSONConstructor objectsJSON) throws JSONException {
      
        
        roomJSON.key(arrayName)
                .array();
        
        
        
        for(AGameObject gameObject : ROOM_OBJECT_LIST){
            if(classReference.isInstance(gameObject)){
                roomJSON.value(gameObject.getId());
                objectsJSON.key(gameObject.getId()+"")
                       .value(new JSONObject(gameObject.toJSONString()));
            }
        }
        
        
        roomJSON.endArray();
    }
    
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        Room inst = new Room();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}

