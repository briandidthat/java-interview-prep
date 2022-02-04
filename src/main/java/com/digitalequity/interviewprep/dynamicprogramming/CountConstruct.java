package com.digitalequity.interviewprep.dynamicprogramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * CountConstruct
 * Write a function countConstruct(target, wordBank) that accepts a target string and an array of strings.
 * The function should return the number of ways that the target can be constructed by concatenating elements
 * of the wordBank array.
 */
public class CountConstruct {
    // Brute force
    // O(n^m * m) exponential time complexity
    // O(m^2) quadratic space complexity
    static int countConstruct(String target, String[] wordBank) {
        if (target.equals("")) return 1;

        int totalCount = 0;

        // iterate through the wordBank
        for (String word : wordBank) {
            // check if the current word is a prefix of the target
            if(target.startsWith(word)) {
                // grad the suffix since we already validated the prefix
                String suffix = target.substring(word.length());
                // recursively count the number of ways we can construct the suffix using the word bank
                int numWaysForSuffix = countConstruct(suffix, wordBank);
                totalCount += numWaysForSuffix;
            }
        }
        return totalCount;
    }

    // Memoized version
    // m = target.length, n = wordBank.length
    // O(n * m^2) polynomial time complexity
    // O(m^2) quadratic space complexity
    static int countConstructMemoized(String target, String[] wordBank, HashMap<String, Integer> memo) {
        if (memo.containsKey(target)) return memo.get(target);
        if (target.equals("")) return 1;

        int totalCount = 0;

        // iterate through the wordBank
        for (String word : wordBank) {
            if (target.startsWith(word)) {
                // grab the suffix since we have already validated the prefix
                String suffix = target.substring(word.length());
                // recursively count the number of ways we can construct the suffix using the wordBank
                int numWaysForSuffix = countConstructMemoized(suffix, wordBank, memo);
                totalCount += numWaysForSuffix;
            }
        }
        // store the current total count in the memo
        memo.put(target, totalCount);
        return totalCount;
    }

    // O(m^2*n) polynomial time
    // O(m) linear space complexity
    static int countConstructTabulated(String target, String[] wordBank) {
        List<Integer> table = new ArrayList<>(target.length() + 1);
        table.add(1); // seed 1 at the 0eth position
        for (int i = 1; i <= target.length(); i++) {
            // add 0's at every other position
            table.add(0);
        }

        for (int i = 0; i <= target.length(); i++) {
            for (String word : wordBank) {
                if (target.startsWith(word, i)) {
                    // calculate the sum of the ith position + i + word.length() position
                    int currentSum = table.get(i) + table.get(i + word.length());
                    // store the current sum at the i + word.length position
                    table.set(i + word.length(), currentSum);
                }
            }
        }

        return table.get(target.length());
    }



    public static void main(String[] args) {
        String target = "abcdef";
        String[] wordBank = new String[] {"ab", "abc", "cd","def","abcd"};

        int countConstructResult = countConstruct(target, wordBank);
        int countConstructMemoizedResult = countConstructMemoized(target, wordBank, new HashMap<>());
        int countConstructTabulatedResult = countConstructTabulated(target, wordBank);

        System.out.println("Count Construct: Expected=1, Actual=" + countConstructResult);
        System.out.println("Count Construct Memoized: Expected=1, Actual=" + countConstructMemoizedResult);
        System.out.println("Count Construct Tabulated: Expected=1, Actual=" + countConstructTabulatedResult);
    }
}
