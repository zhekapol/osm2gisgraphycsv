/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zuq.osm2gisgraphycsv;

import br.zuq.osm2gisgraphycsv.xml.OSM;
import br.zuq.osm2gisgraphycsv.xml.parser.OSMParser;

/**
 *
 * @author zuq
 */
public class OSMConverter {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Wrong number of argument. "
                    + "OSMConverter [srcfile] [destfile]");
            return;
        }

        OSM osm;
        try {
            osm = OSMParser.parse(args[0]);

            GisgraphyCSVEncoder.writeCSVToFile(args[1], GisgraphyCSVEncoder.toCSV(osm));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
