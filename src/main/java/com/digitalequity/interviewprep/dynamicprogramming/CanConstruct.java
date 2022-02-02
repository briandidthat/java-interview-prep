package com.digitalequity.interviewprep.dynamicprogramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CanConstruct
 * Write a function `canConstruct(target, wordBank)` that accepts a target string and an array of strings.
 * The function should return a boolean indicating wether or not the target can be constructed by concatenating
 * elements of the wordBank array. Can reuse any words from the wordBank.
 */
public class CanConstruct {
    // brute force
    // m = target.length, n = wordBank.length
    // O(n^m * m) exponential time complexity
    // O(m^2) quadratic space complexity
    static boolean canConstruct(String target, String[] wordBank) {
        if (target.equals("")) return true;
        // iterate through the wordBank
        for (String word : wordBank) {
            // check if the current word is a prefix of the target word
            if (target.indexOf(word) == 0) {
                // grab the rest of the word minus the prefix
                String suffix = target.substring(word.length());
                if (canConstruct(suffix, wordBank)) return true;
            }
        }

        return false;
    }
    // memoized version
    // m = target.length, n = wordBank.length
    // O(n * m^2) polynomial time complexity
    // O(m^2) quadratic space complexity
    static boolean canConstructMemoized(String target, String[] wordBank, Map<String, Boolean> memo) {
        // if target string already in memo, return the value at that key
        if (memo.containsKey(target)) return memo.get(target);
        // if the current target is the empty string, that means we have found a solution
        if (target.equals("")) return true;

        // iterate through the word bank
        for (String word: wordBank) {
            // check if the current word is a prefix of the target word
            if (target.indexOf(word) == 0) {
                // grab the rest of the word minus the prefix
                String suffix = target.substring(word.length());
                if (canConstructMemoized(suffix, wordBank, memo)) {
                    // add the target to the memo if the suffix can be constructed
                    memo.put(target, true);
                    return true;
                }
            }
        }
        // if we arrive here, we have not found a solution
        return false;
    }

    static boolean canConstructTabulated(String target, String[] wordBank) {
        List<Boolean> table = new ArrayList<>();
        for (int i = 0; i < target.length() + 1; i++) {
            table.add(i,false);
        }
        table.set(0, true); // seed the first position as true

        for (int i = 0; i < table.size(); i++) {
            if (table.get(i)) {
                for (String word : wordBank) {
                    // if the word matches the characters starting at position i
                    if (target.startsWith(word, i)) {
                        // set the element at position i + word.length to true
                        table.set(i + word.length(), true);
                    }
                }
            }
        }

        return table.get(target.length());
    }


    public static void main(String[] args) {
        String target = "abcdef";
        String[] wordBank = new String[] {"ab", "abc", "cd","def","abcd"};

        boolean canConstructResult = canConstruct(target, wordBank);
        boolean canConstructMemoizedResult = canConstructMemoized(target, wordBank, new HashMap<>());
        boolean canConstructTabulatedResult = canConstructTabulated(target, wordBank);

        System.out.println("Can Construct: Expected=true, Actual=" + canConstructResult);
        System.out.println("Can Construct: Expected=true, Actual=" + canConstructMemoizedResult);
        System.out.println("Can Construct: Expected=true, Actual=" + canConstructTabulatedResult);
    }
}

