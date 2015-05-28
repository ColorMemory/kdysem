/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.commands;

/*******************************************************************************
 * Instance of class {@code AAction} represents 
 * 
 * @author Avatar
 * @version 1.00 - 05/15
 */
public abstract class AAction
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================


    /***************************************************************************
     * Implicitní konstruktor
     */
    protected AAction(){
    }


//== ABSTRACT METHODS ==========================================================
    
    /***************************************************************************
     * Vykoná zadanou akci
     */
    public abstract void execute(String gameObjectId);
    
    
    /***************************************************************************
     * Vrátí nízev akce
     * 
     * @return jméno akce
     */
    public abstract String getName();
    
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
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
//        AAction inst = new AAction();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




