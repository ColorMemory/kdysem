/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.commands;

import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.support.IInteractable;
import java.util.Map;



/*******************************************************************************
 * Instances of class {@code CommandInteract} represent ...
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class CommandInteract extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     */
    public CommandInteract()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     *
     * @param touchObject
     * @return
     */
    @Override
    public boolean execute(AGameObject touchObject)
    {
        Item selectedItem = GAME.getInventory().getSelectedItem();
        if(selectedItem != null && touchObject instanceof IInteractable){
            IInteractable interactObject = (IInteractable) touchObject;
            
            Map<ActionList,String> actions = interactObject.interact(selectedItem.getId());
            
            if(actions == null){
                throw new UnsupportedOperationException("Not supported yet");
            }
            
            //Vykoná akce
            for(ActionList action : actions.keySet()){
                action.execute(actions.get(action));
            }
            
            
            //Po úspěcním vykonání musí opět vyhodit příkaz describe
            
            System.out.println(">>> INTERACT: " + touchObject.getName());
            return true;
        }
        else {
            // hra není v interačkním módu
            return CommandList.DESCRIBE.execute(touchObject);
        }
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
//        CommandInteract inst = new CommandInteract();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
