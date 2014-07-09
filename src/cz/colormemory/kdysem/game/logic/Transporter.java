/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import java.awt.Point;





/*******************************************************************************
 * Instance třídy {@code Transporter} představují tlačítka, která přenášejí
 * hráče z jedné místnosti do druhé.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Transporter extends AGameObject
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Cílová místnost */
    private Room targetRoom;

    /** Cílové souřadnice v místnosti */
    private Point targetPosition;

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Základní obecný konstruktor. Nastaví jméno, popis a cílovou místnost.
     * Všechny souřadnice a velikosti bere defaultně.
     *
     * @param name název transportéru
     * @param description popis transportéru
     * @param targetRoom cílová místnost transportéru
     */
    public Transporter(String name, String description, Room targetRoom)
    {
        this(name, description, DEFAULT_PRIORITY, DEFAULT_POSITION,
                DEFAULT_SCALE, targetRoom, targetRoom.getDefaultPlayerPosition());
    }


    /***************************************************************************
     * Základní s nastevenými údaji, které určují, kde přesně se má
     * transportér zobrazit. Velikost transportéru a cílové souřadnice v
     * cílové místnoti, se berou defaultně.
     *
     * @param name název transportéru
     * @param description popis transportéru
     * @param priority Priorita vykrelsení (vrstva)
     * @param position pozice vykreslení
     * @param targetRoom cílová místnost transportéru
     */
    public Transporter(String name, String description,
                        int priority, Point position, Room targetRoom)
    {
        this(name, description, priority, position, DEFAULT_SCALE, targetRoom,
                targetRoom.getDefaultPlayerPosition());
    }


    /***************************************************************************
     * Kompletní konstruktor s rozdělnými údaji o umístění a vykreslení
     * transportéru. Nastaví jméno, popis, prioritu vykrelsení,
     * pozici vykrelsení, velikost vykreslení, cílovou místnost a
     * cílovou pozici v cílové místnosti.
     *
     * @param name název transportéru
     * @param description popis transportéru
     * @param priority priorita vykreslení (vrstva)
     * @param position pozice vykreslení v místnosti
     * @param scale velikost vykreslení v místnosti
     * @param targetRoom cílové místnost
     * @param targetPosition cílové souračnice cílové místnosti
     */
    public Transporter(String name, String description,
                        int priority, Point position, Point scale,
                        Room targetRoom, Point targetPosition)
    {
        this(name, description, new Placement(priority, position, scale),
                targetRoom, targetPosition);
    }


    /***************************************************************************
     * Kompletní zjednodušený kontruktor. Od kompletního se liší v tom,
     * že informace o umístění a vykrelsení nepřjímá jednotlivě, ale
     * jako objekt {@link Placement}.
     * Zavolá rodičovský konstruktor a inicializuje přidaná data.
     *
     * @param name název transportéru.
     * @param description popis transportéru
     * @param placement objet s umíštením a informacemi o vykreslení
     * @param targetRoom cílová místnost
     * @param targetPosition cílové souřednice v cílové místnosti
     */
    public Transporter(String name, String description,
                        Placement placement, Room targetRoom,
                        Point targetPosition)
    {
        super(name, description, placement);
        this.targetRoom = targetRoom;
        this.targetPosition = targetPosition;
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí cílovou místnost transportéru
     *
     * @return odkaz na cílovou místnost
     */
    public Room getTargetRoom()
    {
        return targetRoom;
    }


    /***************************************************************************
     * Nastaví transportéru cílovou místnost
     *
     * @param targetRoom the targetRoom to set
     */
    public void setTargetRoom(Room targetRoom)
    {
        this.targetRoom = targetRoom;
    }


    /***************************************************************************
     * Vrátí cílové souřadnice v místnosti
     *
     * @return the targetPosition
     */
    public Point getTargetPosition()
    {
        return targetPosition;
    }


    /***************************************************************************
     * Nastaví transportéru cílové souřadnice v místnosti.
     *
     * @param targetPosition the targetPosition to set
     */
    public void setTargetPosition(Point targetPosition)
    {
        this.targetPosition = targetPosition;
    }

//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zděděná metoda. Spouštěč herních příkazů. Reakce an dotek Vrátí typ příkazu, který se má zavolat
     *
     * @return Typ příkazu.
     */
    @Override
    public CommandList touch()
    {
        return CommandList.TRANSPORT;
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
//        Transporter inst = new Transporter();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
