/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.zuq.osm2gisgraphycsv.test;

import br.zuq.osm2gisgraphycsv.GisgraphyCSVEncoder;
import br.zuq.osm2gisgraphycsv.xml.OSM;
import br.zuq.osm2gisgraphycsv.xml.parser.OSMParser;

/**
 *
 * @author Willy Tiengo
 */
public class WKGTest {

    public static void main(String[] args) throws Exception {
        OSM osm = OSMParser.parse("./src/map.osm");

        GisgraphyCSVEncoder.writeCSVToFile("src/BR.txt", GisgraphyCSVEncoder.toCSV(osm));
    }
}
