import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Walk {

	static int N;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Walk\\1.in"));
		// BufferedReader in = new BufferedReader(new
		// FileReader("H:\\git\\USACO-Gold\\Gold\\Walk\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("walk.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("walk.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		long[][] adjMat = new long[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				adjMat[i][j] = (2019201913L * (i + 1) + 2019201949L * (j + 1)) % 2019201997L;
				adjMat[j][i] = (2019201913L * (i + 1) + 2019201949L * (j + 1)) % 2019201997L;
			}
		}
		long[] distances = prim(adjMat);
		Arrays.sort(distances);
		out.println(distances[N + 1 - K]);
		out.close();
		in.close();
	}

	static long[] prim(long adjMat[][]) {

		long key[] = new long[N];
		Arrays.fill(key, Long.MAX_VALUE);

		boolean inSet[] = new boolean[N];

		key[0] = 0;

		for (int count = 0; count < N - 1; count++) {
			long min = Long.MAX_VALUE;
			int minIndex = -1;

			for (int v = 0; v < N; v++) {
				if (!inSet[v] && key[v] < min) {
					min = key[v];
					minIndex = v;
				}
			}
			int u = minIndex;
			inSet[u] = true;
			for (int v = 0; v < N; v++) {
				if (adjMat[u][v] != 0 && !inSet[v] && adjMat[u][v] < key[v]) {
					key[v] = adjMat[u][v];
				}
			}
		}
		return key;
	}
}