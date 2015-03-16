package me.artushghandilyan.problems.chapter2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 3/15/2015.
 */
public class TheTurnpikeProblem {
    public static List<Integer> orderedPositiveValues = new ArrayList<>();
    public static Map<Integer, Integer> counts = new HashMap<>();
    public static Integer segmentLength = 0;
    public static int[][] matrix;

    public static void main(String[] args) throws IOException {
        //fill data from file and sort it.
        fillData();
        Collections.sort(orderedPositiveValues, Collections.reverseOrder());

        List<Integer> segment = new ArrayList<>(segmentLength);
        boolean success = reconstruct();

        if(success) {
            int sum = 0;
            segment.add(sum);
            for (int i = 0; i < segmentLength - 1; i++) {
                sum += matrix[i][i+1];
                segment.add(sum);
            }

            //print segment
            for (Integer value : segment) {
                System.out.print(value + " ");
            }
            System.out.println();

        } else {
            System.out.println("Failed!");
        }
    }

    private static boolean reconstruct() {
        matrix = new int[segmentLength][segmentLength];

        int row = 0, col = segmentLength - 1, index = 0;

        //get max value and put top right corner on matrix.
        Integer maxValue = orderedPositiveValues.get(index);
        matrix[row][col] = maxValue;
        counts.put(maxValue, counts.get(maxValue) - 1);

        if(reconstructNext(row, col, index, true) || reconstructNext(row, col, index, false))
            return true;
        return false;
    }

    /**
     * Reconstruct segment next value;
     * @param row       Last reconstructed row index.
     * @param col       Last reconstructed col index.
     * @param index     Last reconstructed positiveValue index.
     * @param isPlaceOnRight Is last reconstructed value in right column.
     * @return          True is reconstruction success.
     */
    private static boolean reconstructNext(int row, int col, int index, boolean isPlaceOnRight) {
        int N = segmentLength - 1;
        
        if(row == col)
            return true;

        Stack<List<Integer>> stack = new Stack<>();
        int maxValue = getNextMaxValue();

        if(isPlaceOnRight) {
            
            if(row + 1 >= N)
                return false;
            
            setValue(row + 1, N, maxValue, stack);
            
            //set diagonal value
            int diagonalValue = matrix[row][N] - matrix[row + 1][N];
            if(setValue(row, row + 1, diagonalValue, stack)) {
                boolean hasProblem = false;

                int fromIndex = Math.max(row+2, col);
                for (int k = fromIndex; k <= N - 1; k++) {
                    if(!setValue(row+1, k, matrix[row][k] - diagonalValue, stack))
                        hasProblem = true;
                }

                if(!hasProblem) {
                    if(reconstructNext(row + 1, col, index, false) || reconstructNext(row + 1, col, index, true))
                        return true;
                }
            }

        } else {
            if(col - 1 <= 1)
                return false;

            setValue(0, col - 1, maxValue, stack);

            //set diagonal value
            int diagonalValue = matrix[0][col] - matrix[0][col - 1];
            if(setValue(col - 1, col, diagonalValue, stack)) {
                boolean hasProblem = false;

                int toIndex = Math.min(row, col - 3);
                for (int k = 1; k <= toIndex; k++) {
                    if (!setValue(k, col - 1, matrix[k][col] - diagonalValue, stack))
                        hasProblem = true;
                }

                if (!hasProblem) {
                    if (reconstructNext(row, col - 1, index, true) || reconstructNext(row, col - 1, index, false))
                        return true;
                }
            }
        }

        rollback(stack);
        return false;
    }

    private static void rollback(Stack<List<Integer>> stack) {
        while (!stack.empty()) {
            List<Integer> pop = stack.pop();
            int row = pop.get(0), col = pop.get(1), value = pop.get(2);
            matrix[row][col] = 0;
            counts.put(value, counts.get(value) + 1);
        }
    }

    private static int getNextMaxValue() {
        for (int i = 0; i < orderedPositiveValues.size(); i++) {
            Integer nextPositiveValue = orderedPositiveValues.get(i);
            if(counts.get(nextPositiveValue) > 0)
                return nextPositiveValue;
        }
        return -1;
    }

    private static boolean setValue(int row, int col, int value, Stack<List<Integer>> stack) {
        if(matrix[row][col] == value) //value already set.
            return true;
        if(!counts.containsKey(value) || counts.get(value) == 0)
            return false;
        matrix[row][col] = value;
        counts.put(value, counts.get(value) - 1);
        stack.push(new ArrayList<Integer>(Arrays.asList(row, col, value)));
        return true;
    }

    private static void fillData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("rosalind_2i.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\\s+");
            for (int i = 0; i < split.length; i++) {
                int value = Integer.parseInt(split[i]);
                if (value > 0) {
                    if(counts.containsKey(value)) {
                        counts.put(value, counts.get(value) + 1);
                    } else {
                        counts.put(value, 1);
                        orderedPositiveValues.add(value);
                    }
                }
                if(value == 0)
                    segmentLength++;
            }
        }
    }
}
