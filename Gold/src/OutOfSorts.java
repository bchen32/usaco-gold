import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class OutOfSorts {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\OutOfSorts\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\OutOfSorts\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("sort.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
		int N = Integer.parseInt(in.readLine());
		OrigInd[] sortedInput = new OrigInd[N];
		for (int i = 0; i < N; i++) {
			sortedInput[i] = new OrigInd(Integer.parseInt(in.readLine()), i);
		}
		Arrays.sort(sortedInput);
		int ans = 1;
		SortBIT BIT = new SortBIT(N);
		for (int i = 1; i < N; i++) {
			BIT.update(sortedInput[i - 1].ind, 1);
			ans = Math.max(ans, i - BIT.get(i - 1));
		}
		out.println(ans);
		out.close();
		in.close();
	}
}

class OrigInd implements Comparable<OrigInd> {
	int num;
	int ind;
	
	public OrigInd(int num, int ind) {
		this.num = num;
		this.ind = ind;
	}
	
	@Override
	public int compareTo(OrigInd o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.num, o.num);
	}
}

class SortBIT {
	int BIT[];
	int N;
	
	public SortBIT(int N) {
		this.N = N;
		BIT = new int[N + 1];
	}
	
	public int get(int index) {
		int sum = 0;
		index++;
		while (index > 0) {
			sum += BIT[index];
			index -= index & (-index);
		}
		return sum;
	}
	
	public void update(int index, int val) {
		index++;
		while (index <= N) {
			BIT[index] += val;
			index += index & (-index);
		}
	}
}