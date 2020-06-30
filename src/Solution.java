/*
Given two arrays A & B of size N each.
Find the maximum N elements from the sum combinations (Ai + Bj) formed from elements in array A and B.

For example if A = [1,2], B = [3,4], then possible pair sums can be 1+3 = 4 , 1+4=5 , 2+3=5 , 2+4=6
and maximum 2 elements are 6, 5

Example:

N = 4
a[]={1,4,2,3}
b[]={2,5,1,6}

Maximum 4 elements of combinations sum are
10   (4+6),
9    (3+6),
9    (4+5),
8    (2+6)
 */

import java.util.ArrayList;

import java.util.Collections;
import java.util.PriorityQueue;

public class Solution {
    public ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<Integer> B) {
        PriorityQueue<Integer> pq= new PriorityQueue<Integer>();
        Collections.sort(A);
        Collections.sort(B);
        for(int i=A.size()-1;i>=0;i--)
        {
            for(int j=B.size()-1;j>=0;j--)
            {
                if(pq.size()<A.size())
                {
                    pq.add(A.get(i)+B.get(j));
                }
                else
                {
                    if(A.get(i)+B.get(j)<=pq.peek())// it is less
                    {
                        break;
                    }
                    else
                    {
                        pq.poll();
                        pq.add(A.get(i)+B.get(j));
                    }
                }
            }
        }
        ArrayList<Integer> result= new ArrayList<Integer>();
        while(pq.size()>0)
            result.add(0,pq.poll());
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        ArrayList<Integer> A=new ArrayList<>();
        ArrayList<Integer> B=new ArrayList<>();
        A.add(1);A.add(3);A.add(5);A.add(9);A.add(14);
        B.add(15);B.add(20);B.add(25);B.add(30);B.add(40);
        solution.solve(A,B);
    }
}
