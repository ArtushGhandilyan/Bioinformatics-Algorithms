package me.artushghandilyan.problems.chapter2;

import me.artushghandilyan.problems.chapter1.ReverseComplementProblem;

import java.util.ArrayList;

/**
 * Created by Artush on 2/28/2015.
 */
public class PeptideEncodingProblem {

    public static void main(String[] args) {
        String dna = "ATGGCCATGGCCCCCAGAACTGAGATCAATAGTACCCGTATTAACGGGTGA";
        String peptide = "MA";

        ArrayList<String> patterns = findPeptideEncoding(peptide, dna);
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    private static ArrayList<String> findPeptideEncoding(String peptide, String dna) {
        ArrayList<String> patterns = new ArrayList<>();

        int lastIndex = dna.length() - peptide.length() * 3;
        for (int i = 0; i <= lastIndex; i++) {

            String patternDna = dna.substring(i, i + peptide.length() * 3);
            String patternRna = dnaToRna(patternDna);

            String reverseComplementPatterDna = reverse(patternDna);
            String reverseComplementPatterRna = dnaToRna(reverseComplementPatterDna);

            if(ProteinTranslationProblem.translate(patternRna).equals(peptide) ||
                    ProteinTranslationProblem.translate(reverseComplementPatterRna).equals(peptide)) {
                patterns.add(patternDna);
            }
        }
        return patterns;
    }

    private static String dnaToRna(String patternDna) {
        return patternDna.replace('T', 'U');
    }

    private static String reverse(String pattern) {
        StringBuilder stringBuilder = new StringBuilder(pattern.length());
        char[] chars = pattern.toCharArray();
        int length = chars.length - 1;
        for (int i = length; i >= 0; i--) {
            switch (chars[i]) {
                case 'A':
                    stringBuilder.append('T');
                    break;
                case 'T':
                    stringBuilder.append('A');
                    break;
                case 'G':
                    stringBuilder.append('C');
                    break;
                case 'C':
                    stringBuilder.append('G');
                    break;
                default:
                    break;
            }
        }
        return stringBuilder.toString();
    }
}
