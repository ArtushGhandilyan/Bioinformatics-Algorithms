package me.artushghandilyan.problems.chapter4;

import java.io.*;
import java.util.*;

/**
 * Created by Artush on 6/1/2015.
 */
public class StringReconstructionProblem {
    public static void main(String[] args) throws IOException {

        int k = 25;

        List<String> patterns = readAllLines("rosalind_4g.txt");
        Map<String, List<String>> adjacencyList = constructDeBruijnGraph(patterns);

        String unbalancedNode = getUnbalancedNode(adjacencyList);
        Stack<String> eulerianPath = getEulerianPath(adjacencyList, unbalancedNode);

        System.out.print(eulerianPath.pop());
        while(!eulerianPath.empty()){
            System.out.print(eulerianPath.pop().charAt(k - 2));
        }
        System.out.println();
    }

    private static Map<String, List<String>> constructDeBruijnGraph(List<String> patterns) {
        Map<String, List<String>> adjacencyList = new HashMap<>();
        for (String pattern : patterns) {
            String leftNode = pattern.substring(0, pattern.length() - 1);
            String rightNode = pattern.substring(1);

            if(!adjacencyList.containsKey(leftNode))
                adjacencyList.put(leftNode, new ArrayList<String>());
            adjacencyList.get(leftNode).add(rightNode);
        }
        return adjacencyList;
    }

    private static String getUnbalancedNode(Map<String, List<String>> adjacencyList){
        for(String node: adjacencyList.keySet()){
            int outDegree = adjacencyList.get(node).size();
            int inDegree = 0;
            for(String currentNode: adjacencyList.keySet()){
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

    private static Stack<String> getEulerianPath(Map<String, List<String>> adjacencyList, String startNode) {
        Stack<String> path = new Stack<>();

        Stack<String> stack = new Stack<>();
        stack.push(startNode);

        while(!stack.empty()){

            String node1 = stack.peek();

            List<String> adjacencies = adjacencyList.get(node1);
            if(adjacencies != null && adjacencies.size() != 0) {
                String node2 = adjacencies.iterator().next();
                stack.push(node2);
                adjacencies.remove(node2);
            } else {
                path.push(node1);
                stack.pop();
            }
        }
        return path;
    }

    public static List<String> readAllLines(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            List<String> result = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;
        }
    }
}
