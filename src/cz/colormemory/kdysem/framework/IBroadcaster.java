/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.framework;



/*******************************************************************************
 * Instance třídy {@code IBroadcaster} fungují jako vysílač pro posluchače.
 * Umějí si tedy u sebe posluchače zaregistrovat, odregistrovat nebo
 * všechny upozornit na změnu.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public interface IBroadcaster
{
//== CONSTATS ==================================================================
//== DECLARED GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Přidá posluchače do kolekce.
     *
     * @param listener posluchač, který se má přidat.
     */
    public void addListener(IListener listener);


    /***************************************************************************
     * Odebere posluchače z kolekce.
     *
     * @param listener posluchač, který má být odebrán.
     */
    public void removeListener(IListener listener);



    /***************************************************************************
     * Upozorní všechny přihlášené posluchače
     */
    public void notifyListeners();

//== INHERITED GETTERS AND SETTERS =============================================
//== REMAINING DECLARED METHODS ================================================
//== REMAINING INHERITED METHODS ===============================================
//== EMBEDDED DATA TYPES =======================================================
}
