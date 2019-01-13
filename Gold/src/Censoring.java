import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Censoring {

	static long prime = 1600084399;
	static int possChars = 26;

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Censoring\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("censor.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
		StringBuilder S = new StringBuilder(in.readLine());
		int N = S.length();
		int t = Integer.parseInt(in.readLine());
		// Sort censor words by length to make rolling hash more efficient
		HashMap<Integer, ArrayList<Censor>> censors = new HashMap<Integer, ArrayList<Censor>>();
		for (int i = 0; i < t; i++) {
			String word = in.readLine();
			int l = word.length();
			if (censors.containsKey(l)) {
				censors.get(l).add(new Censor(hash(word), word));
			} else {
				censors.put(l, new ArrayList<Censor>());
				censors.get(l).add(new Censor(hash(word), word));
			}
		}
		Set<Integer> lSet = censors.keySet();

		boolean change = true;
		while (change) {
			change = false;
			for (int l : lSet) {
				if (!censors.containsKey(l) || l > N) {
					continue;
				}
				HashSet<Censor> currCensors = new HashSet<Censor>();
				currCensors.addAll(censors.get(l));
				long coeff = coeff(l);
				Censor rollingHash = new Censor(hash(S.substring(0, l)), S.substring(0, l));
				for (int i = 0; i + l < N && l < N; i++) {
					if (currCensors.contains(rollingHash)) {
						boolean ok = false;
						for (Censor c : currCensors) {
							if (c.realCheck(rollingHash)) {
								change = true;
								S = S.delete(i, i + l);
								N -= l;
								int start = Math.max(-1, i - l - 1);
								int end = (start == -1) ? Math.min(l, N) : Math.min(i - 1, N);
								rollingHash = new Censor(hash(S.substring(start + 1, end)),
										S.substring(start + 1, end));
								i = start;
								ok = true;
								break;
							}
						}
						if (ok) {
							continue;
						}
					}
					long trailing = (S.charAt(i) - 'a') * coeff;
					long leading = S.charAt(i + l) - 'a';
					rollingHash.hash = (possChars * (rollingHash.hash - trailing) + leading) % prime;
					rollingHash.word = rollingHash.word.delete(0, 1);
					rollingHash.word = rollingHash.word.append(S.charAt(i + l));
					// RollingHash might become negative, which we avoid
					if (rollingHash.hash < 0) {
						rollingHash.hash = (rollingHash.hash + prime);
					}
				}
				if (currCensors.contains(rollingHash)) {
					for (Censor c : currCensors) {
						if (c.realCheck(rollingHash)) {
							change = true;
							S = S.delete(N - l, N);
							N -= l;
						}
					}
				}
			}
		}
		out.println(S);
		out.close();
		in.close();

	}

	static long coeff(int M) {
		long coeff = 1;
		for (int i = 0; i < M - 1; i++) {
			coeff = (coeff * possChars) % prime;
		}
		return coeff;
	}

	static long hash(String s) {
		long hash = 0;
		for (int i = 0; i < s.length(); i++) {
			int x = s.charAt(i) - 'a';
			hash = (possChars * hash + x) % prime;
		}
		return hash;
	}

	static class Censor {
		long hash;
		StringBuilder word;

		public Censor(long a, String b) {
			hash = a;
			word = new StringBuilder(b);
		}

		@Override
		public boolean equals(Object o) {
			Censor other = (Censor) o;
			if (this.hash == other.hash) {
				return true;
			}
			return false;
		}

		public boolean realCheck(Censor other) {
			if (this.word.toString().equals(other.word.toString())) {
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Long.hashCode(hash);
		}
	}
}