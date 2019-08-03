
// Arup Guha
// 5/30/2016
// Solution to 2016 January USACO Gold Problem: Lights Out

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		// Read in data.
		Scanner stdin = new Scanner(new File("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\LightsOut\\8.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lightsout.out")));
		int n = stdin.nextInt();
		long[][] pts = new long[n][2];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < 2; j++)
				pts[i][j] = stdin.nextLong();
			
		// Compile lengths of each side.
		long[] len = new long[n];
		for (int i = 0; i < n; i++)
			len[i] = Math.abs(pts[i][0] - pts[(i + 1) % n][0]) + Math.abs(pts[i][1] - pts[(i + 1) % n][1]);
		
		// Handy to store the sum of these lengths this way.
		long[] cumfreqClock = new long[n];
		cumfreqClock[n - 1] = len[n - 1];
		for (int i = n - 2; i > 0; i--)
			cumfreqClock[i] = cumfreqClock[i + 1] + len[i];
		
		// And the other way.
		long[] cumfreqCounter = new long[n];
		cumfreqCounter[1] = len[0];
		for (int i = 2; i < n; i++)
			cumfreqCounter[i] = cumfreqCounter[i - 1] + len[i - 1];
		
		// Compile turns T = right turn in clockwise order.
		boolean[] angle = new boolean[n];
		for (int i = 0; i < n; i++) {
			long v1x = pts[i][0] - pts[(i - 1 + n) % n][0];
			long v1y = pts[i][1] - pts[(i - 1 + n) % n][1];
			long v2x = pts[i][0] - pts[(i + 1) % n][0];
			long v2y = pts[i][1] - pts[(i + 1) % n][1];
			angle[i] = ((v1x * v2y - v1y * v2x) > 0);
		}
		
		long res = 0;
		
		// Try each spot for Bessie.
		for (int spot = 1; spot < n; spot++) {
			
			// No point looking after here because the optimal path here on out is clockwise.
			if (cumfreqClock[spot] <= cumfreqCounter[spot])
				break;
			
			// Try each vertex as the fake vertex.
			for (int fake = 1; fake < n; fake++) {
				if (spot == 3 && fake == 131) {
					System.out.println("Cool");
				}
				if (fake == spot)
					continue;
				// Go around until you find a discrepancy.
				long total = 0;
				int sI = spot, fI = fake;
				while (sI < n && fI < n) {
					// We can stop now.
					if (angle[sI] != angle[fI])
						break;
					// We have to walk down this path.
					total += len[sI];
					// This is a discrepancy, but we've already walked to the next place.
					if (len[sI] != len[fI]) {
						sI++;
						fI++;
						break;
					}
					// In this case, now advance.
					sI++;
					fI++;
				}
				// Update this total path and the overall result.
				total += Math.min(cumfreqClock[sI % n], cumfreqCounter[sI % n]);
				res = Math.max(res, total - cumfreqCounter[spot]);
			}
		}
		for (int i = 0; i < n; i++) {
			out.println(Math.min(cumfreqCounter[i], cumfreqClock[i]));
		}
		// Write result.
		System.out.println(res);
		stdin.close();
		out.close();
	}
}