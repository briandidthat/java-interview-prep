package com.digitalequity.interviewprep.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;


public class GridTraveler {
    /*
     * GridTraveler
     * Say that you are a traveler on a 2D grid. You begin in the top left corner and your goal is to travel to the
     * bottom right corner. You may only move down or right. In how many ways can you on a grid with dimensions m * n?
     * */

    static Integer gridTravelerMemoized(int m, int n, Map<String, Integer> memo) {
        if (m == 1 && n == 1) return 1;
        if (m == 0 || n == 0) return 0;

        String location = m + "," + n;
        if (memo.containsKey(location)) return memo.get(location);

        memo.put(location, gridTravelerMemoized(m - 1, n, memo) + gridTravelerMemoized(m, n - 1, memo));

        return memo.get(location);
    }

    static Integer gridTravelerTabulation(int m, int n) {
        // base cases: grid is of size 0 or 1
        if (m == 0 || n == 0) return 0;
        if (m == 1 && n == 1) return 1;

        // initialize a 2d array (grid)
        int[][] table = new int[m + 1][n + 1];
        table[1][1] = 1;
        // iterate over the rows
        for (int i = 0; i <= m; i++) {
            // iterate over the columns
            for (int j = 0; j <= n; j++) {
                // grab the current element so that we can add it to the right and downward neighbor
                int current = table[i][j];
                // check if i and j are inbounds before pushing the value
                if (j + 1 <= n) table[i][j + 1] += current; // add to rightward neighbor
                if (i + 1 <= m) table[i + 1][j] += current; // add to downward neighbor
            }
        }

        return table[m][n];
    }

    public static void main(String[] args) {
        int m = 20;
        int n = 20;
        HashMap<String, Integer> memo = new HashMap<>();

        int memoizedResult = gridTravelerMemoized(m, n, memo);
        int tabulatedResult = gridTravelerTabulation(m, n);
        System.out.printf("Grid traveler memoized: %d %n", memoizedResult);
        System.out.printf("Grid traveler memoized: %d", tabulatedResult);
    }
}
