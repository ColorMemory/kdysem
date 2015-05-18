/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.exceptions.GameControlException;
import cz.colormemory.kdysem.game.exceptions.GameInitializeException;
import cz.colormemory.kdysem.framework.IGame;
import cz.colormemory.kdysem.framework.IListener;
import java.awt.Point;





/*******************************************************************************
 * Třída {@code Game} je jedináček a představuje základní funkčnost celé hry.
 * Je vlastně jejím správcem. Ostatní přímo nesouvisející třídy se u
 * ní mohou přihlásit jako posluchači grafiky, zvuku apod.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Game implements IGame
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedináčka */
    private static final Game SINGLETON = new Game();

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Vysílač zpráv o změnách pro posluchače */
    private final Broadcaster BROADCASTER = new Broadcaster();

//== VARIABLE INSTANCE ATTRIBUTES ==============================================
    
    /** Odkaz na Správce herních stavů */
    private StateManager stateManager;
    
    /** Odkaz na executora - vykonávací třída, zajištuje herní běh */
    private Executor executor;

    /** Odkaz na herní inicializátor */
    private SerializeManager serializeManager;
    
    /** Pomocná proměná, které kontroluje, zda-li již hra byla inicializována */
    private boolean initialized = false;
    
    
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda vrátí odkaz na jedináčka.
     *
     * @return odkaz na jedináčka
     */
    public static Game getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Privátní konstruktor zabraňující vytvoření instance
     */
    private Game()
    {
        
        
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí odkaz na Správce herních stavů
     * 
     * @return the StateManager, pokud hra ještě není inicializovaná pomocí 
     * metody initialize(), tak vrátí null
     */
    public StateManager getStateManager() {
        if(!initialized){
            initialize();
        }
        return stateManager;
    }
    
    /***************************************************************************
     * Vrátí odkaz na Správce herních stavů
     * 
     * @return Executor, pokud hra ještě není inicializovaná pomocí 
     * metody initialize(), tak vrátí null
     */
    public Executor getExecutor() {
        if(!initialized){
            initialize();
        }
        return executor;
    }
    
    /***************************************************************************
     * Vrátí odkaz na Správce herních stavů
     * 
     * @return Save Load Manager, pokud hra ještě není inicializovaná pomocí 
     * metody initialize(), tak vrátí null
     */
    public SerializeManager getSerializeManager(){
        if(!initialized){
            initialize();
        }
        return serializeManager;
    }
    
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Metoda zinicializuje herní obejtky a uvede ji do stavu potřebného
     * pro začátek hry
     */
    @Override
    public boolean initialize()
    {
        /* Pomocná kontrolní proměnná. Zbaraňuje volání ostatních příkazu, dokud
         * se nespustí tato metoda. Současně se ale nemůže změnit až na konci 
         * metody, protože by nefunugovali inicializace podobjektů. Nicméně tím,
         * že je metoda už spuštěné, můžeme přepokládám, že doběhne v pořádku.
         */
        initialized = true;
        
        /* Inicializuje ostatní třídy, které potřebují již existující instaci game, nutné v tomto pořadí! */
        stateManager = new StateManager();
        executor = new Executor();
        serializeManager = new SerializeManager();
        
        
        try {
            // inicializuje configuraci dle konfiguračního souboru
            serializeManager.initialize();
        } catch (GameInitializeException ex) {
            System.out.println("==============================================="
                    + "\nException: " + ex.getMessage());
            ex.printStackTrace();
            initialized = false;
        }
        
        return initialized;
    }
    
    
    /***************************************************************************
     * Uloží aktuální stav hry.
     */
    @Override
    public void save() {
        try {
            serializeManager.save();
        } catch (GameControlException ex) {
            System.out.println("==============================================="
                    + "\nException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    /***************************************************************************
     * Naloaduje instance uložené hry
     */
    public void load(){
        try {
            serializeManager.load();
        } catch (GameInitializeException ex) {
            System.out.println("==============================================="
                    + "\nException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /***************************************************************************
     * Zpracuje souřadnice dotyku na displayi.
     *
     * @param point souřadnice dotyku
     */
    @Override
    public boolean processTouch(Point point)
    {
        if(!initialized){
            initialize();
        }
        return executor.processTouch(point);
    }


    /***************************************************************************
     * Přihlásí posluchače k vysílačí zpráv.
     *
     * @param listener posluchač, který má být přihlášen
     */
    @Override
    public void addListener(IListener listener)
    {
        BROADCASTER.addListener(listener);
    }


    /***************************************************************************
     * Odhlásí posluchače od vysílače
     *
     * @param listener posluchač, který má být odstraněn
     */
    @Override
    public void removeListener(IListener listener)
    {
        BROADCASTER.removeListener(listener);
    }


    /***************************************************************************
     * Informuje posluchače o změně
     */
    @Override
    public void notifyListeners()
    {
        BROADCASTER.notifyListeners();
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
//        Game inst = new Game();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
