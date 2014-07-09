/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import java.awt.Point;





/*******************************************************************************
 * Třída {@code CoordinateTranslator} je jedináček, představující překladatele
 * souřadnice. Jelikož může samotná místnost být širší než zobrazovaný display,
 * musí přepošítat displayové souřadnice na souřadnice v místnosti.
 * K tomu používá třídu {@link StateManager}, která si pamatuje právě tento posun.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class CoordinateTranslator
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final CoordinateTranslator SINGLETON =
                                                    new CoordinateTranslator();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na správce herních stavů */
    private final StateManager STATE_MANAGER = Game.getInstance()
                                                            .getStateManager();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================


    /***************************************************************************
     * Tovární metoda vracející odkaz na jedináčka.
     *
     * @return
     */
    public static CoordinateTranslator getInstance(){
        return SINGLETON;
    }


    /***************************************************************************
     * Privátní konstruktor zabřanující vytvoření instance
     */
    private CoordinateTranslator()
    {/* ... */}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přepočítá zadané displayové souřadnice na realné souřadnice v místnosti.
     * Protože hráč může chodit po místnosti, která je delší než zobrazovaný
     * display. V tomto případě se mužou souřadnice místnosti a souřadnice
     * displaye lišit.
     *
     * @param point displayové souřadnice
     * @return raálné souřadnice v místnosti
     */
    public Point calculate(Point point){

        // Získá displayový posun
        int roomShift = STATE_MANAGER.getRoomShift();

        point.x = point.x + roomShift;

        return point;
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
//        CoordinateTranslator inst = new CoordinateTranslator();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
