package me.artushghandilyan.problems.chapter2;

import java.util.*;

/**
 * Created by Artush on 3/9/2015.
 */
public class LeaderboardCyclopeptideSequencingProblem {
    public static int[] AMINO_ACID_MASS = {57,71,87,97,99,101,103,113,114,115,128,129,131,137,147,156,163,186};

    public static void main(String[] args) {
        int n = 10;
        ArrayList<Integer> spectrum = new ArrayList<Integer>(
                Arrays.asList(0, 71, 113, 129, 147, 200, 218, 260, 313, 331, 347, 389, 460));

        ArrayList<Integer> peptide = leaderBoardSequencing(spectrum, n);
        System.out.println(toStringWithDelimiter(peptide, "-"));
    }



    public static ArrayList<Integer> leaderBoardSequencing(ArrayList<Integer> spectrum, int n) {
        ArrayList<ArrayList<Integer>> leaderBoard = new ArrayList<>();
        ArrayList<Integer> leaderPeptide = new ArrayList<>();
        int parentMass = getParentMass(spectrum);

        leaderBoard.add(new ArrayList<Integer>());
        while (!leaderBoard.isEmpty()) {
            leaderBoard = expand(leaderBoard);
            ArrayList<ArrayList<Integer>> tempLeaderBoard = new ArrayList<>(leaderBoard);
            for (ArrayList<Integer> peptide : leaderBoard) {
                int mass = getMass(peptide);
                if(mass == parentMass) {
                    if(getScore(peptide, spectrum) > getScore(leaderPeptide, spectrum)) {
                        leaderPeptide = peptide;
                    }
                } else if(mass > parentMass){
                    tempLeaderBoard.remove(peptide);
                }
            }
            leaderBoard = cutLeads(tempLeaderBoard, n, spectrum);
        }

        return leaderPeptide;
    }

    private static int getParentMass(ArrayList<Integer> spectrum) {
        int max = 0;
        for (Integer integer : spectrum) {
            max = max > integer ? max : integer;
        }
        return max;
    }

    private static ArrayList<ArrayList<Integer>> expand(ArrayList<ArrayList<Integer>> leaderBoard) {
        ArrayList<ArrayList<Integer>> expandedLeaders = new ArrayList<>();
        for (ArrayList<Integer> leader : leaderBoard) {
            for (int i = 0; i < AMINO_ACID_MASS.length; i++) {
                ArrayList<Integer> expandedLeader = new ArrayList<>(leader);
                expandedLeader.add(AMINO_ACID_MASS[i]);
                expandedLeaders.add(expandedLeader);
            }
        }
        return expandedLeaders;
    }

    private static int getMass(ArrayList<Integer> peptide) {
        int sum = 0;
        for (Integer integer : peptide) {
            sum += integer;
        }
        return sum;
    }

    private static Integer getScore(ArrayList<Integer> peptide, ArrayList<Integer> spectrum) {
        ArrayList<Integer> experimentalSpectrum = new ArrayList<>(spectrum);
        ArrayList<Integer> theoreticalSpectrum = getSpectrum(peptide);
        int scope = 0;
        for (Integer integer : theoreticalSpectrum) {
            if(experimentalSpectrum.contains(integer)) {
                scope++;
                experimentalSpectrum.remove(integer);
            }
        }
        return scope;
    }

    public static ArrayList<Integer> getSpectrum(ArrayList<Integer> peptide) {
        ArrayList<ArrayList<Integer>> subPeptides = getSubPeptides(peptide);
        ArrayList<Integer> spectrum = new ArrayList<>();
        spectrum.add(0);
        for (ArrayList<Integer> subPeptide : subPeptides) {
            int sumMass = 0;
            for (Integer mass : subPeptide)
                sumMass += mass;
            spectrum.add(sumMass);
        }
        Collections.sort(spectrum);
        return spectrum;
    }

    private static ArrayList<ArrayList<Integer>> getSubPeptides(ArrayList<Integer> peptide) {
        ArrayList<ArrayList<Integer>> subPeptides = new ArrayList<>();
        subPeptides.add(peptide);
        ArrayList<Integer> extendedPeptide = new ArrayList<>(peptide);
        extendedPeptide.addAll(peptide.subList(0, peptide.size() > 2 ? peptide.size() - 2 : 0));
        for (int i = 0; i < peptide.size(); i++) {
            for (int j = 0; j < peptide.size() - 1; j++) {
                subPeptides.add(new ArrayList<>(extendedPeptide.subList(i, i + j + 1)));
            }
        }
        return subPeptides;
    }

    private static ArrayList<ArrayList<Integer>> cutLeads(ArrayList<ArrayList<Integer>> leaderBoard,
                                                          int n, final ArrayList<Integer> spectrum){
        if(n > leaderBoard.size())
            return leaderBoard;

        Collections.sort(leaderBoard, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return -getScore(o1, spectrum).compareTo(getScore(o2, spectrum));
            }
        });

        ArrayList<ArrayList<Integer>> newLeaderBoard = new ArrayList<>();
        Integer lastScore = getScore(leaderBoard.get(n), spectrum);
        for (int i = 0; i < leaderBoard.size(); i++) {
            if(i < n) {
                newLeaderBoard.add(leaderBoard.get(i)); //top n highest-scoring peptides
            } else if(getScore(leaderBoard.get(i), spectrum) >= lastScore) {
                newLeaderBoard.add(leaderBoard.get(i)); //including ties
            }
        }
        return newLeaderBoard;
    }


    private static String toStringWithDelimiter(ArrayList<Integer> peptide, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer integer : peptide) {
            stringBuilder.append(integer).append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
