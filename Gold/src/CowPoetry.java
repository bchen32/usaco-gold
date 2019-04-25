import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CowPoetry {
	
	static final long MOD = 1000000007L;
	
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
		HashMap<String, Integer> freqs = new HashMap<String, Integer>();
		for (int i = 0; i < M; i++) {
			String rClass = in.readLine();
			if (freqs.containsKey(rClass)) {
				freqs.put(rClass, freqs.get(rClass) + 1);
			} else {
				freqs.put(rClass, 1);
			}
		}
		// Dp[i] is the number of different ways to make something with i syllables
		long[] dp = new long[K + 1];
		dp[0] = 1L;
		// Rhymes[i] is the number of ways to make a line ending with rhyme class i
		long[] rhymes = new long[N + 1];
		for (int i = 0; i < K + 1; i++) {
			for (int j = 0; j < N; j++) {
				int syllables = words[j].syllables;
				int rhyme = words[j].rhyme;
				if (i + syllables > K) {
					continue;
				}
				if (i + syllables == K) {
					rhymes[rhyme] = (rhymes[rhyme] + dp[i]) % MOD;
				}
				dp[i + syllables] = (dp[i + syllables] + dp[i]) % MOD;
			}
		}
		long ans = 1L;
		CowPoetry solve = new CowPoetry();
		for (String r : freqs.keySet()) {
			long currClass = 0L;
			for (int i = 0; i < N + 1; i++) {
				currClass = (currClass + solve.exp(rhymes[i], freqs.get(r))) % MOD;
			}
			ans = (ans * currClass) % MOD;
		}
		out.println(ans);
		out.close();
		in.close();
	}
	
	public long exp(long b, int p) {
		if (p == 0) {
			return 0;
		} else if (p == 1) {
			return (b) % MOD;
		}
		long ans = exp(b, p / 2);
		ans = (ans * ans) % MOD;
		if (p % 2 == 1)
			ans = (ans * b) % MOD;
		return (ans) % MOD;
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