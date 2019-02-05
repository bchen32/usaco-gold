import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BalancedPhoto {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\BalancedPhoto\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("bphoto.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		Cow[] cows = new Cow[N];
		for (int i = 0; i < N; i++) {
			cows[i] = new Cow(Integer.parseInt(in.readLine()), i);
		}
		Arrays.sort(cows);
		BITree tree = new BITree(N);
		int unbalanced = 0;
		for (int i = 0; i < N; i++) {
			Cow curr = cows[i];
			int left = tree.get(curr.ind);
			int max = Math.max(left, i - left);
			int min = Math.min(left, i - left);
			if (max > min * 2) {
				unbalanced++;
			}
			tree.update(curr.ind, 1);
		}
		out.println(unbalanced);
		out.close();
		in.close();
	}

	static class Cow implements Comparable<Cow> {
		int val;
		int ind;

		public Cow(int a, int b) {
			val = a;
			ind = b;
		}

		@Override
		public int compareTo(Cow other) {
			return Integer.compare(other.val, this.val);
		}
	}

	static class BITree {

		int BITree[];
		int N;

		public BITree(int n) {
			N = n;
			BITree = new int[N + 1];
		}

		public int get(int index) {
			int sum = 0; // Iniialize result

			// index in BITree[] is 1 more than
			// the index in arr[]
			index = index + 1;

			// Traverse ancestors of BITree[index]
			while (index > 0) {
				// Add current element of BITree
				// to sum
				sum += BITree[index];

				// Move index to parent node in
				// getSum View
				index -= index & (-index);
			}
			return sum;
		}

		public void update(int index, int val) {
			// index in BITree[] is 1 more than
			// the index in arr[]
			index = index + 1;

			// Traverse all ancestors and add 'val'
			while (index <= N) {
				// Add 'val' to current node of BIT Tree
				BITree[index] += val;

				// Update index to that of parent
				// in update View
				index += index & (-index);
			}
		}

	}

}