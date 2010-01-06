/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zuq.osm2gisgraphycsv.xml.parser;

import br.zuq.osm2gisgraphycsv.xml.OSMNode;
import br.zuq.osm2gisgraphycsv.xml.Tag;
import br.zuq.osm2gisgraphycsv.xml.Way;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author zuq
 */
public class WayParser {

    public static boolean isWay(Node node) {
        return node.getNodeName().equals("way");
    }

    public static Way parseWay(Node wayNode, Map<String, OSMNode> nodes) {
        Way way;

        NamedNodeMap atts = wayNode.getAttributes();

        String id = atts.getNamedItem("id").getNodeValue();

        way = new Way(
                id,
                atts.getNamedItem("visible").getNodeValue(),
                atts.getNamedItem("timestamp").getNodeValue(),
                atts.getNamedItem("version").getNodeValue(),
                atts.getNamedItem("changeset").getNodeValue(),
                atts.getNamedItem("user").getNodeValue(),
                atts.getNamedItem("uid").getNodeValue(),
                getNodes(wayNode.getChildNodes(), nodes),
                OSMParser.parseTags(wayNode.getChildNodes()));

        return way;
    }

    private static List<OSMNode> getNodes(NodeList children, Map<String, OSMNode> nodes) {
        List<OSMNode> result;

        result = new ArrayList<OSMNode>();

        Node node;
        String nodeName;
        List<Tag> tags;

        tags = new ArrayList<Tag>();
        for (int i = 0; i < children.getLength(); i++) {
            node = children.item(i);
            nodeName = node.getNodeName();

            if (nodeName.equals("nd")) {
                result.add(nodes.get(node.getAttributes().
                        getNamedItem("ref").getNodeValue()));
            }
        }

        return result;
    }
}
