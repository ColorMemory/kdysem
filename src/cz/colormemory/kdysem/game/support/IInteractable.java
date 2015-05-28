/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.support;

import cz.colormemory.kdysem.game.commands.ActionList;
import java.util.Map;


/*******************************************************************************
 * Instance rozhraní {@code Interactable} umějí reagovat na příkaz interakce
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public interface IInteractable
{
//== CONSTATS ==================================================================
//== DECLARED GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Představuje reakci na dotyk uživatele
     *
     * @param itemId
     * @return typ příkazu, který se má vykonat
     */
    public abstract Map<ActionList,String> interact(String itemId);
    
    
    /***************************************************************************
     * 
     * @param triggerItemId
     * @param action
     * @param targetItemId 
     */
    public abstract void addInteractAction(String triggerItemId, ActionList action, String targetItemId);

//== INHERITED GETTERS AND SETTERS =============================================
//== REMAINING DECLARED METHODS ================================================
//== REMAINING INHERITED METHODS ===============================================
//== EMBEDDED DATA TYPES =======================================================
}
