package me.artushghandilyan.problems.chapter6;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artush on 5/30/2015.
 */
public class SharedKMersProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_6d.txt");
        findSharedPairs(fileContent.length, fileContent.genome1, fileContent.genome2);
    }

    private static void findSharedPairs(Integer length, String genome1, String genome2) {
        Map<String, List<Integer>> kMersIndexes1 = getKmerIndexes(length, genome1);
        Map<String, List<Integer>> kMersIndexes2 = getKmerIndexes(length, genome2);

        for (String kmer : kMersIndexes1.keySet()) {
            if(kMersIndexes2.containsKey(kmer)) {
                List<Integer> indexes2 = kMersIndexes2.get(kmer);
                List<Integer> indexes1 = kMersIndexes1.get(kmer);

                for (Integer index1 : indexes1) {
                    for (Integer index2 : indexes2) {
                        System.out.println("(" + index1 + ", " + index2 + ")");
                    }
                }
            }

            String reverse = reverse(kmer);
            if(kMersIndexes2.containsKey(reverse)) {
                List<Integer> indexes1 = kMersIndexes1.get(kmer);
                List<Integer> indexes2 = kMersIndexes2.get(reverse);

                for (Integer index1 : indexes1) {
                    for (Integer index2 : indexes2) {
                        System.out.println("(" + index1 + ", " + index2 + ")");
                    }
                }
            }
        }
    }

    private static Map<String, List<Integer>> getKmerIndexes(Integer length, String genome2){
        Map<String, List<Integer>> kmerWithIndexes = new HashMap<>();
        for(int index = 0; index < genome2.length() - length + 1; index++){
            String kmer = genome2.substring(index, index + length);
            if (!kmerWithIndexes.containsKey(kmer))
                kmerWithIndexes.put(kmer, new ArrayList<Integer>());

            List<Integer> indexes = kmerWithIndexes.get(kmer);
            indexes.add(index);
            kmerWithIndexes.put(kmer, indexes);
        }
        return kmerWithIndexes;
    }

    private static String reverse(String pattern) {
        StringBuilder stringBuilder = new StringBuilder(pattern.length());
        char[] chars = pattern.toCharArray();
        int length = chars.length - 1;
        for (int i = length; i >= 0; i--) {
            switch (chars[i]) {
                case 'A':
                    stringBuilder.append('T');
                    break;
                case 'T':
                    stringBuilder.append('A');
                    break;
                case 'G':
                    stringBuilder.append('C');
                    break;
                case 'C':
                    stringBuilder.append('G');
                    break;
                default:
                    break;
            }
        }
        return stringBuilder.toString();
    }

    public static FileContent readFile(String fileName) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))){
            int length = Integer.parseInt(br.readLine());
            String genome1 = br.readLine();
            String genome2 = br.readLine();

            return new FileContent(length, genome1, genome2);
        }
    }

    static class FileContent {
        public Integer length;
        public String genome1;
        public String genome2;

        public FileContent(Integer length, String genome1, String genome2) {
            this.length = length;
            this.genome1 = genome1;
            this.genome2 = genome2;
        }
    }
}
