package me.artushghandilyan.problems.chapter5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Artush on 4/24/2015.
 */
public class LongestCommonSubSequenceProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_5c.txt");
        String sequence1 = fileContent.getSequence1();
        String sequence2 = fileContent.getSequence2();

        String subSequence = longestCommonSubSequence(sequence1, sequence2);
        System.out.println(subSequence);
    }

    private static String longestCommonSubSequence(String sequence1, String sequence2) {
        int[][] matrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }

        for (int i = 0; i < sequence1.length(); i++) {
            for (int j = 0; j < sequence2.length(); j++) {
                if(sequence1.charAt(i) == sequence2.charAt(j)) {
                    matrix[i+1][j+1] = matrix[i][j] + 1;
                } else {
                    matrix[i+1][j+1] = Math.max(matrix[i+1][j], matrix[i][j+1]);
                }
            }
        }

        String longestCommonSequence = "";
        for (int i = sequence1.length(), j = sequence2.length(); i > 0 && j > 0;) {
            if(matrix[i][j] == matrix[i-1][j]) {
                i--;
            } else if (matrix[i][j] == matrix[i][j-1]) {
                j--;
            } else {
                longestCommonSequence = sequence1.charAt(i-1) + longestCommonSequence;
                i--;
                j--;
            }
        }

        return longestCommonSequence;
    }

    public static FileContent readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String sequence1 = reader.readLine();
            String sequence2 = reader.readLine();
            return new FileContent(sequence1, sequence2);
        }
    }

    static class FileContent {
        private String sequence1;
        private String sequence2;

        public FileContent(String sequence1, String sequence2) {
            this.sequence1 = sequence1;
            this.sequence2 = sequence2;
        }

        public String getSequence1() {
            return sequence1;
        }

        public String getSequence2() {
            return sequence2;
        }
    }
}
