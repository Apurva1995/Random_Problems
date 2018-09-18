
import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
    	
	public static class Pair {
		int x, y;
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static void addToMap(LinkedHashMap<Integer, List<Pair>> memo, int sum, Pair p) {
		List<Pair> n = memo.getOrDefault(sum, new ArrayList<Pair>());
		n.add(p); memo.put(sum, n);
	}
    public static class Quadruple {
		int a, b, c, d;
		public Quadruple(int a, int b, int c, int d) {
			int[] tmp = new int[] {a, b, c, d};
			Arrays.sort(tmp);
			this.a = tmp[0];
			this.b = tmp[1];
			this.c = tmp[2];
			this.d = tmp[3];
		}
		
		public String toString() {
			return a + " " + b + " " + c + " " + d + " $";
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Quadruple))
				return false;
			Quadruple other = (Quadruple) o;
			return a == other.a && b == other.b && c == other.c && d == other.d;
		}
		
		@Override
		public int hashCode() {
			return d*1000+c*100+b*10+a;
		}
	}
	
	public static void findFourSumNums(int[] arr, int X) {
		if (arr == null || arr.length < 4)
			return ;
		
		Set<Quadruple> quadruples = new HashSet<Quadruple>();
		
		LinkedHashMap<Integer, List<Pair>> memo = new LinkedHashMap<Integer, List<Pair>>();
		int N = arr.length;
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				int sum = arr[i] + arr[j];
				
				// check if HashMap already contains X-sum
				List<Pair> list = memo.get(X-sum); 
				if (list != null) {
					for (Pair p : list) {
						// combined pairs involve different elements?
						if (p.x != i && p.y != j && p.x !=j && p.y != i)
							quadruples.add(new Quadruple(arr[p.x], arr[p.y], arr[i], arr[j]));
					}
				}
				addToMap(memo, sum, new Pair(i, j));
			}
		}
		
		if (quadruples.size() == 0) {
		    System.out.println("-1");
		    return;
		}
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();
		list.addAll(quadruples);
		Collections.sort(list, new Comparator<Quadruple>() {
			@Override
			public int compare(Quadruple o1, Quadruple o2) {
				if (o1.a != o2.a)
					return o1.a-o2.a;
				if (o1.b != o2.b)
					return o1.b-o2.b;
				if (o1.c != o2.c) 
					return o1.c-o2.c;
				return o1.d -o2.d;
			}
			
		});
		StringBuilder sb = new StringBuilder();
		for (Quadruple q : list) {
		    sb.append(q);
		}
		System.out.println(sb.toString());
	}
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t > 0) {
            int n = sc.nextInt();
            int X = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++)
                arr[i] = sc.nextInt();

            findFourSumNums(arr, X);
            t--;	
        }
	}
}
