/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.json.JSONConstructor;
import cz.colormemory.json.JSONException;
import cz.colormemory.json.JSONObject;
import cz.colormemory.kdysem.game.entities.Item;
import java.util.ArrayList;
import java.util.Collection;





/*******************************************************************************
 * Třída {@code Inventory} Představuje herní invetář, kam si hráč může ukládat 
 * instance třídy {@link Item} a nosit si je s sebou mezi jednotlivými 
 * mistnostmi.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Inventory
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Seznam itemů */
    private final ArrayList<Item> ITEM_LIST = new ArrayList<>();

    /** Maximal capacity */
    private final int MAX_CAPACITY = 10;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
    
    /** Ukazuje, zda je inventář aktuálně zobrazený nebo ne */
    private boolean active = false;
    
    /** Aktuálně vybraný prvek */
    private Item selectedItem = null;    
    
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    
    /***************************************************************************
     * Implicitní konstruktor
     */
    public Inventory()
    {

    }



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
     * Vrátí označených item v inventáři.
     *
     * @return označených item v inventáři.
     */
    public Item getSelectedItem()
    {
        return selectedItem;
    }
    
    
    /***************************************************************************
     * Vrátí hodnotu maximální kapacity inventáře
     * 
     * @return maximální kapacita
     */
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
    
    
    /***************************************************************************
     * Ukazuje, zda je inventář aktuálně zobrazený nebo ne
     * 
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /***************************************************************************
     * Nastaví, zda je inventář aktuálně zobrazený nebo ne
     * 
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přidá Item do seznamu inventáře. Pokud se do něj na základě maximální
     * kapacity ještě vejde. Vrátí uspěšnost.
     *
     * @param item item, který se přidá do kolekce.
     * @return úspěšnost vložení do kolekce.
     */
    public boolean addItem(Item item)
    {
        if(ITEM_LIST.size() <= MAX_CAPACITY){
            return ITEM_LIST.add(item);
        }
        else {
            return false;
        }
    }


    /***************************************************************************
     * Odebere ze seznamu inventáře item.
     *
     * @param item item, který se odebere z kolekce.
     * @return úspěšnost odebrání z kolekce.
     */
    public boolean removeItem(Item item)
    {
        return ITEM_LIST.remove(item);
    }


    /***************************************************************************
     * Vybere předmět z inventáře a ten si uloží jako dočasně vybraný
     *
     * @param index
     * @return vybraný item
     */
    public Item selectItem(int index)
    {
        if(selectedItem == null){
            selectedItem = ITEM_LIST.get(index);
            System.out.println(">>> SELECT: " + selectedItem.getName() + "\n===========================================================");
            return selectedItem;
        }
        else{
            System.out.println("Předmět už je vybraný");
            return null;
        }
    }


    /***************************************************************************
     * Vyčistí dočasný výber inventáře.
     */
    public void unselectItem()
    {
        selectedItem = null;
    }
    
    
    /***************************************************************************
     * Vrací JSONObject reprezentující objekty v inventáři
     * 
     * @return JSONOBject reprezentující itemy v inventáři
     * @throws JSONException při syntaktické JSON chybě
     */
    public JSONObject toJSON() throws JSONException{
        JSONConstructor inventoryJSONConstructor = new JSONConstructor();
           
        inventoryJSONConstructor.object();

        for(Item item : getAllItems()){
            inventoryJSONConstructor
                    .key(item.getId()+"")
                    .value(new JSONObject(item.toJSONString()));
        }

        inventoryJSONConstructor.endObject();
            
        return new JSONObject(inventoryJSONConstructor.toString());
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
