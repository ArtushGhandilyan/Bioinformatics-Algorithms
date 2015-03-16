package me.artushghandilyan.problems.chapter1;

import java.util.ArrayList;

/**
 * Created by Artush on 2/18/2015.
 */
public class FrequentWordsWithMismatchesProblem {

    public static final String LETTERS = "ATGC";
    public static void main(String[] args) {
        String genome = "ACGTTGCATGTCGCATGATGCATGAGAGCT";
        int k = 4;
        int mismatches = 1;

        ArrayList<String> patterns = fetchMostFrequentPattern(genome, k, mismatches);

        for (String pattern : patterns) {
            System.out.print(pattern + " ");
        }
    }

    private static ArrayList<String> fetchMostFrequentPattern(String genome, int k, int mismatches) {
        ArrayList<String> patterns = new ArrayList<>();

        String pattern = getInitialPattern(k);
        String lastPattern = getLastPattern(k);

        patterns.add(pattern);
        int max = fetchApproximateMatchingCount(pattern, genome, mismatches);

        while (!pattern.equals(lastPattern)) {
            pattern = nextPattern(pattern);
            int occurredCount = fetchApproximateMatchingCount(pattern, genome, mismatches);
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

    private static String nextPattern(String pattern) {
        StringBuilder stringBuilder = new StringBuilder(pattern.length());
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if(pattern.charAt(i) == LETTERS.charAt(LETTERS.length() - 1))
                continue;

            int index = LETTERS.indexOf(pattern.charAt(i)) + 1;
            stringBuilder.append(pattern.substring(0, i));
            stringBuilder.append(LETTERS.charAt(index));

            for (int j = i + 1; j < pattern.length(); j++) {
                stringBuilder.append(LETTERS.charAt(0));
            }
            break;
        }
        return stringBuilder.toString();
    }

    private static String getInitialPattern(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append('A');
        }
        return stringBuilder.toString();
    }

    private static String getLastPattern(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append('C');
        }
        return stringBuilder.toString();
    }

    private static int fetchApproximateMatchingCount(String pattern, String genome, int mismatches) {
        int matchingCount = 0;
        for (int i = 0; i < genome.length() - pattern.length(); i++) {
            String substring = genome.substring(i, i + pattern.length());
            if(isApproximateMatching(pattern, substring, mismatches))
                matchingCount++;
        }
        return matchingCount;
    }

    private static boolean isApproximateMatching(String pattern, String substring, int mismatches) {
        char[] patternChars = pattern.toCharArray();
        char[] substringChars = substring.toCharArray();
        for (int i = 0; i < patternChars.length; i++) {
            if(patternChars[i] != substringChars[i])
                mismatches--;
            if(mismatches < 0)
                return false;
        }
        return true;
    }
}
