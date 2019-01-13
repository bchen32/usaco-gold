import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;

public class Cownomics {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Cownomics\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("cownomics.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cownomics.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		int M = Integer.parseInt(ln.nextToken());
		// MapInput is just a cool way to turn the letters into numbers
		HashMap<String, Integer> mapInput = new HashMap<String, Integer>();
		String[] chars = { "A", "C", "G", "T" };
		for (int i = 0; i < 4; i++) {
			mapInput.put(chars[i], i);
		}
		int[][] spotty = new int[N][M];
		int[][] plain = new int[N][M];
		for (int i = 0; i < N; i++) {
			String s = in.readLine();
			for (int j = 0; j < M; j++) {
				spotty[i][j] = mapInput.get(s.substring(j, j + 1));
			}
		}
		for (int i = 0; i < N; i++) {
			String s = in.readLine();
			for (int j = 0; j < M; j++) {
				plain[i][j] = mapInput.get(s.substring(j, j + 1));
			}
		}
		// hash[i] is the rolling hash for the ith cow
		long[] spottyHash = new long[N];
		long[] plainHash = new long[N];
		long[] seeds = new long[M];

		for (int i = 0; i < M; i++) {
			// Fill seeds with random ints
			seeds[i] = BigInteger.probablePrime(31, new Random()).longValue();
		}

		// Worst case you need every character to differentiate
		int ans = M;
		int i = 0;
		int j = 0;
		int dups = N;

		while (j < M) {

			HashSet<Long> hashes = new HashSet<Long>();
			if (dups == 0) {
				ans = Math.min(ans, j - i);
			}

			if (dups > 0) {
				dups = 0;
				for (int k = 0; k < N; k++) {
					// Add to the rolling hash
					spottyHash[k] += seeds[j] * spotty[k][j];
					hashes.add(spottyHash[k]);
				}
				for (int k = 0; k < N; k++) {
					// Add to rolling hash
					plainHash[k] += seeds[j] * plain[k][j];
					// Check for overlaps
					if (hashes.contains(plainHash[k])) {
						dups++;
					}
				}
				j++;
			} else {
				dups = 0;
				for (int k = 0; k < N; k++) {
					// Remove from rolling hash
					spottyHash[k] -= seeds[i] * spotty[k][i];
					hashes.add(spottyHash[k]);
				}
				for (int k = 0; k < N; k++) {
					// Remove and check
					plainHash[k] -= seeds[i] * plain[k][i];
					if (hashes.contains(plainHash[k])) {
						dups++;
					}
				}
				i++;
			}
		}
		out.println(ans);
		out.close();
		in.close();
	}
}