/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.commands;

import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.logic.Inventory;
import cz.colormemory.kdysem.game.entities.Room;



/*******************************************************************************
 * Instances of class {@code CommandPickUp} represent ...
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class CommandPickUp extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /**Odkaz na inventář */
    private final Inventory INVENTORY = Inventory.getInstance();
    
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     */
    public CommandPickUp()
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
        if(INVENTORY.getMaxCapacity() > INVENTORY.getAllItems().size()){
            if(touchObject instanceof Item){
                Room currentRoom = ROOM_MANAGER.getCurrentRoom();
                
                currentRoom.removeObjectFromRoom(touchObject);
                
                System.out.println(">>> DÁVÁM DO INVENTÁŘE!");
                
                return INVENTORY.addItem((Item) touchObject);
            }
            
        }
        
        return false;
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
//        CommandPickUp inst = new CommandPickUp();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
