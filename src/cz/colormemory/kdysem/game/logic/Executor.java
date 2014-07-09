/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import java.awt.Point;





/*******************************************************************************
 * Třída {@code Executor} je jedináček představující hlavní řídící třídu
 * vnitřní logiku hry.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Executor
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz an jedináčka */
    private static final Executor SINGLETON = new Executor();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na hru */
    private final Game GAME = Game.getInstance();

    /** Odkaz na překladač souřadnic */
    private final CoordinateTranslator COORD_TRANSLATOR =
                                            CoordinateTranslator.getInstance();

    /** Odkaz na správce mísností */
    private final RoomManager ROOM_MANAGER = RoomManager.getInstance();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda vrací odkaz na jedináčka.
     *
     * @return odkaz na jedináčka
     */
    public static Executor getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Privátní konstruktor zabřanující vytvoření instance.
     */
    private Executor()
    {/* ... */}


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Metoda by měla předat získané souřadnice na překladatele souřadnic
     * {@link CoordinateTranslator}, který přepočitá displayové souřadnice
     * na ty mapové. Následně pomocí nich získá přístup k objektu, který se
     * v místnosti nachází právě na zadaných souřadnicích. Objekt o sobě ukládá
     * svoje stavy a podle toho ví, který příkaz se má zavolat, tento příkaz nám
     * ve forme enum {@link CommandList} vrátí. Náslědně pak an příkaz zavoláme
     * spouštěcí metodu, zjistíme, úspěšnost spuštění a dáme vědět posluchačům,
     * že nastale nějaká změna.
     *
     * @param coordinates souřadnice dotyku
     */
    public void processTouch(Point coordinates) throws NullPointerException
    {

        /* Přepočítá souřadnice */
        Point roomCoord = COORD_TRANSLATOR.calculate(coordinates);

        boolean isExecute = false;

        /* ziskam od room managera pres souradnice odkaz na AGameObject */
        try {
            AGameObject touchobject = ROOM_MANAGER.getRoomObjectOn(roomCoord);

            /* zavolam jeho metodu touch, ta by měla vědět o co se snazim a vratit
            mi typ prikazu Command list */
            CommandList executeCmd = touchobject.touch();

            isExecute = executeCmd.execute(touchobject);
        }
        catch (NullPointerException e){
            //Na zadaných souřadnicích neí žádný objekt a nedá se nic dělat - nutné nějak dopojistitit !!!!!!!!!!!!!!!
        }



        if(isExecute){
            GAME.notifyListeners();
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
//        Executor inst = new Executor();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
