package me.artushghandilyan.problems.chapter1;

/**
 * Created by Artush on 2/17/2015.
 */
public class ReverseComplementProblem {
    public static void main(String[] args) {
        String dna = "AAAACCCGGT";
        System.out.println(reverse(dna));
    }

    /**
     * Find the reverse complement of a given DNA string pattern.
     * @param pattern a dna string pattern.
     * @return reverse complement of pattern.
     */
    public static String reverse(String pattern) {
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
}
