/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;



/*******************************************************************************
 * Třída {@code StateManager} je jedináček představující správce nejrůznějších
 * herních stavů.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class StateManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final StateManager SINGLETON = new StateManager();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Hodnota uvádí souřadnicový rozdíl osy X mezi místností a její částí
     * zobrazenou na displayi. Například, když je místnost širší než display,
     * tak se "mapa" musí posunout a to právě vyjadřuje tato hodnota. */
    private int RoomShift = 0;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovarání metoda. Vrátí odkaz na jedináčka.
     *
     * @return odkaz ne jedináčka.
     */
    public static StateManager getInstnace()
    {
        return SINGLETON;
    }

    /***************************************************************************
     * Privátní konstruktor zabraňující vytvoření instance
     */
    private StateManager(){}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí souřadnicový rozdíl osy X mezi místností a její částí
     * zobrazenou na displayi. Například, když je místnost širší než display,
     * tak se "mapa" musí posunout. Souřadnice displaye a mapy potom nesouhlasí.
     * To právě vyjadřuje tato hodnota.
     *
     * @return souřadnicový posun osy X
     */
    public int getRoomShift()
    {
        return RoomShift;
    }


    /***************************************************************************
     * Nastavuje souřadnicový rozdíl osy X mezi místností a její částí
     * zobrazenou na displayi. Například, když je místnost širší než display,
     * tak se "mapa" musí posunout. Souřadnice displaye a mapy potom nesouhlasí.
     * To právě vyjadřuje tato hodnota.
     *
     * @param RoomShift souřadnicový posun osy X
     */
    public void setRoomShift(int RoomShift)
    {
        this.RoomShift = RoomShift;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
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
//        StateManager inst = new StateManager();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
