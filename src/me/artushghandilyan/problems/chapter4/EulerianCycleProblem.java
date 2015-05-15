package me.artushghandilyan.problems.chapter4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 3/25/2015.
 */
public class EulerianCycleProblem {
    public static void main(String[] args) throws IOException {
        Graph graph = readGraph("rosalind_4e.txt");
        Cycle cycle = getEulerianCycle(graph);

        System.out.println(cycle);
    }

    public static Cycle getEulerianCycle(Graph graph) {
        Node startNode = graph.getRandomNode();
        Cycle cycle = graph.randomWalking(startNode);

        while(graph.hasUnexploredEdges(cycle)) {
            startNode = graph.getNewStartNode(cycle);
            cycle.traverse(startNode);
            cycle.addAll(graph.randomWalking(startNode));
        }
        return cycle;
    }

    public static Graph readGraph(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Graph result = new Graph();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.replaceAll("\\s", "").split("->");
                String[] array = split[1].split(",");

                int vehicle = Integer.parseInt(split[0]);
                Node outNode = result.getOrCreateAndGetNode(vehicle, array.length);

                for (String item : array) {
                    Node inNode = result.getOrCreateAndGetNode(Integer.parseInt(item));
                    result.addEdge(new Edge(outNode, inNode));
                }
            }
            return result;
        }
    }

    static class Graph {
        List<Node> nodes;
        List<Edge> edges;

        public Graph() {
            nodes = new ArrayList<>();
            edges = new ArrayList<>();
        }

        public Cycle randomWalking(Node startNode) {
            Cycle cycle = new Cycle(startNode);
            Node nextNode = getNextNode(startNode);
            while(nextNode != startNode) {
                cycle.add(nextNode);
                nextNode = getNextNode(nextNode);
                nextNode = (nextNode == null) ? startNode : nextNode;
            }
            cycle.add(nextNode);
            return cycle;
        }

        private Node getNextNode(Node node) {
            for (Edge edge : edges) {
                if (edge.getOut() == node && !edge.isExplored()) {
                    edge.setExplored(true);
                    return edge.getIn();
                }
            }
            return null;
        }

        public void addNode(Node node) {
            nodes.add(node);
        }

        public void addEdge(Edge edge) {
            edges.add(edge);
        }

        public Node getRandomNode() {
            return nodes.get(new Random().nextInt(nodes.size()));
        }

        public boolean hasUnexploredEdges(Cycle cycle) {
            return cycle.getEdgesCount() != edges.size();
        }

        public Node getNewStartNode(Cycle cycle) {
            List<Node> cycleNodes = cycle.getNodes();
            for (Node cycleNode : cycleNodes) {
                if (cycleNode.hasUnexploredEdge())
                    return cycleNode;
            }
            return null;
        }

        public Node getOrCreateAndGetNode(int vehicle) {
            Node outNode = getNode(vehicle);
            if (outNode == null) {
                outNode = new Node(vehicle, 0);
                addNode(outNode);
            }
            return outNode;
        }

        public Node getOrCreateAndGetNode(int vehicle, int edgesCount) {
            Node outNode = getNode(vehicle);
            if (outNode != null) {
                outNode.setUnexploredEdgesCount(edgesCount);
            } else {
                outNode = new Node(vehicle, edgesCount);
                addNode(outNode);
            }
            return outNode;
        }

        private Node getNode(int vehicle) {
            for (Node node : nodes) {
                if(node.getVehicle() == vehicle)
                    return node;
            }
            return null;
        }
    }

    static class Node {
        private int vehicle;
        private int unexploredEdgesCount;

        public Node(int vehicle, int unexploredEdgesCount) {
            this.vehicle = vehicle;
            this.unexploredEdgesCount = unexploredEdgesCount;
        }

        public int getVehicle() {
            return vehicle;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (vehicle != node.vehicle) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return vehicle;
        }

        public boolean hasUnexploredEdge() {
            return unexploredEdgesCount > 0;
        }

        public void setUnexploredEdgesCount(int unexploredEdgesCount) {
            this.unexploredEdgesCount = unexploredEdgesCount;
        }

        public void decreaseUnexploredEdges() {
            this.unexploredEdgesCount -= 1;
        }
    }

    static class Edge {
        private Node out;
        private Node in;
        private boolean explored;

        public Edge(Node outNode, Node inNode) {
            this.out = outNode;
            this.in = inNode;
            explored = false;
        }

        public Node getOut() {
            return out;
        }

        public Node getIn() {
            return in;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Edge) {
                Edge other = (Edge) obj;
                return this.out.equals(other.getOut()) && this.in.equals(other.getIn());
            }
            return false;
        }

        public boolean isExplored() {
            return explored;
        }

        public void setExplored(boolean explored) {
            this.explored = explored;
        }
    }

    static class Cycle {
        private List<Node> nodes;
        private List<Edge> edges;

        public Cycle(Node startNode) {
            nodes = new ArrayList<>();
            nodes.add(startNode);
            edges = new ArrayList<>();
        }

        public void add(Node inNode) {
            Node outNode = nodes.get(nodes.size() - 1);
            edges.add(new Edge(outNode, inNode));
            nodes.add(inNode);

            outNode.decreaseUnexploredEdges();
        }

        public void addAll(Cycle cycle) {
            for (int i = 1; i < cycle.getNodes().size(); i++)
                nodes.add(cycle.getNodes().get(i));
            for (Edge edge : cycle.getEdges())
                edges.add(edge);
        }

        public int getEdgesCount() {
            return edges.size();
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public List<Edge> getEdges() {
            return edges;
        }

        public void traverse(Node startNode) {
            List<Node> traversedNodes = new ArrayList<>(nodes.size());
            for (int i = 0; i < nodes.size(); i++) {
                if(nodes.get(i) == startNode) {
                    traversedNodes.addAll(nodes.subList(i, nodes.size()));
                    traversedNodes.addAll(nodes.subList(1, i + 1));
                    break;
                }
            }
            nodes = traversedNodes;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (nodes.size() > 0) {
                sb.append(nodes.get(0).getVehicle());
                for (int i = 1; i < nodes.size(); i++) {
                    sb.append("->");
                    sb.append(nodes.get(i).getVehicle());
                }
            }
            return sb.toString();
        }
    }
}
