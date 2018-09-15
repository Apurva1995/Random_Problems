import java.util.*;
import java.lang.*;
import java.io.*;

class Pair {
    
    private int firstIndex;
    private int secondIndex;
    private int value;
    
    public int getValue() { return value; }
    
    public void setValue(int value) { this.value = value; }
    
    public int getFirstIndex() { return firstIndex; }
    
    public void setFirstIndex(int firstIndex) { this.firstIndex = firstIndex; }
    
    public int getSecondIndex() { return secondIndex; }
    
    public void setSecondIndex(int secondIndex) { this.secondIndex = secondIndex; }
}

class FinalResult {
    
    public Integer a;
    public Integer b;
    public Integer c;
    public Integer d;
    
    @Override
    public String toString() {
        
        return a + " " + b + " " + c + " " + d + " $";
    }
}


class GFG
 {
     
    public boolean noCommon(Pair pair1, Pair pair2) {
        
        if(pair1.getFirstIndex() == pair2.getFirstIndex() || pair1.getFirstIndex() == pair2.getSecondIndex() || pair1.getSecondIndex() == pair2.getFirstIndex()
                                                            || pair1.getSecondIndex() == pair2.getSecondIndex())
                                                            
                                                            return false;
        return true;
    } 
    
    public void getSolution() throws Exception {
        
        int t, n, k;
        String str;
        String arrStr[];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        str = br.readLine();
        t = Integer.valueOf(str);
        for(int i=0;i<t;i++) {
            
            List<Pair> aux = new ArrayList<>();
            List<FinalResult> finalResultList = new ArrayList<>();
            int quad[] = new int[4];
            String result;
            Set<String> set = new TreeSet<>();
            boolean flag = false;
            
            int arr[];
            
            str = br.readLine();
            arrStr = str.split(" ");
            n = Integer.valueOf(arrStr[0]);
            k = Integer.valueOf(arrStr[1]);
            arr = new int[n];
        
            str = br.readLine();
            arrStr = str.split(" ");
            
            for(int j=0;j<n;j++) {
                
                arr[j] = Integer.valueOf(arrStr[j]);
            }
            
            for(int j=0;j<n;j++) {
                
                for(int l=j+1;l<n;l++) {
                    
                    Pair pair = new Pair();
                    pair.setValue(arr[j] + arr[l]);
                    pair.setFirstIndex(j);
                    pair.setSecondIndex(l);
                    aux.add(pair);
                }
            }
            
            Collections.sort(aux, new Comparator<Pair>() { 
                    @Override
                    public int compare(Pair p1, Pair p2) {
                        
                        return new Integer(p1.getValue()).compareTo(new Integer(p2.getValue()));
                    }
                });
            
            int j,l;
            j = 0;
            l = aux.size() - 1;
            
            while(j < aux.size() && l >= 0) {
                
                if(aux.get(j).getValue()+aux.get(l).getValue() == k && noCommon(aux.get(j), aux.get(l))) {
                    
                    quad[0] = arr[aux.get(j).getFirstIndex()];
                    quad[1] = arr[aux.get(j).getSecondIndex()];
                    quad[2] = arr[aux.get(l).getFirstIndex()];
                    quad[3] = arr[aux.get(l).getSecondIndex()];
                    
                    Arrays.sort(quad);
                    
                    result = String.valueOf(quad[0]) + " ";
                    result = result + String.valueOf(quad[1]) + " ";
                    result = result + String.valueOf(quad[2]) + " ";
                    result = result + String.valueOf(quad[3]) + " ";
                    
                    if(set.add(result)) {
                        
                        flag = true;
                        FinalResult finalResult = new FinalResult();
                        finalResult.a = quad[0];
                        finalResult.b = quad[1];
                        finalResult.c = quad[2];
                        finalResult.d = quad[3];
                        
                        finalResultList.add(finalResult);
                    }
                    j++;
                    l--;
                }
                else if(aux.get(j).getValue()+aux.get(l).getValue() < k)
                    j++;
                else
                    l--;
            }
            
            Collections.sort(finalResultList, new Comparator<FinalResult>() { 
                    @Override
                    public int compare(FinalResult f1, FinalResult f2) {
                        
                        if(f1.a.compareTo(f2.a) > 0)
                            return f1.a.compareTo(f2.a);
                        else if(f1.b.compareTo(f2.b) > 0)
                            return f1.b.compareTo(f2.b);
                        else if(f1.c.compareTo(f2.c) > 0)
                            return f1.c.compareTo(f2.c);
                        else
                            return f1.d.compareTo(f2.d);
                    }
                });
        
            if(flag) {
                for(int x=0;x<finalResultList.size();x++)
                    System.out.print(finalResultList.get(x).toString());
                System.out.println();
            }
            else
                System.out.println("-1");
        }  
    } 
     
	public static void main (String[] args) throws Exception
	{
	    GFG gfg = new GFG();
	    gfg.getSolution();
	}
}
