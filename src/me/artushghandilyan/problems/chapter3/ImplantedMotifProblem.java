package me.artushghandilyan.problems.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Artush on 3/24/2015.
 */
public class ImplantedMotifProblem {
    public static final String LETTERS = "ATGC";

    public static void main(String[] args) throws IOException {
        Integer k = 5;
        Integer d = 1;
        List<String> dnaList = readAllLines("rosalind_3a.txt");

        Set<String> motifs = motifEnumeration(dnaList, k, d);
        for (String motif : motifs) {
            System.out.print(motif + " ");
        }
    }

    private static Set<String> motifEnumeration(List<String> dnaList, Integer k, Integer d) {
        Set<String> motifs = new HashSet<>();
        boolean first = true;

        for (String dna : dnaList) {
            Set<String> dnaMotifs = new HashSet<>();
            for (int i = 0; i < dna.length() - k + 1; i++) {
                List<String> mismatchedKMerList = getMismatchedKMerList(dna.substring(i, i + k), d);
                dnaMotifs.addAll(mismatchedKMerList);
            }
            if(first) {
                motifs = dnaMotifs;
                first = false;
                continue;
            }
            motifs.retainAll(dnaMotifs);
        }

        return motifs;
    }

    /**
     * Returns all k-mers that are within d mismatches of the given k-mer.
     * @param kMer given k-mers.
     * @param d mismatches count.
     * @return
     */
    private static List<String> getMismatchedKMerList(String kMer, Integer d) {
        List<String> kMers = new ArrayList<>();
        kMers.add(kMer);

        String pattern = getInitialPattern(kMer.length());
        String lastPattern = getLastPattern(kMer.length());

        while (!pattern.equals(lastPattern)) {
            pattern = nextPattern(pattern);
            if(isApproximateMatching(pattern, kMer, d)) {
                kMers.add(pattern);
            }
        }

        return kMers;
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
