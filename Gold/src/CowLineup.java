import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CowLineup {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CowLineup\\4.in"));
		BufferedReader in = new BufferedReader(new FileReader("lineup.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lineup.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[] cows = new int[N];
		for (int i = 0; i < N; i++) {
			cows[i] = Integer.parseInt(in.readLine());
		}
		int leftPtr = 0;
		int max = 0;
		int numBreeds = 0;
		HashMap<Integer, Integer> breedCounts = new HashMap<Integer, Integer>();
		for (int i = 0; i < N; i++) {
			int curr = cows[i];
			if (breedCounts.containsKey(curr)) {
				breedCounts.put(curr, breedCounts.get(curr) + 1);
			} else {
				breedCounts.put(curr, 1);
				numBreeds++;
			}
			while (numBreeds > K + 1) {
				int toRemove = cows[leftPtr];
				breedCounts.put(toRemove, breedCounts.get(toRemove) - 1);
				if (breedCounts.get(toRemove) == 0) {
					numBreeds--;
					breedCounts.remove(toRemove);
				}
				leftPtr++;
			}
			max = Math.max(max, breedCounts.get(curr));
		}
		out.println(max);
		out.close();
		in.close();
	}
}