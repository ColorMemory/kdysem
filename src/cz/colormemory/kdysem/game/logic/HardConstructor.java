/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.logic;

import cz.colormemory.kdysem.game.entities.Placement;
import cz.colormemory.kdysem.game.entities.Room;
import cz.colormemory.kdysem.game.entities.Area;
import cz.colormemory.kdysem.game.entities.Transporter;
import cz.colormemory.kdysem.game.entities.Item;
import cz.colormemory.kdysem.game.entities.Person;
import java.awt.Point;

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
        
        RoomManager rm = RoomManager.getInstance();
        
        Room laborator = rm.createRoom("01", "Laboratoř", "Laboratoř, kde naleznete šifru", Area.PRESENT_PRAGUE, new Point(0, 0));
        Room kancelar = rm.createRoom("02","Kancelář","Kancelář profesora, zde najdete hrnek", Area.PRESENT_PRAGUE, new Point(0, 0));
        Room hala = rm.createRoom("03", "Hala", "Hala univerzity, moc toho tu není", Area.PRESENT_PRAGUE, new Point(0, 0));
        Room predUniverzitou = rm.createRoom("04", "Před univerzitou", "velké prsotorné nádvoří", Area.PRESENT_PRAGUE, new Point(0, 0));
        
        
        String[] kniha1 = new String[1];
        kniha1[0] = "Něajká historická kniha";
        
        String[] kniha2 = new String[1];
        kniha2[0] = "Asi porno z dvacátejch let";
        
        String[] kniha3 = new String[1];
        kniha3[0] = "Kuchařka babica";
        
        String[] sifra = new String[1];
        sifra[0] = "Velmi podstatná část hry";
        
        String[] kancelarT = new String[1];
        kancelarT[0] = "Přejde do kanceláíře";
        
        laborator.addObjectToRoom(new Item("Kniha 1", kniha1));
        laborator.addObjectToRoom(new Item("kniha 2", kniha2));
        laborator.addObjectToRoom(new Item("kniha 3", kniha3));
        laborator.addObjectToRoom(new Item("Šifra",sifra));
        laborator.addObjectToRoom(new Transporter("Kancelář", kancelarT, "02"));
        
        
        String[] hrnek = new String[1];
        hrnek[0] = "Hrnek do nějš se dá udělat káva";
        
        String[] profesor = new String[1];
        profesor[0] = "Profesor je čilý chlapík";
        
        String[] laboratorT = new String[1];
        laboratorT[0] = "Nejprve udělěj kafe";
        
        String[] halaT = new String[1];
        halaT[0] = "Přesune do haly";
        
        kancelar.addObjectToRoom(new Item("Hrnek", hrnek, new Placement(1, new Point(2, 12)), false, false));
        kancelar.addObjectToRoom(new Person("Profesor", profesor, null));
        kancelar.addObjectToRoom(new Transporter("Laboratoř", laboratorT, new Placement(2, new Point(5, 3)),"01", true));
        kancelar.addObjectToRoom(new Transporter("Hala", halaT, new Placement(1, new Point(18, 3)), "03", false));
        
        
        String[] kavovar = new String[1];
        kavovar[0] = "Kávovaru došly hrnky";
        
        String[] vratny = new String[1];
        vratny[0] = "Dědek jeden";
        
        String[] kancelarT2 = new String[1];
        kancelarT2[0] = "Přesune do kanceláře";
        
        String[] predUniverzitouT = new String[1];
        predUniverzitouT[0] = "Vyjde ven z univerzity";
        
        hala.addObjectToRoom(new Item("Kávovar", kavovar, new Placement(1, new Point(2, 10)), false, false));
        hala.addObjectToRoom(new Person("Vrátný", vratny, null));
        hala.addObjectToRoom(new Transporter("Kancelář", kancelarT2, new Placement(1, new Point(6, 3)),"02",false));
        hala.addObjectToRoom(new Transporter("Před univerzitou", predUniverzitouT, new Placement(1, new Point(18,3)),"04", true));
        
        Area.setActualArea(Area.PRESENT_PRAGUE);
        RoomManager.getInstance().setCurrentRoom(kancelar);
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
//        HardConstructor inst = new HardConstructor();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}




