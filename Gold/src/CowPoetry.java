import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CowPoetry {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\CowPoetry\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\CowPoetry\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("poetry.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("poetry.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		CPWord[] words = new CPWord[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			words[i] = new CPWord(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()));
		}
		for (int i = 0; i < M; i++) {
			
		}
		// Dp[i] is the number of different ways to make something with i syllables
		int[] dp = new int[K];
		out.close();
		in.close();
	}
}

class CPWord {
	int syllables;
	int rhyme;
	
	public CPWord(int syllables, int rhyme) {
		this.syllables = syllables;
		this.rhyme = rhyme;
	}
}