package me.artushghandilyan.problems.chapter4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 3/24/2015.
 */
public class DeBruijnGraphFromStringProblem {
    public static void main(String[] args) throws IOException {
        String text = readFile("rosalind_4c.txt");
        int measure = 12;

        Map<String, List<String>> adjacencyList = constructDeBruijnGraph(text, measure);

        SortedSet<String> keys = new TreeSet<>(adjacencyList.keySet());

        for (String key : keys) {
            String values = adjacencyList.get(key).toString().replaceAll("[\\ \\[ \\]]", "");
            System.out.println(key + " -> " + values);
        }
    }

    private static Map<String, List<String>> constructDeBruijnGraph(String text, int measure) {
        Map<String, List<String>> adjacencyList = new HashMap<>();
        List<String> patterns = getComposition(text, measure - 1);
        for (int i = 0; i < patterns.size() - 1; i++) {
            String leftPattern = patterns.get(i);
            String rightPattern = patterns.get(i+1);
            if(!adjacencyList.containsKey(leftPattern))
                adjacencyList.put(leftPattern, new ArrayList<String>());
            adjacencyList.get(leftPattern).add(rightPattern);
        }
        return adjacencyList;
    }

    private static List<String> getComposition(String dna, int measure) {
        List<String> compositions = new ArrayList<>();
        for (int i = 0; i <= dna.length() - measure; i++) {
            compositions.add(dna.substring(i, i + measure));
        }

        return compositions;
    }

    public static String readFile(String filename) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.readLine();
        }
    }
}
