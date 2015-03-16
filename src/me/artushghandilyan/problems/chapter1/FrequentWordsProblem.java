package me.artushghandilyan.problems.chapter1;

import java.util.ArrayList;

public class FrequentWordsProblem {
    public static void main(String[] args) {

        String dna = "ACGTTGCATGTCGCATGATGCATGAGAGCT";
        int k = 4;

        ArrayList<String> patterns = fetchMostFrequentPattern(dna, k);

        for (String pattern : patterns) {
            System.out.print(pattern + " ");
        }
    }

    /**
     * Fetch all most frequent k-mers in given text.
     * @param dna input DNA string.
     * @param k the length of pattern.
     */
    public static ArrayList<String> fetchMostFrequentPattern(String dna, int k) {
        ArrayList<String> patterns = new ArrayList<String>();
        int max = 0;

        int lastIndex = dna.length() - k;
        for (int i = 0; i <= lastIndex; i++) {
            String pattern = dna.substring(i, k+i); // get a serial pattern.

            if(patterns.contains(pattern)) // if we were observed this pattern, just continue loop.
                continue;

            // Count pattern occurred times.
            int occurredCount = getPatternPositions(pattern, i, dna).size();

            if(occurredCount > max) {
                patterns.clear();
                patterns.add(pattern);
                max = occurredCount;
            } else if(occurredCount == max) {
                patterns.add(pattern);
            }
        }

        return patterns;
    }

    public static ArrayList<Integer> getPatternPositions(String pattern, int startIndex, String genome) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int lastOccurredIndex = startIndex;
        while (lastOccurredIndex != -1) {
            indexes.add(lastOccurredIndex);
            lastOccurredIndex = genome.indexOf(pattern, lastOccurredIndex + 1);
        }
        return indexes;
    }
}
