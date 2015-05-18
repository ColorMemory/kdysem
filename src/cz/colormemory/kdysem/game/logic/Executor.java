/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.commands.CommandList;
import cz.colormemory.kdysem.game.entities.AGameObject;
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
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na Hlavní třídu hry */
    private final Game GAME = Game.getInstance();

    /** Odkaz na správce mísností */
    private final RoomManager ROOM_MANAGER = RoomManager.getInstance();
    
    private final StateManager STATE_MANAGER = GAME.getStateManager();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================
    
    /***************************************************************************
     * Privátní konstruktor zabřanující vytvoření instance.
     */
    public Executor()
    {
     
    }


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
     * @return 
     */
    public boolean processTouch(Point coordinates)
    {

        /* Přepočítá souřadnice */
        Point roomCoord = calculateRoomShift(coordinates);

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
            System.out.println("Nic nanalezeno!");
        }

        if(isExecute){
            GAME.notifyListeners();
            return true;
        }
        else {
            return false;
        }
    }

//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Přepočítá zadané displayové souřadnice na realné souřadnice v místnosti.
     * Protože hráč může chodit po místnosti, která je delší než zobrazovaný
     * display. V tomto případě se mužou souřadnice místnosti a souřadnice
     * displaye lišit.
     *
     * @param point displayové souřadnice
     * @return raálné souřadnice v místnosti
     */
    private Point calculateRoomShift(Point point){

        // Získá displayový posun
        int roomShift = STATE_MANAGER.getRoomShift();

        point.x = point.x + roomShift;

        return point;
    }
    
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
