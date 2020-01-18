import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Hopscotch {

	static final int MOD = 1000000007;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Hopscotch\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("hopscotch.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int R = Integer.parseInt(tk.nextToken());
		int C = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[][] grid = new int[R][C];
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] cols = new ArrayList[K];
		for (int i = 0; i < K; i++) {
			cols[i] = new ArrayList<Integer>();
		}
		for (int r = 0; r < R; r++) {
			tk = new StringTokenizer(in.readLine());
			for (int c = 0; c < C; c++) {
				grid[r][c] = Integer.parseInt(tk.nextToken()) - 1;
			}
		}
		for (int c = 0; c < C; c++) {
			for (int r = 0; r < R; r++) {
				int color = grid[r][c];
				if (!cols[color].isEmpty() && cols[color].get(cols[color].size() - 1) == c) {
					continue;
				}
				cols[color].add(c);
			}
		}
		BIT[] bits = new BIT[K];
		for (int i = 0; i < K; i++) {
			if (cols[i].isEmpty()) {
				continue;
			}
			bits[i] = new BIT(cols[i]);
		}
		ArrayList<Integer> gen = new ArrayList<Integer>();
		for (int c = 0; c < C; c++) {
			gen.add(c);
		}
		BIT full = new BIT(gen);
		full.update(0, 1);
		bits[grid[0][0]].update(0, 1);
		for (int r = 1; r < R - 1; r++) {
			for (int c = C - 2; c > 0; c--) {
				int val = full.query(c - 1) - bits[grid[r][c]].query(c - 1);
				if (val < 0)
					val += MOD;
				full.update(c, val);
				bits[grid[r][c]].update(c, val);
			}
		}
		int ans = full.query(C - 2) - bits[grid[R - 1][C - 1]].query(C - 2);
		if (ans < 0) {
			ans += MOD;
		}
		out.println(ans);
		out.close();
		in.close();
	}

	static class BIT {
		public int[] tree;
		public int[] indices;

		public BIT(ArrayList<Integer> set) {
			indices = new int[set.size() + 2];
			tree = new int[indices.length];
			indices[0] = -1;
			int index = 1;
			for (int out : set) {
				indices[index++] = out;
			}
			indices[indices.length - 1] = Integer.MAX_VALUE;
		}

		public void update(int index, int val) {
			int actual = Arrays.binarySearch(indices, index);
			while (actual < indices.length) {
				tree[actual] += val;
				if (tree[actual] >= MOD)
					tree[actual] -= MOD;
				actual += actual & -actual;
			}
		}

		public int query(int index) {
			int left = 0;
			int right = indices.length - 1;
			while (left != right) {
				int mid = (left + right + 1) / 2;
				if (indices[mid] > index) {
					right = mid - 1;
				} else {
					left = mid;
				}
			}
			int ret = 0;
			while (left > 0) {
				ret += tree[left];
				if (ret >= MOD)
					ret -= MOD;
				left -= left & -left;
			}
			return ret;
		}
	}

}