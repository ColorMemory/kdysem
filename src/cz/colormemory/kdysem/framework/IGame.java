/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.framework;

import java.awt.Point;





/*******************************************************************************
 * Interface {@code IGame} představuje hlavní třídu reprezentující a
 * spravující celou hru. S tímto rozhraním by měly komunikovat jak třídy
 * uživatelského vstupu (dotyky, stiknutí tlačítek, atd.) Tak i třídy
 * uživatelského výstupu ovladající vykreslovanou grafiku a zvuk
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public interface IGame
{
//== CONSTATS ==================================================================
//== DECLARED GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Inicializuje celou hru. Vytvoří objekty, ověří grafiku,.... DOPSAT!!!!!!
     */
    public void initialize();


    /***************************************************************************
     * Metoda zpracovává předané souřadnice doteku na displayi.
     *
     * @param point předané souřadnice
     */
    public void processTouch(Point point);


//== INHERITED GETTERS AND SETTERS =============================================
//== REMAINING DECLARED METHODS ================================================
//== REMAINING INHERITED METHODS ===============================================
//== EMBEDDED DATA TYPES =======================================================
}
