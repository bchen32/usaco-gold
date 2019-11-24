import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RouteDesign {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\RouteDesign\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("route.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("route.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int R = Integer.parseInt(tk.nextToken());
		int[] leftVals = new int[N];
		int[] rightVals = new int[M];
		Edge[] edges = new Edge[R];
		int[] dpLeft = new int[N];
		int[] dpRight = new int[M];
		for (int i = 0; i < N; i++) {
			leftVals[i] = Integer.parseInt(in.readLine());
			dpLeft[i] = leftVals[i];
		}
		for (int i = 0; i < M; i++) {
			rightVals[i] = Integer.parseInt(in.readLine());
			dpRight[i] = rightVals[i];
		}
		for (int i = 0; i < R; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			edges[i] = new Edge(a, b);
		}
		Arrays.sort(edges);
		for (int i = R - 1; i >= 0; i--) {
			int left = edges[i].l;
			int right = edges[i].r;
			int newLeft = leftVals[left] + dpRight[right];
			int newRight = rightVals[right] + dpLeft[left];
			dpLeft[left] = Math.max(dpLeft[left], newLeft);
			dpRight[right] = Math.max(dpRight[right], newRight);
		}
		int max = 0;
		for (int i = 0; i < N; i++) {
			max = Math.max(max, dpLeft[i]);
		}
		for (int i = 0; i < M; i++) {
			max = Math.max(max, dpRight[i]);
		}
		out.println(max);
		out.close();
		in.close();
	}

	static class Edge implements Comparable<Edge> {
		int l;
		int r;

		public Edge(int l, int r) {
			this.l = l;
			this.r = r;
		}

		@Override
		public int compareTo(Edge o) {
			if (this.l == o.l) {
				return Integer.compare(o.r, this.r);
			}
			return Integer.compare(o.l, this.l);
		}
	}
}