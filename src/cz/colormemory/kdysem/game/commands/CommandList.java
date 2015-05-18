/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.commands;

import cz.colormemory.kdysem.game.entities.AGameObject;







/*******************************************************************************
 * Intance výčtového typu {@code CommandList} představují jednotlivé herní
 * příkazy. Výčtový typ usnadňuje porovnávání Jednotlivých příkazů. Za každou
 * instancí stojí instance speciálně vytvořené třídy každého příkazu.
 * Jejich společným předkem je třída {@link ACommand}.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public enum CommandList
{
//== VALUES OF THE ENUMERATION TYPE ============================================

    /* Vytvoří instanci eálných příkacových tříd - potomků {@link ACommand} */

    /** Příkaz Describe - výchozí příkaz zobrazí popis předmětu */
    DESCRIBE(new CommandDescribe()),

    /** Příkaz Move - příkaz slouží k pohybu po místnosti */
    MOVE(new CommandMove()),

    /** Příkaz USE - příkaz slouží k aktivaci základní funkcionality objektu,
     * pokud jí má. Př. Zavolám telefonem. */
    USE(new CommandUse()),

    /** Příkaz COMBINE - příkaz slouží ke kombinaci dvou vybraných
     * věcí v inventáři */
    COMBINE(new CommandCombine()),

    /** Příkaz TRANSPORT - příkaz slouží k přesunu z místnosti do místnoti */
    TRANSPORT(new CommandTransport()),

    /** Příkaz PICK UP - příkaz slouží, k sebrání věcí v místnoti
     * a uložení do inventáře */
    PICK_UP(new CommandPickUp()),

    /** Příkaz DIALOG - příkaz spustí dialog s osobou v místnosti */
    DIALOG(new CommandDialog()),

    /** Příkaz INTERCTION - příkaz nějakým způsobem interaguje věci
     * vybrané v inventáři nebo v mísnosti v závoslosti na jiné věci.
     * Př. Nožem uříznu lano. */
    INTERACTION(new CommandInteraction());


//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Odkaz na reálný příkaz */
    private final ACommand CMD;

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Privátní konstruktor. Přiřadí instanci příkazu.
     */
    private CommandList(ACommand cmd)
    {
        this.CMD = cmd;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Spustí vykonání příkazu správného typu.
     *
     * @param touchObject objekt související s příkazem
     * @return úspěšnost spuštění
     */
    public boolean execute(AGameObject touchObject)
    {
        return CMD.execute(touchObject);
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
