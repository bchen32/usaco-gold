import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SleepySort {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\SleepySort\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\SleepySort\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("sleepy.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int[] input = new int[N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			input[i] = Integer.parseInt(tk.nextToken()) - 1;
		}
		int sortInd = N - 1;
		while (sortInd >= 1 && input[sortInd] > input[sortInd - 1]) {
			sortInd--;
		}
		int[] instructions = new int[sortInd];
		SleepyBIT BIT = new SleepyBIT(N);
		for (int i = sortInd; i < N; i++) {
			BIT.update(input[i], 1);
		}
		for (int i = 0; i < sortInd; i++) {
			instructions[i] = (sortInd - i - 1) + BIT.get(input[i]);
			BIT.update(input[i], 1);
		}
		StringBuilder ret = new StringBuilder("");
		ret.append(sortInd + "\n");
		for (int i = 0; i < sortInd; i++) {
			ret.append(instructions[i] + " ");
		}
		ret.deleteCharAt(ret.length() - 1);
		out.println(ret);
		out.close();
		in.close();
	}
}

class SleepyBIT {
	int BIT[];
	int N;
	
	public SleepyBIT(int N) {
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