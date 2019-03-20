/******************************************************************************
 *  Compilation:  javac Taxicab.java
 *  Execution:    java Taxicab n
 *  Dependencies: StdOut.java MinPQ.java
 * 
 *  Find all nontrivial integer solutions to a^3 + b^3 = c^3 + d^3 where
 *  a, b, c, and d are between 0 and n. By nontrivial, we mean
 *  a <= b, c <= d, and a < c.
 *
 *  % java Taxicab 11
 *
 *  % java Taxicab 12
 *  1729 = 1^3 + 12^3 = 9^3 + 10^3
 *
 *  % java Taxicab 1000
 *  1729 = 1^3 + 12^3 = 9^3 + 10^3
 *  4104 = 2^3 + 16^3 = 9^3 + 15^3
 *  13832 = 2^3 + 24^3 = 18^3 + 20^3
 *  20683 = 10^3 + 27^3 = 19^3 + 24^3
 *  32832 = 4^3 + 32^3 = 18^3 + 30^3
 *  ...
 *  87539319 = 167^3 + 436^3 = 228^3 + 423^3 = 255^3 + 414^3
 *  ...
 *  1477354411 = 802^3 + 987^3 = 883^3 + 924^3
 *
 ******************************************************************************/
 
 /*
  * Solution using Priority Queue.
  * Time Complexity  :  O(n^2logn). logn has come into picture because of heapifying Prioroty Queue.
  * Space Complexity :  O(n)
 */
 public class Taxicab implements Comparable<Taxicab> {
    private final int i;
    private final int j;
    private final long sum;   // i^3 + j^3, cached to avoid recomputation

    // create a new tuple (i, j, i^3 + j^3)
    public Taxicab(int i, int j) {
        this.sum = (long) i*i*i + (long) j*j*j;
        this.i = i;
        this.j = j;
    }

    // compare by i^3 + j^3, breaking ties by i
    public int compareTo(Taxicab that) {
        if      (this.sum < that.sum) return -1;
        else if (this.sum > that.sum) return +1;
        else if (this.i < that.i)     return -1;
        else if (this.i > that.i)     return +1;
        else                          return  0;
    }

    public String toString() {
        return i + "^3 + " + j + "^3";
    }

    public static void main(String[] args) {

        // find a^3 + b^3 = c^3 + d^3, where a, b, c, d <= n
        int n = Integer.parseInt(args[0]);

        // initialize priority queue
        MinPQ<Taxicab> pq = new MinPQ<Taxicab>();
        for (int i = 1; i <= n; i++) {
            pq.insert(new Taxicab(i, i));
        }

        // enumerate sums in ascending order, look for repeated sums
        int run = 1;
        Taxicab prev = new Taxicab(0, 0);   // sentinel
        while (!pq.isEmpty()) {
            Taxicab curr = pq.delMin();

            // current sum is same as previous sum
            if (prev.sum == curr.sum) {
                run++;
                if (run == 2) StdOut.print(prev.sum + " = " + prev);
                StdOut.print(" = " + curr);
            }
            else {
                if (run > 1) StdOut.println();
                run = 1;
            }
            prev = curr;

            if (curr.j < n) pq.insert(new Taxicab(curr.i, curr.j + 1));
        }
        if (run > 1) StdOut.println();
    }
}


/*
 *  Solution using list.
 *  Time Complexity   : O(n^2logn^2) -> O(2(n^2logn)) -> O(n^2logn). After storing all the n^2 possible combinations, we are sorting the
 *                      list. So the time comlexity goes to O(n^2logn).
 *  Space Complexity  : O(n^2)
*/
public static void Taxicab1(int n)
{
    // O(n^2) time and O(n^2) space
    var list = new List<int>();
    for (int i = 1; i <= n; i++)
    {
        for (int j = i; j <= n; j++)
        {
            list.Add(i * i * i + j * j * j);
        }
    }

    // O(n^2*log(n^2)) time
    list.Sort();

    // O(n^2) time
    int prev = -1;
    foreach (var next in list)
    {
        if (prev == next)
        {
            Console.WriteLine(prev);
        }
        prev = next;
    }
}



/ *
  * The best solution is using Priority queue. We get best time complexity using a hashmap[O(n^2)]. But the space complexity is O(n^2), 
  * which causes out of memory error for higher ranges. Since Priority queue has space complexity of O(n), it's best for higher range.
  * Below are the different explanations that help in understanding priority queue solution.
*/

/ *
  * Reverse thinking can be easily done by looking at the code. It can be done in an array of size N because the min sums are deleted
  * from the array after comparing to the next minimum and then the array is made to size N by adding a new sum - (i^3 + (j+1)^3).
  * A intuitive proof is here : Initially, we have added (1,1),(2,2),(3,3),...,(N,N) in the min-priority queue. Suppose a^+b^3=c^3+d^3, 
  * and (a,b) is the minimum that will be taken out of the priority queue next. To be able to detect this taxicab number, (c,d) must 
  * also be in the priority queue which would be taken out after (a,b).

  * Note: We would be adding (a,b+1) after extracting (a,b) so there is no way that extraction of (a,b) would result in addition of (c,d)
  * to the priority queue, so it must already exist in the priority queue. Now lets assume that (c,d) is not in the priority queue, 
  * because we haven't gotten to it yet. Instead, there is some (c,d−k) in the priority queue where k>0.
  * Since (a,b) is being taken out, a^3+b^3≤c^3+(d−k)^3. 
  * However, a^3+b^3=c^3+d^3.
  * Therefore, c^3+d^3≤c^3+(d−k)^3 d≤d−k k≤0
  * Since k>0, this is impossible. Thus our assumption can never come to pass. Thus for every (a,b) which is being removed from the 
  * min-PQ, (c,d) is already in the min-PQ (or was just removed) if a^3+b^3=c^3+d^3.
  * /
  
 / * 
   * First, if you had all the sums (O(N^2)) and could sort them (O(N^2lgN)) you would just pick the duplicates as you traverse the 
   * sorted array. Well, in our case using a minHeap we can traverse in-order all the sums: we just need to ensure that the minHeap 
   * always contains the minimum unprocessed sum.
    
   * Now, we have a huge number of sums (O(N^2)). But, notice that this number can be split into N groups each of which has an easily 
   * defined minimum! (fix a, change b from 0 to N-1 => here are your N groups. The sum in one group with a smaller b is smaller than
   * one with a bigger b in the same group - because a is the same).

   * The minimum of union of these groups is in the union of mins of these groups. Therefore, if you keep all minimums of these groups 
   * in the minHeap you are guaranteed to have the total minimum in the minHeap. Now, when you extract Min from the heap, you just add
   * next smallest element from the group of this extracted min (so if you extracted (a, b) you add (a, b+1)) and you are guaranteed 
   * that your minHeap still contains the next unprocessed min of all the sums.
   * /
