// class Solution {
//     public int minimumDistance(int[] nums) 
//     {
//         HashMap <Integer ,Integer[]> map = new HashMap<>();
//         ArayList <ArrayList<>> arr = new ArrayList<>();

//         for(int i=0;i<nums.length;i++)
//         {
//             int x= nums[i];

//            new int[] temp = map.getOrDefault(x,new int []));
//            if(temp.size==3)
//            {
//             if()
//            }
//         }


        
//     }
// }

class Solution {
    public int minimumDistance(int[] nums) {
        int n = nums.length ;
        if (n <= 2) return -1 ;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++)
            for (int j = i+1; j < n; j++)
                if (nums[i] == nums[j])
                    for (int k = j+1; k < n; k++)
                        if (nums[j] == nums[k])
                            ans = Math.min(ans, 2*(k-i)) ;
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}