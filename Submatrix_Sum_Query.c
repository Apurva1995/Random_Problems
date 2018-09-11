#include <stdio.h>
#define M 4
#define N 5

void preProcess(int mat[M][N], int aux[M][N]) {
    
    //Creating first level matrix that contains the sum calculated for all indexes row wise. (For aux[0][3], it contains the sum of all the elements from mat[0][0] to mat[0][3]).
    for(int i=0;i<M;i++) {
        
        aux[i][0] = mat[i][0];
        for(int j=1;j<N;j++) {
            
            aux[i][j] = aux[i][j-1] + mat[i][j]; 
        }
    }
    
    //Creating final matrix that contains sum of submatrix.
    for(int i=0;i<N;i++) {
        
        for(int j=1;j<M;j++) {
            
            aux[j][i] += aux[j-1][i];
        }
    }
}

int sumQuery(int aux[M][N], int x1, int y1, int x2, int y2) {
    
    int sum = 0;
    sum += aux[x2][y2];
    
    if((x1-1 >= 0) && (y1-1) >= 0) {
        
        sum -= (aux[x1-1][y2] + aux[x2][y1-1]);
        sum += aux[x1-1][y1-1];
    }
    else if(x1-1 >= 0)
        sum -= aux[x1-1][y2];
    else if(y1-1 >= 0)
        sum -= aux[x2][y1-1];
    
    return sum;
}

int main(void) {
	
	int mat[M][N] = {{1, 2, 3, 4, 6},
                    {5, 3, 8, 1, 2},
                    {4, 6, 7, 5, 5},
                    {2, 4, 8, 9, 4} };
   int aux[M][N];
 
   preProcess(mat, aux);
 
   int tli = 2, tlj = 2, rbi = 3, rbj = 4;
   printf("\nQuery1: %d\n", sumQuery(aux, tli, tlj, rbi, rbj));
 
   tli = 0, tlj = 0, rbi = 1, rbj = 1;
   printf("\nQuery2: %d\n", sumQuery(aux, tli, tlj, rbi, rbj));
 
   tli = 1, tlj = 2, rbi = 3, rbj = 3;
   printf("\nQuery3: %d\n", sumQuery(aux, tli, tlj, rbi, rbj));
	
}

