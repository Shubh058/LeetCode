class Solution {
    public int xorAfterQueries(int[] nums, int[][] queries)
     {
        int d = 1000000007;
        for(int[] x :queries)
        {
            int id =x[2];
            int v=x[3];
            for(int idx=x[0];idx<=x[1];)
            {
                  nums[idx] = (int)((long) nums[idx] * v % d);
                 idx+=id;
            }
            
        }
        int res=0;
        for(int i=0;i<nums.length;i++)
        {
            res^=nums[i];
        }

        return res;
        
    }
}