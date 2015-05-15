package me.artushghandilyan.problems.chapter4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Artush on 3/24/2015.
 */
public class StringCompositionProblem {
    public static void main(String[] args) throws IOException {
        String dna = readDnaFromFile("rosalind_4a.txt");
        int measure = 100;

        List<String> compositions = getComposition(dna, measure);
        Collections.sort(compositions, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (String composition : compositions) {
            System.out.println(composition);
        }
    }

    private static List<String> getComposition(String dna, int measure) {
        List<String> compositions = new ArrayList<>();
        for (int i = 0; i <= dna.length() - measure; i++) {
            compositions.add(dna.substring(i, i + measure));
        }

        return compositions;
    }

    public static String readDnaFromFile(String filename) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }
}
