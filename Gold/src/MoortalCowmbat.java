import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MoortalCowmbat {

    public static void main(String[] args) throws IOException {
        // BufferedReader in = new BufferedReader(new
        // FileReader("D:\\Java\\USACO-Gold\\Gold\\MoortalCowmbat\\2.in"));
        BufferedReader in = new BufferedReader(new FileReader("cowmbat.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowmbat.out")));
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(tk.nextToken());
        int M = Integer.parseInt(tk.nextToken());
        int K = Integer.parseInt(tk.nextToken());
        char[] c = in.readLine().toCharArray();
        int[] s = new int[N];
        int[][] adjMat = new int[M][M];
        for (int i = 0; i < N; i++) {
            s[i] = (int) (c[i] - 'a');
        }
        for (int i = 0; i < M; i++) {
            tk = new StringTokenizer(in.readLine());
            for (int j = 0; j < M; j++) {
                adjMat[i][j] = Integer.parseInt(tk.nextToken());
            }
        }
        int[][] dists = new int[M][M];
        for (int i = 0; i < M; i++) {
            dists[i] = dijkstra(adjMat, i, M);
        }
        int[][] sums = new int[N + 1][M];
        for (int i = 0; i < M; i++) {
            for (int j = 1; j < N + 1; j++) {
                sums[j][i] = sums[j - 1][i] + dists[s[j - 1]][i];
            }
        }
        int[][] dp = new int[N + 1][M];
        int[] min = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j] = 1 << 30;
            }
            min[i] = 1 << 30;
        }
        min[0] = 0;
        for (int i = 1; i < N + 1; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dists[s[i - 1]][j]);
                if (i - K >= 0) {
                    dp[i][j] = Math.min(dp[i][j], sums[i][j] - sums[i - K][j] + min[i - K]);
                }
                min[i] = Math.min(min[i], dp[i][j]);
            }
        }
        out.println(min[N]);
        out.close();
        in.close();
    }

    static int[] dijkstra(int[][] adjMat, int root, int N) {
        int[] dists = new int[N];
        Arrays.fill(dists, Integer.MAX_VALUE);
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
                int distsThroughU = dists[smallest] + adjMat[smallest][v];
                if (!inSet[v]) {
                    dists[v] = Math.min(dists[v], distsThroughU);
                }
            }
        }
        return dists;
    }
}