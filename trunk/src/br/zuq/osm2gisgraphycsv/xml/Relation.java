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
public class Relation extends AbstractNode {

    public List<Member> members;
    public List<Tag> tags;

    public Relation(String id, String visible, String timestamp, String version, String changeset, String user, String uid, List<Member> members, List<Tag> tags) {
        super(id, visible, timestamp, version, changeset, user, uid);
        this.members = members;
        this.tags = tags;
    }
}
