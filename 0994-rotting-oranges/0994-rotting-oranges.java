class Solution {
    public int orangesRotting(int[][] grid)
     
     {
        int minutes=0;
        int count=0;
        Queue <Integer> que= new LinkedList<>();
        for(int i=0;i<grid.length;i++)
        {
            for(int j=0;j<grid[0].length;j++)
            {
                if(grid[i][j]==2)
                {
                    que.offer(i);
                    que.offer(j);
                }
                else if(grid[i][j]==1)
                {
                    count++;
                }
            }
        }
        while(!que.isEmpty() && count>0)
        {
            minutes++;
            int size = que.size();
            for(int i=0;i<size/2;i++)
            {
                int row=que.poll();
                int col = que.poll();
                if(BFS(grid,row+1,col,que,count))count--;
                if(BFS(grid,row,col+1,que,count))count--;
                if(BFS(grid,row-1,col,que,count))count--;
                if(BFS(grid,row,col-1,que,count))count--;  
            }
        }
        return (count>0)?-1:minutes;

        
    }
    public boolean BFS(int[][]grid,int row,int col,Queue que,int count)
    {
        if(
        row>=0&& row<grid.length && col>=0&& col<grid[0].length && grid[row][col]==1)
        {
        que.offer(row);
        que.offer(col);
        grid[row][col]=2;
        return true;
        }
        return false;
    }
}