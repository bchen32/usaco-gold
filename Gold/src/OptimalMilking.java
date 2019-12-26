import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class OptimalMilking {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\OptimalMilking\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("optmilk.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("optmilk.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int D = Integer.parseInt(tk.nextToken());
		int[] milks = new int[N];
		for (int i = 0; i < N; i++) {
			milks[i] = Integer.parseInt(in.readLine());
		}
		SegmentTree tree = new SegmentTree(milks, N);
		long ans = 0;
		for (int i = 0; i < D; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken());
			tree.update(a, b);
			ans += tree.tree[1].noneEmpty;
		}
		out.println(ans);
		out.close();
		in.close();
	}

	static class SegmentTree {
		Node[] tree;
		int[] arr;
		int N;

		public SegmentTree(int[] arr, int N) {
			this.arr = arr;
			this.N = N;
			int height = (int) (Math.ceil(Math.log(N) / Math.log(2))) + 1;
			int length = (int) Math.pow(2, height);
			tree = new Node[length];
			for (int i = 0; i < length; i++) {
				tree[i] = new Node(0, 0, 0, 0);
			}
			build(1, 0, N - 1);
		}

		void merge(int node) {
			int l = node * 2;
			int r = node * 2 + 1;
			tree[node].bothEmpty = Math.max(tree[l].bothEmpty + tree[r].rightEmpty,
					tree[l].leftEmpty + tree[r].bothEmpty);
			tree[node].leftEmpty = Math.max(tree[l].bothEmpty + tree[r].noneEmpty,
					tree[l].leftEmpty + tree[r].leftEmpty);
			tree[node].rightEmpty = Math.max(tree[l].rightEmpty + tree[r].rightEmpty,
					tree[l].noneEmpty + tree[r].bothEmpty);
			tree[node].noneEmpty = Math.max(tree[l].rightEmpty + tree[r].noneEmpty,
					tree[l].noneEmpty + tree[r].leftEmpty);
		}

		void build(int node, int start, int end) {
			if (start == end) {
				// Leaf node will have a single element
				tree[node] = new Node(0, 0, 0, arr[start]);
			} else {
				int mid = (start + end) / 2;
				// Recurse on the left child
				build(2 * node, start, mid);
				// Recurse on the right child
				build(2 * node + 1, mid + 1, end);
				// Internal node will have the sum of both of its children
				merge(node);
			}
		}

		void update(int ind, int val) {
			updateUtil(1, 0, N - 1, ind, val);
		}

		void updateUtil(int node, int start, int end, int ind, int val) {
			if (start == end) {
				// Leaf node
				arr[ind] = val;
				tree[node] = new Node(0, 0, 0, val);
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
				merge(node);
			}
		}
	}

	static class Node {
		int bothEmpty;
		int leftEmpty;
		int rightEmpty;
		int noneEmpty;

		public Node(int both, int left, int right, int none) {
			this.bothEmpty = both;
			this.leftEmpty = left;
			this.rightEmpty = right;
			this.noneEmpty = none;
		}
	}
}