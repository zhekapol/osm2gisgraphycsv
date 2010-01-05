/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zuq.osm2gisgraphycsv;

/**
 *
 * @author Willy Tiengo
 */
public class CSVItem {

    public String id; // UniqueId of the street(always null for the moment)
    public String name;
    public String location; // The middle point of the street in HEXEWKB
    public String length; // meters
    public String countrycode; // The iso3166 Alpha2 Code of the country
    public String gid; // GlobalId (not use yet)
    public String type; // The type of street (see bellow)
    public String oneway; // Whether the street is a one way street
    public String shape; // The delimitation of the street in HEXEWKB

    public CSVItem(String id, String name, String location, String length, 
            String countrycode, String gid, String type, String oneway, String shape) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.length = length;
        this.countrycode = countrycode;
        this.gid = gid;
        this.type = type;
        this.oneway = oneway;
        this.shape = shape;
    }

    @Override
    public String toString() {
        return id + "\t" +
                name +  "\t" +
                location +  "\t" +
                length + "\t" +
                countrycode + "\t" +
                 gid + "\t" +
                 type + "\t" +
                 oneway +  "\t" +
                 shape +  "\n";
    }



    public enum StreetType {
        BYWAY,
        MINOR,
        SECONDARY_LINK,
        CONSTRUCTION,
        UNSURFACED,
        BRIDLEWAY,
        PRIMARY_LINK,
        LIVING_STREET,
        TRUNK_LINK,
        STEPS,
        PATH,
        ROAD,
        PEDESTRIAN,
        TRUNK,
        MOTROWAY,
        CYCLEWAY,
        MOTORWAY_LINK,
        PRIMARY,
        FOOTWAY,
        TERTIARY,
        SECONDARY,
        TRACK,
        UNCLASSIFIED,
        SERVICE,
        RESIDENTIAL
    }
}
