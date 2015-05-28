/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.commands.CommandList;
import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.support.ITouchable;
import java.awt.Point;





/*******************************************************************************
 * Třída {@code Executor} je vykonavatelem herní logiky. V zásadě dokáže použe 
 * zpracovat dotek uživatele delegovanou třídou hry. To však zahrnuje různé 
 * přepočty souřadnic, výběr a prioritizaci správného předmětu, vykonání 
 * samotného příkazu a následné upozorněrnění posluchačů, že došlo ke změně.
 *
 * @author  André HELLER
 * @version 2.00 — 05/2015
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
    private final RoomManager ROOM_MANAGER = GAME.getRoomManager();
    
    /** Odkaz na správce stavů */
    private final StateManager STATE_MANAGER = GAME.getStateManager();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================
    
    /***************************************************************************
     * Implicitní kontruktor
     */
    public Executor()
    {
     
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Metoda by měla přepočítat získané souřadnicena na ty mapové nebo 
     * alternativně inventářové Následně pomocí nich získá přístup k objektu, 
     * který se v místnosti nachází právě na zadaných souřadnicích. Objekt o sobě
     * ukládá svoje stavy a podle toho ví, který příkaz se má zavolat, tento 
     * příkaz nám ve formě enum {@link CommandList} vrátí. Náslědně pak na příkaz
     * zavoláme spouštěcí metodu, zjistíme, úspěšnost spuštění a dáme vědět 
     * posluchačům, že nastale nějaká změna.
     *
     * @param coordinates souřadnice dotyku
     * @return 
     */
    public boolean processTouch(Point coordinates)
    {
        boolean isExecute = false;
        
        //Pokud má hra otevřený inventář, neprohledává místnost ale jej.
        if(GAME.getInventory().isActive()){
            Inventory inv = GAME.getInventory();
            
            //nastavení tabulky inventáře (počet řádků, sloupců a pixelovou vzálednost strany čtvěrcového políčka))
            int interval = 5;
            int colsCount = 10;
            int rowsCount = 4;
            
            //Upravení hraničních souřadnic na pravém a dolním kraji tabulky
            int xException = interval*colsCount;
            int yException = interval*rowsCount;
            coordinates.x = coordinates.x % xException == 0 ? coordinates.x - 1 : coordinates.x;
            coordinates.y = coordinates.y % yException == 0 ? coordinates.y - 1 : coordinates.y;
            
            int xIndex = coordinates.x/interval;
            int yIndex = coordinates.y/interval;
            
            //podle souřadnic získá hodnotu indexu v inventáři
            inv.selectItem(xIndex + (yIndex*colsCount));
            
            isExecute = true;
        }
        else {
        
            /* Přepočítá souřadnice */
            Point roomCoord = calculateRoomShift(coordinates);


            /* ziskam od room managera pres souradnice odkaz na AGameObject */
            try {
                ITouchable touchobject = ROOM_MANAGER.getRoomObjectOn(roomCoord);

                /* zavolam jeho metodu touch, ta by měla vědět o co se snazim a vratit
                mi typ prikazu Command list */
                CommandList executeCmd = touchobject.touch();

                //Přetypuje na gameobject
                if(touchobject instanceof AGameObject){
                    isExecute = executeCmd.execute((AGameObject) touchobject);
                }
            }
            catch (NullPointerException e){
                //Na zadaných souřadnicích neí žádný objekt a nedá se nic dělat - nutné nějak dopojistitit !!!!!!!!!!!!!!!
                System.out.println("Nic nanalezeno!");
            }
        }

        //Pokud se povědlo vykonat příkaz upozorní poslcuhače.
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
