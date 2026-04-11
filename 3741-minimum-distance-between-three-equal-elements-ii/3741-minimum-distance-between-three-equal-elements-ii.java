
class Solution {
    public int minimumDistance(int[] nums) 
    {
        int min = Integer.MAX_VALUE;
        HashMap <Integer ,ArrayList<Integer>> map = new HashMap<>();

        for(int i=0;i<nums.length;i++)
        {
            int x= nums[i];

            map.computeIfAbsent(x,list->new ArrayList<Integer>()).add(i);  
        }

        for(ArrayList<Integer>arr: map.values())
        {
            if(arr.size()>=3)
            {
                int i=0;
                int j=2;
                while(j<arr.size())
                {
                    min = Math.min(min,2*(arr.get(j)-arr.get(i)));
                    i++;
                    j++;
                }
            }
        }

    return (min==Integer.MAX_VALUE)?-1:min;
        
    }
}
