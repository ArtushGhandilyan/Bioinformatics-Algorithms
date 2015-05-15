package me.artushghandilyan.problems.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artush on 4/8/2015.
 */
public class ProfileMostProbableKMerProblem {
    public static final String LETTERS = "ACGT";

    public static void main(String[] args) throws IOException {
        String text = "ACCTGTTTATTGCCTAAGTTCCGAACAAACCCAATATAGCCCGAGGGCCT";
        Integer k = 5;
        Map<String, List<Float>> matrix = fillMatrixFromFile("rosalind_3c.txt");

        String kMer = findMostProbableKMer(text, k, matrix);
        System.out.println(kMer);
    }

    public static String findMostProbableKMer(String text, Integer k, Map<String, List<Float>> matrix) {
        float probability = 0;
        String mostProbableKMer = text.substring(0, k);
        for (int i = 0; i < text.length() - k + 1; i++) {
            String kmer = text.substring(i, i + k);
            float pr = getProbability(kmer, matrix);
            if(pr > probability) {
                probability = pr;
                mostProbableKMer = kmer;
            }
        }
        return mostProbableKMer;
    }

    private static float getProbability(String kmer, Map<String, List<Float>> matrix) {
        float pr = 1;
        for (int i = 0; i < kmer.length(); i++) {
            pr *= matrix.get(kmer.substring(i, i + 1)).get(i);
        }
        return pr;
    }

    private static Map<String, List<Float>> fillMatrixFromFile(String fileName) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Map<String, List<Float>> matrix = new HashMap<>();
            String line;
            int key = 0;
            while((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                List<Float> row = new ArrayList<>();
                for (String s : split) {
                    row.add(Float.valueOf(s));
                }
                matrix.put(LETTERS.substring(key, ++key), row);
            }
            return matrix;
        }
    }
}
