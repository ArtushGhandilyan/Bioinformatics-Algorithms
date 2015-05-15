package me.artushghandilyan.problems.chapter4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 3/24/2015.
 */
public class DeBruijnGraphFromPatternsProblem {
    public static void main(String[] args) throws IOException {
        List<String> patterns = readAllLines("rosalind_4d.txt");

        Map<String, List<String>> adjacencyList = constructDeBruijnGraph(patterns);
        SortedSet<String> keys = new TreeSet<>(adjacencyList.keySet());
        for (String key : keys) {
            String values = adjacencyList.get(key).toString().replaceAll("[\\ \\[ \\]]", "");
            System.out.println(key + " -> " + values);
        }
    }

    private static Map<String, List<String>> constructDeBruijnGraph(List<String> patterns) {
        Map<String, List<String>> adjacencyList = new HashMap<>();
        for (String pattern : patterns) {
            String leftNode = pattern.substring(0, pattern.length() - 1);
            String rightNode = pattern.substring(1);

            if(!adjacencyList.containsKey(leftNode))
                adjacencyList.put(leftNode, new ArrayList<String>());
            adjacencyList.get(leftNode).add(rightNode);
        }
        return adjacencyList;
    }

    public static List<String> readAllLines(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            List<String> result = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;
        }
    }
}
