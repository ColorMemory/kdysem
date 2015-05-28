/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.entities;

import cz.colormemory.json.JSONConstructor;
import cz.colormemory.json.JSONException;
import cz.colormemory.kdysem.game.commands.CommandList;
import java.awt.Point;





/*******************************************************************************
 * Instance třídy {@code Transporter} představují tlačítka, která přenášejí
 * hráče z jedné místnosti do druhé.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Transporter extends AGameObject
{
//== CONSTANT CLASS ATTRIBUTES =================================================
    
    /** Výchozí pozice. která se ignoruje, pokud místnost obdrží tuto hodnotu, 
     * nahradí je svojí výchozí pozicií */
    public static final Point NULL_TARGET_POSITION = new Point(-1, -1);
    
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================    
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Cílová místnost */
    private String targetRoomId;

    /** Cílové souřadnice v místnosti */
    private Point targetPosition;
    
    /** Boolean uzamčenosti transportéru. Pokud je zamený, nemůže přenášet */
    private boolean locked;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Základní obecný konstruktor. Nastaví jméno, popis a cílovou místnost.
     * Všechny souřadnice a velikosti bere defaultně.
     *
     * @param name název transportéru
     * @param description popis transportéru
     * @param targetRoom cílová místnost transportéru
     */
    public Transporter(String id, String name, String[] description, String targetRoom)
    {
        this(id, name, description, new Placement(DEFAULT_PRIORITY, DEFAULT_POSITION,
                DEFAULT_SCALE), targetRoom, NULL_TARGET_POSITION, false);
    }
    
    
    /***************************************************************************
     * Základní obecný konstruktor. Nastaví jméno, popis a cílovou místnost.
     * Všechny souřadnice a velikosti bere defaultně.
     *
     * @param name název transportéru
     * @param description popis transportéru
     * @param targetRoom cílová místnost transportéru
     * @param locked stav uzamčení transportéru, pookud je zamknutý, neumožnuje přenášet
     */
    public Transporter(String id, String name, String[] description, String targetRoom, boolean locked)
    {
        this(id, name, description, new Placement(DEFAULT_PRIORITY, DEFAULT_POSITION,
                DEFAULT_SCALE), targetRoom, NULL_TARGET_POSITION, locked);
    }
    
    
    /***************************************************************************
     * Kompletní zjednodušený kontruktor. Od kompletního se liší v tom,
     * že informace o umístění a vykrelsení nepřjímá jednotlivě, ale
     * jako objekt {@link Placement}.
     * Zavolá rodičovský konstruktor a inicializuje přidaná data.
     *
     * @param name název transportéru.
     * @param description popis transportéru
     * @param placement objet s umíštením a informacemi o vykreslení
     * @param targetRoomId cílová místnost
     * @param locked stav uzamčení transportéru, pookud je zamknutý, neumožnuje přenášet
     */
    public Transporter(String id, String name, String[] description,
                        Placement placement, String targetRoomId, boolean locked)
    {
        this(id, name, description, placement, targetRoomId, NULL_TARGET_POSITION, locked);
    }
    
    
    /***************************************************************************
     * Kompletní zjednodušený kontruktor. Od kompletního se liší v tom,
     * že informace o umístění a vykrelsení nepřjímá jednotlivě, ale
     * jako objekt {@link Placement}.
     * Zavolá rodičovský konstruktor a inicializuje přidaná data.
     *
     * @param name název transportéru.
     * @param description popis transportéru
     * @param placement objet s umíštením a informacemi o vykreslení
     * @param targetRoomId cílová místnost
     * @param targetPosition cílové souřednice v cílové místnosti
     * @param locked stav uzamčení transportéru, pookud je zamknutý, neumožnuje přenášet
     */
    public Transporter(String id, String name, String[] description,
                        Placement placement, String targetRoomId,
                        Point targetPosition, boolean locked)
    {
        super(id, name, description, placement);
        this.targetRoomId = targetRoomId;
        this.targetPosition = targetPosition;
        this.locked = locked;
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí cílovou místnost transportéru
     *
     * @return odkaz na cílovou místnost
     */
    public String getTargetRoomId()
    {
        return targetRoomId;
    }


    /***************************************************************************
     * Nastaví transportéru cílovou místnost
     *
     * @param targetRoomId the targetRoom to set
     */
    public void setTargetRoomId(String targetRoomId)
    {
        this.targetRoomId = targetRoomId;
    }


    /***************************************************************************
     * Vrátí cílové souřadnice v místnosti
     *
     * @return the targetPosition
     */
    public Point getTargetPosition()
    {
        return targetPosition;
    }


    /***************************************************************************
     * Nastaví transportéru cílové souřadnice v místnosti.
     *
     * @param targetPosition the targetPosition to set
     */
    public void setTargetPosition(Point targetPosition)
    {
        this.targetPosition = targetPosition;
    }
    
    
    /***************************************************************************
     * Vrací informaci o uzamčenosti transportéru, když je uzamčen, tak nepřenáší.
     * 
     * @return uzamčenost transportéru.
     */
    public boolean isLocked(){
        return locked;
    }
    
    
    /***************************************************************************
     * Nastaví parametr uzamčenosti. Zamčený nepřenáší.
     * 
     * @param locked boolean uzamčenosti.
     */
    public void setLocked(boolean locked){
        this.locked = locked;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů. Reakce an dotek Vrátí typ příkazu, který se má zavolat
     *
     * @return Typ příkazu.
     */
    @Override
    public CommandList touch()
    {
        if(isLocked()){
            return CommandList.DESCRIBE;
        }
        else {
            return CommandList.TRANSPORT;
        }
    }
    
    
    /***************************************************************************
     * Převede instanci transporteru na JSON string
     * 
     * @return Neformátovaný JSON string reprezentující instanci
     * {
            "position": "java.awt.Point[x=0,y=0]",
            "name": "Koupelna",
            "priority": 1,
            "description": "Vlez do vany",
            "target": "03"
        }
     * 
     * @throws cz.colormemory.json.JSONException pokud se v JSONU vyskytne 
     * syntaktická chyba
     */
    @Override
    public String toJSONString() throws JSONException {
        JSONConstructor json = new JSONConstructor();
        
        json.object()
                .key("name").value(super.getName())
                .key("description").array();

        for(String description : super.getDescription()){
                json.value(description);
        }
            json.endArray()
                .key("priority").value(super.getPriority())
                .key("position").array()
                    .value(super.getPosition().x)
                    .value(super.getPosition().y)
                .endArray()
                .key("target").value(getTargetRoomId())
                .key("locked").value(isLocked())
        .endObject();
        
        return json.toString();
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
//        Transporter inst = new Transporter();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
