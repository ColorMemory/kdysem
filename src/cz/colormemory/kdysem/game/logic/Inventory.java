/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.entities.Item;
import java.util.ArrayList;
import java.util.Collection;





/*******************************************************************************
 * Třída {@code Inventory} je jedináček představující herní inventář.
 * Tedy schránku na jednotlivé itemy, které si hráč během hry různě přenáší.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Inventory
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Jediná vytvoření instance inventáře = jedináček */
    private static final Inventory SINGLETON = new Inventory();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** kolekce itemů */
    private final Collection<Item> ITEM_LIST = new ArrayList<>();

    /** kolekce itemů */
    private final Collection<Item> SELECTED_LIST = new ArrayList<>();
    
    /** Maximal capacity */
    private final int MAX_CAPACITY = 10;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí odkaz na jedináčka.
     *
     * @return odkaz an jedináčka.
     */
    public static Inventory getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Privátní konstruktor zabraňující vytvoření instance.
     */
    private Inventory()
    {/* ... */}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci všech itemů v inventáři.
     *
     * @return kolekce itemů v inventáři
     */
    public Collection<Item> getAllItems()
    {
        return ITEM_LIST;
    }


    /***************************************************************************
     * Vrátí koleci označených itemů v inventáři.
     *
     * @return kolekce označených itemů v inventáři.
     */
    public Collection<Item> getSelectedItems()
    {
        throw new UnsupportedOperationException("Not yet");
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přidá Item do kolekce inventáře. Vrátí uspěšnost.
     *
     * @param item item, který se přidá do kolekce.
     * @return úspěšnost vložení do kolekce.
     */
    public boolean addItem(Item item)
    {
        return ITEM_LIST.add(item);
    }


    /***************************************************************************
     * Odebere z kolekce inventáře item.
     *
     * @param item item, který se odebere z kolekce.
     * @return úspěšnost odebrání z kolekce.
     */
    public boolean removeItem(Item item)
    {
        return ITEM_LIST.remove(item);
    }


    /***************************************************************************
     * Přidá do kolekce vybraných věcí
     *
     * @param item item, který se přidá do kolekce.
     * @return úspěšnost vložení do kolekce.
     */
    public boolean selectItem(Item item)
    {
        return SELECTED_LIST.add(item);
    }


    /***************************************************************************
     * Odebre z kolekce vybraných věcí
     *
     * @param item item, který se odebere z kolekce.
     * @return úspěšnost odebrání z kolekce.
     */
    public boolean unselectItem(Item item)
    {
        return SELECTED_LIST.remove(item);
    }
    
    /***************************************************************************
     * Vrátí hodnotu maximální kapacity inventáře
     * 
     * @return maximální kapacita
     */
    public int getMaxCapacity() {
        return MAX_CAPACITY;
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
//        Inventory inst = new Inventory();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
