/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.framework;



/*******************************************************************************
 * Instance třídy {@code IListener} jsou posluchači, kteřá se mohou přihlásit
 * k vysílači {@link IBroadcaster}. Musí implementovat metodu, kterou vysílač
 * zavolá v případě změny.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public interface IListener
{
//== CONSTATS ==================================================================
//== DECLARED GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Reakce na volání broadcasteru
     */
    public void notice();

//== INHERITED GETTERS AND SETTERS =============================================
//== REMAINING DECLARED METHODS ================================================
//== REMAINING INHERITED METHODS ===============================================
//== EMBEDDED DATA TYPES =======================================================
}
