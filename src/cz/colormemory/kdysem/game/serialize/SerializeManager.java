/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.serialize;


import cz.colormemory.kdysem.game.exceptions.GameInitializeException;
import cz.colormemory.kdysem.game.exceptions.GameSaveException;
import cz.colormemory.kdysem.game.logic.Game;
import java.util.logging.Level;
import java.util.logging.Logger;



//@todo výhledově je nutné tuhle třídu pročistit, asi rozdělit do více samostaných tříd, včetně nějaké file\Managaeru a celkově trochu víc domysle, momentálně to strukturální fungje, ale je to příšerný guláš a je to celé poměrně hnusné.


/*******************************************************************************
 * Třída {@code SerializeManager} představuje správce pro ukládání a 
 * incicialozování hry
 * Zajistí správné vytvoření všech objektů a funkčností.
 *
 * @author  André HELLER
 * @version 4.00 — 05/2015
 */
public class SerializeManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /** Odkaz na správce inicializace */
    private final InitializeManager INITIALIZE_MANAGER;
    
    /** Odkaz na správce ukládání */
    private final SaveManager SAVE_MANAGER;
    
    /** Odkaz na správce souborů */
    private final FileManager FILE_MANAGER;
    
    /** Odkaz na hru */
    private final Game GAME;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Kontruktor vytvoří své podsprávce
     */
    public SerializeManager()
    {       
        this.GAME = Game.getInstance();
        this.FILE_MANAGER = new FileManager();
        
        this.INITIALIZE_MANAGER = new InitializeManager(GAME, FILE_MANAGER);
        this.SAVE_MANAGER = new SaveManager(GAME, FILE_MANAGER);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Znicializuje ibjekty potřebné ke hraní hry
     * 
     * @return úspěšnost inicializace+ true pouze v případě, že je všechno inicializováno správně
     */
    public boolean initialize(){
        try {
            return INITIALIZE_MANAGER.initialize();
            
        } catch (GameInitializeException ex) {
            Logger.getLogger(SerializeManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    /***************************************************************************
     * Uloží aktuální stav hry.
     * 
     * @return úspěšnost uložení; tru pouze v případě kdy se všechno uložilo tak, jak mělo.
     */
    public boolean save() {
        try {
            return SAVE_MANAGER.save();
            
        } catch (GameSaveException ex) {
            Logger.getLogger(SerializeManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
//        SerializeManager inst = new SerializeManager();
//        
//            inst.initialize();
//        
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
