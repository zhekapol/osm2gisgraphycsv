/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zuq.osm2gisgraphycsv.xml;

import java.util.Map;

/**
 *
 * @author Willy Tiengo
 */
public class OSM {

    private Map<String, OSMNode> nodes;
    private Map<String, Way> ways;
    private Map<String, Relation> relations;

    public OSM(Map<String, OSMNode> nodes, Map<String, Way> ways,
            Map<String, Relation> relations) {
        this.nodes = nodes;
        this.ways = ways;
        this.relations = relations;
    }

    public Map<String, OSMNode> getNodes() {
        return nodes;
    }

    public Map<String, Relation> getRelations() {
        return relations;
    }

    public Map<String, Way> getWays() {
        return ways;
    }
}
