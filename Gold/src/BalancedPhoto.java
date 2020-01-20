import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class BalancedPhoto {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\BalancedPhoto\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("bphoto.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));
		int N = Integer.parseInt(in.readLine());
		Cow[] heights = new Cow[N];
		for (int i = 0; i < N; i++) {
			heights[i] = new Cow(Integer.parseInt(in.readLine()), i);
		}
		Arrays.sort(heights);
		BIT tree = new BIT(N);
		int unbalanced = 0;
		for (int i = N - 1; i >= 0; i--) {
			int curr = heights[i].ind;
			int moreLeft = tree.get(curr);
			int moreRight = tree.get(N - 1) - moreLeft;
			if (Math.min(moreLeft, moreRight) * 2 < Math.max(moreLeft, moreRight)) {
				unbalanced++;
			}
			tree.update(curr, 1);
		}
		out.println(unbalanced);
		out.close();
		in.close();
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

	static class Cow implements Comparable<Cow> {
		int height;
		int ind;

		public Cow(int height, int ind) {
			this.height = height;
			this.ind = ind;
		}

		@Override
		public int compareTo(Cow o) {
			return height - o.height;
		}
	}
}