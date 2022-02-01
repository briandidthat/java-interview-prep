package com.digitalequity.interviewprep.dynamicprogramming;

/**
 * CanConstruct
 * Write a function `canConstruct(target, wordbank)` that accepts a target string and an array of strings.
 * The function should return a boolean indicating wether or not the target can be constructed by concatenating
 * elements of the wordbank array. Can reuse any words from the wordbank.
 */
public class CanConstruct {
    static boolean canConstruct(String target, String[] wordBank) {
        if (target.equals("")) return true;

        for (String word : wordBank) {
            // check if the current word is a prefix of the target word
            if (target.indexOf(word) == 0) {
                // grab the rest of the word
                String suffix = target.substring(word.length());
                if (canConstruct(suffix, wordBank)) return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String target = "abcdef";
        String[] wordBank = new String[] {"ab", "abc", "cd","def","abcd"};

        boolean canConstructResult = canConstruct(target, wordBank);
        System.out.println("Can Construct: Expected=true, Actual=" + canConstructResult);
    }
}

