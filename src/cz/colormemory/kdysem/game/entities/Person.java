/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.entities;

import cz.colormemory.json.JSONConstructor;
import cz.colormemory.json.JSONException;
import cz.colormemory.kdysem.game.commands.ActionList;
import cz.colormemory.kdysem.game.commands.CommandList;
import cz.colormemory.kdysem.game.exceptions.GameControlException;
import cz.colormemory.kdysem.game.support.IInteractable;
import java.util.Map;



/*******************************************************************************
 * Instance třídy {@code Person} představují osoby v místnostech
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Person extends AGameObject implements IInteractable
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Dialog osoby */
    private final Dialog DIALOG;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Deafultní konstruktor. NUTNÉ PŘIDAT DALŠÍ TYPY A CELKOVĚ JE LÉPE DOMYSLET
     *
     * @param name
     * @param dialog
     * @param description
     */
    public Person(String id, String name, String[] description, Dialog dialog)
    {
        super(id, name, description, new Placement());
        this.DIALOG = dialog;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * 
     * @param object
     * @return 
     */
    @Override
    public Map<ActionList,String> interact(String itemId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public void setInteractivity(boolean interactable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addInteractAction(String triggerItemId, String actionName, String targetItemId) throws GameControlException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů.
     *
     * @return typ spouštěného příkazu
     */
    @Override
    public CommandList touch()
    {
        return CommandList.DIALOG;
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
//        Person inst = new Person();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
