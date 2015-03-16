package me.artushghandilyan.problems.chapter2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artush on 2/28/2015.
 */
public class GeneratingTheoreticalSpectrumProblem {
    public static final Map<String, Integer> aminoAcidIntegerMass = new HashMap<String, Integer>(20);

    static {
        aminoAcidIntegerMass.put("G", 57);
        aminoAcidIntegerMass.put("A", 71);
        aminoAcidIntegerMass.put("S", 87);
        aminoAcidIntegerMass.put("P", 97);
        aminoAcidIntegerMass.put("V", 99);
        aminoAcidIntegerMass.put("T", 101);
        aminoAcidIntegerMass.put("C", 103);
        aminoAcidIntegerMass.put("I", 113);
        aminoAcidIntegerMass.put("L", 113);
        aminoAcidIntegerMass.put("N", 114);
        aminoAcidIntegerMass.put("D", 115);
        aminoAcidIntegerMass.put("K", 128);
        aminoAcidIntegerMass.put("Q", 128);
        aminoAcidIntegerMass.put("E", 129);
        aminoAcidIntegerMass.put("M", 131);
        aminoAcidIntegerMass.put("H", 137);
        aminoAcidIntegerMass.put("F", 147);
        aminoAcidIntegerMass.put("R", 156);
        aminoAcidIntegerMass.put("Y", 163);
        aminoAcidIntegerMass.put("W", 186);
    }

    public static void main(String[] args) {
        String peptide = "GYKCNACWICYCRN";

        ArrayList<Integer> theoreticalSpectrum = generate(peptide);
        for (Integer integer : theoreticalSpectrum) {
            System.out.print(integer + " ");
        }
    }

    public static ArrayList<Integer> generate(String peptide) {
        ArrayList<String> subStrings = getAllSubString(peptide);
        ArrayList<Integer> theoreticalSpectrum = new ArrayList<Integer>();
        for (String subString : subStrings) {
            theoreticalSpectrum.add(countLinearMass(subString));
        }
        return theoreticalSpectrum;
    }

    public static Integer countLinearMass(String peptide) {
        int mass = 0;
        for (int i = 0; i < peptide.length(); i++) {
            mass += aminoAcidIntegerMass.get(peptide.substring(i, i + 1));
        }
        return mass;
    }

    private static ArrayList<String> getAllSubString(String peptide) {
        ArrayList<String> subStrings = new ArrayList<String>();
        int length = peptide.length();
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(j + i > length) {
                    subStrings.add(peptide.substring(j) + peptide.substring(0, i + j - length));
                } else {
                    subStrings.add(peptide.substring(j, j + i));
                }
            }
        }
        subStrings.add(0, "");
        subStrings.add(peptide);
        return subStrings;
    }
}
