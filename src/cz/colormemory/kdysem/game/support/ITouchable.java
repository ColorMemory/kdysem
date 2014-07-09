/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.support;

import cz.colormemory.kdysem.game.logic.CommandList;








/*******************************************************************************
 * Instance rozhraní {@code ITouchable} umějí nějak reagovat na dotyk a
 * vrátí zpět správný příkaz dle jejich typu.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public interface ITouchable extends IDrawable
{
//== CONSTATS ==================================================================
//== DECLARED GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Představuje reakci na dotyk uživatele
     *
     * @return typ příkazu, který se má vykonat
     */
    public CommandList touch();

//== INHERITED GETTERS AND SETTERS =============================================
//== REMAINING DECLARED METHODS ================================================
//== REMAINING INHERITED METHODS ===============================================
//== EMBEDDED DATA TYPES =======================================================
}
