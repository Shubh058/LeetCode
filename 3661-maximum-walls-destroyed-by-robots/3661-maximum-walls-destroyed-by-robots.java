class Solution {
    class Robot {
        int pos;
        int dist;

        Robot(int pos, int dist) {
            this.pos = pos;
            this.dist = dist;
        }
    }

    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        Robot[] r_arr = new Robot[n];
        for (int i = 0; i < n; i++) {
            r_arr[i] = new Robot(
                robots[i],
                distance[i]
            );
        }

        Arrays.sort(r_arr, (a, b) -> Integer.compare(a.pos, b.pos));
        Arrays.sort(walls);

        int[] left_count = new int[n], right_count = new int[n], common = new int[n];

        for (int i = 0; i < n; i++) {
            Robot r = r_arr[i];

            int left_start = r.pos - r.dist;
            if (i > 0) {
                left_start = Math.max(left_start, r_arr[i-1].pos + 1);
            }

            int left_end = r.pos;

            int left_idx_start = lower_bound(walls, left_start);
            int left_idx_end = upper_bound(walls, left_end) - 1;

            if (left_idx_start <= left_idx_end) {
                left_count[i] = left_idx_end - left_idx_start + 1;
            } else {
                left_count[i] = 0;
            }

            int right_start = r.pos;
            int right_end = r.pos + r.dist;

            if (i < n-1) {
                right_end = Math.min(right_end, r_arr[i+1].pos - 1);
            }

            int right_idx_start = lower_bound(walls, right_start);
            int right_idx_end = upper_bound(walls, right_end) - 1;

            if (right_idx_start <= right_idx_end) {
                right_count[i] = right_idx_end - right_idx_start + 1;
            } else {
                right_count[i] = 0;
            }
        }

        for (int i = 1; i < n; i++) {
            Robot prev = r_arr[i-1], curr = r_arr[i];

            int prev_right_start = prev.pos;
            int prev_right_end = prev.pos + prev.dist;

            prev_right_end = Math.min(prev_right_end, curr.pos - 1);

            int curr_left_start = curr.pos - curr.dist;
            curr_left_start = Math.max(curr_left_start, prev.pos + 1);
            int curr_left_end = curr.pos;

            int common_start = Math.max(prev_right_start, curr_left_start);
            int common_end = Math.min(prev_right_end, curr_left_end);

            if (common_start <= common_end) {
                int common_idx_start = lower_bound(walls, common_start);
                int common_idx_end = upper_bound(walls, common_end) - 1;
                
                if (common_idx_start <= common_idx_end) {
                    common[i] = common_idx_end - common_idx_start + 1;
                } 
            }
        }

        int[] dp_left = new int[n], dp_right = new int[n];
        dp_left[0] = left_count[0]; dp_right[0] = right_count[0];

        for (int i = 1; i < n; i++) {
            dp_left[i] = left_count[i] + Math.max(dp_left[i-1], dp_right[i-1] - common[i]);

            dp_right[i] = right_count[i] + Math.max(dp_left[i-1], dp_right[i-1]);
        }

        return Math.max(dp_left[n-1], dp_right[n-1]);
    }

    private int lower_bound(int[] walls, int target) {
        int low = 0, high = walls.length;

        while (low < high) {
            int mid = (low + high) / 2;

            if (walls[mid] < target){
                low = mid + 1;
            } else high = mid;
        }

        return low;
    }
    
    private int upper_bound(int[] walls, int target) {
        int low = 0, high = walls.length;

        while (low < high) {
            int mid = (low + high) / 2;

            if (walls[mid] <= target) {
                low = mid + 1;
            } else high = mid;
        }

        return low;
    }
}


/*
import java.util.Arrays;

class Solution {
    private long[][] intervals;
    private int[][] memo;
    private int[] globalWalls;
    private int n;

    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        n = robots.length;
        if (n == 0) return 0;
        
        globalWalls = walls;
        Arrays.sort(globalWalls);

        // Pair robots with distances and sort by robot position
        long[][] vp = new long[n][2];
        for (int i = 0; i < n; i++) {
            vp[i][0] = robots[i];
            vp[i][1] = distance[i];
        }
        Arrays.sort(vp, (a, b) -> Long.compare(a[0], b[0]));

        intervals = new long[n][4];

        for (int i = 0; i < n; i++) {
            long pos = vp[i][0];
            long d = vp[i][1];

            long left_block = (i > 0 ? vp[i-1][0] : Long.MIN_VALUE);
            long right_block = (i < n - 1 ? vp[i+1][0] : Long.MAX_VALUE);

            intervals[i][0] = Math.max(pos - d, left_block);
            intervals[i][1] = pos;
            intervals[i][2] = pos;
            intervals[i][3] = Math.min(pos + d, right_block);
        }

        // memo[i][prev_dir]
        // prev_dir mapping: 0 = Left, 1 = Right, 2 = No previous robot
        memo = new int[n][3];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Start from index 0 with state 2 (no previous direction)
        return solve(0, 2);
    }

    private int solve(int i, int prev_dir) {
        // Base case: We've processed all robots
        if (i == n) return 0;
        
        // Return cached result if already computed
        if (memo[i][prev_dir] != -1) return memo[i][prev_dir];

        long prev_r = Long.MIN_VALUE;
        if (prev_dir == 0) {
            prev_r = intervals[i-1][1];
        } else if (prev_dir == 1) {
            prev_r = intervals[i-1][3];
        }

        // Option 1: Move LEFT
        long l1 = intervals[i][0], r1 = intervals[i][1];
        int walls_left = getCount(Math.max(l1, prev_r + 1), r1);
        int ans_left = walls_left + solve(i + 1, 0);

        // Option 2: Move RIGHT
        long l2 = intervals[i][2], r2 = intervals[i][3];
        int walls_right = getCount(Math.max(l2, prev_r + 1), r2);
        int ans_right = walls_right + solve(i + 1, 1);

        // Cache the best result and return
        return memo[i][prev_dir] = Math.max(ans_left, ans_right);
    }

    private int getCount(long l, long r) {
        // Prevent negative returns when lower bound > upper bound
        if (l > r) return 0;
        int it1 = lowerBound(globalWalls, l);
        int it2 = upperBound(globalWalls, r);
        return it2 - it1;
    }

    // Equivalent to C++ std::lower_bound
    private int lowerBound(int[] arr, long target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // Equivalent to C++ std::upper_bound
    private int upperBound(int[] arr, long target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}*/

