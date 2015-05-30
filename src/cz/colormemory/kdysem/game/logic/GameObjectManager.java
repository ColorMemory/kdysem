/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.entities.Dialog;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.entities.Person;
import cz.colormemory.kdysem.game.entities.Placement;
import cz.colormemory.kdysem.game.entities.Transporter;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;





/*******************************************************************************
 * Instance třídy {@code GameObjectManager} představuje správce herních objektů.
 * Třída obsahuje mapu všech herních objektů {@link AGameObject} aktuální lokace
 * {@link Area}. Pro které má vytvořené tovární metody. 
 * 
 * Používá se pro rychlý přístup k objektům. Využívájí toho například třídy 
 * herních akcí {@link ActionList}.
 * 
 * Jinak za svoje herní objekty odpovídají přímo jednotlivé místnosti {@link Room}.
 * 
 * @author Avatar
 * @version 1.00 - 05/15
 */
public class GameObjectManager
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
    
    /** Mapa všechn objektů lokace */
    private Map<String, AGameObject> GAME_OBJECTS = new HashMap<>();
    
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================


    /***************************************************************************
     * Implicitní konstruktor
     */
    public GameObjectManager(){
        
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
    
    /***********************************************************************
     * Vrátí herní objekt z kolekce na základě jeho klíče.
     * 
     * @param key klíč objektu
     * @return herní objekt
     */
    public AGameObject getGameObject(String key){
        return GAME_OBJECTS.get(key);
    }
    
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
    
    public AGameObject createObject(String type, String key, String name, String[] description, int priority, Point position,
                           Object ... params){
        //@todo Tady to přetypování mi přijde divné, ještě někdy ověřit
        
        AGameObject gameObject;
        
        switch(type){
            case "item" :
                boolean pickability = (boolean) params[0],
                        useability = (boolean) params[1],
                        interactability = (boolean) params[2];
                
                gameObject = this.createItem(
                        key, name, description, new Placement(priority, position), 
                        pickability, 
                        useability, 
                        interactability
                );
                break;
                
            case "transporter" :
                String targetRoom = (String) params[0];
                boolean locked = (boolean) params[1];
                
                gameObject = this.createTransporter(
                        key, name, description, new Placement(priority, position), 
                        targetRoom, 
                        locked
                );
                break;
                
            case "person" :
                Dialog dialog = (Dialog) params[0];
                
                gameObject = this.createPerson(
                        key, name, description, new Placement(priority, position), 
                        dialog
                );
                break;
            
            default:
                throw new IllegalArgumentException("Tento typ objektu neexistuje.");
        }
        
        return gameObject;
    }
    
    /***************************************************************************
     * Tovární metoda. Vytvoří {@link Item} a přidá ho do mapy objektů.
     * 
     * @param key klíč objektu
     * @param name název
     * @param description pole popisků
     * @param placement umístění
     * @param pickability zvednutelnost
     * @param usability použitelnost
     * @param interactability Interakčnost
     * @return vytvořený Item
     */
    private Item createItem(String key, String name, String[] description, Placement placement,
                           boolean pickability, boolean usability, boolean interactability){
        Item item = new Item(key, name, description, placement, pickability, usability, interactability);
        addGameObject(item);
        
        return item;
    }
    
    
    /***************************************************************************
     * Tovární metoda. Vytvoří {@link Tranporter} a přidá ho do mapy objektů.
     * 
     * @param key klíč objektu
     * @param name název
     * @param description pole popisků
     * @param placement umístění
     * @param targetRoom klíč cílové místnosti
     * @param locked para,etr uzamčení
     * @return vytvořený Transporter
     */
    private Transporter createTransporter(String key, String name, String[] description, Placement placement,
                           String targetRoom, boolean locked){
        Transporter transporter = new Transporter(key, name, description, placement, targetRoom, new Point(-1,-1), locked);
        addGameObject(transporter);
        
        return transporter;
    }
    
    
    /***************************************************************************
     * Tovární metoda. Vytvoří osobu {@link Person} a přidá jí do mapy objektů.
     * 
     * @param key klíč objektu
     * @param name název
     * @param description pole popisků
     * @param placement umístění
     * @param dialog dislog osoby
     * @return vytvořenou osobu
     */
    private Person createPerson(String key, String name, String[] description, Placement placement, Dialog dialog){
        Person person = new Person(key, name, description, dialog);
        addGameObject(person);
        
        return person;
    }
    
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
    
    /***************************************************************************
     * Přidá herní objekt do mapy
     * 
     * @param gameObject 
     */
    private void addGameObject(AGameObject gameObject){
        GAME_OBJECTS.put(gameObject.getId(), gameObject);
    }
    
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        GameObjectManager inst = new GameObjectManager();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




