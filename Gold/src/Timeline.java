import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Timeline {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Timeline\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("timeline.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("timeline.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		tk.nextToken();
		int C = Integer.parseInt(tk.nextToken());
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adjList = new LinkedList[N];
		for (int i = 0; i < N; i++) {
			adjList[i] = new LinkedList<Integer>();
		}
		Session[] sessions = new Session[N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			int day = Integer.parseInt(tk.nextToken());
			sessions[i] = new Session(i, day);
		}
		for (int i = 0; i < C; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			int x = Integer.parseInt(tk.nextToken());
			sessions[b].c.add(new Constraint(a, b, x));
			adjList[a].add(b);
		}
		int[] order = topologicalSort(adjList, N);
		int[] days = new int[N];
		for (int i = 0; i < N; i++) {
			Session curr = sessions[order[i]];
			int place = curr.first;
			for (Constraint j : curr.c) {
				place = Math.max(place, days[j.first] + j.days);
			}
			days[curr.num] = place;
		}
		for (int i = 0; i < N; i++) {
			out.println(days[i]);
		}
		out.close();
		in.close();
	}

	static int[] topologicalSort(LinkedList<Integer>[] adjList, int N) {
		int inDegree[] = new int[N];
		for (int i = 0; i < N; i++) {
			LinkedList<Integer> adj = adjList[i];
			for (int node : adj) {
				inDegree[node]++;
			}
		}
		LinkedList<Integer> q = new LinkedList<Integer>();
		for (int i = 0; i < N; i++) {
			if (inDegree[i] == 0) {
				q.add(i);
			}
		}
		int[] topOrder = new int[N];
		int ind = 0;
		while (!q.isEmpty()) {
			int u = q.poll();
			topOrder[ind] = u;
			ind++;
			for (int node : adjList[u]) {
				if (--inDegree[node] == 0) {
					q.add(node);
				}
			}
		}
		return topOrder;
	}

	static class Session implements Comparable<Session> {
		int first;
		int num;
		LinkedList<Constraint> c = new LinkedList<Constraint>();

		public Session(int num, int first) {
			this.num = num;
			this.first = first;
		}

		@Override
		public int compareTo(Session o) {
			return first - o.first;
		}
	}

	static class Constraint {
		int first;
		int second;
		int days;

		public Constraint(int first, int second, int days) {
			this.first = first;
			this.second = second;
			this.days = days;
		}
	}
}