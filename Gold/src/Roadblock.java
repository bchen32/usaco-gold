import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Roadblock {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Roadblock\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("rblock.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("rblock.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int[][] adjMat = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j) {
					continue;
				}
				adjMat[i][j] = Integer.MAX_VALUE;
			}
		}
		for (int i = 0; i < M; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			int l = Integer.parseInt(tk.nextToken());
			adjMat[a][b] = l;
			adjMat[b][a] = l;
		}
		long[] origDist = dijkstra(adjMat, 0, N);
		long max = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i != j && adjMat[i][j] != Integer.MAX_VALUE) {
					adjMat[i][j] *= 2;
					adjMat[j][i] *= 2;
					long[] newDist = dijkstra(adjMat, 0, N);
					max = Math.max(max, newDist[N - 1] - origDist[N - 1]);
					adjMat[i][j] /= 2;
					adjMat[j][i] /= 2;
				}
			}
		}
		out.println(max);
		out.close();
		in.close();
	}

	static long[] dijkstra(int[][] adjMat, int root, int N) {
		long[] dists = new long[N];
		Arrays.fill(dists, Long.MAX_VALUE);
		boolean[] inSet = new boolean[N];
		dists[root] = 0;

		for (int k = 0; k < N - 1; k++) {

			int smallest = -1;
			long min = Long.MAX_VALUE;
			for (int i = 0; i < N; i++) {
				if (!inSet[i] && dists[i] < min) {
					smallest = i;
					min = dists[i];
				}
			}
			inSet[smallest] = true;

			for (int v = 0; v < N; v++) {
				long distsThroughU = dists[smallest] + adjMat[smallest][v];
				if (!inSet[v] && adjMat[smallest][v] != 0) {
					dists[v] = Math.min(dists[v], distsThroughU);
				}
			}
		}
		return dists;
	}
}