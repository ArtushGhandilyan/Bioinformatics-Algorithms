package me.artushghandilyan.problems.chapter4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Artush on 6/1/2015.
 */
public class StringReconstructionFromReadPairsProblem {
    public static void main(String[] args) throws IOException {
        FileContent fileContent = readFile("rosalind_4i.txt");

        int k = fileContent.k;
        int d = fileContent.d;
        Map<Pair, List<Pair>> adjacencyList = fileContent.pairs;

        Pair unbalancedNode = getUnbalancedNode(adjacencyList);
        Stack<Pair> eulerianPath = getEulerianPath(adjacencyList, unbalancedNode);

        StringBuilder stringBuilderOne = new StringBuilder();
        StringBuilder stringBuilderTwo = new StringBuilder();

        int index = eulerianPath.get(0).getOne().length() - 1;

        stringBuilderOne.append(eulerianPath.get(eulerianPath.size() - 1).getOne());
        for(int j = eulerianPath.size() - 2; j >= 0; j--) {
            stringBuilderOne.append(eulerianPath.get(j).getOne().charAt(index));
        }

        stringBuilderTwo.append(eulerianPath.get(eulerianPath.size() - 1).getTwo());
        for(int j = eulerianPath.size() - 2; j >= 0; j--) {
            stringBuilderTwo.append(eulerianPath.get(j).getTwo().charAt(index));
        }

        System.out.println(stringBuilderOne.substring(0, k + d) + stringBuilderTwo);
    }

    private static Pair getUnbalancedNode(Map<Pair, List<Pair>> adjacencyList){
        for(Pair node: adjacencyList.keySet()){
            int outDegree = adjacencyList.get(node).size();
            int inDegree = 0;
            for(Pair currentNode: adjacencyList.keySet()){
                if(adjacencyList.get(currentNode).contains(node)){
                    inDegree++;
                }
            }
            if(outDegree == inDegree + 1){
                return node;
            }
        }
        return null;
    }

    private static Stack<Pair> getEulerianPath(Map<Pair, List<Pair>> adjacencyList, Pair startNode) {
        Stack<Pair> path = new Stack<>();

        Stack<Pair> stack = new Stack<>();
        stack.push(startNode);

        while(!stack.empty()){

            Pair node1 = stack.peek();

            List<Pair> adjacencies = adjacencyList.get(node1);
            if(adjacencies != null && adjacencies.size() != 0) {
                Pair node2 = adjacencies.iterator().next();
                stack.push(node2);
                adjacencies.remove(node2);
            } else {
                path.push(node1);
                stack.pop();
            }
        }
        return path;
    }

    public static FileContent readFile(String fileName) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            Map<Pair, List<Pair>> pairs = new HashMap<>();
            String line;
            while((line = br.readLine()) != null) {
                String[] split = line.split("\\|");
                Pair pair1 = new Pair(split[0]);
                Pair pair2 = new Pair(split[1]);

                String two = pair1.two;
                pair1.two = pair2.one;
                pair2.one = two;

                if (!pairs.containsKey(pair1))
                    pairs.put(pair1, new ArrayList<Pair>());

                pairs.get(pair1).add(pair2);
            }
            return new FileContent(k, d, pairs);
        }
    }

    static class FileContent {
        public Integer k;
        public Integer d;
        public Map<Pair, List<Pair>> pairs;

        public FileContent(Integer k, Integer d, Map<Pair, List<Pair>> pairs) {
            this.k = k;
            this.d = d;
            this.pairs = pairs;
        }
    }

    static class Pair {
        String one;
        String two;

        private Pair(String string) {
            this.one = string.substring(0, string.length() - 1);
            this.two= string.substring(1);
        }

        public String getOne() {
            return one;
        }

        public String getTwo() {
            return two;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + Arrays.deepHashCode(new String[]{one, two});
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair other = (Pair) obj;
            return this.one.equals(other.getOne()) && this.two.equals(other.getTwo());
        }
    }
}
