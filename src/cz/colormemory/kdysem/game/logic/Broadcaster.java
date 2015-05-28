/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.framework.IBroadcaster;
import cz.colormemory.kdysem.framework.IListener;
import java.util.HashSet;





/*******************************************************************************
 * Instance třídy {@code Broadcaster} představují jednotlvé vysílače,
 * ke kterým se přihlašují skupiny posluchačů.
 *
 * @author  André HELLER
 * @version 1.1 — 02/2014
 */
public class Broadcaster implements IBroadcaster
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Množina posluchačů */
    private final HashSet<IListener> LISTENERS = new HashSet<>();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Implicitní konstruktor
     */
    public Broadcaster(){/* ... */}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Přidá posluchače do množiny.
     *
     * @param listener posluchač, který se má přidat.
     */
    @Override
    public void addListener(IListener listener)
    {
        LISTENERS.add(listener);
    }


    /***************************************************************************
     * Odebere posluchače z množiny.
     *
     * @param listener posluchač, který má být odebrán.
     */
    @Override
    public void removeListener(IListener listener)
    {
        LISTENERS.remove(listener);
    }



    /***************************************************************************
     * Upozorní všechny přihlášené posluchače, že se stala změna.
     */
    @Override
    public void notifyListeners()
    {
        for(IListener listener : LISTENERS){
            listener.notice();
        }
    }


//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        Broadcaster inst = new Broadcaster();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
