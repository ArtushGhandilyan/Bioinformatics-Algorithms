package me.artushghandilyan.problems.chapter6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artush on 5/14/2015.
 */
public class TwoBreakDistanceProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_6c.txt");
        int distance = twoBreakDistance(fileContent.permutations1, fileContent.permutations2);
    }

    private static int twoBreakDistance(List<List<Integer>> permutations1, List<List<Integer>> permutations2) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (List<Integer> permutation : permutations1) {
            for (int i = 0; i < permutation.size() - 1; i++) {
                if(!graph.containsKey(permutation.get(i)))
                    graph.put(permutation.get(i), new ArrayList<Integer>());
                graph.get(permutation.get(i)).add(-1 * permutation.get(i + 1));

                if(!graph.containsKey(-1 * permutation.get(i + 1)))
                    graph.put(-1 * permutation.get(i + 1), new ArrayList<Integer>());
                graph.get(-1 * permutation.get(i + 1)).add(permutation.get(i));
            }
        }


        return 0;
    }

    public static FileContent readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            FileContent fileContent = new FileContent();
            for (int i = 0; i < 2; i++) {
                List<List<Integer>> permutations = new ArrayList<>();
                String[] line = reader.readLine().split("\\)");
                for (String perm : line) {
                    String[] split = perm.replaceAll("[(+)]", "").split(" ");

                    List<Integer> permutation = new ArrayList<>(split.length);
                    for(int j = 0; j < split.length; j++) {
                        permutation.add(Integer.parseInt(split[j]));
                    }
                    permutations.add(permutation);
                }
                fileContent.setPermutations(i, permutations);
            }

            return fileContent;
        }
    }

    static class FileContent {
        public List<List<Integer>> permutations1;
        public List<List<Integer>> permutations2;

        public void setPermutations(int index, List<List<Integer>> permutations) {
            if(index == 0) {
                permutations1 = permutations;
            } else {
                permutations2 = permutations;
            }
        }
    }

}
