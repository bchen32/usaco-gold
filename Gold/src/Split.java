import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Split {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Split\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Split\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("split.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("split.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		Node[] xLocs = new Node[N];
		Node[] yLocs = new Node[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tk.nextToken());
			int y = Integer.parseInt(tk.nextToken());
			Node toAdd = new Node(x, y);
			xLocs[i] = toAdd;
			yLocs[i] = toAdd;
		}
		Arrays.sort(xLocs, new CompareX());
		Arrays.sort(yLocs, new CompareY());
		int leftX = xLocs[0].x;
		int bottomY = yLocs[0].y;
		int rightX = xLocs[N - 1].x;
		Solver ans = new Solver(N, xLocs, yLocs);
		System.out.println(Math.min(ans.minArea(leftX, bottomY), ans.minArea(rightX, bottomY)));
		out.close();
		in.close();
	}
}

class Solver {
	private int N;
	private Node[] xLocs;
	private Node[] yLocs;
	
	public Solver(int N, Node[] xLocs, Node[] yLocs) {
		this.N = N;
		this.xLocs = xLocs;
		this.yLocs = yLocs;
	}
	
	public int minArea(int minX, int minY) {
		int w = 0;
		int h = 0;
		int currX = 0;
		int currY = 0;
		for (int i = 0; i < N; i++) {
			Node tryX = xLocs[currX];
			while (tryX.used) {
				currX++;
				tryX = xLocs[currX];
			}
			Node tryY = yLocs[currY];
			while (tryY.used) {
				currY++;
				tryY = yLocs[currY];
			}
			int xArea = (w + Math.max(0, tryX.x)) * (h + Math.max(0, tryX.y));
			int yArea = (w + Math.max(0, tryY.x)) * (h + Math.max(0, tryY.y));
			if (xArea > yArea) {
				
			} else if (yArea < xArea) {
				
			}
		}
		return w * h;
	}
}

class Node {
	public int x;
	public int y;
	public boolean used = false;
	
	public Node(int a, int b) {
		x = a;
		y = b;
	}
}

class CompareX implements Comparator<Node> {
	
	@Override
	public int compare(Node a, Node b) {
		if (a.x == b.x) {
			return Integer.compare(a.y, b.y);
		}
		return Integer.compare(a.x, b.x);
	}
}

class CompareY implements Comparator<Node> {
	
	@Override
	public int compare(Node a, Node b) {
		if (a.y == b.y) {
			return Integer.compare(a.x, b.x);
		}
		return Integer.compare(a.y, b.y);
	}
}