/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.serialize;

import cz.colormemory.json.JSONException;
import cz.colormemory.json.JSONObject;
import cz.colormemory.kdysem.game.exceptions.GameSaveException;
import cz.colormemory.kdysem.game.logic.Game;
import java.io.File;

/*******************************************************************************
 * Instance of class {@code SaveManager} represents 
 * 
 * @author Avatar
 * @version 1.00 - 05/15
 */
public class SaveManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /** Odkaz na hru */
    private final Game GAME;
    
    private final FileManager FM;
    
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================


    /***************************************************************************
     * Uloží odkaz na hru.
     */
    public SaveManager(Game game, FileManager fm){
        this.GAME = game;
        this.FM = fm;
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
    
    /***************************************************************************
     * Uloží aktuální hru
     * 
     * @throws cz.colormemory.kdysem.game.exceptions.GameSaveException
     */
    public boolean save() throws GameSaveException{
        
        /* @todo Save config - neukládá konfiguraci*/
       
        saveRooms(GAME.getProperty("folder.config"), GAME.getActualArea().getName());
        
        return true;
    }
    
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Uloží aktuální lokaci do souborů.
     */
    private void saveRooms(String configFolderPath, String actualArea) throws GameSaveException {
        try {
            JSONObject gameJSON =  GAME.toJSON();
            
            JSONObject roomsJSON = gameJSON.getJSONObject("rooms");
            JSONObject objectsJSON = gameJSON.getJSONObject("objects");
            JSONObject inventoryJSON = gameJSON.getJSONObject("inventory");
            
            FM.write(configFolderPath + File.separator + "saves" + File.separator + actualArea, actualArea+".rooms",roomsJSON.toString(2));
            FM.write(configFolderPath + File.separator + "saves" + File.separator + actualArea, actualArea+".objects",objectsJSON.toString(2));
            FM.write(configFolderPath + File.separator + "saves", "inventory.objects",inventoryJSON.toString(2));            
            
        } catch (JSONException ex) {
            throw new GameSaveException("Hra nevrátila validní JSON" + ex.getMessage(),ex);
        }
    }
    
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        SaveManager inst = new SaveManager();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




