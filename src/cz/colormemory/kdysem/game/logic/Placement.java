/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.support.IMoveable;
import cz.colormemory.kdysem.game.support.IDrawable;
import java.awt.Point;





/*******************************************************************************
 * Instance třídy {@code Placement} fungují jako přepravka.
 * Uchovávjí informace, které jiné herní instance potřebují k zobrazování
 * jako je pozice, velikost a priorita vykleslení. Je vhodná pouze k zobrazování
 * obdélníkových či čtvercových oblastí. Spravovat a měnit tyto hodnoty lze
 * pouze přes tuto třídu.
 *
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Placement implements IMoveable
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Priorita vykreslení */
    private int priority;

    /** Pozice zobrazení */
    private Point position;

    /** Velikost zobrazení */
    private Point scale;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Defaultní prázný bezparametrický konstruktor.
     */
    public Placement()
    {
        this(DEFAULT_PRIORITY, DEFAULT_POSITION, DEFAULT_SCALE);
    }


    /***************************************************************************
     * Základní konstruktor, nastaví prioritu a pozici.
     * Velikost instance nastaví defaultně.
     *
     * @param priority priorita vykleslení
     * @param position pozice instance
     */
    public Placement(int priority, Point position)
    {
        this(priority, position, DEFAULT_SCALE);
    }


    /***************************************************************************
     * Plný konstruktor. Nastaví prioritu, pozici, i velikost instance
     *
     * @param priority priorita vykleslení
     * @param position pozice instance
     * @param scale velikost instance
     */
    public Placement(int priority, Point position, Point scale)
    {
        this.priority = priority;
        this.position = position;
        this.scale = scale;
    }

//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí prioritu vykleslení instance
     *
     * @return Celé číslo. Čím větší tím větší priorita == vyšší vrstva.
     */
    @Override
    public int getPriority()
    {
        return priority;
    }


    /***************************************************************************
     * Nastaví prioritu vykreslení instance.
     *
     * @param priority Celé číslo. Čím větší tím větší priorita == vyšší vrstva.
     */
    @Override
    public void setPriority(int priority)
    {
        this.priority = priority;
    }


    /***************************************************************************
     * Vrátí pozici instance;
     *
     * @return pozice instance
     */
    @Override
    public Point getPosition()
    {
        return position;
    }


    /***************************************************************************
     * Nastaví pozici instance.
     *
     * @param position pozice instance
     */
    @Override
    public void setPosition(Point position)
    {
        this.position = position;
    }


    /***************************************************************************
     * Vrátí velikost instance.
     *
     * @return velikost instance
     */
    @Override
    public Point getScale()
    {
        return scale;
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
//        Posotion inst = new Posotion();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
