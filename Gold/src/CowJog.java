import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CowJog {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CowJog\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("cowjog.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowjog.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int T = Integer.parseInt(tk.nextToken());
		long[] ends = new long[N];
		ArrayList<Long> arr = new ArrayList<Long>();
		boolean[] inLane = new boolean[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			long start = Long.parseLong(tk.nextToken());
			long speed = Long.parseLong(tk.nextToken());
			long end = start + speed * T;
			ends[i] = end;
		}
		int numLanes = 1;
		arr.add(ends[0]);
		inLane[0] = true;
		int numToProcess = N - 1;
		int startInd = 0;
		while (numToProcess > 0) {
			int firstInd = 0;
			for (int i = startInd; i < N; i++) {
				if (!inLane[i]) {
					if (arr.get(arr.size() - 1) < ends[i]) {
						inLane[i] = true;
						arr.add(ends[i]);
						numToProcess--;
						if (numToProcess <= 0) {
							break;
						}
					} else if (firstInd == 0) {
						firstInd = i;
					}
				}
			}
			if (numToProcess > 0) {
				inLane[firstInd] = true;
				arr.add(ends[firstInd]);
				numToProcess--;
				numLanes++;
				startInd = firstInd + 1;
			}
		}
		out.println(numLanes);
		out.close();
		in.close();
	}
}