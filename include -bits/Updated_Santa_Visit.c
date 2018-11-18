#include <bits/stdc++.h>
#include <stdio.h>
using namespace std; 
#define MAXN 100001 
#define level 18 

vector <int> tree[MAXN]; 
int depth[MAXN]; 
int parent[MAXN][level];
 
void dfs(int cur, int prev) 
{ 
	depth[cur] = depth[prev] + 1; 
	parent[cur][0] = prev; 
	for (int i=0; i<tree[cur].size(); i++) 
	{ 
		if (tree[cur][i] != prev) 
			dfs(tree[cur][i], cur); 
	} 
} 
 
void precomputeSparseMatrix(int n) 
{ 
	for (int i=1; i<level; i++) 
	{ 
		for (int node = 1; node <= n; node++) 
		{ 
			if (parent[node][i-1] != -1) 
				parent[node][i] = 
					parent[parent[node][i-1]][i-1]; 
		} 
	} 
} 

int lca(int u, int v) 
{ 
	if (depth[v] < depth[u]) 
		swap(u, v); 

	int diff = depth[v] - depth[u]; 

	for (int i=0; i<level; i++) 
		if ((diff>>i)&1) 
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

void addEdge(int u,int v) 
{ 
	tree[u].push_back(v); 
	tree[v].push_back(u); 
} 

int fillMaxNodes(int cur, int prev, int maxNodes[], int travelled[], int *max) 
{ 
	maxNodes[cur] = travelled[cur];
	
	for (int i=0; i<tree[cur].size(); i++) 
	{ 
		if (tree[cur][i] != prev) 
			maxNodes[cur] += fillMaxNodes(tree[cur][i], cur, maxNodes, travelled, max); 
	} 
	if(*max < maxNodes[cur])
        *max = maxNodes[cur];
    return maxNodes[cur];
} 

int main() 
{ 
	memset(parent,-1,sizeof(parent)); 
	int n, m, i, u, v, a, b, tempLca, max;
	scanf("%d", &n);
	scanf("%d", &m);
	for(i=1;i<n;i++) {
	    scanf("%d", &u);
	    scanf("%d", &v);
	    addEdge(u,v); 
	} 
	depth[0] = 0; 
	dfs(1,0); 
	precomputeSparseMatrix(n); 

    int travelled[n+1];
    int maxNodes[n+1];
    memset(travelled,0,sizeof(travelled));
    for(i=0;i<m;i++) {
        scanf("%d", &a);
        scanf("%d", &b);
        
        tempLca = lca(a, b);
        travelled[a] += 1;
        travelled[b] += 1;
        travelled[tempLca] -= 1;
            
        if(parent[tempLca][0] != 0)
            travelled[parent[tempLca][0]] -= 1;
    }
    max = 0;
    fillMaxNodes(1, 0, maxNodes, travelled, &max);
    printf("%d", max);
	return 0; 
} 
