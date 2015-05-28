/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.entities;

import cz.colormemory.json.JSONConstructor;
import cz.colormemory.json.JSONException;
import cz.colormemory.kdysem.game.commands.ActionList;
import cz.colormemory.kdysem.game.commands.CommandList;
import static cz.colormemory.kdysem.game.support.IDrawable.DEFAULT_POSITION;
import static cz.colormemory.kdysem.game.support.IDrawable.DEFAULT_PRIORITY;
import static cz.colormemory.kdysem.game.support.IDrawable.DEFAULT_SCALE;
import cz.colormemory.kdysem.game.support.IInteractable;
import java.awt.Point;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Intance třídy {@code Item} představují herní objekty, které může hráč
 * různě používat, zvedat, kombinvoat, apod.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Item extends AGameObject implements IInteractable
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /** Mapa ve které jsou ulopžené objekty se kterými je schopný objekt 
     * interagovat a k tomu přidružená akce, která se má vykonat */
    private final Map<String, Map<ActionList,String>> ACTIONS = new HashMap<>();
   
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Zvednutelnost předmětu pro příkaz Pick Up */
    private boolean pickable;
    
    /** Použitelnost předmětu pro příkaz Use */
    private boolean usable;
    
    /** Inteakčnost předmětu pro příkaz Interact */
    private boolean interactable;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Deafultní konstruktor. NUTNÉ PŘIDAT DALŠÍ TYPY A CELKOVĚ JE LÉPE DOMYSLET
     * 
     * @param id
     * @param name
     * @param description
     */
    public Item(String id, String name, String[] description)
    {
        this(id, name, description, new Placement(DEFAULT_PRIORITY, DEFAULT_POSITION,
                DEFAULT_SCALE), false, false, false);
    }


    /***************************************************************************
     * Kompletní zjednodušený kontruktor. Od kompletního se liší v tom,
     * že informace o umístění a vykrelsení nepřjímá jednotlivě, ale
     * jako objekt {@link Placement}.
     * Zavolá rodičovský konstruktor a inicializuje přidaná data.
     *
     * @param id
     * @param name název transportéru.
     * @param description popis transportéru
     * @param placement objet s umíštením a informacemi o vykreslení
     * @param pickable zvednutelnost
     * @param usable použitelnost
     * @param interactable
     */
    public Item(String id, String name, String[] description, Placement placement, 
                boolean pickable, boolean usable, boolean interactable)
    {
        super(id, name, description, placement);
        this.pickable = pickable;
        this.usable = usable;
        this.interactable = interactable;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
    
    /***************************************************************************
     * Nastaví pozici předmětu, vhodné pro přidání do invetáře
     * 
     * @param point nová pozice předmětu
     */
    public void setPosition(Point point){
        super.getPlacement().setPosition(point);
    }
    
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vráci typ akce na základě interce
     * @return 
     */
    @Override
    public Map<ActionList,String> interact(String itemId) {
        
        return ACTIONS.get(itemId);
    }
    
    
    /***************************************************************************
     * 
     * 
     * @param triggerItemId
     * @param action
     * @param targetItemId 
     */
    @Override
    public void addInteractAction(String triggerItemId, ActionList action, String targetItemId) {
        Map<ActionList,String> itemMap = new EnumMap<>(ActionList.class);
        itemMap.put(action, targetItemId);
        ACTIONS.put(triggerItemId, itemMap);
    }
    
    
    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů.
     *
     * @return typ příkazu, který se má zavolat
     */
    @Override
    public CommandList touch()
    {
        //@todo Každý objekt bude mít asi jiné pořadí vyhodnocení, takže ještě bude nutno nějak generalizovat., ačkoliv možná nakonec, ne, interact si s tím poradí a ostanít to asi ani potřebovat nebudou
        if(isPickable()){
            return CommandList.PICK_UP;
        }
        else if(isInteractable()){
            return CommandList.INTERACT;
        }
        else if(isUsable()){
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
                .key("pickability").value(isPickable())
                .key("usability").value(isUsable())
                .key("interactability").value(isInteractable())
                .key("actions")
                    .object();
                    
        for(String triggerId : ACTIONS.keySet()){
                    json.key(triggerId)
                            .object();
            for(ActionList action : ACTIONS.get(triggerId).keySet()){
                            json.key(action.getName())
                                .value(ACTIONS.get(triggerId).get(action));
            }
                        json.endObject();
        }                                
                json.endObject()
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
    public boolean isPickable(){
        return pickable;
    }


    /***************************************************************************
     * Nastaví zvednutelnost předmětu
     *
     * @param pickable nová zvednutelnost
     */
    public void setPickable(boolean pickable){
        this.pickable = pickable;
    }
    
    
    /***************************************************************************
     * Vrátí použitelnost objektu
     * 
     * @return použitelnost objektu
     */
    public boolean isUsable(){
        return usable;
    }
    
    
    /**************************************************************************-
     * Nastaví použitelnost objektu
     * 
     * @param usable boolean použitelnosti
     */
    public void setUsability(boolean usable){
        this.usable = usable;
    }
    
    
    /**************************************************************************-
     * Inteakčnost předmětu pro příkaz Interact
     * 
     * @return the inteactable
     */
    public boolean isInteractable() {
        return interactable;
    }

    /**************************************************************************-
     * Inteakčnost předmětu pro příkaz Interact
     * 
     * @param inteactable the inteactable to set
     */
    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
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
