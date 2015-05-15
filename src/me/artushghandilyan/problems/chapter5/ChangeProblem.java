package me.artushghandilyan.problems.chapter5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Artush on 4/24/2015.
 */
public class ChangeProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_5a.txt");
        Integer amount = fileContent.getAmount();
        List<Integer> coins = fileContent.getCoins();

        Integer minNumber = charge(amount, coins);
        System.out.println(minNumber);
    }

    private static Integer charge(Integer amount, List<Integer> coins) {
        int[] minCoins = new int[amount+1];
        int min = Collections.min(coins);
        Arrays.fill(minCoins, amount/min+1);
        minCoins[0] = 0;

        for (int i = 1; i < amount + 1; i++) {
            for (Integer coin : coins) {
                if(i >= coin) {
                    if(minCoins[i - coin] + 1 < minCoins[i]) {
                        minCoins[i] = minCoins[i - coin] + 1;
                    }
                }
            }
        }

        return minCoins[amount];
    }

    public static FileContent readFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Integer amount = Integer.parseInt(reader.readLine());
            List<Integer> coins = new ArrayList<>();
            for (String coin : reader.readLine().split(",")) {
                coins.add(Integer.parseInt(coin));
            }
            return new FileContent(amount, coins);
        }
    }

    static class FileContent {
        private Integer amount;
        private List<Integer> coins;

        public FileContent(Integer amount, List<Integer> coins) {
            this.amount = amount;
            this.coins = coins;
        }

        public Integer getAmount() {
            return amount;
        }

        public List<Integer> getCoins() {
            return coins;
        }
    }
}
