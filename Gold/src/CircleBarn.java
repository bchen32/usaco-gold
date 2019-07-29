import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CircleBarn {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\CircleBarn\\7.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\CircleBarn\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("cbarn.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cbarn.out")));
		int N = Integer.parseInt(in.readLine());
		int[] cows = new int[N];
		for (int i = 0; i < N; i++) {
			cows[i] = Integer.parseInt(in.readLine());
		}
		int running = 0;
		int startInd = -1;
		int diff = 0;
		for (int i = 0; i < N; i++) {
			running += cows[i];
			if (i + 1 - running > diff) {
				diff = i + 1 - running;
				startInd = i;
			}
		}
		long totalEnergy = 0L;
		for (int i = N - 1; i >= 0; i--) {
			int ind = (i + startInd + 1) % N;
			int curr = cows[ind];
			if (curr == 0) {
				for (long j = 1; j < N; j++) {
					int findInd = (int) ((ind - j + N) % N);
					if (cows[findInd] > 0) {
						cows[findInd]--;
						totalEnergy += j * j;
						break;
					}
				}
			}
			cows[ind]++;
		}
		out.println(totalEnergy);
		out.close();
		in.close();
	}
}