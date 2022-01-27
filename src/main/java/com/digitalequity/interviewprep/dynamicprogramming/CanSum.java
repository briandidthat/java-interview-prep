package com.digitalequity.interviewprep.dynamicprogramming;

import java.util.HashMap;

public class CanSum {
    static boolean canSum(int targetSum, int[] numbers) {
        // base case: if the target sum is 0, or we have reduced to 0 via recursion: return true
        if (targetSum == 0) return true;
        // base case: if target sum is negative, or we have gone too far down with recursion: return false
        if (targetSum < 0) return false;

        for (int num : numbers) {
            int remainder = targetSum - num;
            if (canSum(remainder, numbers)) return true;
        }

        return false;
    }

    static boolean canSumMemoized(int targetSum, int[] numbers, HashMap<Integer, Boolean> memo) {
        if (memo.containsKey(targetSum)) return memo.get(targetSum);
        // base case: if the target sum is 0, or we have reduced to 0 via recursion: return true
        if (targetSum == 0) return true;
        // base case: if target sum is negative, or we have gone too far down with recursion: return false
        if (targetSum < 0) return false;

        for (int num : numbers) {
            int remainder = targetSum - num;
            if (canSumMemoized(remainder, numbers, memo)) {
                memo.put(remainder, true);
                return true;
            }
        }

        memo.put(targetSum, false);
        return memo.get(targetSum);
    }

    static boolean canSumTabulated(int targetSum, int[] numbers) {
        boolean[] table = new boolean[targetSum + 1];
        table[0] = true; // seed the array with table[0] since 0 will always return true
        // loop through table up to targetSum
        for (int i = 0; i<= targetSum; i++) {
            // if the value at the current index is true, we need to look at the rest of the table
            if (table[i]) {
                for (int num: numbers) {
                    // if index is valid, set index to true
                    if (i + num <= targetSum) table[i + num] = true;
                }
            }
        }

        return table[targetSum];
    }

    public static void main(String[] args) {
        int target = 37; // should return true for this val
        int[] nums = new int[]{2, 5, 7, 3, 1};
        boolean canSumResult = canSum(target, nums);
        boolean canSumMemoizedResult = canSumMemoized(target, nums, new HashMap<>());
        boolean canSumTabulatedResult = canSumTabulated(target, nums);

        System.out.printf("Can sum naive solution: %b %n", canSumResult);
        System.out.printf("Can sum memoized solution: %b %n", canSumMemoizedResult);
        System.out.printf("Can sum tabulated solution: %b %n", canSumTabulatedResult);
    }
}
