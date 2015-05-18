/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem.game.commands;

import cz.colormemory.kdysem.game.entities.AGameObject;
import cz.colormemory.kdysem.game.entities.Transporter;
import cz.colormemory.kdysem.game.entities.Room;
import java.awt.Point;





/*******************************************************************************
 * Třída {@code CommandTransport} představuje příkaz, který přesouvá hráče
 * z místnosti do místnosti. Jinými slovy mění aktuální místnost a zajištuje
 * vše potřebné okolo toho.
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class CommandTransport extends ACommand
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
    public CommandTransport()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vyvolá spuštení příkazu.
     *
     * @param touchObject objekt, který souvisí se spuštením (Spuštěný Transporter)
     * @return true nebo {@link IllegalArgumentException}
     */
    @Override
    public boolean execute(AGameObject touchObject)
    {
        if(touchObject instanceof  Transporter){
            Transporter transporter = (Transporter) touchObject;
            
            Room targetRoom = ROOM_MANAGER.getAllRooms().get(transporter.getTargetRoomId());
            Point targetPosition = transporter.getTargetPosition();
            
            // Pokud transporter nemá nastavené cílové souřadnice
            if(targetPosition == Transporter.NULL_TARGET_POSITION){
                targetPosition = targetRoom.getDefaultPlayerPosition();
            }

            ROOM_MANAGER.setCurrentRoom(targetRoom);
            ROOM_MANAGER.getCurrentRoom().setActiveTransportPosition(targetPosition);

            System.out.println(">>> TRANSPORTUJI!");
            
            return true;

        }
        else {
            throw new IllegalArgumentException("Tento příkaz podporuje pouze typ Transporter");
        }
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
//        CommandTransport inst = new CommandTransport();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
