package me.artushghandilyan.problems.chapter1;

import java.util.ArrayList;

/**
 * Created by Artush on 2/18/2015.
 */
public class ApproximatePatternMatchingProblem {
    public static void main(String[] args) {

        String genome = "CGCCCGAATCCAGAACGCATTCCCATATTTCGGGACCACTGGCCTCCACGGTACGGACGTCAATCAAATGCCTAGCGGCTTGTGGTTTCTCCTACGCTCC";
        String pattern = "ATTCTGGA";
        int mismatches = 3;

        ArrayList<Integer> startPositions = fetchApproximateMatchingPosition(pattern, genome, mismatches);
        for (Integer startPosition : startPositions) {
            System.out.print(startPosition + " ");
        }
    }

    public static ArrayList<Integer> fetchApproximateMatchingPosition(String pattern, String genome, int mismatches) {
        ArrayList<Integer> startPositions = new ArrayList<Integer>();
        for (int i = 0; i < genome.length() - pattern.length(); i++) {
            String substring = genome.substring(i, i + pattern.length());
            if(isApproximateMatching(pattern, substring, mismatches))
                startPositions.add(i);
        }
        return startPositions;
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
