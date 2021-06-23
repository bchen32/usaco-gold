import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Portals {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(tk.nextToken());
        PriorityQueue<Cost> costs = new PriorityQueue<Cost>();
        int[][] portals = new int[N][4];
        int[][] portalsReverse = new int[2 * N][2];
        for (int i = 0; i < 2 * N; i++) {
            for (int j = 0; j < 2; j++) {
                portalsReverse[i][j] = -1;
            }
        }
        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(in.readLine());
            costs.add(new Cost(i, Integer.parseInt(tk.nextToken())));
            for (int j = 0; j < 4; j++) {
                portals[i][j] = Integer.parseInt(tk.nextToken()) - 1;
                if (portalsReverse[portals[i][j]][0] != -1) {
                    portalsReverse[portals[i][j]][1] = 4 * i + j;
                } else {
                    portalsReverse[portals[i][j]][0] = 4 * i + j;
                }
            }
        }
        HashSet<Integer> untraversed = new HashSet<Integer>();
        int[][] adjMat = new int[4 * N][2];
        for (int i = 0; i < 4 * N; i++) {
            untraversed.add(i);
            int portalInd = i % 4;
            int vertex = i / 4;
            if (portalInd == 0 || portalInd == 1) {
                adjMat[i][0] = 4 * vertex + (1 - portalInd);
            } else {
                adjMat[i][0] = 4 * vertex + (3 - portalInd) + 2;
            }
        }
        for (int i = 0; i < 2 * N; i++) {
            int u = portalsReverse[i][0];
            int v = portalsReverse[i][1];
            adjMat[u][1] = v;
            adjMat[v][1] = u;
        }
        int numIslands = 0;
        DisjointSet ds = new DisjointSet(4 * N);
        while (!untraversed.isEmpty()) {
            numIslands++;
            Iterator<Integer> iter = untraversed.iterator();
            int start = iter.next();
            LinkedList<Integer> q = new LinkedList<Integer>();
            boolean[] traversed = new boolean[4 * N];
            q.add(start);
            traversed[start] = true;
            while (!q.isEmpty()) {
                int curr = q.pop();
                untraversed.remove(curr);
                for (int i = 0; i < 2; i++) {
                    int toTry = adjMat[curr][i];
                    if (!traversed[toTry]) {
                        q.add(toTry);
                        traversed[toTry] = true;
                        ds.union(curr, toTry);
                    }
                }
            }
        }
        int totalCost = 0;
        while (!costs.isEmpty() && numIslands > 0) {
            Cost curr = costs.poll();
            int portal1 = 4 * curr.vertex;
            int portal2 = 4 * curr.vertex + 2;
            if (!ds.find(portal1, portal2)) {
                ds.union(portal1, portal2);
                numIslands--;
                totalCost += curr.cost;
            }
        }
        System.out.print(totalCost);
        in.close();
    }

    static class Cost implements Comparable<Cost> {
        int vertex;
        int cost;

        public Cost(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        @Override
        public int compareTo(Portals.Cost o) {
            return Integer.compare(cost, o.cost);
        }
    }

    static class DisjointSet {
		int[] arr;
		int[] size;
		int N;

		public DisjointSet(int N) {
			this.N = N;
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
				arr[aRoot] = arr[bRoot];
				size[bRoot] += size[aRoot];
			} else {
				arr[bRoot] = arr[aRoot];
				size[aRoot] += size[bRoot];
			}
		}
	}
}
