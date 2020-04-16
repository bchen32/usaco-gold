import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Haircut {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Haircut\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("haircut.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("haircut.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		tk = new StringTokenizer(in.readLine());
		int[] orig = new int[N];
		for (int i = 0; i < N; i++) {
			orig[i] = Integer.parseInt(tk.nextToken());
		}
		long[] ans = slow(orig, N);
		for (int i = 0; i < N; i++) {
			out.println(ans[i]);
		}
		out.close();
		in.close();
	}

	static long[] slow(int[] orig, int N) {
		long[] ans = new long[N];
		for (int j = N - 1; j >= 0; j--) {
			int[] lengths = new int[N];
			for (int i = 0; i < N; i++) {
				if (orig[i] > j) {
					lengths[i] = j;
				} else {
					lengths[i] = orig[i];
				}
			}
			BIT bit = new BIT(N + 1);
			long inversions = 0;
			for (int i = N - 1; i >= 0; i--) {
				inversions += bit.get(lengths[i] - 1);
				bit.update(lengths[i], 1);
			}
			ans[j] = inversions;
		}
		return ans;
	}

	static class BIT {
		long BIT[];
		int N;

		public BIT(int N) {
			this.N = N;
			BIT = new long[N + 1];
		}

		public long get(int index) {
			long sum = 0;
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
}