import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class CowLand {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CowLand\\9.in"));
		BufferedReader in = new BufferedReader(new FileReader("cowland.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowland.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int Q = Integer.parseInt(tk.nextToken());
		int[] enjoyments = new int[N];
		LinkedList<Integer>[] adjList = new LinkedList[N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			enjoyments[i] = Integer.parseInt(tk.nextToken());
			adjList[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < N - 1; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			adjList[a].add(b);
			adjList[b].add(a);
		}
		// set up the HLD
		Stack<Integer> s1 = new Stack<Integer>();
		Stack<Integer> s2 = new Stack<Integer>();
		int[] parent = new int[N];
		s1.add(0);
		while (!s1.isEmpty()) {
			int curr = s1.pop();
			s2.push(curr);
			for (int adj : adjList[curr]) {
				if (parent[curr] != adj) {
					s1.push(adj);
					parent[adj] = curr;
				}
			}
		}
		int[] heavy = new int[N];
		int[] subtreeSize = new int[N];
		Arrays.fill(subtreeSize, 1);
		while (!s2.isEmpty()) {
			int curr = s2.pop();
			int max = 0;
			int maxChild = 0;
			for (int adj : adjList[curr]) {
				if (parent[curr] != adj && subtreeSize[adj] > max) {
					max = subtreeSize[adj];
					maxChild = adj;
				}
			}
			heavy[curr] = maxChild;
			if (curr != 0) {
				subtreeSize[parent[curr]] += subtreeSize[curr];
			}
		}
		LinkedList<Integer> heads = new LinkedList<Integer>();
		HashSet<Integer> unused = new HashSet<Integer>();
		s1 = new Stack<Integer>();
		s1.push(0);
		while (!s1.isEmpty()) {
			int curr = s1.pop();
			unused.add(curr);
			for (int adj : adjList[curr]) {
				if (parent[curr] != adj) {
					if (heavy[curr] == adj && heavy[parent[curr]] != curr) {
						heads.add(curr);
					}
					s1.push(adj);
				}
			}
		}
		int[] conversion = new int[N];
		// convert new num to orig
		int[] reconvert = new int[N];
		int[] chainNums = new int[N];
		Arrays.fill(chainNums, -1);
		// stores what chain each node belongs to
		int[] chainHeads = new int[heads.size()];
		// stores the head of each chain num
		int ind = 0;
		int numChain = 0;
		// relabel chains
		for (int start : heads) {
			unused.remove(start);
			chainHeads[numChain] = start;
			while (true) {
				chainNums[start] = numChain;
				conversion[ind] = start;
				reconvert[start] = ind;
				start = heavy[start];
				ind++;
				unused.remove(start);
				if (start == 0) {
					break;
				}
			}
			numChain++;
		}
		// relabel the rest
		Iterator<Integer> it = unused.iterator();
		while (it.hasNext()) {
			conversion[ind] = it.next();
			reconvert[conversion[ind]] = ind;
			ind++;
		}
		// HLD finished, create segtree
		SegmentTree segtree = new SegmentTree(enjoyments, conversion, reconvert, N);
		// Setup LCA
		LCA lcaCalc = new LCA(adjList, N);
		// answer queries
		LinkedList<Long> answers = new LinkedList<Long>();
		for (int i = 0; i < Q; i++) {
			tk = new StringTokenizer(in.readLine());
			int type = Integer.parseInt(tk.nextToken());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken());
			if (type == 2) {
				b--;
				int lca = lcaCalc.lca(a, b);
				long ans = 0;
				ans ^= crawl(a, lca, chainNums, chainHeads, parent, enjoyments, segtree);
				ans ^= crawl(b, lca, chainNums, chainHeads, parent, enjoyments, segtree);
				ans ^= enjoyments[lca];
				answers.add(ans);
			} else {
				enjoyments[a] = b;
				segtree.update(a, b);
			}
		}
		for (long ans : answers) {
			out.println(ans);
		}
		out.close();
		in.close();
	}

	static long crawl(int a, int lca, int[] chainNums, int[] chainHeads, int[] parent, int[] enjoyments,
			SegmentTree segtree) {
		long ans = 0;
		while (true) {
			if (chainNums[a] == chainNums[lca] && chainNums[a] != -1) {
				// same chain as LCA
				ans ^= segtree.query(a, lca);
				break;
			} else {
				if (chainNums[a] == -1 || chainNums[a] != chainNums[parent[a]]) {
					// traverse light edge
					ans ^= enjoyments[a];
					a = parent[a];
				} else {
					ans ^= segtree.query(a, chainHeads[chainNums[a]]);
					a = parent[chainHeads[chainNums[a]]];
				}
			}
		}
		return ans;
	}

	static class LCA {
		int[] depth;
		int[][] parent;
		int level;
		LinkedList<Integer>[] adjList;
		int N;

		public LCA(LinkedList<Integer>[] adjList, int N) {
			this.adjList = adjList;
			this.N = N;
			level = (int) (Math.ceil(Math.log(N) / Math.log(2)));
			depth = new int[N];
			parent = new int[N][level];
			dfsLCA(0, -1);
			precomputeSparseMatrix();
		}

		public void dfsLCA(int curr, int prev) {
			if (curr != 0) {
				depth[curr] = depth[prev] + 1;
				parent[curr][0] = prev;
			}
			for (int adj : adjList[curr]) {
				if (adj != prev) {
					dfsLCA(adj, curr);
				}
			}
		}

		public void precomputeSparseMatrix() {
			for (int i = 1; i < level; i++) {
				for (int node = 0; node < N; node++) {
					if (parent[node][i - 1] != -1)
						parent[node][i] = parent[parent[node][i - 1]][i - 1];
				}
			}
		}

		public int lca(int u, int v) {
			if (depth[v] < depth[u]) {
				int temp = u;
				u = v;
				v = temp;
			}
			int diff = depth[v] - depth[u];
			for (int i = 0; i < level; i++) {
				if (((diff >> i) & 1) == 1) {
					v = parent[v][i];
				}
			}
			if (u == v) {
				return u;
			}
			for (int i = level - 1; i >= 0; i--) {
				if (parent[u][i] != parent[v][i]) {
					u = parent[u][i];
					v = parent[v][i];
				}
			}
			return parent[u][0];
		}
	}

	static class SegmentTree {
		int[] tree;
		int[] arr;
		int[] conversion;
		int[] reconvert;
		int N;

		public SegmentTree(int[] arr, int[] conversion, int[] reconvert, int N) {
			this.arr = arr;
			this.conversion = conversion;
			this.reconvert = reconvert;
			this.N = N;
			int height = (int) (Math.ceil(Math.log(N) / Math.log(2))) + 1;
			int length = (int) Math.pow(2, height);
			tree = new int[length];
			build(1, 0, N - 1);
		}

		void merge(int node) {
			tree[node] = tree[2 * node] ^ tree[2 * node + 1];
		}

		void build(int node, int start, int end) {
			if (start == end) {
				tree[node] = arr[conversion[start]];
			} else {
				int mid = (start + end) / 2;
				build(2 * node, start, mid);
				build(2 * node + 1, mid + 1, end);
				merge(node);
			}
		}

		void update(int ind, int val) {
			ind = reconvert[ind];
			updateUtil(1, 0, N - 1, ind, val);
		}

		void updateUtil(int node, int start, int end, int ind, int val) {
			if (start == end) {
				arr[conversion[ind]] = val;
				tree[node] = val;
			} else {
				int mid = (start + end) / 2;
				if (start <= ind && ind <= mid) {
					updateUtil(2 * node, start, mid, ind, val);
				} else {
					updateUtil(2 * node + 1, mid + 1, end, ind, val);
				}
				merge(node);
			}
		}

		int query(int l, int r) {
			if (r < l) {
				int temp = l;
				l = r;
				r = temp;
			}
			l = reconvert[l];
			r = reconvert[r];
			return queryUtil(1, 0, N - 1, l, r);
		}

		int queryUtil(int node, int start, int end, int l, int r) {
			if (r < start || end < l) {
				return 0;
			}
			if (l <= start && end <= r) {
				return tree[node];
			}
			int mid = (start + end) / 2;
			int p1 = queryUtil(2 * node, start, mid, l, r);
			int p2 = queryUtil(2 * node + 1, mid + 1, end, l, r);
			return p1 ^ p2;
		}
	}
}