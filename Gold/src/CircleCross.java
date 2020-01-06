import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class CircleCross {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CircleCross\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("circlecross.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("circlecross.out")));
		int N = Integer.parseInt(in.readLine());
		Pair[] pairs = new Pair[N];
		int[][] points = new int[N][2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 2; j++) {
				points[i][j] = -1;
			}
		}
		for (int i = 0; i < 2 * N; i++) {
			int num = Integer.parseInt(in.readLine()) - 1;
			if (points[num][0] == -1) {
				points[num][0] = i;
			} else {
				points[num][1] = i;
			}
		}
		int ind = 0;
		for (int i = 0; i < N; i++) {
			pairs[ind] = new Pair(points[i][0], points[i][1]);
			ind++;
		}
		Arrays.sort(pairs);
		BIT bit = new BIT(2 * N);
		int numIntersects = 0;
		for (int i = 0; i < N; i++) {
			Pair curr = pairs[i];
			numIntersects += bit.get(curr.end) - bit.get(curr.start);
			bit.update(curr.start, 1);
			bit.update(curr.end, 1);
		}
		out.println(numIntersects);
		out.close();
		in.close();
	}

	static class Pair implements Comparable<Pair> {
		int start;
		int end;

		public Pair(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Pair o) {
			return start - o.start;
		}
	}

	static class BIT {
		int BIT[];
		int N;

		public BIT(int N) {
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
}