import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class StampPainting {
	
	static final int MOD = 1000000007;
	static final BigInteger bMOD = new BigInteger("1000000007");
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\StampPainting\\3.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\StampPainting\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("spainting.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spainting.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		long[] sums = new long[N + 1];
		sums[0] = 0L;
		for (int i = 1; i < K; i++) {
			sums[i] = (M * sums[i - 1] + M) % MOD;
		}
		for (int i = K; i < N + 1; i++) {
			sums[i] = (M * sums[i - 1] + MOD - (M * sums[i - K] % MOD) + sums[i - K]) % MOD;
		}
		long total = 1;
		for (int i = 1; i < N + 1; i++) {
			total = M * total % MOD;
		}
		out.println((total + MOD - sums[N] + sums[N - 1]) % MOD);
		out.close();
		in.close();
	}
}