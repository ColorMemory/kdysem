/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.entities;

import cz.colormemory.kdysem.game.commands.CommandList;
import cz.colormemory.kdysem.game.support.ITouchable;
import java.awt.Point;





/*******************************************************************************
 * Instance třídy {@code Floor} představují podlahu v místnosti.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Floor implements ITouchable
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Nutné domyslet!!!!!!!!!!! Pravdě pdoobne top bude arraylist nebo hashset zjistit rozdíl a vybrat lepší usnadnilo by mnám práci, kdybchom podlehu mohli považovat za obdílník nejde to? to by pak dokonce skoro mohla být AGameOBject
     *
     * @param polygon
     */
    public Floor(int[] polygon)
    {

    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Tyto metody tu jsou celekm na nic, přesto je v zásadě má, musíme se o tom pobavit....
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    /***************************************************************************
     * Vrátí prioritu vykleslení instance
     *
     * @return Celé číslo. Čím větší tím větší priorita == vyšší vrstva.
     */
    @Override
    public int getPriority()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /***************************************************************************
     * Vrátí pozici instance;
     *
     * @return pozice instance
     */
    @Override
    public Point getPosition()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /***************************************************************************
     * Vrátí velikost
     *
     * @return
     */
    @Override
    public Point getScale()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů.
     */
    @Override
    public CommandList touch()
    {
        throw new UnsupportedOperationException("Not yet.");
    }

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
//        Floor inst = new Floor();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
