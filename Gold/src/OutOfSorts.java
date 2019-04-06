import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class OutOfSorts {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\OutOfSorts\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\OutOfSorts\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("sort.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		for (int i = 0; i < N; i++) {
			
		}
		out.close();
		in.close();
	}
}

class SortBITree {
	int BITree[];
	int N;
	
	public SortBITree(int N) {
		this.N = N;
		BITree = new int[N + 1];
	}
	
	public int get(int index) {
		int sum = 0;
		index = index + 1;
		while (index > 0) {
			sum += BITree[index];
			index -= index & (-index);
		}
		return sum;
	}
	
	public void update(int index, int val) {
		index = index + 1;
		
		while (index <= N) {
			BITree[index] += val;
			index += index & (-index);
		}
	}
}