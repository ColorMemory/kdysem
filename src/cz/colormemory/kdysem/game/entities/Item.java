/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.entities;

import cz.colormemory.json.JSONConstructor;
import cz.colormemory.json.JSONException;
import cz.colormemory.kdysem.game.commands.CommandList;
import cz.colormemory.kdysem.game.logic.Inventory;
import static cz.colormemory.kdysem.game.support.IDrawable.DEFAULT_POSITION;
import static cz.colormemory.kdysem.game.support.IDrawable.DEFAULT_PRIORITY;
import static cz.colormemory.kdysem.game.support.IDrawable.DEFAULT_SCALE;



/*******************************************************************************
 * Intance třídy {@code Item} představují herní objekty, které může hráč
 * různě používat, zvedat, kombinvoat, apod.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Item extends AGameObject
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na Inventář */
    private final Inventory INVENTORY = Inventory.getInstance();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Zvednutelnost předmětu pro příkaz Pick Up */
    private boolean pickable;
    
    /** Použitelnost předmětu pro příkaz Use */
    private boolean usable;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Deafultní konstruktor. NUTNÉ PŘIDAT DALŠÍ TYPY A CELKOVĚ JE LÉPE DOMYSLET
     * 
     * @param name
     * @param description
     */
    public Item(String name, String[] description)
    {
        this(name, description, new Placement(DEFAULT_PRIORITY, DEFAULT_POSITION,
                DEFAULT_SCALE), false, false);
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
     * @param pickable zvednutelnost
     * @param usable použitelnost
     */
    public Item(String name, String[] description,
                        Placement placement, boolean pickable, boolean usable)
    {
        super(name, description, placement);
        this.pickable = pickable;
        this.usable = usable;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů.
     *
     * @return typ příkazu, který se má zavolat
     */
    @Override
    public CommandList touch()
    {
        if(getPickability()){
            return CommandList.PICK_UP;
        }
        else if(getUsability()){
            return CommandList.USE;
        }
        else {
            return CommandList.DESCRIBE;
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
                .key("pickability").value(getPickability())
                .key("usability").value(getUsability())
        .endObject();
        
        return json.toString();
    }

//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Vrátí zvednutelnost předmětu
     *
     * @return zvednutelnost
     */
    private boolean getPickability(){
        return pickable;
    }


    /***************************************************************************
     * Nastaví zvednutelnost předmětu
     *
     * @param pickable nová zvednutelnost
     */
    private void setPickability(boolean pickable){
        this.pickable = pickable;
    }
    
    
    /***************************************************************************
     * Vrátí použitelnost objektu
     * 
     * @return použitelnost objektu
     */
    private boolean getUsability(){
        return usable;
    }
    
    
    /**************************************************************************-
     * Nastaví použitelnost objektu
     * 
     * @param usable boolean použitelnosti
     */
    private void setUsability(boolean usable){
        this.usable = usable;
    }


//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        Item inst = new Item();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
