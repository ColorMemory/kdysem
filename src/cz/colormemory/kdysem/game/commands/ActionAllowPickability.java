/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.commands;

import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.exceptions.GameControlException;
import cz.colormemory.kdysem.game.logic.Game;
import java.util.logging.Level;
import java.util.logging.Logger;

/*******************************************************************************
 * Instance of class {@code ActionAllowPickability} represents 
 * 
 * @author Avatar
 * @version 1.00 - 05/15
 */
public class ActionAllowPickability extends AAction
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /* název akce */
    private String NAME = "allowPickability";
    
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================


    /***************************************************************************
     * Implicitní konstruktor
     */
    public ActionAllowPickability(){
        
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
    
    public String getName(){
        return NAME;
    }
    
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
    
    /***************************************************************************
     * 
     * @param gameObjectId
     */
    @Override
    public void execute(String gameObjectId) {
        AGameObject gameObject = Game.getInstance().getGameObjectManager().getGameObject(gameObjectId);
        
        if(gameObject instanceof AGameObject){
            Item item = (Item) gameObject;
            
            item.setPickable(true);
        }
        else {
            try {
                throw new GameControlException("Bad Scenario");
            } catch (GameControlException ex) {
                Logger.getLogger(ActionAllowPickability.class.getName()).log(Level.SEVERE, null, ex);
            }
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
//        ActionAllowPickability inst = new ActionAllowPickability();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




