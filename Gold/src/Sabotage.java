import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Sabotage {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Sabotage\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("sabotage.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sabotage.out")));
		int N = Integer.parseInt(in.readLine());
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		SegmentTree segTree = new SegmentTree(arr, N);
		double worst = Double.MAX_VALUE;
		for (int i = 1; i < N - 1; i++) {
			for (int j = 0; i + j < N - 1; j++) {
				int leftSum = segTree.querySum(0, i - 1);
				int rightSum = segTree.querySum(i + j + 1, N - 1);
				int leftSize = segTree.querySize(0, i - 1);
				int rightSize = segTree.querySize(i + j + 1, N - 1);
				double avg = (double) (leftSum + rightSum) / (leftSize + rightSize);
				worst = Math.min(worst, avg);
			}
		}
		out.printf("%.3f", worst);
		out.close();
		in.close();
	}

	static class SegmentTree {
		int[] tree;
		int[] arr;
		int[] size;
		int N;

		public SegmentTree(int[] arr, int N) {
			this.arr = arr;
			this.N = N;
			int height = (int) (Math.ceil(Math.log(N) / Math.log(2)));
			int length = 2 * (int) Math.pow(2, height) - 1;
			tree = new int[length];
			size = new int[length];
			checkSize(1, 0, N - 1);
			build(1, 0, N - 1);
		}

		void checkSize(int node, int start, int end) {
			if (start == end) {
				size[node] = 1;
			} else {
				int mid = (start + end) / 2;
				checkSize(2 * node, start, mid);
				checkSize(2 * node + 1, mid + 1, end);
				size[node] = size[2 * node] + size[2 * node + 1];
			}
		}

		void build(int node, int start, int end) {
			if (start == end) {
				// Leaf node will have a single element
				tree[node] = arr[start];
			} else {
				int mid = (start + end) / 2;
				// Recurse on the left child
				build(2 * node, start, mid);
				// Recurse on the right child
				build(2 * node + 1, mid + 1, end);
				// Internal node will have the sum of both of its children
				tree[node] = tree[2 * node] + tree[2 * node + 1];
			}
		}

		void update(int ind, int val) {
			updateUtil(1, 0, N - 1, ind, val);
		}

		void updateUtil(int node, int start, int end, int ind, int val) {
			if (start == end) {
				// Leaf node
				arr[ind] = val;
				tree[node] = val;
			} else {
				int mid = (start + end) / 2;
				if (start <= ind && ind <= mid) {
					// If idx is in the left child, recurse on the left child
					updateUtil(2 * node, start, mid, ind, val);
				} else {
					// if idx is in the right child, recurse on the right child
					updateUtil(2 * node + 1, mid + 1, end, ind, val);
				}
				// Internal node will have the sum of both of its children
				tree[node] = tree[2 * node] + tree[2 * node + 1];
			}
		}

		int querySum(int l, int r) {
			return queryUtil(1, 0, N - 1, l, r);
		}

		int querySize(int l, int r) {
			return querySizeUtil(1, 0, N - 1, l, r);
		}

		int queryUtil(int node, int start, int end, int l, int r) {
			if (r < start || end < l) {
				// range represented by a node is completely outside the given range
				return 0;
			}
			if (l <= start && end <= r) {
				// range represented by a node is completely inside the given range
				return tree[node];
			}
			// range represented by a node is partially inside and partially outside the
			// given range
			int mid = (start + end) / 2;
			int p1 = queryUtil(2 * node, start, mid, l, r);
			int p2 = queryUtil(2 * node + 1, mid + 1, end, l, r);
			return p1 + p2;
		}

		int querySizeUtil(int node, int start, int end, int l, int r) {
			if (r < start || end < l) {
				// range represented by a node is completely outside the given range
				return 0;
			}
			if (l <= start && end <= r) {
				// range represented by a node is completely inside the given range
				return size[node];
			}
			// range represented by a node is partially inside and partially outside the
			// given range
			int mid = (start + end) / 2;
			int p1 = querySizeUtil(2 * node, start, mid, l, r);
			int p2 = querySizeUtil(2 * node + 1, mid + 1, end, l, r);
			return p1 + p2;
		}
	}
}