/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import java.io.File;
import java.util.Properties;



/*******************************************************************************
 * Třída {@code StateManager} je jedináček představující konfiguraci hry. Má v 
 * sobě uložené všechny herní konfigurační proměnné
 *
 * @author  André HELLER
 * @version 1.1 — 05/2015
 */
public class StateManager extends Properties
{
    /** Serial UID */
    private static final long serialVersionUID = 121213225312621034L;
    
//== CONSTANT CLASS ATTRIBUTES =================================================    
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
     * Implicitní kontruktor. Zajištuje nastavení jediné vlastnosti -> název 
     * složky s konfiguračním souborem.
     */
    public StateManager(){
        super();
        this.setProperty("folder.config", 
                System.getProperty("user.dir") + File.separator + "config");
    }

    
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
