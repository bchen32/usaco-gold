import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class FileDirectory {
	
	private int N;
	private LinkedList<Integer>[] adjList;
	private int[] parents;
	private HashSet<Integer> files;
	private String[] names;
	private Node[] subtreeNodes;
	private Node[] supertreeNodes;
	
	public FileDirectory(int N, LinkedList<Integer>[] adjList2, int[] parents, String[] names, HashSet<Integer> files2) {
		this.N = N;
		this.adjList = adjList2;
		this.parents = parents;
		this.names = names;
		this.files = files2;
		this.subtreeNodes = new Node[N];
		this.supertreeNodes = new Node[N];
		this.supertreeNodes[0] = new Node(0, 0);
	}
	
	public void findsupertreeNodes() {
		Stack<Integer> s = new Stack<Integer>();
		boolean[] visited = new boolean[N];
		
		s.add(0);
		visited[0] = true;
		
		while (!s.isEmpty()) {
			int curr = s.pop();
			visited[curr] = true;
			LinkedList<Integer> adj = adjList[curr];
			Node aSuper = supertreeNodes[curr];
			Node aSub = subtreeNodes[curr];
			long baselineSum = aSuper.sum + (3 * aSuper.numFiles) + aSub.sum + (3 * aSub.numFiles);
			for (int i : adj) {
				if (parents[curr] != i) {
					Node bSub = subtreeNodes[i];
					long sum = baselineSum;
					int numSuperfiles = aSuper.numFiles + aSub.numFiles - bSub.numFiles;
					sum += -bSub.sum - (bSub.numFiles * (names[i].length() + 4));
					supertreeNodes[i] = new Node(sum, numSuperfiles);
					s.push(i);
				}
			}
		}
	}
	
	public void findsubtreeNodes() {
		Stack<Integer> s1 = new Stack<Integer>();
		Stack<Integer> s2 = new Stack<Integer>();
		boolean[] visited = new boolean[N];
		
		s1.add(0);
		visited[0] = true;
		
		while (!s1.isEmpty()) {
			int curr = s1.pop();
			visited[curr] = true;
			s2.push(curr);
			LinkedList<Integer> adj = adjList[curr];
			for (int i : adj) {
				if (!visited[i]) {
					s1.push(i);
				}
			}
		}
		while (!s2.isEmpty()) {
			int curr = s2.pop();
			LinkedList<Integer> adj = adjList[curr];
			long sum = 0;
			int numSubfiles = 0;
			for (int i : adj) {
				if (parents[curr] != i) {
					if (files.contains(i)) {
						sum += names[i].length();
						numSubfiles++;
					} else {
						Node child = subtreeNodes[i];
						sum += child.numFiles * (names[i].length() + 1) + child.sum;
						numSubfiles += child.numFiles;
					}
				}
			}
			subtreeNodes[curr] = new Node(sum, numSubfiles);
		}
	}
	
	public long bestDist() {
		long best = Long.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			if (!files.contains(i)) {
				best = Math.min(best, subtreeNodes[i].sum + supertreeNodes[i].sum);
			}
		}
		return best;
	}
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\FileDirectory\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\FileDirectory\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("dirtraverse.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adjList = new LinkedList[N];
		HashSet<Integer> files = new HashSet<Integer>();
		String[] names = new String[N];
		int[] parents = new int[N];
		
		for (int i = 0; i < N; i++) {
			adjList[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			String name = tk.nextToken();
			names[i] = name;
			int m = Integer.parseInt(tk.nextToken());
			if (m == 0) {
				files.add(i);
			}
			for (int j = 0; j < m; j++) {
				int node = Integer.parseInt(tk.nextToken()) - 1;
				adjList[i].add(node);
				adjList[node].add(i);
				parents[node] = i;
			}
		}
		FileDirectory calc = new FileDirectory(N, adjList, parents, names, files);
		calc.findsubtreeNodes();
		calc.findsupertreeNodes();
		out.println(calc.bestDist());
		out.close();
		in.close();
	}
	
	static class Node {
		int numFiles;
		long sum;
		
		public Node(long s, int sub) {
			sum = s;
			numFiles = sub;
		}
	}
}