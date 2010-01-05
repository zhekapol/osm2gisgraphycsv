/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.zuq.osm2gisgraphycsv.xml;

import java.util.List;

/**
 *
 * @author Willy Tiengo
 */
public class Way extends AbstractNode {

    public List<OSMNode> nodes;
    public List<Tag> tags;

    public Way(String id, String visible, String timestamp, String version, String changeset, String user, String uid, List<OSMNode> nodes, List<Tag> tags) {
        super(id, visible, timestamp, version, changeset, user, uid);
        this.nodes = nodes;
        this.tags = tags;
    }


    public boolean isHighway() {
        for (Tag tag : tags) {
            if (tag.k.equals("highway")) {
                return true;
            }
        }
        return false;
    }
    
}
