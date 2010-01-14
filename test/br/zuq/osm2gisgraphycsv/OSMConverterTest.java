/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.zuq.osm2gisgraphycsv;

import br.zuq.osm2gisgraphycsv.xml.OSM;
import br.zuq.osm2gisgraphycsv.xml.parser.OSMParser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zuq
 */
public class OSMConverterTest {

    public OSMConverterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of main method, of class OSMConverter.
     */
    @Test
    public void testMain() {
        OSM osm;
        try {
            osm = OSMParser.parse("/home/zuq/Downloads/map.osm");

            GisgraphyCSVEncoder.writeCSVToFile("BR.txt", GisgraphyCSVEncoder.toCSV(osm));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}