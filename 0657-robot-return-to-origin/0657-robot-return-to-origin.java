class Solution {
    public boolean judgeCircle(String moves)
     
     {
        int u=0;
        int d=0;
        int r=0;
        int l=0;

        for(int i=0;i<moves.length();i++)
        {
            char x = moves.charAt(i);
            if(x=='U')u++;
            else if(x=='D')d++;
            else if(x=='R')r++;
            else l++;
        }
        return (u==d && r==l);
        
    }
}