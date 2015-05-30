/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.serialize;

import cz.colormemory.json.JSONException;
import cz.colormemory.json.JSONObject;
import static cz.colormemory.kdysem.data.TextConstants.FILE_IO_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.FILE_JSON_PARSE_ERROR;
import static cz.colormemory.kdysem.data.TextConstants.FILE_NOT_FOUND;
import cz.colormemory.kdysem.game.exceptions.GameInitializeException;
import cz.colormemory.kdysem.game.exceptions.GameSaveException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*******************************************************************************
 * Instance of class {@code FileManager} represents 
 * 
 * @author Avatar
 * @version 1.00 - 05/15
 */
public class FileManager
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
    public FileManager(){
        
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
    
    /***************************************************************************
     * Přečte zadaný soubor a přeparsuje ho na JSON objekt, který vrátí
     * 
     * @param folder cesta k souboru
     * @param file název douboru
     * @return JSONObject reprezentující přečtený soubor
     * @throws GameInitializeException V případě, kdy nebyl soubor nalezen, 
     * nepodařil přečíst nebo obsahoval nevalidní JSOn a nešel přeparsovat.
     */
    public JSONObject read(String folder, String file) throws GameInitializeException{
        // Otevře konfigurační soubor
        File configFile = new File(folder,file);
        
        // Výsledný string pro JSON Constructor
        String fileContent;
        
        
        if(!configFile.exists()){
            //Soubor neexistuje
            throw new GameInitializeException(String.format(FILE_NOT_FOUND,folder+"\\"+file));
        }
        
        try {
            BufferedReader in = new BufferedReader(new FileReader(configFile));
            StringBuilder sb =  new StringBuilder();
            
            // Proiteruje soubor a vytvoří z něj dlouhý string
            while(true){
                String line = in.readLine();

                if(line == null){
                    break;
                }

                sb.append(line).append('\n');
            }
        
            fileContent = sb.toString();
            
        } catch (IOException ex) {
            // Soubor se nepovedlo přečíst
            throw new GameInitializeException(String.format(FILE_IO_ERROR, folder+"\\"+file) + ex.getMessage(),ex);
        }
        

        /* Z nějakého důvodu, je občas prvním znakem nějaký bordel, zatím 
         * nevím proč. Každopádně, pokud první znak není počáteční JSON "{", 
         * zjistí kde je a string ořízne */
        if(!fileContent.startsWith("{")){
           fileContent = fileContent.substring(fileContent.indexOf("{"));
        }
        
        
        try {
            return new JSONObject(fileContent);
            
        } catch (JSONException ex) {
            //Přečtený soubor se nepodařilo přeparsovat na JSON
            throw new GameInitializeException(String.format(FILE_JSON_PARSE_ERROR,folder+"\\"+file) + ex.getMessage(),ex);
        }
    }
    
    
    public boolean write(String folder, String file, String content) throws GameSaveException{
        File areaFile = new File(folder,file);
        if(!areaFile.exists()){
            try {
                areaFile.createNewFile();
            } catch (IOException ex) {
                throw new GameSaveException(String.format("Soubor %s neexistuje a nepodařilo se ani jeho vytvoření.\n",folder+"\\"+file) + ex.getMessage(),ex);
            }
        }
        
        BufferedWriter out = null;
        
        try {
            out = new BufferedWriter(new FileWriter(areaFile));
            out.write(content);
            
        } catch (IOException ex) {
            throw new GameSaveException(String.format("Do souboru %s se nedá zapisovat.\n",folder+"\\"+file) + ex.getMessage(),ex);
        }
        finally {
            
            try {
                if(out != null) {
                    out.close();
                }
                
            } catch (IOException ex) {
                throw new GameSaveException(String.format("Soubor %s se nepodařilo uzavřít.\n",folder+"\\"+file) + ex.getMessage(),ex);
            }
        }

        return true;
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
//        FileManager inst = new FileManager();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




