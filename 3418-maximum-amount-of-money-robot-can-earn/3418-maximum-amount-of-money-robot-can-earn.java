class Solution {
    public int maximumAmount(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;
        // Using long to handle potential underflow/overflow during calculations
        // dp[i][j][k] = max profit at cell (i, j) with k neutralizations remaining
        long[][][] dp = new long[m][n][3];

        // Initialize with a very small value
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[i][j][k] = Long.MIN_VALUE / 2; 
                }
            }
        }

        // Base case: starting cell (0,0)
        dp[0][0][0] = coins[0][0]; // Don't use a neutralization
        dp[0][0][1] = Math.max(dp[0][0][1], 0); // Use 1 neutralization (if negative)
        dp[0][0][2] = Math.max(dp[0][0][2], 0); // Use 1 neutralization (same as k=1 here)

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;

                for (int k = 0; k < 3; k++) {
                    long prevMax = Long.MIN_VALUE / 2;
                    if (i > 0) prevMax = Math.max(prevMax, dp[i - 1][j][k]);
                    if (j > 0) prevMax = Math.max(prevMax, dp[i][j - 1][k]);

                    // Option 1: Take the robber/coins as they are
                    dp[i][j][k] = Math.max(dp[i][j][k], prevMax + coins[i][j]);

                    // Option 2: Use a neutralization if we have one left (k > 0)
                    if (k > 0) {
                        long prevKMax = Long.MIN_VALUE / 2;
                        if (i > 0) prevKMax = Math.max(prevKMax, dp[i - 1][j][k - 1]);
                        if (j > 0) prevKMax = Math.max(prevKMax, dp[i][j - 1][k - 1]);
                        
                        // Current cell effectively becomes 0
                        dp[i][j][k] = Math.max(dp[i][j][k], prevKMax);
                    }
                }
            }
        }

        // Return the max of any neutralization state at the bottom-right corner
        long result = Math.max(dp[m - 1][n - 1][0], 
                      Math.max(dp[m - 1][n - 1][1], dp[m - 1][n - 1][2]));
        
        return (int) result;
    }
}
