import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Lasers {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Lasers\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Lasers\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("lasers.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lasers.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int lX = Integer.parseInt(tk.nextToken());
		int lY = Integer.parseInt(tk.nextToken());
		int bX = Integer.parseInt(tk.nextToken());
		int bY = Integer.parseInt(tk.nextToken());
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adjList = new LinkedList[2 * N + 4];
		for (int i = 0; i < 2 * N + 4; i++) {
			adjList[i] = new LinkedList<Integer>();
		}
		HashMap<Integer, Integer> xToNum = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> yToNum = new HashMap<Integer, Integer>();
		int xSLine = 2;
		int ySLine = N + 4;
		xToNum.put(lX, 0);
		yToNum.put(lY, N + 2);
		xToNum.put(bX, 1);
		yToNum.put(bY, N + 3);
		int startNum1 = xToNum.get(lX);
		int startNum2 = yToNum.get(lY);
		int endNum1 = xToNum.get(bX);
		int endNum2 = yToNum.get(bY);
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tk.nextToken());
			int y = Integer.parseInt(tk.nextToken());
			int xNum = -1;
			int yNum = -1;
			if (xToNum.containsKey(x)) {
				xNum = xToNum.get(x);
			} else {
				xToNum.put(x, xSLine);
				xNum = xSLine;
				xSLine++;
			}
			if (yToNum.containsKey(y)) {
				yNum = yToNum.get(y);
			} else {
				yToNum.put(y, ySLine);
				yNum = ySLine;
				ySLine++;
			}
			adjList[xNum].add(yNum);
			adjList[yNum].add(xNum);
		}
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] visited = new boolean[2 * N + 4];
		int[] dists = new int[2 * N + 4];
		Arrays.fill(dists, -1);
		dists[startNum1] = 0;
		dists[startNum2] = 0;
		visited[startNum1] = true;
		visited[startNum2] = true;
		q.add(startNum1);
		q.add(startNum2);
		while (!q.isEmpty()) {
			int curr = q.poll();
			visited[curr] = true;
			for (int toAdd : adjList[curr]) {
				if (!visited[toAdd]) {
					q.add(toAdd);
					dists[toAdd] = dists[curr] + 1;
				}
			}
		}
		int ans = -1;
		if (dists[endNum1] != -1 && dists[endNum2] != -1) {
			ans = Math.min(dists[endNum1], dists[endNum2]);
		} else if (dists[endNum1] != -1) {
			ans = dists[endNum1];
		} else if (dists[endNum2] != -1) {
			ans = dists[endNum2];
		}
		out.println(ans);
		out.close();
		in.close();
	}
}