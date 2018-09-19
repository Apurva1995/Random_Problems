/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{
    private int n, m;
    private Map<Integer, List<Integer>> tree;
    private int[] parent;
    private int[] depth;
    private List<Integer> firstApperance;
    private List<Integer> eulerPath;
    private int[] depthEuler;
    private BufferedReader br;
    private int levels;
    private int sparseTable[][];
    private static int count = 0;
    
    
    private void addEdge(int u, int v) {
        
        if(tree.get(u) == null) {
            
            tree.put(u, new ArrayList<Integer>());
        }
        
        if(tree.get(v) == null) {
            
            tree.put(v, new ArrayList<Integer>());
        }
        
        tree.get(u).add(v);
        tree.get(v).add(u);
    }
    
    private void dfs(int cur, int prev) 
    { 
        depth[cur] = depth[prev] + 1;
        parent[cur] = prev;
        if(firstApperance.get(cur) < 0)
            firstApperance.set(cur, count);
        eulerPath.add(cur);
        
        for (int i=0; i<tree.get(cur).size(); i++) 
        { 
            if (tree.get(cur).get(i) != prev) {
                count++;
                dfs(tree.get(cur).get(i), cur); 
                eulerPath.add(cur);
                count++;
            }
        } 
    } 
    
    public void createTree() throws Exception{
        
        String input;
        String inputArr[];
        
        for(int i=1;i<n;i++) {
            
            input = br.readLine();
            inputArr = input.split(" ");
            addEdge(Integer.valueOf(inputArr[0]), Integer.valueOf(inputArr[1]));
        }
    }
    
    private void fillDepthEuler() {
        
        for(int i=0;i<eulerPath.size();i++) {
            
            depthEuler[i] = depth[eulerPath.get(i)];
        }
    }
    
    private void fillSparseTable() {
    
        //Initializing minimum for the intervals with length 1
        for(int i=0;i<eulerPath.size();i++)
            sparseTable[i][0] = i;
    
        //Computing minimum for smaller to bigger intervals
        for(int i=1;(1<<i)<=eulerPath.size();i++) {
        
            //Computing minimum for all intervals of size 2^j
            for(int j=0;(j+(1<<i)-1)<eulerPath.size();j++) {
            
                if(depthEuler[sparseTable[j][i-1]] < depthEuler[sparseTable[j+(1<<i-1)][i-1]])
                    sparseTable[j][i] = sparseTable[j][i-1];
                
                else
                    sparseTable[j][i] = sparseTable[j+(1<<i)-1][i-1];
            }
        }
    }
    
    private void preprocess() throws Exception{
        
        tree = new HashMap<>();
        parent = new int[n+1];
        depth = new int[n+1];
        firstApperance = new ArrayList<>(Collections.nCopies(n+1,-1));
        eulerPath = new ArrayList<>();
        
        //creating Tree
        createTree();
        //Doing DFS to fill parent, depth, eulerPath and firstApperance
        depth[0] = -1;
        dfs(1, 0);
        
        depthEuler = new int[eulerPath.size()];
        //Filling depthEuler using eulerPath and depth
        fillDepthEuler();
        
        //Preparing for filling sparse matrix
        levels = (int)Math.ceil(Math.log(eulerPath.size())/Math.log(2.0));
        //System.out.println(levels);
        /*sparseTable = new int[eulerPath.size()][levels];
        fillSparseTable();*/
        
    }
    
    private int RMQ(int l, int r) {
    
        int j = (int)(Math.log(r-l+1)/Math.log(2.0));
        System.out.println(j);
    
        if(depthEuler[sparseTable[l][j]] < depthEuler[sparseTable[r-(1<<j)+1][j]])
            return eulerPath.get(sparseTable[l][j]);
        else
            return eulerPath.get(sparseTable[r-(1<<j)+1][j]);
    }
    
    private void answerQueries() throws Exception{
        
        String input;
        String inputArr[];
        int lca;
        
        for(int i=0;i<m;i++) {
            
            input = br.readLine();
            inputArr = input.split(" ");
            
            lca = RMQ(firstApperance.get(Integer.valueOf(inputArr[0])), firstApperance.get(Integer.valueOf(inputArr[1])));
            
            System.out.println(lca);
        }
    }
    
    public void getInput() throws Exception {
        
        br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String inputArr[];
        
        input = br.readLine();
        inputArr = input.split(" ");
        n = Integer.valueOf(inputArr[0]);
        m = Integer.valueOf(inputArr[1]);
        
        preprocess();
        answerQueries();
        
        
    }
    
	public static void main (String[] args) throws java.lang.Exception
	{
		Codechef codechef = new Codechef();
		codechef.getInput();
	}
}
