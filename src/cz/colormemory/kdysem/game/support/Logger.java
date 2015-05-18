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
 * Třída {@code Game} je jedináček a představuje základní funkčnost celé hry.
 * Je vlastně jejím správcem. Ostatní přímo nesouvisející třídy se u
 * ní mohou přihlásit jako posluchači grafiky, zvuku apod.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
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
     * Privátní konstruktor zabraňující vytvoření instance
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
     * 
     */
    @Override
    public void notice() {
        RoomManager roomManager = RoomManager.getInstance();
        
        
        StringBuilder sb = new StringBuilder();
        
        if(roomManager.getCurrentRoom() == null){
            return;
        }
        
        sb.append("===========================================================")
          .append("\nAktuální stav hry:").append("\n---------------------------")
          .append("\n\nAktuální místnost: ").append(roomManager.getCurrentRoom().getName())
          .append("\n\nObsah: \n");
        
        for(AGameObject gameObject : roomManager.getCurrentRoom().getRoomObjects()){
            sb.append("- ").append(gameObject.getClass().getName()).append(": ").append(gameObject.getName()).append("\n");
        }
        
        Inventory inventory = Inventory.getInstance();
        sb.append("\nINVENTORY:");
        
        for(AGameObject gameObject : inventory.getAllItems()){
            sb.append("- ").append(gameObject.getName()).append("\n");
        }
        
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
