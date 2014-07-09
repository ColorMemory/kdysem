/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import java.util.ArrayList;
import java.util.Collection;





/*******************************************************************************
 * Instance výčtového typu {@code Area} představují jednotlivé herní lokace,
 * které shlukují skupiny místností {@link Room}.
 *
 * !!!!!!!!!!!!
 * Plánované využití je pro loadování hry po částech a ne jako celek.
 * Nutno domyslet.
 * !!!!!!!!!!!!!!!!!!!!!
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public enum Area 
{
//== VALUES OF THE ENUMERATION TYPE ============================================
  PRESENT_PRAGUE("",""),
  PRESENT_DAVLE("",""),
  BETWEEN_YEARS("",""),
  PAST_PRAGUE("",""),
  PAST_DAVLE("",""),
  DESTROYED_DAVLE("","");



//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název lokace */
    private final String NAME;

    /** Popis lokace */
    private final String DESCRIPTION;

    /** Kolekce místností **/
    private final Collection<Room> ROOMS = new ArrayList<Room>();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří lokaci s přiřazeným názvem a popisem.
     */
    private Area(String name, String description)
    {
        this.NAME = name;
        this.DESCRIPTION = description;
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci všech místností v dané lokaci
     *
     * @return kolekce místností v lokaci
     */
    public Collection<Room> getAllRooms()
    {
        return ROOMS;
    }


    /***************************************************************************
     * Vrátí název lokace.
     *
     * @return název lokace
     */
    public String getName()
    {
        return NAME;
    }


    /***************************************************************************
     * Vrátí popis lokace.
     *
     * @return popis lokace
     */
    public String getDescription()
    {
        return DESCRIPTION;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přidá do lokace místnost - instance třídy {@link Room}
     *
     * @param room Místnost, která se má přidat
     * @return úspěšnost vložení -> true/false
     */
    public boolean addRoomToArea(Room room)
    {
        return ROOMS.add(room);
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
