import java.util.*;
import java.lang.*;
import java.io.*;
class GFG
 {
	public static void main (String[] args) throws IOException
	 {
	    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
	    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int n, val;
	    n = Integer.parseInt((br.readLine()).trim());
	    for(int i=0;i<n;i++) {
	        val = Integer.parseInt((br.readLine()).trim());
	        if(minHeap.size() == 0) {
	            minHeap.add(val);
	            System.out.println(minHeap.peek());
	            continue;
	        }
	        if(minHeap.size() > 0) {
	            if(minHeap.peek() < val)
	                minHeap.add(val);
	            else
	                maxHeap.add(val);
	        }
	            
	        if(maxHeap.size() > minHeap.size() && (maxHeap.size()-minHeap.size()) > 1)
	            minHeap.add(maxHeap.poll());
	        else if(maxHeap.size() < minHeap.size() && (minHeap.size()-maxHeap.size()) > 1)
	            maxHeap.add(minHeap.poll());
	        
	        if((i+1)%2 == 1) {
	            if(maxHeap.size()>minHeap.size())
	                System.out.println(maxHeap.peek());
	            else
	                System.out.println(minHeap.peek());
	        }
	        else {
    	            int val1 = maxHeap.peek();
    	            int val2 = minHeap.peek();
    	            System.out.println((val1+val2)/2);
	        }
	    }
	 }
}
