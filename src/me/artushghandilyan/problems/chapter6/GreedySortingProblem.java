package me.artushghandilyan.problems.chapter6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artush on 5/5/2015.
 */
public class GreedySortingProblem {
    public static void main(String[] args) throws IOException {
        List<Integer> permutation = readFile("rosalind_6a.txt");
        List<List<Integer>> permutations = greedySorting(permutation);
        for (List<Integer> perm : permutations) {
            System.out.print("(");
            for (int i = 0; i < perm.size(); i++) {
                Integer integer = perm.get(i);
                System.out.print(integer > 0 ? "+" + integer : integer);
                if(i != perm.size() - 1)
                    System.out.print(" ");
            }
            System.out.println(")");
        }
    }


    public static List<List<Integer>> greedySorting(List<Integer> permutation) {
        List<List<Integer>> permutations = new ArrayList<>();
        int i = 0;
        while(i < permutation.size()) {
            if(permutation.get(i) == i + 1) {
                i++;
            } else {
                int index = getIndex(permutation, i + 1);
                permutation = transform(permutation, i, index);
                permutations.add(permutation);
            }
        }
        return permutations;
    }

    private static List<Integer> transform(final List<Integer> permutation, int position, int index) {
        List<Integer> transformedPermutation = new ArrayList<>(permutation.size());
        for (int j = 0; j < position; j++)
            transformedPermutation.add(permutation.get(j));
        for (int j = index; j >= position; j--)
            transformedPermutation.add(-permutation.get(j));
        for (int j = index + 1; j < permutation.size(); j++)
            transformedPermutation.add(permutation.get(j));

        return transformedPermutation;
    }

    private static int getIndex(List<Integer> permutation, Integer k) {
        int index = permutation.indexOf(k);
        return index != -1 ? index : permutation.indexOf(-k);
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
