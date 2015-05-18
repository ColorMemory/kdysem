/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.support;

import java.awt.Point;





/*******************************************************************************
 * Instance rozhraní {@code IDrawable} představují objekty, které jsou schopny
 * se nějak vykreslit. Mají počáteční souřadnice, velikost a
 * prioritu vykreslní (vrstvu ve které se vykreslit).
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public interface IDrawable
{
//== CONSTATS ==================================================================

    /** Defaultní pozice instance */
    public final static Point DEFAULT_POSITION = new Point(0,0);

    /** Defaultní velikost instance */
    public final static Point DEFAULT_SCALE = new Point(5,5);

    /** Defaultní priority vykreslení instance */
    public final static int DEFAULT_PRIORITY = 1;

//== DECLARED GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí prioritu vykleslení instance
     *
     * @return Celé číslo. Čím větší tím větší priorita == vyšší vrstva.
     */
    public int getPriority();


    /***************************************************************************
     * Vrátí pozici instance;
     *
     * @return pozice instance
     */
    public Point getPosition();


    /***************************************************************************
     * Vrátí velikost instance;
     *
     * @return velikost instance
     */
    public Point getScale();

//== INHERITED GETTERS AND SETTERS =============================================
//== REMAINING DECLARED METHODS ================================================
//== REMAINING INHERITED METHODS ===============================================
//== EMBEDDED DATA TYPES =======================================================
}
