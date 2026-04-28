import java.util.*;

class Solution {
    public int minOperations(int[][] grid, int x) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[] arr = new int[rows * cols];
        int mod = grid[0][0] % x;
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // All elements must have the same remainder when divided by x
                if (grid[i][j] % x != mod) return -1;
                arr[index++] = grid[i][j];
            }
        }

        // Sort to find the median
        Arrays.sort(arr);
        int median = arr[arr.length / 2];
        int operations = 0;

        for (int val : arr) {
            // Sum the number of steps of size x to reach the median
            operations += Math.abs(val - median) / x;
        }

        return operations;
    }
}
