/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.framework.IGame;
import cz.colormemory.kdysem.framework.IListener;
import java.awt.Point;





/*******************************************************************************
 * Třída {@code Game} je jedináček a představuje základní funkčnost celé hry.
 * Je vlastně jejím správcem. Ostatní přímo nesouvisející třídy se u
 * ní mohou přihlásit jako posluchači grafiky, zvuku apod.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Game implements IGame
{


//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final Game SINGLETON = new Game();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na executora - vykonávací třída, zajištuje herní běh */
    private final Executor EXECUTOR = Executor.getInstance();

    /** Odkaz na Správce herních stavů */
    private final StateManager STATE_MANAGER = StateManager.getInstnace();

    /** Odkaz na herní inicializátor */
    private final Initializator INITIALIZATOR = Initializator.getInstance();

    /** Vysílač zpráv o změnách pro posluchače */
    private final Broadcaster BROADCASTER = new Broadcaster();

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
    public static Game getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Privátní konstruktor zabraňující vytvoření instance
     */
    private Game()
    {/* ... */}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí odkaz na správce stavů.
     *
     * @return odkaz na správce stavů
     */
    public StateManager getStateManager()
    {
        return STATE_MANAGER;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Metoda zinicializuje herní obejtky a uvede ji do stavu potřebného
     * pro začátek hry
     */
    @Override
    public void initialize()
    {
        INITIALIZATOR.initialize();
    }


    /***************************************************************************
     * Zpracuje souřadnice dotyku na displayi.
     *
     * @param point souřadnice dotyku
     */
    @Override
    public void processTouch(Point point)
    {
        EXECUTOR.processTouch(point);
    }


    /***************************************************************************
     * Přihlásí posluchače k vysílačí zpráv.
     *
     * @param listener posluchač, který má být přihlášen
     */
    public void addListener(IListener listener)
    {
        BROADCASTER.addListener(listener);
    }


    /***************************************************************************
     * Odhlásí posluchače od vysílače
     *
     * @param listener posluchač, který má být odstraněn
     */
    public void removeListener(IListener listener)
    {
        BROADCASTER.removeListener(listener);
    }


    /***************************************************************************
     * Informuje posluchače o změně
     */
    public void notifyListeners()
    {
        BROADCASTER.notifyListeners();
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
