import java.util.*;

class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        // Directions: 0:North, 1:East, 2:South, 3:West
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        // Store obstacles in a HashSet for O(1) lookup
        Set<String> obstacleSet = new HashSet<>();
        for (int[] obs : obstacles) {
            obstacleSet.add(obs[0] + "," + obs[1]);
        }

        int x = 0, y = 0, d = 0; // d=0 is North
        int maxDistSq = 0;

        for (int cmd : commands) {
            if (cmd == -1) { // Turn right
                d = (d + 1) % 4;
            } else if (cmd == -2) { // Turn left
                d = (d + 3) % 4;
            } else {
                // Move steps one by one
                for (int step = 0; step < cmd; step++) {
                    int nextX = x + dirs[d][0];
                    int nextY = y + dirs[d][1];
                    
                    // Check if next position is an obstacle
                    if (!obstacleSet.contains(nextX + "," + nextY)) {
                        x = nextX;
                        y = nextY;
                        maxDistSq = Math.max(maxDistSq, x * x + y * y);
                    } else {
                        break; // Stop moving in this direction
                    }
                }
            }
        }

        return maxDistSq;
    }
}
