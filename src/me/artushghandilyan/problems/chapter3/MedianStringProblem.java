package me.artushghandilyan.problems.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artush on 3/24/2015.
 */
public class MedianStringProblem {
    public static final String LETTERS = "ATGC";

    public static void main(String[] args) throws IOException {
        Integer k = 6;
        List<String> dnaList = readAllLines("rosalind_3b.txt");

        String pattern = findMedianString(dnaList, k);
        System.out.println(pattern);
    }

    private static String findMedianString(List<String> dnaList, Integer k) {
        int minDistance = Integer.MAX_VALUE;
        String bestPattern = null;

        String pattern = getInitialPattern(k);
        String lastPattern = getLastPattern(k);

        while (!pattern.equals(lastPattern)) {
            pattern = nextPattern(pattern);
            int distance = 0;
            for (String dnaItem : dnaList) {
                distance += getDiff(dnaItem, pattern);
            }
            if (distance < minDistance) {
                bestPattern = pattern;
                minDistance = distance;
            }
        }

        return bestPattern;
    }

    private static int getDiff(String dnaItem, String kMer) {
        int min = Integer.MAX_VALUE;
        int k = kMer.length();
        for (int i = 0; i < dnaItem.length() - k + 1; i++) {
            String substring = dnaItem.substring(i, i + k);
            int mismatches = getMismatches(substring, kMer);
            if(mismatches < min)
                min = mismatches;
        }
        return min;
    }

    private static int getMismatches(String substring, String kMer) {
        //substring and kMer length must be equals.
        int length = substring.length();
        int mismatches = 0;
        for (int i = 0; i < length; i++) {
            if(substring.charAt(i) != kMer.charAt(i))
                mismatches++;
        }
        return mismatches;
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
