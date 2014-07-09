/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.support.ITouchable;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;





/*******************************************************************************
 * Třída {@code AGameObject} je abstraktní třídou představující herní objekty.
 * Itemy, Tlačítka, Osoby a Transportéry.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public abstract class AGameObject implements ITouchable
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Unikátní ID objektu */
    private final int ID;

    /** Název předmětu */
    private final String NAME;

    /** Popis předmětu */
    private final String DESCRIPTION;

    /** Umístění předmětu */
    private final Placement PLACEMENT;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Použitelnost objektu - aktivní/neaktivní */
    private boolean useable;


//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Plný konstruktor. Vytvoří herní objekt. Je chráněný,
     * takže ho mohou volat pouze potomci. Nastavuje všechny hodnoty:
     * id, název a popis. Pozici, velikost a prioritu vykrelsení bere z
     * parametru pomocí třídy {@link Placement}.
     *
     * @param name název objektu
     * @param description popis objektu
     * @param placement informace o umístění, velikosti a vykreslení
     */
    protected AGameObject(String name, String description, Placement placement)
    {
        this.ID = IDManager.generate();
        this.NAME = name;
        this.DESCRIPTION = description;
        this.PLACEMENT = placement;
    }

//== ABSTRACT METHODS ==========================================================

    /***************************************************************************
     * Metoda vrátí podle typu herního objektu typ příkazu pomocí
     * výčtového typu {@link CommandList}.
     */
    @Override
    public abstract CommandList touch();

//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrací přepravku s informacemi o umístění, velikosti a grafické prioritě
     * objektu. JEe to jediná možnost, jak lze tyto údaje nastavit.
     * Nic jiného než třída {@link Plcement} by nemělo mít přístup k setterům.
     *
     * @return přepravku s umístěním, velikostí a informacemi k vykrelsní objektů.
     */
    public Placement getPlacement()
    {
        return PLACEMENT;
    }


    /***************************************************************************
     * Vratí použitelnost objektu - Zda je object aktivní nebo ne.
     *
     * @return použitelnost objektu.
     */
    public boolean getUsability()
    {
        return useable;
    }


    /***************************************************************************
     * Nastaví použitelnost objektu.
     *
     * @param useable Zaktivní object v případě true, zneaktivní v případě false
     */
    public void setUsability(boolean useable)
    {
        this.useable = useable;
    }


    /***************************************************************************
     * Vrátí unikátní ID.
     *
     * @return unikátní ID
     */
    public int getId(){
        return ID;
    }


    /***************************************************************************
     * Vrátí prioritu vykleslení objektu (vrstva)
     *
     * @return Celé číslo. Čím větší tím větší priorita == vyšší vrstva.
     */
    @Override
    public int getPriority()
    {
        return this.PLACEMENT.getPriority();
    }


    /***************************************************************************
     * Vrátí pozici instance;
     *
     * @return pozice instance
     */
    @Override
    public Point getPosition()
    {
        return this.PLACEMENT.getPosition();
    }


    /***************************************************************************
     * Vrátí velikost instance;
     *
     * @return velikost instance
     */
    @Override
    public Point getScale()
    {
        return this.PLACEMENT.getScale();
    }


    /***************************************************************************
     * Vrátí název objektu.
     *
     * @return popis objektu
     */

    public String getName()
    {
        return NAME;
    }


    /***************************************************************************
     * Vrátí popis objektu.
     *
     * @return popis objektu
     */
    public String getDescription()
    {
        return DESCRIPTION;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
    * Třída {@code IDManager} zajišťuje generování a unikátnost generovaných ID
    * pro každý objekt. ID zatím vě hře jsou prakticky napoužitelné prosot se
    * případně bude muset vylepšit přístupnost kolekce a jejich generování.
    *
    * @author  André HELLER
    * @version 1.00 — 02/2014
    */
   private static class IDManager
   {
   //== CONSTANT CLASS ATTRIBUTES ==============================================

       /** Kolekce všech dosud vytvořených ID */
       private static final Collection<Integer> IDS = new HashSet<>();

   //== VARIABLE CLASS ATTRIBUTES ==============================================
   //== STATIC INITIALIZER (CLASS CONSTRUCTOR) =================================
   //== CLASS GETTERS AND SETTERS ==============================================
   //== OTHER NON-PRIVATE CLASS METHODS ========================================

       /************************************************************************
        * Vygeneruje id číslo na základě dosud vytvořených čísel.
        * Tak aby bylo unikátní.
        *
        * @return identifikační číslo
        */
       public static int generate()
       {
           int number = IDS.size()+1;
           IDS.add(number);

           return number;
       }

   //###########################################################################
   //== PRIVATE AND AUXILIARY CLASS METHODS ====================================
   //== EMBEDDED TYPES AND INNER CLASSES =======================================
   //== TESTING CLASSES AND METHODS ============================================
   }
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        AGameObject inst = new AGameObject();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
