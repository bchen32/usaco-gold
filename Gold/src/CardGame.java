import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class CardGame {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CardGame\\4.in"));
		BufferedReader in = new BufferedReader(new FileReader("cardgame.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cardgame.out")));
		int N = Integer.parseInt(in.readLine());
		TreeSet<Integer> bessieSet = new TreeSet<Integer>();
		int[][] elsie = new int[2][N / 2];
		for (int i = 0; i < 2 * N; i++) {
			bessieSet.add(i + 1);
		}
		for (int i = 0; i < N; i++) {
			int e = Integer.parseInt(in.readLine());
			elsie[i < N / 2 ? 0 : 1][i - (i < N / 2 ? 0 : N / 2)] = e;
			bessieSet.remove(e);
		}
		// sort elsie
		Arrays.sort(elsie[1]);
		Arrays.sort(elsie[0]);
		TreeSet<Integer> b1 = new TreeSet<Integer>();
		TreeSet<Integer> b2 = new TreeSet<Integer>();
		while (bessieSet.size() > N / 2) {
			b1.add(bessieSet.pollLast());
		}
		while (!bessieSet.isEmpty()) {
			b2.add(bessieSet.pollFirst());
		}
		int wins = 0;
		// play the first half of the game
		for (int i = 0; i < N / 2; i++) {
			int e = elsie[0][N / 2 - i - 1];
			if (b1.last() > e) {
				b1.pollLast();
				wins++;
			} else {
				b1.pollFirst();
			}
		}
		// play the second half
		for (int i = 0; i < N / 2; i++) {
			int e = elsie[1][i];
			if (b2.first() < e) {
				b2.pollFirst();
				wins++;
			} else {
				b2.pollLast();
			}
		}
		out.println(wins);
		out.close();
		in.close();
	}
}