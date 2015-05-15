package me.artushghandilyan.problems.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 4/9/2015.
 */
public class GreedyMotifSearch {
    public static final String LETTERS = "ACGT";

    public static void main(String[] args) throws IOException {
        Integer k = 12;
        Integer t = 25;
        List<String> dnaList = readAllLines("rosalind_3d.txt");

        List<String> bestMotifs = greedyMotifSearch(dnaList, k, t);

        for (String bestMotif : bestMotifs) {
            System.out.println(bestMotif);
        }
    }

    private static List<String> greedyMotifSearch(List<String> dnaList, Integer k, Integer t) {
        List<String> bestMotifs = null;
        float bestScope = Float.MAX_VALUE;

        String firstDna = dnaList.get(0);
        for (int i = 0; i < firstDna.length() - k + 1; i++) {
            List<String> motifs = new ArrayList<>();
            motifs.add(firstDna.substring(i, i + k));

            for (int j = 1; j < dnaList.size(); j++) {
                Map<String, List<Float>> profile = profile(motifs);
                motifs.add(ProfileMostProbableKMerProblem.findMostProbableKMer(dnaList.get(j), k, profile));
            }

            float currentScope = getScore(motifs);
            if(currentScope < bestScope) {
                bestMotifs = motifs;
                bestScope = currentScope;
            }
        }
        return bestMotifs;
    }

    public static float getScore(List<String> motifs) {
        int length = motifs.get(0).length();
        int score = 0;
        for (int i = 0; i < length; i++) {

            Map<String, Integer> countMap = new HashMap<>();
            for (int j = 0; j < LETTERS.length(); j++) {
                String letter = LETTERS.substring(j, j + 1);
                countMap.put(letter, 0);
            }

            for (String motif : motifs) {
                String currentLetter = motif.substring(i, i + 1);
                Integer count = countMap.get(currentLetter);
                countMap.put(currentLetter, count + 1);
            }

            int max = 0;
            int sum = 0;
            for (Integer count : countMap.values()) {
                if(count > max)
                    max = count;
                sum += count;
            }
            score += (sum - max);
        }

        return score;
    }

    private static Map<String, List<Float>> profile(List<String> motifs) {
        int length = motifs.get(0).length();
        int count = motifs.size();

        Map<String, List<Float>> matrix = new HashMap<>();
        for (int i = 0; i < LETTERS.length(); i++) {
            String letter = LETTERS.substring(i, i + 1);
            matrix.put(letter, new ArrayList<Float>(Collections.<Float>nCopies(length, 0f)));
        }

        for (int i = 0; i < length; i++) {
            for (String motif : motifs) {
                List<Float> row = matrix.get(motif.substring(i, i + 1));
                row.set(i, row.get(i) + 1);
            }

            for (int j = 0; j < LETTERS.length(); j++) {
                String letter = LETTERS.substring(j, j + 1);
                matrix.get(letter).set(i, matrix.get(letter).get(i) / count);
            }
        }

        return matrix;
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
