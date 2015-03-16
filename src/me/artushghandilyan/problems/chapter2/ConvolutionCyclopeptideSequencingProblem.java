package me.artushghandilyan.problems.chapter2;

import java.util.*;

/**
 * Created by Artush on 3/14/2015.
 */
public class ConvolutionCyclopeptideSequencingProblem {
    public static void main(String[] args) {
        int m = 20;
        int n = 60;
        ArrayList<Integer> spectrum = new ArrayList<Integer>(
                Arrays.asList(57, 57, 71, 99, 129, 137, 170, 186, 194, 208, 228, 265, 285, 299, 307, 323, 356, 364, 394, 422, 493));

        ArrayList<Integer> peptide = convolutionCyclopeptideSequencing(m, n, spectrum);
        System.out.println(toStringWithDelimiter(peptide, "-"));
    }

    private static ArrayList<Integer> convolutionCyclopeptideSequencing(int m, int n, ArrayList<Integer> spectrum) {
        ArrayList<Integer> spectralConvolution = computeConvolution(spectrum);

        ArrayList<Integer> mostFrequentElements = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            mostFrequentElements.add(spectralConvolution.get(i));
        }

        LeaderboardCyclopeptideSequencingProblem.AMINO_ACID_MASS = convertListToArray(mostFrequentElements);
        return LeaderboardCyclopeptideSequencingProblem.leaderBoardSequencing(spectrum, n);
    }

    public static ArrayList<Integer> computeConvolution(ArrayList<Integer> spectrum) {
        final Map<Integer, Integer> elementsCountMap = new HashMap<>();
        ArrayList<Integer> convolutionOfSpectrum = new ArrayList<>();
        for (int i = 0; i < spectrum.size(); i++) {
            for (int j = 0; j < i; j++) {
                int element = spectrum.get(i) - spectrum.get(j);
                if(element != 0 && element >= 57 && element <= 200) {
                    int count = 1;
                    if(elementsCountMap.containsKey(element))
                        count += elementsCountMap.get(element);

                    if(!convolutionOfSpectrum.contains(element))
                        convolutionOfSpectrum.add(element);
                    elementsCountMap.put(element, count);
                }
            }
        }

        Collections.sort(convolutionOfSpectrum, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int comparator = -elementsCountMap.get(o1).compareTo(elementsCountMap.get(o2));
                if (comparator == 0)
                    comparator = o1.compareTo(o2);
                return comparator;
            }
        });
        return convolutionOfSpectrum;
    }

    private static String toStringWithDelimiter(ArrayList<Integer> peptide, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer integer : peptide) {
            stringBuilder.append(integer).append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private static int[] convertListToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i=0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
