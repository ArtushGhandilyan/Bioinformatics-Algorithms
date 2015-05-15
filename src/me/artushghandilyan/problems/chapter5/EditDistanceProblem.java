package me.artushghandilyan.problems.chapter5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Artush on 4/24/2015.
 */
public class EditDistanceProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_5g.txt");
        String sequence1 = fileContent.getSequence1();
        String sequence2 = fileContent.getSequence2();

        System.out.println(editDistance(sequence1, sequence2));
    }

    private static Integer editDistance(String sequence1, String sequence2) {
        int length1 = sequence1.length();
        int length2 = sequence2.length();
        int[][] matrix = new int[length1 + 1][length2 + 1];
        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }

        for (int i = 1; i < length1 + 1; i++)
            matrix[i][0] = i;
        for (int j = 1; j < length2 + 1; j++)
            matrix[0][j] = j;

        for (int i = 1; i < length1 + 1; i++) {
            for (int j = 1; j < length2 + 1; j++) {
                System.out.println(sequence1.charAt(i-1) + " == " + sequence2.charAt(j-1) + " ??");
                if(sequence1.charAt(i-1) == sequence2.charAt(j-1)) {
                    matrix[i][j] = matrix[i-1][j-1];
                } else {
                    matrix[i][j] = Math.min(Math.min(
                            matrix[i]  [j-1] + 1,
                            matrix[i-1][j]   + 1),
                            matrix[i-1][j-1] + 1);
                }
            }
        }
        return matrix[length1][length2];
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
