// Java program to print largest contiguous array sum with
// This code is available on GeeksForGeeks for finding maxSum. I have just added the code to find index of the subarray with maximum sum.

import java.io.*; 
import java.util.*; 
  
class Kadane 
{ 
    public static void main (String[] args) 
    { 
        int [] a = {-3,-2,-1,0,4}; 
        System.out.println("Maximum contiguous sum is " + 
                                       maxSubArraySum(a)); 
    } 
  
    static int maxSubArraySum(int a[]) 
    { 
        int size = a.length; 
        int max_so_far = Integer.MIN_VALUE, max_ending_here = 0; 
        String last;
        boolean transition = false;
        int tempStartIndex, startIndex, endIndex;
        tempStartIndex = startIndex = endIndex = 0;
        if(a[0] < 0)
            last = "negative";
        else    
            last = "positive";
  
        for (int i = 0; i < size; i++) 
        { 
            max_ending_here = max_ending_here + a[i]; 
            if(last.equals("negative") && max_ending_here >0 ) {
                last = "positive";
                transition = true;
                tempStartIndex = i;
            }
            else if(last.equals("positive") && max_ending_here <= 0 ) {
                last = "negative";
                transition = true;
                tempStartIndex = i;
            }
                
            if (max_so_far < max_ending_here) { 
                max_so_far = max_ending_here; 
                if(transition) {
                    startIndex = tempStartIndex;
                    transition = false;
                }
                else {
                    if(max_ending_here<=0)
                        startIndex = i;
                }
                endIndex = i;
            }
            if (max_ending_here < 0) 
                max_ending_here = 0; 
        }
        System.out.println("Start index : " + startIndex);
        System.out.println("End index : " + endIndex);
        return max_so_far; 
    } 
}
