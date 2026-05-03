class Solution {
    public boolean rotateString(String s, String goal) 
    {
        if(s.length()!=goal.length())return false;
        StringBuilder str = new StringBuilder(s);
        str.append(s);
        if(str.indexOf(goal)!=-1)return true;
        return false;
    }
}