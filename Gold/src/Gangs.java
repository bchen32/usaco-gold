import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Gangs {

	static int l;
	static Gang biggestL;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Gangs\\9.in"));
		BufferedReader in = new BufferedReader(new FileReader("gangs.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gangs.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int[] numCows = new int[M];
		for (int i = 0; i < M; i++) {
			numCows[i] = Integer.parseInt(in.readLine());
			if (i == 0) {
				continue;
			}
		}
		check(numCows, M);
		int left = l;
		Gang biggestLeft = biggestL;
		boolean possible = left > 0;
		StringBuilder output = new StringBuilder("");
		if (possible) {
			output.append("YES\n");
			output.append(left + "\n");
			boolean occupied = false;
			int gangOccupying = 0;
			int numOccupying = 0;
			if (biggestLeft != null) {
				for (int i = 0; i < biggestLeft.num; i++) {
					output.append(1 + "\n");
				}
				occupied = true;
				numOccupying = biggestLeft.num;
			}
			for (int i = 0; i < N - numCows[0]; i++) {
				if (i == 63) {
					System.out.println();
				}
				for (int j = 1; j < M; j++) {
					if (numCows[j] == 0) {
						continue;
					}
					if (occupied && gangOccupying == j && numOccupying == numCows[j]) {
						continue;
					}
					if (occupied && j != gangOccupying) {
						numCows[gangOccupying]--;
						numCows[j]--;
						numOccupying--;
					}
					check(numCows, M);
					if (l < left) {
						if (occupied && j != gangOccupying) {
							numCows[gangOccupying]++;
							numCows[j]++;
							numOccupying++;
						}
					} else {
						if (!occupied) {
							occupied = true;
							gangOccupying = j;
							numOccupying = 1;
						} else {
							if (numOccupying == 0) {
								occupied = false;
							} else if (j == gangOccupying) {
								numOccupying++;
							}
						}
						output.append((j + 1) + "\n");
						break;
					}
				}
			}
			for (int i = 0; i < left; i++) {
				output.append(1 + "\n");
			}
		} else {
			output.append("NO\n");
		}
		out.print(output.toString());
		in.close();
		out.close();
	}

	static void check(int[] numCows, int M) {
		PriorityQueue<Gang> gangs = new PriorityQueue<Gang>();
		for (int i = 1; i < M; i++) {
			if (numCows[i] == 0) {
				continue;
			}
			gangs.add(new Gang(i, numCows[i]));
		}
		while (gangs.size() > 1) {
			Gang bigger = gangs.poll();
			Gang smaller = gangs.poll();
			bigger.num--;
			smaller.num--;
			if (bigger.num > 0) {
				gangs.add(bigger);
			}
			if (smaller.num > 0) {
				gangs.add(smaller);
			}
		}
		biggestL = gangs.poll();
		l = numCows[0] - (biggestL != null ? biggestL.num : 0);
	}

	static class Gang implements Comparable<Gang> {
		int ind;
		int num;

		public Gang(int ind, int num) {
			this.ind = ind;
			this.num = num;
		}

		@Override
		public int compareTo(Gang o) {
			if (num == o.num) {
				return ind - o.ind;
			}
			return o.num - num;
		}
	}
}