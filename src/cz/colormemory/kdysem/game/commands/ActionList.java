/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.commands;







/*******************************************************************************
 * Intance výčtového typu {@code CommandList} představují jednotlivé herní
 * příkazy. Výčtový typ usnadňuje porovnávání Jednotlivých příkazů. Za každou
 * instancí stojí instance speciálně vytvořené třídy každého příkazu.
 * Jejich společným předkem je třída {@link ACommand}.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public enum ActionList
{
//== VALUES OF THE ENUMERATION TYPE ============================================
    
    ALLOW_PICKUPABILITY(new ActionAllowPickability());

//== CONSTANT CLASS ATTRIBUTES =================================================
    
    private final AAction action;
    
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
    
    /***************************************************************************
     * Vrátí odkaz na instanci na základě jména.
     * 
     * @param name
     * @return 
     */
    public static ActionList getAction(String name){
        for(ActionList action : ActionList.values()){
            if(action.getName().equals(name)){
                return action;
            }
        }
        return null;
    }
    
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Privátní konstruktor. Přiřadí instanci příkazu.
     */
    private ActionList(AAction action)
    {
        this.action = action;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
    
    /***************************************************************************
     * 
     */
    public void execute(String gameObjectId){
        this.action.execute(gameObjectId);
    }
    
    
    /***************************************************************************
     * Vrátí jméno příkazu
     * 
     * @return 
     */
    public String getName(){
        return this.action.getName();
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
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
