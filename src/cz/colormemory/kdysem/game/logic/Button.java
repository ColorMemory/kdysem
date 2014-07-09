/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;





/*******************************************************************************
 * Instance třídy {@code Button} představují tlačítko invetáře, které nějak aktivuje vybrané věci.
 * Nutno domyslet a dopsat.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Button extends AGameObject
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na inventář */
    private static final Inventory INVENTORY = Inventory.getInstance();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Deafultní konstruktor. NUTNÉ PŘIDAT DALŠÍ TYPY A CELKOVĚ JE LÉPE DOMYSLET
     */

    public Button()
    {
        super("", "", new Placement());
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů.
     *
     * Musí se dopsat, bude to něco na procipu, když bude v inventáři víc vybraných položek, bude je kombinovat jinak je bde interagovat a dávat do výberu, který zatím také asi nen vytvořen.
     *
     * @return typ spouštěného příkazu
     */
    @Override
    public CommandList touch()
    {
        return CommandList.COMBINE;
        //return CommandList.INTERACTION;
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
//        Button inst = new Button();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
