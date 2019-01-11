import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class Cownomics {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bjchen\\git\\USACO-Gold\\Gold\\Cownomics\\1.in"));
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
		long[] spottyHash = new long[M + 1];
		long[] plainHash = new long[M + 1];
		long[] seeds = new long[M + 1];

		for (int i = 0; i < M + 1; i++) {
			// Fill seeds with random ints
			seeds[i] = BigInteger.probablePrime(31, new Random()).longValue();
		}
		
		
		out.close();
		in.close();
	}
}