package br.zuq.osm2gisgraphycsv.xml;

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
        NodeList children;
        NodeList nodesList;
        NamedNodeMap map;
        List<Tag> tags;
        Map<String, OSMNode> nodes;
        Map<String, Way> ways;
        Map<String, Relation> relations;
        String id;
        String nodeName;

        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = builder.parse(path);

        nodesList = doc.getChildNodes().item(0).getChildNodes();

        nodes = new LinkedHashMap<String, OSMNode>();
        ways = new LinkedHashMap<String, Way>();
        relations = new LinkedHashMap<String, Relation>();
        for (int i = 0; i < nodesList.getLength(); i++) {
            node = nodesList.item(i);

            children = node.getChildNodes();
            nodeName = node.getNodeName();

            map = node.getAttributes();
            tags = verifyTag(children);

            if (nodeName.equals("bounds")) {
            } else if (nodeName.equals("node")) {
                OSMNode osmNode;

                id = map.getNamedItem("id").getNodeValue();

                osmNode = new OSMNode(id,
                        map.getNamedItem("visible").getNodeValue(),
                        map.getNamedItem("timestamp").getNodeValue(),
                        map.getNamedItem("version").getNodeValue(),
                        map.getNamedItem("changeset").getNodeValue(),
                        map.getNamedItem("user").getNodeValue(),
                        map.getNamedItem("uid").getNodeValue(),
                        map.getNamedItem("lat").getNodeValue(),
                        map.getNamedItem("lon").getNodeValue(),
                        tags);

                nodes.put(id, osmNode);
            } else if (nodeName.equals("way")) {
                Way way;

                id = map.getNamedItem("id").getNodeValue();

                way = new Way(id,
                        map.getNamedItem("visible").getNodeValue(),
                        map.getNamedItem("timestamp").getNodeValue(),
                        map.getNamedItem("version").getNodeValue(),
                        map.getNamedItem("changeset").getNodeValue(),
                        map.getNamedItem("user").getNodeValue(),
                        map.getNamedItem("uid").getNodeValue(),
                        getNodes(children, nodes),
                        tags);

                ways.put(id, way);
            } else if (nodeName.equals("relation")) {
                Relation relation;

                id = map.getNamedItem("id").getNodeValue();

                relation = new Relation(id,
                        map.getNamedItem("visible").getNodeValue(),
                        map.getNamedItem("timestamp").getNodeValue(),
                        map.getNamedItem("version").getNodeValue(),
                        map.getNamedItem("changeset").getNodeValue(),
                        map.getNamedItem("user").getNodeValue(),
                        map.getNamedItem("uid").getNodeValue(),
                        getMembers(children),
                        tags);

                relations.put(id, relation);
            } else {
//                System.out.println(nodes.item(i).getNodeType());
            }
        }

        return new OSM(nodes, ways, relations);
    }

    private static List<Member> getMembers(NodeList children) {
        List<Member> result;
        Node node;
        NamedNodeMap map;

        result = new ArrayList<Member>();

        for (int i = 0; i < children.getLength(); i++) {
            node = children.item(i);
            map = node.getAttributes();

            if (node.getNodeName().equals("member")) {
                result.add(new Member(
                        map.getNamedItem("type").getNodeValue(),
                        map.getNamedItem("ref").getNodeValue(),
                        map.getNamedItem("role").getNodeValue()));
            }
        }

        return result;
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

    private static List<Tag> verifyTag(NodeList children) {
        Node node;
        String nodeName;
        List<Tag> tags;

        tags = new ArrayList<Tag>();
        for (int i = 0; i < children.getLength(); i++) {
            node = children.item(i);
            nodeName = node.getNodeName();

            if (nodeName.equals("tag")) {
                tags.add(new Tag(node.getAttributes().getNamedItem("k").getNodeValue(),
                        node.getAttributes().getNamedItem("v").getNodeValue()));
            }
        }

        return tags;
    }
}