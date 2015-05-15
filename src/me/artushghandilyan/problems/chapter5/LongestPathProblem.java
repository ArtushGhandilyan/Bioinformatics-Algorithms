package me.artushghandilyan.problems.chapter5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 4/24/2015.
 */
public class LongestPathProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_5d.txt");
        Integer source = fileContent.source;
        Integer sink = fileContent.sink;
        Map<Integer, List<Map<Integer, Integer>>> edges = fileContent.edges;


        Path path = longestPath(source, sink, edges);
        path.addNode(source, 0);
        System.out.println(path.weight);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = path.nodes.size() - 1; i >= 0; i--) {
            stringBuilder.append(path.nodes.get(i)).append("->");
        }
        System.out.println(stringBuilder.toString().substring(0, stringBuilder.length() - 2));
    }

    private static Path longestPath(Integer source, Integer sink,
                                             Map<Integer, List<Map<Integer, Integer>>> edges) {
        Path longestPath = new Path(new ArrayList<Integer>(), 0);
        if(source.equals(sink))
            return longestPath;

        List<Map<Integer, Integer>> neighbors = edges.get(source);
        for (Map<Integer, Integer> neighbor : neighbors) {
            for (Map.Entry<Integer, Integer> entry : neighbor.entrySet()) {
                int node = entry.getKey();
                int weight = entry.getValue();
                Path path = longestPath(node, sink, edges);
                if(weight + path.weight > longestPath.weight) {
                    longestPath = new Path(path);
                    longestPath.addNode(node, weight);
                }
            }
        }
        return longestPath;
    }

    static class Path {
        List<Integer> nodes;
        Integer weight;

        public Path(List<Integer> nodes, Integer weight) {
            this.nodes = nodes;
            this.weight = weight;
        }

        public Path(Path nodes) {
            this(nodes.nodes, nodes.weight);
        }

        public void addNode(Integer node, Integer weight) {
            this.nodes.add(node);
            this.weight += weight;
        }
    }
    public static FileContent readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Integer source = Integer.parseInt(reader.readLine());
            Integer sink = Integer.parseInt(reader.readLine());
            Map<Integer, List<Map<Integer, Integer>>> edges = new HashMap<>();

            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                String[] nodes = split[0].split("->");

                int start = Integer.parseInt(nodes[0]);
                int end = Integer.parseInt(nodes[1]);
                int weight = Integer.parseInt(split[1]);

                Map<Integer, Integer> neighbor = new HashMap<>();
                neighbor.put(end, weight);

                if(!edges.containsKey(start)) {
                    List<Map<Integer, Integer>> neighbors = new ArrayList<>();
                    neighbors.add(neighbor);
                    edges.put(start, neighbors);
                } else {
                    edges.get(start).add(neighbor);
                }
            }

            return new FileContent(source, sink, edges);
        }
    }

    static class FileContent {
        private Integer source;
        private Integer sink;
        private Map<Integer, List<Map<Integer, Integer>>> edges;

        public FileContent(Integer source, Integer sink, Map<Integer, List<Map<Integer, Integer>>> edges) {
            this.source = source;
            this.sink = sink;
            this.edges = edges;
        }
    }

}
