package me.artushghandilyan.problems.chapter4;

import java.util.*;

/**
 * Created by Artush on 6/1/2015.
 */
public class UniversalCircularStringProblem {

    public static void main(String[] args) {
        int k = 4;

        Map<String, List<String>> adjacencyList = constructDeBruijnGraph(buildPatterns(4));
        Stack<String> eulerianPath = getEulerianPath(adjacencyList, adjacencyList.keySet().iterator().next());

        int index = eulerianPath.peek().length() - 1;
        while (eulerianPath.size() > 1) {
            System.out.print(eulerianPath.pop().charAt(index));
        }
        System.out.println();
    }

    private static List<String> buildPatterns(int k) {
        List<String> sequence = new ArrayList<>();
        for(int i = 0; i < Math.pow(2, k); i++) {
            String str = Integer.toBinaryString(i);
            if(str.length() < k) {
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < k - str.length(); j++) {
                    sb.append(0);
                }
                str = sb.append(str).toString();
            }
            sequence.add(str);
        }
        return sequence;
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
}
