package me.artushghandilyan.problems.chapter1;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Artush on 2/17/2015.
 */
public class MinimumSkewProblem {
    public static final String GENOME = "CCTATCGGTGGATTAGCATGTCCCTGTACGTTTCGCCGCGAACTAGTTCACACGGCTTGATGGCAAATGGTTTTTCCGGCGACCGTAATCGTCCACCGAG";

    public static void main(String[] args) {

        ArrayList<Integer> minSkewIndexes = getMinSkewIndexes(GENOME);
        for (Integer minSkewIndex : minSkewIndexes) {
            System.out.print(minSkewIndex + " ");
        }
    }

    private static ArrayList<Integer> getMinSkewIndexes(String genome) {
        ArrayList<Integer> minSkewIndexes = new ArrayList<>();
        int minSkew = 0;
        int skew = 0;
        minSkewIndexes.add(0);

        char[] chars = genome.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] == 'G')
                skew++;
            else if(chars[i] == 'C')
                skew--;

            if(skew < minSkew) {
                minSkew = skew;
                minSkewIndexes.clear();
                minSkewIndexes.add(i + 1);
            } else if(skew == minSkew) {
                minSkewIndexes.add(i + 1);
            }
        }
        return minSkewIndexes;
    }

}
