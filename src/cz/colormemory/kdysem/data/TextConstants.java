/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.data;

/*******************************************************************************
 * Třída {@code TextConstants} představuje schránku na texty použité ve hře. 
 * Aby se dal snáze měnit jazyk.
 * 
 * @author André Heller
 * @version 1.00 - 05/15
 */
public final class TextConstants
{
//== CONSTANT CLASS ATTRIBUTES =================================================
    
    /** Serializační vyjímky */
    public static final String
        FILE_NOT_FOUND = "Soubor %s nebyl nalezen.\n",
        FILE_IO_ERROR = "Soubor %s se nepodařilo přečíst\n",
        FILE_JSON_PARSE_ERROR = "Soubor %s se nepovedlo přeparsovat na JSON.\n",
        
        CONFIG_CREATING_SYNTAX_ERROR = "Při vytváření konfigurace se někde posral JSON.\n",
        
        AREA_CANT_CREATE = "Nepodařilo se vytvořit lokaci: %s\n",
        ROOM_CANT_CREATE = "Nepodařilo se vytvořit místnost: %s\n",
        GAMEOBJECT_CANT_ADD = "Do místnosti %s se nepodařilo přidat objekt %s\n",
        INSTANCES_CREATING_SYNTAX_ERROR = "Při vytváření instancí mísností a herních objektů se někde posral JSON.\n",
            
        ACTIONS_CREATING_SYNTAX_ERROR = "Při vytváření závislostních akcí objektů se někde posral JSON.\n",
        ACTIONS_DOES_NOT_EXIST = "Při vytváření akci došlo k chybě. Jedna z definovaných akcí neexistuje.\n",
            
            
            
        AREA_DOES_NOT_EXIST = "Inicializovaná lokace \"%s\" neexistuje.\n.",
        AREA_FILE_IO_ERROR = "Soubory lokace \"%s\" se nepodařilo přečíst.\n",
        AREA_FILE_WRONG_SYNTAX = "Soubory lokace \"%s\" jsou v nesprávném tvaru.\n",
        AREA_JSON_IS_NULL = "Soubory lokace se špatně přečetli a nedokázali vytvořit instanci JSONObjectu tak, že to neochytila ani IO ani JSON vyjímka.\n KOMU SE TOHLE ZOBRAZÍ MÁ U MĚ PIVO. StackTrace a okolnosti prosím zapsat!!\n",
        
        SAVE_FILE_CREATE_ERROR = "Hra se nepodařila uložit do sobouru.\n",
        SAVE_FILE_WRONG_SYNTAX = "Hra generuje nevalidní JSON, nejde tedy uložit.\n";
        
    
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
    private TextConstants(){
        
    }


//== ABSTRACT METHODS ==========================================================
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
//        TextConstants inst = new TextConstants();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




