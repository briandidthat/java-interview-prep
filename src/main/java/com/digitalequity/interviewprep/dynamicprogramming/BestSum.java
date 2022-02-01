package com.digitalequity.interviewprep.dynamicprogramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BestSum
 * Write a function bestNum(targetSum, numbers) that takes in a target sum and an array of numbers as arguments.
 * The function should return an array containing the shortest combination of numbers that add up to exactly the
 * targetSum. IF there is a tie, return either one of the shortest.
 */
public class BestSum {
    static List<Integer> bestSum(int targetSum, int[] numbers) {
        // base case: if targetSum is 0 return empty list
        if (targetSum == 0) return new ArrayList<>();
        // base case: if target sum is negative number, return null
        if (targetSum < 0) return null;

        List<Integer> shortestCombination = null;
        for (int number: numbers) {
            // calculate the remainder
            int remainder = targetSum - number;
            // call this function recursively using the remainder
            List<Integer> remainderCombination = bestSum(remainder, numbers);
            if (remainderCombination != null) {
                // initialize a list and add all the current numbers in remainder Combination.
                List<Integer> combination = new ArrayList<>(remainderCombination);
                combination.add(number); // add the current number to the end of the list
                // if the shortest combination list is empty (first go) or combination is less than current shortest
                if (shortestCombination == null || combination.size() < shortestCombination.size()) {
                    // update the value to reflect the current shortest
                    shortestCombination = combination;
                }
            }
        }
        return shortestCombination;
    }

    // Memoized
    // O(n * m^2) polynomial time complexity
    // O(m^2) space complexity
    static List<Integer> bestSumMemoized(int targetSum, int[] numbers, Map<Integer, List<Integer>> memo) {
        if (memo.containsKey(targetSum)) return memo.get(targetSum);
        if (targetSum == 0) return new ArrayList<>();
        if (targetSum < 0) return null;

        List<Integer> shortestCombination = null;
        for (int num : numbers) {
            int remainder = targetSum - num;
            List<Integer> remainderCombination = bestSumMemoized(remainder, numbers, memo);
            if (remainderCombination != null) {
                // initialize a list and add all the current numbers in remainder Combination.
                List<Integer> combination = new ArrayList<>(remainderCombination);
                combination.add(num); // add the current number to the end of the list
                if (shortestCombination == null ||
                        combination.size() < shortestCombination.size()) {
                    shortestCombination = combination;
                }
            }
        }
        memo.put(targetSum, shortestCombination);
        return shortestCombination;
    }

    static List<Integer> bestSumTabulated(int targetSum, int[] numbers) {
        // initialize a list of length targetSum + 1
        List<List<Integer>> table = new ArrayList<>(targetSum + 1);
        table.add(0, new ArrayList<>()); // seed the first position with an empty list
        // fill the rest with nulls
        for (int i = 1; i <= targetSum; i++) {
            table.add(null);
        }

        // iterate up to the target sum
        for (int i = 0; i <= targetSum; i++) {
            if (table.get(i) != null) {
                // iterate over the input numbers array
                for (int num : numbers) {
                    List<Integer> combination = new ArrayList<>(table.get(i));
                    combination.add(num);
                    // make sure that i + num is inbounds
                    if (i + num <= targetSum) {
                        // if the combination is null or shorter than current combination, assign at position i + num
                        if (table.get(i + num) == null || combination.size() < table.get(i + num).size()) {
                            // using set since we want to replace the value instead of push the existing value right
                            table.set(i + num, combination);
                        }
                    }
                }
            }
        }

        return table.get(targetSum);
    }

    public static void main(String[] args) {
        int targetSum = 7;
        int[] numbers = new int[]{5,3,4,7};
        List<Integer> bestSumResult = bestSum(targetSum, numbers);
        List<Integer> bestSumMemoizedResult = bestSumMemoized(targetSum, numbers, new HashMap<>());
        List<Integer> bestSumTabulatedResult = bestSumTabulated(targetSum, numbers);

        System.out.println("Best sum brute force: " + bestSumResult);
        System.out.println("Best sum memoized: " + bestSumMemoizedResult);
        System.out.println("Best sum tabulated: " + bestSumTabulatedResult);
    }
}
