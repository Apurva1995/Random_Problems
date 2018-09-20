/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

class Codechef
{
    private int n, m, level;
    private Map<Integer, List<Integer>> tree;
    private int[][] parent;
    private int[] depth;
    private int[] maxNodes;
    private int[] travelled;
    private static int max = 0;
    private BufferedReader br;
    
    
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
        parent[cur][0] = prev;
        
        for (int i=0; i<tree.get(cur).size(); i++) 
        { 
            if (tree.get(cur).get(i) != prev) {
                dfs(tree.get(cur).get(i), cur); 
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
    
    private void precomputeSparseMatrix() 
    { 
        for (int i=1; i<level; i++) 
        { 
            for (int node = 1; node <= n; node++) 
            { 
                if (parent[node][i-1] != 0) 
                    parent[node][i] = 
                        parent[parent[node][i-1]][i-1]; 
            } 
        } 
    }
    
    private int fillMaxNodes(int cur, int prev) {
        
        maxNodes[cur] = travelled[cur];
        
        for (int i=0; i<tree.get(cur).size(); i++) 
        { 
            if (tree.get(cur).get(i) != prev) {
                maxNodes[cur] += fillMaxNodes(tree.get(cur).get(i), cur); 
            }
        }
        
        if(max < maxNodes[cur])
            max = maxNodes[cur];
        return maxNodes[cur];
    }
    
    private void preprocess() throws Exception{
        
        level = (int)Math.ceil(Math.log(n)/Math.log(2.0)+1e-10);
        tree = new HashMap<>();
        parent = new int[n+1][level];
        depth = new int[n+1];
        maxNodes =  new int[n+1];
        travelled = new int[n+1];
        
        //creating Tree
        createTree();
        
        dfs(1, 0);
        
        precomputeSparseMatrix();
        for(int i=1;i<=n;i++) {
            
            for(int j=0;j<level;j++) {
                
                System.out.print(parent[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    int LCA(int u, int v) 
    { 
        if (depth[v] < depth[u]) {
            
            int temp;
            temp = v;
            v = u;
            u = temp;
        }
        int diff = depth[v] - depth[u]; 
  
        for (int i=0; i<level; i++) 
            if (((diff>>i) & 1) == 1) 
                v = parent[v][i]; 
  
        if (u == v) 
            return u; 
 
        for (int i=level-1; i>=0; i--) 
            if (parent[u][i] != parent[v][i]) 
            { 
                u = parent[u][i]; 
                v = parent[v][i]; 
            } 
  
        return parent[u][0]; 
    }
    
    private void answerQueries() throws Exception{
        
        String input;
        String inputArr[];
        int lca, u, v;
        
        for(int i=0;i<m;i++) {
            
            input = br.readLine();
            inputArr = input.split(" ");
            
            u = Integer.valueOf(inputArr[0]);
            v = Integer.valueOf(inputArr[1]);
            
            lca = LCA(u, v);
            
            
            travelled[u] += 1;
            travelled[v] += 1;
            travelled[lca] -= 1;
            
            if(parent[lca][0] != 0)
                travelled[parent[lca][0]] -= 1;
                
        }
        fillMaxNodes(1, 0);
    }
    
    public void getInput() throws Exception {
        
        br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String inputArr[];
        
        input = br.readLine();
        inputArr = input.split(" ");
        n = Integer.valueOf(inputArr[0]);
        m = Integer.valueOf(inputArr[1]);
        
        if(n == 1)
            System.out.println(m);
        else {
            preprocess();
            answerQueries();
            System.out.println(max);
        }
    }
    
	public static void main (String[] args) throws java.lang.Exception
	{
		Codechef codechef = new Codechef();
		codechef.getInput();
	}
}
