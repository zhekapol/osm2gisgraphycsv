package br.zuq.osm2gisgraphycsv;

import br.zuq.osm2gisgraphycsv.xml.OSMNode;
import br.zuq.osm2gisgraphycsv.xml.Tag;
import br.zuq.osm2gisgraphycsv.xml.Way;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.io.WKBWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import br.zuq.osm2gisgraphycsv.util.LatLongUtil;
import br.zuq.osm2gisgraphycsv.xml.OSM;
import com.vividsolutions.jts.geom.PrecisionModel;

/**
 *
 * @author Willy Tiengo
 */
public class GisgraphyCSVEncoder {

    // Public Methods ----------------------------------------------------------

    public static String encode(List<CSVItem> list) {
        StringBuffer buffer;

        buffer = new StringBuffer();
        for (CSVItem item : list) {
            buffer.append(item);
        }

        return buffer.toString();
    }

    public static List<CSVItem> toCSV(OSM osm) throws Exception {
        CSVItem item;
        List<CSVItem> list;

        list = new ArrayList<CSVItem>();

        for (String id : osm.getWays().keySet()) {
            Way way = osm.getWays().get(id);

            if (way.isHighway()) {

                String oneway = "false";
                if (getTagValue("oneway", way.tags) != null
                        && getTagValue("oneway", way.tags).equals("yes")) {

                    oneway = "true";
                }

                item = new CSVItem(way.id,
                        getTagValue("name", way.tags),
                        wayMiddle(way.nodes),
                        wayLength(way.nodes),
                        "BR", "",
                        getTagValue("highway", way.tags),
                        oneway,
                        shape(way.nodes));

                list.add(item);
            }
        }

        return list;
    }

    public static void writeCSVToFile(String dest, List<CSVItem> list) throws Exception {
        FileOutputStream out;

        out = new FileOutputStream(dest);
        out.write(encode(list).getBytes());
        out.close();
    }

    // Private Methods ---------------------------------------------------------

    private static String wayLength(List<OSMNode> nodes) {
        double length = 0d;
        OSMNode n1, n2;

        n1 = nodes.get(0);
        for (int i = 1; i < nodes.size(); i++) {
            n2 = nodes.get(i);

            length += LatLongUtil.distance(
                    Double.parseDouble(n1.lat), Double.parseDouble(n1.lon),
                    Double.parseDouble(n2.lat), Double.parseDouble(n2.lon));

            n1 = n2;
        }

        return Double.toString(length);
    }

    private static String wayMiddle(List<OSMNode> nodes) throws Exception {
        double lenMiddle, distance, lineDistance;
        GeometryFactory fac;

        fac = new GeometryFactory();

        OSMNode n1 = null, n2 = null;
        lenMiddle = Double.parseDouble(wayLength(nodes)) / 2;
        distance = 0d;

        for (int i = 0; i < nodes.size() - 1; i++) {

            n1 = nodes.get(i);
            n2 = nodes.get(i + 1);

            System.out.println(n1.lat + ", " + n1.lon);
            System.out.println(n2.lat + ", " + n2.lon);
            lineDistance = lineDistance(n1, n2);

            if ((distance + lineDistance) > lenMiddle) {
                distance = (lenMiddle - distance) / lineDistance;
                break;
            }

            distance += lineDistance;
        }

        double lat = Double.parseDouble(n2.lat);
        double lon = Double.parseDouble(n2.lon);

        if (distance > 0.0d) {
            distance = (1 / distance);

            //   System.out.println(distance + "\t" + n2.lat + "\t" + n1.lat);

            // Baseado na prova do ponto médio
            lat = (Double.parseDouble(n2.lat) + (distance - 1) * Double.parseDouble(n1.lat)) / distance;
            lon = (Double.parseDouble(n2.lon) + (distance - 1) * Double.parseDouble(n1.lon)) / distance;
        }

        System.out.println(lat + ", " + lon);

        return WKBWriter.bytesToHex(
                new WKBWriter().write(fac.createPoint(new Coordinate(lon, lat))));
    }

    private static String getTagValue(String key, List<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.k.equals(key)) {
                return tag.v;


            }
        }
        return "";


    }

    private static Double lineDistance(OSMNode n1, OSMNode n2) {
        return LatLongUtil.distance(
                Double.parseDouble(n1.lat), Double.parseDouble(n1.lon),
                Double.parseDouble(n2.lat), Double.parseDouble(n2.lon));


    }

    private static String shape(List<OSMNode> nodes) throws Exception {
        List<LineString> coords;
        GeometryFactory fac;

        coords = new ArrayList<LineString>();
        fac = new GeometryFactory(new PrecisionModel(0.001), 4326);

        OSMNode n1, n2;
        Coordinate c1, c2;


        for (int i = 0; i
                < nodes.size() - 1; i++) {
            n1 = nodes.get(i);
            n2 = nodes.get(i + 1);

            c1 = new Coordinate(Double.parseDouble(n1.lon), Double.parseDouble(n1.lat));
            c2 = new Coordinate(Double.parseDouble(n2.lon), Double.parseDouble(n2.lat));

            coords.add(fac.createLineString(new Coordinate[]{c1, c2}));


        }

        MultiLineString mls;

        mls = fac.createMultiLineString(coords.toArray(new LineString[0]));



        return WKBWriter.bytesToHex(new WKBWriter().write(mls));

    }
}
