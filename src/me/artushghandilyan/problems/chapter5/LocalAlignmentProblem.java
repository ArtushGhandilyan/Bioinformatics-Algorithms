package me.artushghandilyan.problems.chapter5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 4/24/2015.
 */
public class LocalAlignmentProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_5f.txt");
        String sequence1 = fileContent.getSequence1();
        String sequence2 = fileContent.getSequence2();

        Map<String, Map<String, Integer>> scoringMatrix = readPAM250("pam250.txt");
        Integer sigma = 5;

        localAlignment(sequence1, sequence2, scoringMatrix, sigma);
    }

    private static void localAlignment(String sequence1, String sequence2,
                                       Map<String, Map<String, Integer>> scoringMatrix, Integer sigma) {
        int[][] matrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }

        int[][] backtrack = new int[sequence1.length() + 1][sequence2.length() + 1];
        for (int[] row : backtrack) {
            Arrays.fill(row, 0);
        }

        int maxScore = -1, maxI = 0, maxJ = 0;

        for (int i = 1; i <= sequence1.length(); i++) {
            for (int j = 1; j <= sequence2.length(); j++) {
                List<Integer> scores = new ArrayList<>(Arrays.asList(
                        matrix[i-1][j]- sigma,
                        matrix[i][j-1]- sigma,
                        matrix[i-1][j-1] + scoringMatrix.get(sequence1.substring(i-1,i)).get(sequence2.substring(j-1,j)),
                        0
                ));
                matrix[i][j] = Collections.max(scores);
                backtrack[i][j] = scores.indexOf(matrix[i][j]);

                if(matrix[i][j] > maxScore) {
                    maxScore = matrix[i][j];
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        int i = maxI, j = maxJ;
        String alignedSequence1 = sequence1.substring(0, i);
        String alignedSequence2 = sequence2.substring(0, j);

        for (; i > 0 && j > 0 && backtrack[i][j] != 3;) {
            if(backtrack[i][j] == 0) {
                i--;
                alignedSequence2 = alignedSequence2.substring(0,j) + "-" + alignedSequence2.substring(j);
            } else if (backtrack[i][j] == 1) {
                j--;
                alignedSequence1 = alignedSequence1.substring(0,i) + "-" + alignedSequence1.substring(i);
            } else if (backtrack[i][j] == 2) {
                i--;
                j--;
            }
        }

        alignedSequence1 = alignedSequence1.substring(i);
        alignedSequence2 = alignedSequence2.substring(j);

        System.out.println(maxScore);
        System.out.println(alignedSequence1);
        System.out.println(alignedSequence2);
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

    private static Map<String, Map<String, Integer>> readPAM250(String fileName) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Map<String, Map<String, Integer>> scoringMatrix = new HashMap<>();
            String[] letters = reader.readLine().split(" ");
            String line;
            int i = 0;
            while((line = reader.readLine()) != null) {
                String[] scores = line.split(" ");
                for (int j = 0; j < scores.length; j++) {
                    if(!scoringMatrix.containsKey(letters[i])) {
                        scoringMatrix.put(letters[i], new HashMap<String, Integer>());
                    }
                    scoringMatrix.get(letters[i]).put(letters[j], Integer.parseInt(scores[j]));
                }
                i++;
            }
            return scoringMatrix;
        }
    }
}
