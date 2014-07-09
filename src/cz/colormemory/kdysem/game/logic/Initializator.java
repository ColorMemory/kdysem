/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;


import java.awt.Point;

import java.util.logging.Level;
import java.util.logging.Logger;









/*******************************************************************************
 * Třída {@code Initializator} je jedináček představující inicializační třídu hry.
 * Zajistí správné vytvoření všechn objektů a funkčností.
 *
 * @author  André HELLER
 * @version 1.00 — mm/2013
 */
public class Initializator
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final Initializator SINGLETON = new Initializator();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na správce místností */
    private final RoomManager ROOM_MANAGER = RoomManager.getInstance();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda vrací odkaz na vytvořenou instanci.
     *
     * @return singleton
     */
    public static Initializator getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     *
     */
    public Initializator()
    {

    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlavní inicicalizační metoda. Vytvoří základní místnosti hry i s obsahem.
     */
    public void initialize()
    {

//        Room sandbox = ROOM_MANAGER.createRoom("Sandbox", "Cvičná místnost ke zkoušení hry",
//                                                          Area.PRESENT_PRAGUE, new Point(0, 0));
//
//        Room sandbox2 = ROOM_MANAGER.createRoom("Sandbox2", "Cvičná místnost ke zkoušení hry",
//                                           Area.PRESENT_PRAGUE, new Point(0, 0));
//
//
//        sandbox.addObjectToRoom(new Transporter("Sousedství", "Přestěhuje člověka do sousedství", 1, new Point(0,1), sandbox2));


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
//        Initializator inst = new Initializator();
//        try {
//            inst.initialize();
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
