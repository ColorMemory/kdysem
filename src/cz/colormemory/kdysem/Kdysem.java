/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.colormemory.kdysem;

import cz.colormemory.json.JSONConstructor;
import cz.colormemory.json.JSONException;
import java.util.logging.Level;
import java.util.logging.Logger;





/*******************************************************************************
 * Třída {@code Kdysem} je hlavní třídou projektu, která zatím nic neumím a
 * vzhledem k implementaci na android asi ani umět nebude :P
 *
 * @author  André HELLER
 * @version 1.00 — 02/2014
 */
public class Kdysem
{
    /***************************************************************************
     * Method starting the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        JSONConstructor json = new JSONConstructor();
        try {

            json.object()
                  .key("lab")
                    .object()
                        .key("name").value("Laboratoř s texty")
                        .key("description").value("Výchozí místnost")
                        .key("floor")
                            .array()
                                .value(0).value(0)
                                .value(150).value(0)
                                .value(150).value(150)
                                .value(0).value(150)
                            .endArray()
                        .key("defaulPlayerPosition")
                            .array()
                                .value(10).value(10)
                            .endArray()
                        .key("neighbours")
                            .array()
                                .value("cabinet")
                            .endArray()
                        .key("items")
                            .array()
                                .object()
                                    .key("name").value("Sifra")
                                    .key("description").value("Muheeh Pointa celé hry")
                                .endObject()
                                .object()
                                    .key("name").value("Kniha")
                                    .key("description").value("Důležitá kniha")
                                .endObject()
                            .endArray()
                    .endObject()
                  .endObject();

            System.out.println(json.toString());
        }
        catch (JSONException ex) {
            Logger.getLogger(Kdysem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /** Private constructor preventing instance creation.*/
    private Kdysem() {

    }
}
