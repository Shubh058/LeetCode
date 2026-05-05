/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) 
    {
        ListNode back =head;
        ListNode front=head;
        int count = 0;
        while(front!=null)
        {
            count++;
            front=front.next;
        }
        if(count==0)return null;
        front=head;
        k=k%count;
        for(int i=0;i<k;i++)
        {
            front=front.next;
        }
        while(front.next!=null)
        {
            front=front.next;
            back=back.next;
        }
        front.next=head;
        head=back.next;
        back.next=null;
        return head;

        
    }
}