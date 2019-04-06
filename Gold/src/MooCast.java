import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MooCast {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\MooCast\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("moocast.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		int[][] input = new int[N][2];
		for (int i = 0; i < N; i++) {
			ln = new StringTokenizer(in.readLine());
			input[i][0] = Integer.parseInt(ln.nextToken());
			input[i][1] = Integer.parseInt(ln.nextToken());
		}
		int numMCEdges = N * (N - 1) / 2;
		MCEdge[] MCEdges = new MCEdge[numMCEdges];
		int x = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < i; j++) {
				int dist = (int) (Math.pow((input[i][0] - input[j][0]), 2) + Math.pow((input[i][1] - input[j][1]), 2));
				MCEdges[x] = new MCEdge(i, j, dist);
				x++;
			}
		}
		Arrays.sort(MCEdges);
		MCDisjointSet set = new MCDisjointSet(N);
		int best = 0;
		for (int i = 0; i < numMCEdges; i++) {
			MCEdge curr = MCEdges[i];
			if (!set.find(curr.v1, curr.v2)) {
				// Not connected
				set.union(curr.v1, curr.v2);
				best = curr.dist;
			}
			if (set.size[curr.v1] == N) {
				break;
			}
		}
		out.println(best);
		out.close();
		in.close();
	}
}

class MCEdge implements Comparable<MCEdge> {
	int v1;
	int v2;
	int dist;
	
	public MCEdge(int a, int b, int c) {
		v1 = a;
		v2 = b;
		dist = c;
	}
	
	@Override
	public int compareTo(MCEdge other) {
		return Integer.compare(this.dist, other.dist);
	}
}

class MCDisjointSet {
	int[] arr;
	int[] size;
	int N;
	
	public MCDisjointSet(int a) {
		N = a;
		arr = new int[N];
		size = new int[N];
		Arrays.fill(size, 1);
		for (int i = 0; i < N; i++) {
			arr[i] = i;
		}
	}
	
	public int root(int curr) {
		while (arr[curr] != curr) {
			arr[curr] = arr[arr[curr]];
			curr = arr[curr];
		}
		return curr;
	}
	
	public boolean find(int a, int b) {
		if (root(a) == root(b)) {
			return true;
		}
		return false;
	}
	
	public void union(int a, int b) {
		int aRoot = root(a);
		int bRoot = root(b);
		if (size[aRoot] < size[bRoot]) {
			// Subset B is bigger than subset A, so B should be A's parent
			arr[aRoot] = arr[bRoot];
			size[bRoot] += size[aRoot];
		} else {
			arr[bRoot] = arr[aRoot];
			size[aRoot] += size[bRoot];
		}
	}
}