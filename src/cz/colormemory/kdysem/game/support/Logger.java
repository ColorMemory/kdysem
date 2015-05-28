/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.support;

import cz.colormemory.kdysem.framework.IListener;
import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.logic.Game;
import cz.colormemory.kdysem.game.logic.Inventory;
import cz.colormemory.kdysem.game.logic.RoomManager;





/*******************************************************************************
 * Třída {@code Logger} je podpůrnou třídou, který do konzole vypisuje aktuální 
 * stav hry. Je přihlášena jako posluchač ke hře a kdykoliv hra vydá nofotokaci,
 * vypíše kompletní stav.
 *
 * @author  André HELLER
 * @version 1.10 — 05/2014
 */
public class Logger implements IListener
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final Logger SINGLETON = new Logger();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /** Odklaz na hlavní třídu hry */
    private final Game GAME;
    
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda vrátí odkaz na jedináčka.
     *
     * @return odkaz na jedináčka
     */
    public static Logger getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Získá instanci třídy Game a přihlásí se u ní jako posluchač.
     */
    private Logger()
    {
        this.GAME = Game.getInstance();
        this.GAME.addListener(this);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
    
    /***************************************************************************
     * Metoda posluchače. Reaguje na notifikaci od hry. Vypíše do konzole 
     * formátovaný stav hry. Informace o aktuální místnosti, jejím obsahu a 
     * obsahu inventáře.
     */
    @Override
    public void notice() {
        RoomManager roomManager = GAME.getRoomManager();
        Inventory inventory = GAME.getInventory();
        
        StringBuilder sb = new StringBuilder();
        
        if(roomManager.getCurrentRoom() == null){
            return;
        }
        
        sb.append("===========================================================")
          .append("\nRoom: ").append(roomManager.getCurrentRoom().getName().toUpperCase())
          .append("\n\nContent: \n");
        
        for(AGameObject gameObject : roomManager.getCurrentRoom().getRoomObjects()){
            sb.append("- ").append(gameObject.getClass().getSimpleName()).append(": ").append(gameObject.getName().toUpperCase()).append("\n");
        }
                
        sb.append("\nInventory: \n");
        
        for(AGameObject gameObject : inventory.getAllItems()){
            sb.append("- ").append(gameObject.getName().toUpperCase()).append("\n");
        }
        
        sb.append("===========================================================");
        
        System.out.println(sb.toString());
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
//        Game inst = new Game();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
