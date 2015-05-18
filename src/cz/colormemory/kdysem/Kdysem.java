/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem;

import cz.colormemory.kdysem.game.logic.Game;
import cz.colormemory.kdysem.game.support.Logger;
import java.awt.Point;






/*******************************************************************************
 * Třída {@code Kdysem} je hlavní třídou projektu, která zatím nic neumím a
 * vzhledem k implementaci na android asi ani umět nebude :P
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Kdysem
{
    /***************************************************************************
     * Method starting the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        //Spustí loger, který vypisuje stav hry
        Logger.getInstance();

        // Vytvoří hru a inicializuje ji
        Game game = Game.getInstance();
        game.initialize();
                    
        //Simuluje dotek uživatele
        game.processTouch(new Point(5,3)); //Přesun do laboratoře - zamčeno! --> DESCRIBE
        game.processTouch(new Point(2,12)); //Sebrání hrnku - ještě nezkusil kávovar --> DESCRIBE
        game.processTouch(new Point(18,3)); // Přesun do haly - mělo by fungovat.
        game.processTouch(new Point(2,10)); // Kliknutí na kávovar, který sám o sobě nic neumí, může být jenom kombinován
        
        
        game.save();
        
//        game.load();

    }


    /** Private constructor preventing instance creation.*/
    private Kdysem() {

    }
}
