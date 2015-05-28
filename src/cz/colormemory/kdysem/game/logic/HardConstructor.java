/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.commands.ActionList;
import cz.colormemory.kdysem.game.entities.Area;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.entities.Placement;
import cz.colormemory.kdysem.game.entities.Room;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;

/*******************************************************************************
 * Instance of class {@code HardConstructor} represents 
 * 
 * @author Avatar
 * @version 1.00 - 05/15
 */
public class HardConstructor
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
    public HardConstructor(){
        
        RoomManager rm = Game.getInstance().getRoomManager();
        GameObjectManager gom = Game.getInstance().getGameObjectManager();
        
        Room laborator = rm.createRoom("01", "Laboratoř", "Laboratoř, kde naleznete šifru", Area.PRESENT_PRAGUE, new Point(0, 0));
        Room kancelar = rm.createRoom("02","Kancelář","Kancelář profesora, zde najdete hrnek", Area.PRESENT_PRAGUE, new Point(0, 0));
        Room hala = rm.createRoom("03", "Hala", "Hala univerzity, moc toho tu není", Area.PRESENT_PRAGUE, new Point(0, 0));
        Room predUniverzitou = rm.createRoom("04", "Před univerzitou", "velké prsotorné nádvoří", Area.PRESENT_PRAGUE, new Point(0, 0));
        
        
        String[] kniha1 = new String[1];
        kniha1[0] = "Nějaká historická kniha";
        
        String[] kniha2 = new String[1];
        kniha2[0] = "Asi porno z dvacátejch let";
        
        String[] kniha3 = new String[1];
        kniha3[0] = "Kuchařka babica";
        
        String[] sifra = new String[1];
        sifra[0] = "Velmi podstatná část hry";
        
        String[] kancelarT = new String[1];
        kancelarT[0] = "Přejde do kanceláíře";
        
        laborator.addObjectToRoom(gom.createItem(IDManager.generate(), "Kniha 1", kniha1, new Placement(1, new Point(-10, -10)), false, false, false));
        laborator.addObjectToRoom(gom.createItem(IDManager.generate(), "kniha 2", kniha2, new Placement(1, new Point(-10, -10)), false, false, false));
        laborator.addObjectToRoom(gom.createItem(IDManager.generate(), "kniha 3", kniha3, new Placement(1, new Point(-10, -10)), false, false, false));
        laborator.addObjectToRoom(gom.createItem(IDManager.generate(), "Šifra",sifra, new Placement(1, new Point(-10, -10)), false, false, false));
        laborator.addObjectToRoom(gom.createTransporter(IDManager.generate(), "Kancelář", kancelarT,new Placement(1, new Point(-10, -10)), "02", false));
        
        
        String[] hrnek = new String[2];
        hrnek[0] = "Hrnek. K čemu by mi byl. Automat má snad kelímky ne?";
        hrnek[1] = "Tak přecejnom se nakonec hodil.";
        
        String[] profesor = new String[1];
        profesor[0] = "Profesor je čilý chlapík";
        
        String[] laboratorT = new String[1];
        laboratorT[0] = "Nemůžeš do laboratoře. Profesor chtěl kafe.";
        
        String[] halaT = new String[1];
        halaT[0] = "Přesune do haly";
        
        Item hrnekI = gom.createItem(IDManager.generate(), "Hrnek", hrnek, new Placement(1, new Point(2, 12)), false, false, false);
        
        kancelar.addObjectToRoom(hrnekI);
        kancelar.addObjectToRoom(gom.createPerson(IDManager.generate(), "Profesor", profesor, new Placement(1, new Point(-10, -10)), null));
        kancelar.addObjectToRoom(gom.createTransporter(IDManager.generate(), "Laboratoř", laboratorT, new Placement(2, new Point(5, 3)),"01", true));
        kancelar.addObjectToRoom(gom.createTransporter(IDManager.generate(), "Hala", halaT, new Placement(1, new Point(18, 3)), "03", false));
        
        
        String[] kavovar = new String[4];
        kavovar[0] = "Kde si myslíš že seš? Kafe zadara nám tu teda fakt nedávaj.";
        kavovar[1] = "A jéjé voni ty kelímky fakt došly.";
        kavovar[2] = "Teď už nemám žádné mince a bankovky to asi nevezme.";
        kavovar[3] = "Fuj, pořád je to hnusný. No radši mu to už donesu.";

        
        String[] vratny = new String[1];
        vratny[0] = "Dědek jeden";
        
        String[] kancelarT2 = new String[1];
        kancelarT2[0] = "Přesune do kanceláře";
        
        String[] predUniverzitouT = new String[1];
        predUniverzitouT[0] = "Vyjde ven z univerzity";
        
        Item automatNaKafe = gom.createItem(IDManager.generate(), "Automat na kafe", kavovar, new Placement(1, new Point(2, 10)), false, false, true);
        
        hala.addObjectToRoom(automatNaKafe);
        hala.addObjectToRoom(gom.createPerson(IDManager.generate(), "Vrátný", vratny, new Placement(1, new Point(-10, -10)), null));
        hala.addObjectToRoom(gom.createTransporter(IDManager.generate(), "Kancelář", kancelarT2, new Placement(1, new Point(6, 3)),"02",false));
        hala.addObjectToRoom(gom.createTransporter(IDManager.generate(), "Před univerzitou", predUniverzitouT, new Placement(1, new Point(18,3)),"04", true));
        
        
        // Přidání věcí pro inventář
        String[] mince = new String[1];
        mince[0] = "Toto je mince do automatu";
        
        String[] bankovky = new String[1];
        bankovky[0] = "Bankovky";
        
        Inventory inv = Game.getInstance().getInventory();
        
        Item minceI = gom.createItem(IDManager.generate(), "Mince", mince, new Placement(1, new Point(22, 10)), true, true, true);
        
        
        inv.addItem(gom.createItem(IDManager.generate(), "Bankovky", bankovky, new Placement(1, new Point(4, 18)), true, true, false));
        inv.addItem(minceI);
        
        
        //konec přidání věcí pro inventář
        
        
        //inicializace akcí
        automatNaKafe.addInteractAction(minceI.getId(), ActionList.ALLOW_PICKUPABILITY, hrnekI.getId());
        
        Area.setActualArea(Area.PRESENT_PRAGUE);
        Game.getInstance().getRoomManager().setCurrentRoom(kancelar);
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
    
    /***************************************************************************
    * Třída {@code IDManager} zajišťuje generování a unikátnost generovaných ID
    * pro každý objekt. ID zatím vě hře jsou prakticky napoužitelné prosot se
    * případně bude muset vylepšit přístupnost kolekce a jejich generování.
    *
    * @author  André HELLER
    * @version 1.00 — 02/2014
    */
   private static class IDManager
   {
   //== CONSTANT CLASS ATTRIBUTES ==============================================

       /** Kolekce všech dosud vytvořených ID */
       private static final Collection<Integer> IDS = new HashSet<>();

   //== VARIABLE CLASS ATTRIBUTES ==============================================
   //== STATIC INITIALIZER (CLASS CONSTRUCTOR) =================================
   //== CLASS GETTERS AND SETTERS ==============================================
   //== OTHER NON-PRIVATE CLASS METHODS ========================================

       /************************************************************************
        * Vygeneruje id číslo na základě dosud vytvořených čísel.
        * Tak aby bylo unikátní.
        *
        * @return identifikační číslo
        */
       public static String generate()
       {
           int number = IDS.size()+1;
           IDS.add(number);

           return number+"";
       }

   //###########################################################################
   //== PRIVATE AND AUXILIARY CLASS METHODS ====================================
   //== EMBEDDED TYPES AND INNER CLASSES =======================================
   //== TESTING CLASSES AND METHODS ============================================
   }
    
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        HardConstructor inst = new HardConstructor();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




