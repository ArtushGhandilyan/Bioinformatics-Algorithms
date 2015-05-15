package me.artushghandilyan.problems.chapter4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artush on 3/24/2015.
 */
public class OverlapGraphProblem {
    public static void main(String[] args) throws IOException {
        List<String> patterns = readAllLines("rosalind_4b.txt");
        Map<String, String> adjacencyList = constructOverlapGraph(patterns);

        for (Map.Entry<String, String> entry : adjacencyList.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    private static Map<String, String> constructOverlapGraph(List<String> patterns) {
        Map<String, String> adjacencyList = new HashMap<>();
        int length = patterns.get(0).length();
        for (String leftPattern : patterns) {
            for (String rightPattern : patterns) {
                if(leftPattern.substring(1).equals(rightPattern.substring(0, length - 1))) {
                    adjacencyList.put(leftPattern, rightPattern);
                }
            }
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
