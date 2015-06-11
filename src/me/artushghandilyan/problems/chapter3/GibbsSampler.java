package me.artushghandilyan.problems.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 5/30/2015.
 */
public class GibbsSampler {
    public static final String LETTERS = "ACGT";

    public static Random random = new Random();

    public static void main(String[] args) throws IOException {
        Integer k = 15;
        Integer t = 20;
        Integer N = 2000;
        List<String> patterns = readAllLines("rosalind_3g.txt");


        List<String> bestMotifs = randomizedMotifSearch(patterns, k, t, N);
        for (String bestMotif : bestMotifs) {
            System.out.println(bestMotif);
        }
    }

    private static List<String> randomizedMotifSearch(List<String> patterns, Integer length, Integer t, Integer N) {
        float minScore = Float.MAX_VALUE;
        List<String> bestMotifs = new ArrayList<>();

        for(int i = 0; i < 1000; i++){
            List<String> currentBestMotifs = generateRandomMotifs(patterns, length);
            float currentBestScore = getScore(currentBestMotifs);

            for(int j = 0; j < N; j++) {
                int index = random.nextInt(patterns.size() - 1);
                Map<String, List<Float>> profile = profile(currentBestMotifs, index);

                List<String> currentMotifs = new ArrayList<>();
                currentMotifs.addAll(currentBestMotifs);

                String mostProbableKMer = findMostProbableKMer(patterns.get(index), length, profile);
                currentMotifs.add(index, mostProbableKMer);

                float score = getScore(currentMotifs);

                if(score >= currentBestScore)
                    break;

                currentBestScore = score;
                currentBestMotifs.clear();
                currentBestMotifs.addAll(currentMotifs);
            }

            if(currentBestScore < minScore){
                minScore = currentBestScore;
                bestMotifs.clear();
                bestMotifs.addAll(currentBestMotifs);
            }
        }

        return bestMotifs;
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

    private static Map<String, List<Float>> profile(List<String> motifs, int index) {
        int length = motifs.get(0).length();
        int count = motifs.size();

        Map<String, List<Float>> matrix = new HashMap<>();
        for (int i = 0; i < LETTERS.length(); i++) {
            String letter = LETTERS.substring(i, i + 1);
            matrix.put(letter, new ArrayList<Float>(Collections.<Float>nCopies(length, 0.25f)));
        }

        for (int i = 0; i < length; i++) {
            for (int i1 = 0; i1 < motifs.size(); i1++) {
                if(i1 != index) {
                    String motif = motifs.get(i1);
                    List<Float> row = matrix.get(motif.substring(i, i + 1));
                    row.set(i, row.get(i) + 1);
                }
            }

            for (int j = 0; j < LETTERS.length(); j++) {
                String letter = LETTERS.substring(j, j + 1);
                matrix.get(letter).set(i, matrix.get(letter).get(i) / count);
            }
        }

        return matrix;
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

    private static List<String> generateRandomMotifs(List<String> patterns, Integer length){
        List<String> motifs = new ArrayList<>();
        for(String pattern: patterns){
            int startIndex = random.nextInt(pattern.length() - length + 1);
            int endIndex = startIndex + length;
            motifs.add(pattern.substring(startIndex, endIndex));
        }
        return motifs;
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
