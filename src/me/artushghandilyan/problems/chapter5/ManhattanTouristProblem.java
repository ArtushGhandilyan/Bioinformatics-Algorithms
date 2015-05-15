package me.artushghandilyan.problems.chapter5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Artush on 4/24/2015.
 */
public class ManhattanTouristProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_5b.txt");
        Integer targetX = fileContent.getTargetX();
        Integer targetY = fileContent.getTargetY();
        int[][] downMatrix = fileContent.getDownMatrix();
        int[][] rightMatrix = fileContent.getRightMatrix();


        Integer result = tour(targetX, targetY, downMatrix, rightMatrix);
        System.out.println(result);
    }

    private static Integer tour(Integer targetX, Integer targetY, int[][] downMatrix, int[][] rightMatrix) {
        int[][] matrix = new int[targetX + 1][targetY + 1];
        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }


        for (int i = 1; i <= targetX; i++)
            matrix[i][0] = matrix[i-1][0] + downMatrix[i-1][0];
        for (int j = 1; j <= targetY; j++)
            matrix[0][j] = matrix[0][j-1] + rightMatrix[0][j-1];

        for (int i = 1; i <= targetX; i++) {
            for (int j = 1; j <= targetY; j++) {
                matrix[i][j] = Math.max(matrix[i-1][j] + downMatrix[i-1][j],
                        matrix[i][j-1] + rightMatrix[i][j-1]);
            }
        }

        return matrix[targetX][targetY];
    }

    public static FileContent readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String[] targetPoints = reader.readLine().split(" ");
            Integer targetX = Integer.parseInt(targetPoints[0]);
            Integer targetY = Integer.parseInt(targetPoints[1]);

            int[][] downMatrix = new int[targetX][targetY + 1];
            for (int i = 0; i < targetX; i++) {
                String[] row = reader.readLine().split(" ");
                for (int j = 0; j < row.length; j++) {
                    downMatrix[i][j] = Integer.parseInt(row[j]);
                }
            }

            reader.readLine(); // "-" symbol

            int[][] rightMatrix = new int[targetX + 1][targetY];
            for (int i = 0; i < targetX + 1; i++) {
                String[] row = reader.readLine().split(" ");
                for (int j = 0; j < row.length; j++) {
                    rightMatrix[i][j] = Integer.parseInt(row[j]);
                }
            }

            return new FileContent(targetX, targetY, downMatrix, rightMatrix);
        }
    }

    static class FileContent {
        private Integer targetX;
        private Integer targetY;
        private int[][] downMatrix;
        private int[][] rightMatrix;

        public FileContent(Integer targetX, Integer targetY, int[][] downMatrix, int[][] rightMatrix) {
            this.targetX = targetX;
            this.targetY = targetY;
            this.downMatrix = downMatrix;
            this.rightMatrix = rightMatrix;
        }

        public Integer getTargetX() {
            return targetX;
        }

        public Integer getTargetY() {
            return targetY;
        }

        public int[][] getDownMatrix() {
            return downMatrix;
        }

        public int[][] getRightMatrix() {
            return rightMatrix;
        }
    }
}
