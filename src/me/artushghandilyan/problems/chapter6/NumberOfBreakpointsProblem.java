package me.artushghandilyan.problems.chapter6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artush on 5/6/2015.
 */
public class NumberOfBreakpointsProblem {
    public static void main(String[] args) throws IOException {
        List<Integer> permutation = readFile("rosalind_6b.txt");
        int number = getNumberOfBreakpoints(permutation);
        System.out.println(number);
    }

    private static int getNumberOfBreakpoints(List<Integer> permutation) {
        int number = permutation.get(0) == 1 ? 0 : 1;
        for (int i = 1; i < permutation.size(); i++) {
            if(permutation.get(i) != permutation.get(i - 1) + 1)
                number++;
        }
        if (permutation.get(permutation.size() - 1) != permutation.size())
            number++;
        return number;
    }

    public static List<Integer> readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            line = line.replaceAll("[(+)]", "");
            String[] split = line.split(" ");

            List<Integer> permutation = new ArrayList<>(split.length);
            for(int i = 0; i < split.length; i++) {
                permutation.add(Integer.parseInt(split[i]));
            }

            return permutation;
        }
    }
}
