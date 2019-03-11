import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class FileDirectory {
	public static final int INF = 1 << 30;
	
	private int N;
	private List<Integer>[] adjList;
	private int[] parents;
	private Set<Integer> files;
	private String[] names;
	
	public FileDirectory(int N, List<Integer>[] adjList2, int[] parents, String[] names, Set<Integer> files2) {
		this.N = N;
		this.adjList = adjList2;
		this.parents = parents;
		this.names = names;
		this.files = files2;
	}
	
	public void subtreeSums() {
		
	}
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\FileDirectory\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\FileDirectory\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("dirtraverse.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		
		@SuppressWarnings("unchecked")
		List<Integer>[] adjList = new LinkedList[N];
		Set<Integer> files = new HashSet<Integer>();
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
		out.close();
		in.close();
	}
}