package com.digitalequity.interviewprep.dynamicprogramming;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HowSum {
    // brute force recursive solution
    // m = target sum, n = array length
    // O(n^m * m) exponential time complexity, O(m) space complexity
    static int[] howSum(int targetSum, int[] numbers) {
        // if we arrive 0, bubble the empty array up
        if (targetSum == 0) return new int[]{};
        // if the targetSum becomes negative, there is no solution so return null
        if (targetSum < 0) return null;
        // iterate over the array
        for (int number : numbers) {
            // calculate the difference btw targetSum and current num
            int remainder = targetSum - number;
            int[] remainderResult = howSum(remainder, numbers);

            if (remainderResult != null) {
                // create temp array that we'll use to copy values of length remainderResult + 1
                int[] result = new int[remainderResult.length + 1];
                System.arraycopy(remainderResult, 0, result, 0, remainderResult.length);
                // add the current number to the end of the array
                result[remainderResult.length] = number;
                remainderResult = result; // replace the remainder result arr with the temp array
                return remainderResult;
            }
        }
        // if we reach here, there is no way to sum to the target
        return null;
    }

    // memoized solution
    // m = targetSum, n = array length
    // O(n * m^2) polynomial time complexity, O(m^2) space complexity
    static int[] howSumMemoized(int targetSum, int[] numbers, HashMap<Integer, int[]> memo) {
        // if we already have the current targetSum in memo, return in
        if (memo.get(targetSum) != null) return memo.get(targetSum);
        if (targetSum == 0) return new int[]{}; // bubble up empty array
        if (targetSum < 0) return null; // if negative, we went too deep with recursion

        for (int number : numbers) {
            int remainder = targetSum - number;
            int[] remainderResult = howSumMemoized(remainder, numbers, memo);

            if (remainderResult != null) {
                // create temp array that we'll use to copy values of length remainderResult.length + 1
                int[] result = new int[remainderResult.length + 1];
                System.arraycopy(remainderResult, 0, result, 0, remainderResult.length);
                result[remainderResult.length] = number;
                remainderResult = result; // replace the remainder result arr with the temp array
                // store the current remainder result array in memo
                memo.put(targetSum, remainderResult);
                return remainderResult;
            }
        }
        // if we arrive here, there is no path to target sum so return null
        memo.put(targetSum, null);
        return null;
    }

    // tabulated solution
    // O(m^2*n) polynomial time complexity, O(m^2) space complexity
    static int[] howSumTabulation(int targetSum, int[] numbers) {
        // Initialize a 2d List of length targetSum + 1
        List<List<Integer>> table = new ArrayList<>(targetSum + 1);
        table.add(0, new ArrayList<>()); // seed an empty list at position 0
        for (int i = 1; i < targetSum; i++) {
            // add null values at all other positions in the table
            table.add(i, null);
        }

        // iterate through the array until we arrive at target sum
        for (int i = 0; i < targetSum; i++){
            if (table.get(i) != null) {
                for (int num : numbers) {
                    // create a new list with
                    List<Integer> temp = new ArrayList<>(table.get(i));
                    temp.add(num);
                    table.add(i + num, temp);
                }
            }
        }

        return table.get(targetSum).stream().mapToInt(i-> i).toArray();
    }

    public static void main(String[] args) {
        final int targetSum = 7;
        final int[] numbers = new int[]{2, 3};
        int[] howSumResult = howSum(targetSum, numbers); // should be [3,2,2]
        int[] howSumMemoizedResult = howSumMemoized(targetSum, numbers, new HashMap<>()); // should be [3,2,2]
        int[] howSumTabulatedResult = howSumTabulation(targetSum, numbers); // should be [3,2,2]

        System.out.println("Expected result: [3, 2, 2]");
        System.out.println("How sum brute force result: " + Arrays.toString(howSumResult));
        System.out.println("How sum memoized result: " + Arrays.toString(howSumMemoizedResult));
        System.out.println("How sum tabulated result: " + Arrays.toString(howSumTabulatedResult));

    }

}
