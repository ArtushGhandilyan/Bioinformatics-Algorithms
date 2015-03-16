package me.artushghandilyan.problems.chapter1;

import java.util.ArrayList;

public class PatternMatchingProblem {
    public static void main(String[] args) {
        String pattern = "ATAT";
        String genome = "GATATATGCATATACTT";

        ArrayList<Integer> patternPositions = getPatternPositions(pattern, genome);
        for (Integer patternPosition : patternPositions) {
            System.out.print(patternPosition + " ");
        }

    }

    public static ArrayList<Integer> getPatternPositions(String pattern, String genome) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int lastOccurredIndex = genome.indexOf(pattern);
        while (lastOccurredIndex != -1) {
            indexes.add(lastOccurredIndex);
            lastOccurredIndex = genome.indexOf(pattern, lastOccurredIndex + 1);
        }
        return indexes;
    }
}
