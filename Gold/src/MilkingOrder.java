import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class MilkingOrder {
	int N;
	int M;
	ArrayList<MOEdge>[] rules;
	int[] answer;
	
	public MilkingOrder(int N, int M, ArrayList<MOEdge>[] rules) {
		this.N = N;
		this.M = M;
		this.rules = rules;
		this.answer = new int[N];
	}
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\MilkingOrder\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\MilkingOrder\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("milkorder.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milkorder.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		ArrayList<MOEdge>[] rules = new ArrayList[M];
		for (int i = 0; i < M; i++) {
			rules[i] = new ArrayList<MOEdge>();
		}
		for (int i = 0; i < M; i++) {
			tk = new StringTokenizer(in.readLine());
			int l = Integer.parseInt(tk.nextToken());
			int[] rule = new int[l];
			for (int j = 0; j < l; j++) {
				rule[j] = Integer.parseInt(tk.nextToken()) - 1;
			}
			for (int j = 0; j < l - 1; j++) {
				rules[i].add(new MOEdge(rule[j], rule[j + 1]));
			}
		}
		int low = 0;
		int high = M;
		MilkingOrder simulate = new MilkingOrder(N, M, rules);
		while (low < high) {
			int mid = (low + high + 1) / 2;
			boolean success = simulate.topologicalSort(mid);
			if (success) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		// Final run
		simulate.topologicalSort(low);
		StringBuilder ans = new StringBuilder("");
		for (int i = 0; i < N; i++) {
			ans.append((simulate.answer[i] + 1) + " ");
		}
		ans.deleteCharAt(ans.length() - 1);
		out.println(ans);
		out.close();
		in.close();
	}
	
	public boolean topologicalSort(int x) {
		ArrayList<Integer>[] next = new ArrayList[N];
		int[] numPrev = new int[N];
		for (int i = 0; i < N; i++) {
			next[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < x; i++) {
			for (MOEdge curr : rules[i]) {
				next[curr.first].add(curr.second);
				numPrev[curr.second]++;
			}
		}
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		for (int i = 0; i < N; i++) {
			if (numPrev[i] == 0) {
				q.add(i);
			}
		}
		for (int i = 0; i < N; i++) {
			if (q.isEmpty()) {
				return false;
			}
			int curr = q.poll();
			answer[i] = curr;
			for (int toAdd : next[curr]) {
				numPrev[toAdd]--;
				if (numPrev[toAdd] == 0) {
					q.add(toAdd);
				}
			}
		}
		return true;
	}
}

class MOEdge {
	int first;
	int second;
	
	public MOEdge(int first, int second) {
		this.first = first;
		this.second = second;
	}
}