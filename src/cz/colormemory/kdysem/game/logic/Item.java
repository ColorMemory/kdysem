/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;



/*******************************************************************************
 * Intance třídy {@code Item} představují herní objekty, které může hráč
 * různě používat, zvedat, kombinvoat, apod.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Item extends AGameObject
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na Inventář */
    private final Inventory INVENTORY = Inventory.getInstance();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Zvednutelnost předmětu */
    private boolean pickable;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Deafultní konstruktor. NUTNÉ PŘIDAT DALŠÍ TYPY A CELKOVĚ JE LÉPE DOMYSLET
     */
    public Item()
    {
        super("", "", new Placement());
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů.
     *
     * @return typ příkazu, který se má zavolat
     */
    @Override
    public CommandList touch()
    {

        // NEBERE V POTAZ PŘÍKAZ DESCRIBE, MUSÍ SE DOMYSLET
        if(getPickability()){
            return CommandList.PICK_UP;
        }
        else {
            return CommandList.USE;
        }
    }

//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Vrátí zvednutelnost předmětu
     *
     * @return zvednutelnost
     */
    private boolean getPickability(){
        return pickable;
    }


    /***************************************************************************
     * Nastaví zvednutelnost předmětu
     *
     * @param pickable nová zvednutelnost
     */
    private void setPickability(boolean pickable){
        this.pickable = pickable;
    }


//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        Item inst = new Item();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
