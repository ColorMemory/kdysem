/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.support;

import java.awt.Point;





/*******************************************************************************
 * Instancím rozhraní {@code IMoveable} můžeme změnit pozici a prioritu.
 * Jinými slovy, se můžou pohybovat.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public interface IMoveable extends IDrawable
{
//== CONSTATS ==================================================================
//== DECLARED GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Nastaví pozici instance.
     *
     * @param position pozice instance
     */
    public void setPosition(Point position);


    /***************************************************************************
     * Nastaví prioritu vykreslení instance.
     *
     * @param priority Celé číslo. Čím větší tím větší priorita == vyšší vrstva.
     */
    public void setPriority(int priority);

//== INHERITED GETTERS AND SETTERS =============================================
//== REMAINING DECLARED METHODS ================================================
//== REMAINING INHERITED METHODS ===============================================
//== EMBEDDED DATA TYPES =======================================================
}
