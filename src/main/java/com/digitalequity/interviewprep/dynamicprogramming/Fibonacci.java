package com.digitalequity.interviewprep.dynamicprogramming;

import java.util.*;

public class Fibonacci {
    // 0, 1, 1, 2, 3, 5, 8, 13, 21
    static Integer fibonacciMemoized(int n, Map<Integer, Integer> memo) {
        if (n <= 2) return 1;
        if (memo.containsKey(n)) return memo.get(n);

        memo.put(n, fibonacciMemoized(n - 1 , memo) + fibonacciMemoized(n - 2, memo));

        return memo.get(n);
    }

    static Integer fibonacciTabulation(int n) {
        // initialize table of size n + 1
        int[] table = new int[n + 1];
        table[0] = 0;
        table[1] = 1;

        for (int i = 2; i <= n; i++) {
            table[i] = table[i - 1] + table[i - 2];
        }

        return table[n];
    }


    public static void main(String[] args) {
        Map<Integer, Integer> memo = new HashMap<>();
        int n = 8;
        int memoizedNum = fibonacciMemoized(n, memo); // should return 21
        int tabulatedNum = fibonacciTabulation(n); // should return 21

        System.out.printf("Fibonacci memoized: number at position %d = %d %n", n, memoizedNum);
        System.out.printf("Fibonacci tabulated: number at position %d = %d", n, tabulatedNum);
    }
}
