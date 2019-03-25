import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PieForPie {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\PieForPie\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\PieForPie\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("piepie.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("piepie.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int D = Integer.parseInt(tk.nextToken());
		Pie[] bessie = new Pie[N];
		Pie[] elsie = new Pie[N];
		int[] t = new int[N];
		int[] d = new int[N];
		boolean[] visitedBessie = new boolean[N];
		boolean[] visitedElsie = new boolean[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int score = Integer.parseInt(tk.nextToken());
			int otherScore = Integer.parseInt(tk.nextToken());
			bessie[i] = new Pie(score, otherScore, i, true);
		}
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int score = Integer.parseInt(tk.nextToken());
			int otherScore = Integer.parseInt(tk.nextToken());
			elsie[i] = new Pie(otherScore, score, i, false);
		}
		Arrays.sort(bessie);
		Arrays.sort(elsie);
		out.close();
		in.close();
	}
}

class Pie implements Comparable<Pie> {
	int score;
	int otherScore;
	int num;
	boolean bessie;
	
	public Pie(int score, int otherScore, int num, boolean bessie) {
		this.score = score;
		this.otherScore = otherScore;
		this.num = num;
		this.bessie = bessie;
	}
	
	@Override
	public int compareTo(Pie other) {
		// TODO Auto-generated method stub
		return Integer.compare(this.otherScore, other.otherScore);
	}
}