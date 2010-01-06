package br.zuq.osm2gisgraphycsv.xml.parser;

import br.zuq.osm2gisgraphycsv.xml.Member;
import br.zuq.osm2gisgraphycsv.xml.OSM;
import br.zuq.osm2gisgraphycsv.xml.OSMNode;
import br.zuq.osm2gisgraphycsv.xml.Relation;
import br.zuq.osm2gisgraphycsv.xml.Tag;
import br.zuq.osm2gisgraphycsv.xml.Way;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Willy Tiengo
 */
public class OSMParser {

    /**
     * @param args the command line arguments
     */
    public static OSM parse(String path) throws Exception {

        Document doc;
        DocumentBuilder builder;
        Node node;
        NodeList nodesList;
        Map<String, OSMNode> nodes;
        Map<String, Way> ways;
        Map<String, Relation> relations;

        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = builder.parse(path);

        nodesList = doc.getChildNodes().item(0).getChildNodes();

        nodes = new LinkedHashMap<String, OSMNode>();
        ways = new LinkedHashMap<String, Way>();
        relations = new LinkedHashMap<String, Relation>();

        for (int i = 0; i < nodesList.getLength(); i++) {
            node = nodesList.item(i);

            if (NodeParser.isNode(node)) {

                OSMNode osmNode = NodeParser.parseNode(node);
                nodes.put(osmNode.id, osmNode);

            } else if (WayParser.isWay(node)) {

                Way way = WayParser.parseWay(node, nodes);
                ways.put(way.id, way);

            } else if (RelationParser.isRelation(node)) {

                Relation relation = RelationParser.parseRelation(node);
                relations.put(relation.id, relation);

            }
        }

        return new OSM(nodes, ways, relations);
    }

    public static List<Tag> parseTags(NodeList nodes) {
        Node node;
        String nodeName;
        List<Tag> tags;

        tags = new ArrayList<Tag>();

        for (int i = 0; i < nodes.getLength(); i++) {

            node = nodes.item(i);
            nodeName = node.getNodeName();

            if (nodeName.equals("tag")) {

                tags.add(
                        new Tag(
                        node.getAttributes().getNamedItem("k").getNodeValue(),
                        node.getAttributes().getNamedItem("v").getNodeValue()));

            }
        }

        return tags;
    }
}
