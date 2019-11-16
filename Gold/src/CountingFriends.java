import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class CountingFriends {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CountingFriends\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("fcount.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fcount.out")));
		int N = Integer.parseInt(in.readLine()) + 1;
		int[] friends = new int[N];
		for (int i = 0; i < N; i++) {
			friends[i] = Integer.parseInt(in.readLine());
		}
		boolean[] unneeded = new boolean[N];
		int numUnneeded = 0;
		for (int i = 0; i < N; i++) {
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());
			for (int j = 0; j < N; j++) {
				if (j == i) {
					continue;
				}
				pq.add(friends[j]);
			}
			boolean possible = true;
			loop: while (!pq.isEmpty()) {
				ArrayList<Integer> reAdd = new ArrayList<Integer>();
				int curr = pq.poll();
				for (int j = 0; j < curr; j++) {
					if (pq.isEmpty()) {
						possible = false;
						break loop;
					}
					int next = pq.poll() - 1;
					if (next > 0) {
						reAdd.add(next);
					}
				}
				for (int j : reAdd) {
					pq.add(j);
				}
			}
			if (possible) {
				unneeded[i] = true;
				numUnneeded++;
			}
		}
		out.println(numUnneeded);
		for (int i = 0; i < N; i++) {
			out.print(unneeded[i] ? (i + 1) + "\n" : "");
		}
		out.close();
		in.close();
	}
}