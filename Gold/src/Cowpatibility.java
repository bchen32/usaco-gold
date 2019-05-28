import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Cowpatibility {
	
	static long[] flip = { 1, -1, 1, -1, 1 };
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Cowpatibility\\7.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Cowpatibility\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("cowpatibility.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		long N = Long.parseLong(tk.nextToken());
		long total = N * (N - 1) / 2;
		@SuppressWarnings("unchecked")
		HashMap<HashedArray, Long>[] subsets = new HashMap[5];
		for (int i = 0; i < 5; i++) {
			subsets[i] = new HashMap<HashedArray, Long>();
		}
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int[] flavors = new int[5];
			for (int j = 0; j < 5; j++) {
				flavors[j] = Integer.parseInt(tk.nextToken());
			}
			Arrays.sort(flavors);
			for (int j = 1; j <= 31; j++) {
				int[] sub = new int[5];
				int size = -1;
				for (int k = 4; k >= 0; k--) {
					if ((j & (1 << k)) == 1 << k) {
						size++;
						sub[size] = flavors[k];
					}
				}
				HashedArray toAdd = new HashedArray(sub);
				HashMap<HashedArray, Long> curr = subsets[size];
				if (curr.containsKey(toAdd)) {
					curr.put(toAdd, curr.get(toAdd) + 1L);
				} else {
					curr.put(toAdd, 1L);
				}
			}
		}
		long compatible = 0;
		for (int i = 0; i < 5; i++) {
			int subsetSum = 0;
			for (HashedArray a : subsets[i].keySet()) {
				long x = subsets[i].get(a);
				subsetSum += x * (x - 1) / 2;
			}
			compatible += subsetSum * flip[i];
		}
		out.println(total - compatible);
		out.close();
		in.close();
	}
}

class HashedArray {
	int[] arr;
	
	public HashedArray(int[] a) {
		arr = a;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(arr);
	}
	
	@Override
	public boolean equals(Object other) {
		HashedArray o = (HashedArray) other;
		for (int i = 0; i < 5; i++) {
			if (arr[i] != o.arr[i]) {
				return false;
			}
		}
		return true;
	}
}