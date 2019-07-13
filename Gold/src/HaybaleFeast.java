import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class HaybaleFeast {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\HaybaleFeast\\4.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\HaybaleFeast\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("hayfeast.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hayfeast.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		long M = Long.parseLong(tk.nextToken());
		Feastbale[] bales = new Feastbale[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			bales[i] = new Feastbale(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), i);
		}
		TreeSet<Feastbale> currInterval = new TreeSet<Feastbale>();
		int minSpice = Integer.MAX_VALUE;
		long totalFlavor = 0;
		int prevStop = -1;
		for (int i = 0; i < N; i++) {
			Feastbale curr = bales[i];
			for (int j = prevStop + 1; j < N && totalFlavor < M; j++) {
				currInterval.add(bales[j]);
				totalFlavor += bales[j].flavor;
				prevStop = j;
			}
			if (totalFlavor < M) {
				break;
			}
			minSpice = Math.min(minSpice, currInterval.first().spice);
			currInterval.remove(curr);
			totalFlavor -= curr.flavor;
		}
		out.println(minSpice);
		out.close();
		in.close();
	}
}

class Feastbale implements Comparable<Feastbale> {
	int flavor;
	int spice;
	int ind;
	
	public Feastbale(int flavor, int spice, int ind) {
		this.flavor = flavor;
		this.spice = spice;
		this.ind = ind;
	}
	
	public boolean equals(Object other) {
		Feastbale o = (Feastbale) other;
		if (this.ind == o.ind) {
			return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Feastbale o) {
		// TODO Auto-generated method stub
		if (this.spice == o.spice) {
			return Integer.compare(this.ind, o.ind);
		}
		return Integer.compare(o.spice, this.spice);
	}
	
	@Override
	public String toString() {
		return flavor + " " + spice;
	}
}