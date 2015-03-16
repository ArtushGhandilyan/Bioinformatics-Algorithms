package me.artushghandilyan.problems.chapter2;

import java.util.*;

/**
 * Created by Artush on 3/9/2015.
 */
public class SpectralConvolutionProblem {
    public static void main(String[] args) {
        ArrayList<Integer> spectrum = new ArrayList<Integer>(Arrays.asList(0, 137, 186, 323));
        Collections.sort(spectrum);
        ArrayList<Integer> result = computeConvolution(spectrum);
        for (Integer integer : result) {
            System.out.print(integer + " ");
        }
    }

    public static ArrayList<Integer> computeConvolution(ArrayList<Integer> spectrum) {
        final Map<Integer, Integer> elementsCountMap = new HashMap<>();
        ArrayList<Integer> convolutionOfSpectrum = new ArrayList<>();
        for (int i = 0; i < spectrum.size(); i++) {
            for (int j = 0; j < i; j++) {
                int element = spectrum.get(i) - spectrum.get(j);
                if(element != 0) {
                    int count = 1;
                    if(elementsCountMap.containsKey(element))
                        count += elementsCountMap.get(element);

                    convolutionOfSpectrum.add(element);
                    elementsCountMap.put(element, count);
                }
            }
        }

        Collections.sort(convolutionOfSpectrum, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int comparator = -elementsCountMap.get(o1).compareTo(elementsCountMap.get(o2));
                if(comparator == 0)
                    comparator = o1.compareTo(o2);
                return comparator;
            }
        });
        return convolutionOfSpectrum;
    }
}
