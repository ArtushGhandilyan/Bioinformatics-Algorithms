package me.artushghandilyan.problems.chapter2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Artush on 2/28/2015.
 */
public class ProteinTranslationProblem {

    public static final int A = 0;
    public static final int C = 1;
    public static final int G = 2;
    public static final int U = 3;
    public static final String LETTER = "ACGU";
    public static final String[][][] translator = new String[4][4][4];

    static {
        translator[A][A][A] = "K";
        translator[A][A][C] = "N";
        translator[A][A][G] = "K";
        translator[A][A][U] = "N";
        translator[A][C][A] = "T";
        translator[A][C][C] = "T";
        translator[A][C][G] = "T";
        translator[A][C][U] = "T";
        translator[A][G][A] = "R";
        translator[A][G][C] = "S";
        translator[A][G][G] = "R";
        translator[A][G][U] = "S";
        translator[A][U][A] = "I";
        translator[A][U][C] = "I";
        translator[A][U][G] = "M";
        translator[A][U][U] = "I";

        translator[C][A][A] = "Q";
        translator[C][A][C] = "H";
        translator[C][A][G] = "Q";
        translator[C][A][U] = "H";
        translator[C][C][A] = "P";
        translator[C][C][C] = "P";
        translator[C][C][G] = "P";
        translator[C][C][U] = "P";
        translator[C][G][A] = "R";
        translator[C][G][C] = "R";
        translator[C][G][G] = "R";
        translator[C][G][U] = "R";
        translator[C][U][A] = "L";
        translator[C][U][C] = "L";
        translator[C][U][G] = "L";
        translator[C][U][U] = "L";

        translator[G][A][A] = "E";
        translator[G][A][C] = "D";
        translator[G][A][G] = "E";
        translator[G][A][U] = "D";
        translator[G][C][A] = "A";
        translator[G][C][C] = "A";
        translator[G][C][G] = "A";
        translator[G][C][U] = "A";
        translator[G][G][A] = "G";
        translator[G][G][C] = "G";
        translator[G][G][G] = "G";
        translator[G][G][U] = "G";
        translator[G][U][A] = "V";
        translator[G][U][C] = "V";
        translator[G][U][G] = "V";
        translator[G][U][U] = "V";

        translator[U][A][A] = "*";
        translator[U][A][C] = "Y";
        translator[U][A][G] = "*";
        translator[U][A][U] = "Y";
        translator[U][C][A] = "S";
        translator[U][C][C] = "S";
        translator[U][C][G] = "S";
        translator[U][C][U] = "S";
        translator[U][G][A] = "*";
        translator[U][G][C] = "C";
        translator[U][G][G] = "W";
        translator[U][G][U] = "C";
        translator[U][U][A] = "L";
        translator[U][U][C] = "F";
        translator[U][U][G] = "L";
        translator[U][U][U] = "F";
    }

    public static void main(String[] args) {
        String rna = "AUGGCCAUGGCGCCCAGAACUGAGAUCAAUAGUACCCGUAUUAACGGGUGA";
        System.out.println(translate(rna));
    }

    public static String translate(String rna) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = rna.toCharArray();
        for (int i = 0; i < chars.length - 2; i += 3) {
            int i1 = LETTER.indexOf(chars[i]);
            int i2 = LETTER.indexOf(chars[i + 1]);
            int i3 = LETTER.indexOf(chars[i + 2]);
            if(translator[i1][i2][i3].equals("*"))
                break;
            stringBuilder.append(translator[i1][i2][i3]);
        }
        return stringBuilder.toString();
    }
}
