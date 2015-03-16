package me.artushghandilyan.problems.chapter1;

import java.util.ArrayList;

/**
 * Created by Artush on 2/17/2015.
 */
public class ClumpFindingProblem {
    public static void main(String[] args) {

        String genome = "CGGACTCGACAGATGTGAAGAAATGTGAAGACTGAGTGAAGAGAAGAGGAAACACGACACGACATTGCGACATAATGTACGAATGTAATGTGCCTATGGC";
        int k = 5;
        int l = 75;
        int t = 4;

        ArrayList<String> patternsFormingClumps = fetchAllDistinctPatternsFormingClumps(genome, k, t, l);
        for (String pattern : patternsFormingClumps) {
            System.out.print(pattern + " ");
        }
    }

    public static ArrayList<String> fetchAllDistinctPatternsFormingClumps(String genome, int k, int t, int clumpsLength) {
        ArrayList<String> patternsFormingClumps = new ArrayList<String>();

        ArrayList<String> patterns = fetchFrequentPattern(genome, k, t);
        for (String pattern : patterns) {
            if(isFormingClumps(genome, pattern, clumpsLength, t))
                patternsFormingClumps.add(pattern);
        }
        return patternsFormingClumps;
    }

    private static ArrayList<String> fetchFrequentPattern(String genome, int length, int minOccurredCount) {
        ArrayList<String> patterns = new ArrayList<String>();

        int lastIndex = genome.length() - length;
        for (int i = 0; i <= lastIndex; i++) {
            String pattern = genome.substring(i, length+i); // get a serial pattern.

            if(patterns.contains(pattern)) // if we were observed this pattern, just continue loop.
                continue;

            // Count pattern occurred times.
            int occurredCount = PatternMatchingProblem.getPatternPositions(pattern, genome).size();

            if(occurredCount >= minOccurredCount)
                patterns.add(pattern);
        }

        return patterns;
    }

    private static boolean isFormingClumps(String genome, String pattern, int clumpsLength, int t) {
        ArrayList<Integer> patternPositions = PatternMatchingProblem.getPatternPositions(pattern, genome);
        for (int i = 0; i <= patternPositions.size() - t; i++) {
            int endIndex = patternPositions.get(i + t - 1) + pattern.length();
            int startIndex = patternPositions.get(i);
            if(endIndex - startIndex <= clumpsLength)
                return true;
        }
        return false;
    }
}
