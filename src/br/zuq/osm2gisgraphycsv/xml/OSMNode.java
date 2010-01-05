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
public class OSMNode extends AbstractNode {

    public String lat;
    public String lon;
    public List<Tag> tags;

    public OSMNode(String id, String visible, String timestamp, String version, String changeset, String user, String uid, String lat, String lon, List<Tag> tags) {
        super(id, visible, timestamp, version, changeset, user, uid);
        this.lat = lat;
        this.lon = lon;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OSMNode other = (OSMNode) obj;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
