import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ThreeSum {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\ThreeSum\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("threesum.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("threesum.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int Q = Integer.parseInt(tk.nextToken());
		int[] arr = new int[N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(tk.nextToken());
		}
		int[][] triplets = findTriplets(arr, N);
		long[][] dp = new long[N][N];
		for (int l = 2; l < N; l++) {
			for (int i = 0; i + l < N; i++) {
				dp[l][i] = triplets[i][i + l] + dp[l - 1][i + 1] + dp[l - 1][i] - dp[l - 2][i + 1];
			}
		}
		for (int i = 0; i < Q; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			out.println(dp[b - a][a]);
		}
		out.close();
		in.close();
	}

	static int[][] findTriplets(int[] arr, int N) {
		int[][] triplets = new int[N][N];
		int[] z = new int[2000001];
		for (int i = N - 1; i >= 0; i--) {
			for (int j = i + 1; j < N; j++) {
				int ind = 1000000 - arr[i] - arr[j];
				if (ind >= 0 && ind <= 2000000) {
					triplets[i][j] = z[ind];
				}
				z[1000000 + arr[j]]++;
			}
			for (int j = i + 1; j < N; j++) {
				z[1000000 + arr[j]]--;
			}
		}
		return triplets;
	}
}