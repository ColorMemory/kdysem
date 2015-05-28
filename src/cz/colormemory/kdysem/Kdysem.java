/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem;

import cz.colormemory.kdysem.game.logic.Game;
import java.awt.Point;





/*******************************************************************************
 * Třída {@code Kdysem} je hlavní třídou projektu, Momentálně slouží jako 
 * náhrada androidové částí pro vstupy hry. Simuluje dotek obrazovky, a 
 * využívá některé dočasné metody jako třeba openInventory(), které jsou zatím
 * implementované natvbrdo pro ulehčení absence UI.
 *
 * @author  André HELLER
 * @version 2.00 — 06/2015
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
        //Spustí logger, který vypisuje stav hry do konzole
//        Logger.getInstance();

        // Vytvoří hru a inicializuje ji
        Game game = Game.getInstance();
        game.initialize();
                    
        //Simuluje dotek uživatele
        game.processTouch(new Point(5,3)); // Přesun do laboratoře - zamčeno! --> DESCRIBE
        game.processTouch(new Point(2,12)); // Sebrání hrnku - ještě nezkusil kávovar --> DESCRIBE
        game.processTouch(new Point(18,3)); // Přesun do haly - mělo by fungovat. --> TRANSPORT
        game.processTouch(new Point(2,10)); // Kliknutí na kávovar, který sám o sobě nic neumí, může být jenom kombinován --> DESCRIBE
        
        game.openInventory(); //Dočasná pomocná metoda, "otevře inventář"
        
        game.processTouch(new Point(5, 0)); // Vybrání mince v inventáři
        
        game.closeInventory(); // "zavře inventář"
        
        game.processTouch(new Point(2, 10)); // Opětovní klinutí na kávor tentokrát již s mincí; --> INTERACT
        game.processTouch(new Point(6, 3)); // Zpět do kanceláře --> TRANSPORT
        game.processTouch(new Point(2, 12)); //Sebrání hnrku, tentokrát by mělo jít. --> PICKUP
        
        game.save(); // Uloží aktuální stav hry do souborů
        
//        game.load(); //Znovu načte hru z již uložených souborů - zatím enní dotažené
    }


    /** Private constructor preventing instance creation.*/
    private Kdysem() {

    }
}
