import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class MilkVisits {

    static int[] depth;
    static int[][] parent;
    static int level;
    static LinkedList<Integer>[] adjList;
    static int[] cows;
    static int[][] query;
    static LinkedList<Integer>[] queryList;
    static ArrayList<Integer>[] lastCows;
    static boolean[] ans;
    static int[] preorderNums;
    static int[] postorderNums;
    static boolean[] visited;
    static int N;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        // BufferedReader in = new BufferedReader(new
        // FileReader("D:\\Java\\USACO-Gold\\Gold\\MilkVisits\\4.in"));
        BufferedReader in = new BufferedReader(new FileReader("milkvisits.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milkvisits.out")));
        StringTokenizer tk = new StringTokenizer(in.readLine());
        // input vars
        N = Integer.parseInt(tk.nextToken());
        int M = Integer.parseInt(tk.nextToken());
        cows = new int[N];
        // lca vars
        level = (int) (Math.ceil(Math.log(N) / Math.log(2)));
        depth = new int[N];
        parent = new int[N][level];
        // other vars
        adjList = new LinkedList[N];
        queryList = new LinkedList[N];
        lastCows = new ArrayList[N];
        preorderNums = new int[N];
        postorderNums = new int[N];
        visited = new boolean[N];
        // ans vars
        ans = new boolean[M];
        for (int i = 0; i < N; i++) {
            adjList[i] = new LinkedList<Integer>();
            queryList[i] = new LinkedList<Integer>();
            lastCows[i] = new ArrayList<Integer>();
        }
        tk = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            cows[i] = Integer.parseInt(tk.nextToken()) - 1;
        }
        for (int i = 0; i < N - 1; i++) {
            tk = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(tk.nextToken()) - 1;
            int y = Integer.parseInt(tk.nextToken()) - 1;
            adjList[x].add(y);
            adjList[y].add(x);
        }
        dfsLCA(0, -1);
        precomputeSparseMatrix(N);
        query = new int[M][3];
        for (int i = 0; i < M; i++) {
            tk = new StringTokenizer(in.readLine());
            query[i][0] = Integer.parseInt(tk.nextToken()) - 1;
            query[i][1] = Integer.parseInt(tk.nextToken()) - 1;
            query[i][2] = Integer.parseInt(tk.nextToken()) - 1;
            queryList[query[i][0]].add(i);
            queryList[query[i][1]].add(i);
        }
        preandpost();
        dfs(0);
        for (int i = 0; i < M; i++) {
            out.print(ans[i] ? "1" : "0");
        }
        out.println();
        out.close();
        in.close();
    }

    static void dfs(int curr) {
        lastCows[cows[curr]].add(curr);
        visited[curr] = true;
        for (int i : queryList[curr]) {
            int a = query[i][0];
            int b = query[i][1];
            int c = query[i][2];
            if (!lastCows[c].isEmpty()) {
                int last = lastCows[c].get(lastCows[c].size() - 1);
                if (last == a || last == b) {
                    ans[i] = true;
                }
                if (!ancestor(last, b) || !ancestor(last, a)) {
                    ans[i] = true;
                }
                if (lca(a, b) == last) {
                    ans[i] = true;
                }
            }
        }
        for (int adj : adjList[curr]) {
            if (!visited[adj]) {
                dfs(adj);
            }
        }
        lastCows[cows[curr]].remove(lastCows[cows[curr]].size() - 1);
    }

    static void preandpost() {
        int num = 0;
        boolean[] visited = new boolean[N];
        Stack<Integer> s1 = new Stack<Integer>();
        s1.push(0);
        while (!s1.isEmpty()) {
            int curr = s1.pop();
            visited[curr] = true;
            preorderNums[curr] = num;
            num++;
            Iterator<Integer> it = adjList[curr].descendingIterator();
            while (it.hasNext()) {
                int toTry = it.next();
                if (!visited[toTry]) {
                    s1.push(toTry);
                }
            }
        }
        s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();
        visited = new boolean[N];
        s1.add(0);
        while (!s1.isEmpty()) {
            int curr = s1.pop();
            visited[curr] = true;
            s2.push(curr);
            for (int adj : adjList[curr]) {
                if (!visited[adj]) {
                    s1.push(adj);
                }
            }
        }
        num = 0;
        while (!s2.isEmpty()) {
            int curr = s2.pop();
            postorderNums[curr] = num;
            num++;
        }
    }

    static boolean ancestor(int a, int b) {
        return preorderNums[a] <= preorderNums[b] && postorderNums[b] <= postorderNums[a];
    }

    static void dfsLCA(int curr, int prev) {
        if (curr != 0) {
            depth[curr] = depth[prev] + 1;
            parent[curr][0] = prev;
        }
        for (int adj : adjList[curr]) {
            if (adj != prev) {
                dfsLCA(adj, curr);
            }
        }
    }

    static void precomputeSparseMatrix(int N) {
        for (int i = 1; i < level; i++) {
            for (int node = 0; node < N; node++) {
                if (parent[node][i - 1] != -1)
                    parent[node][i] = parent[parent[node][i - 1]][i - 1];
            }
        }
    }

    static int lca(int u, int v) {
        if (depth[v] < depth[u]) {
            int temp = u;
            u = v;
            v = temp;
        }
        int diff = depth[v] - depth[u];
        for (int i = 0; i < level; i++) {
            if (((diff >> i) & 1) == 1) {
                v = parent[v][i];
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = level - 1; i >= 0; i--) {
            if (parent[u][i] != parent[v][i]) {
                u = parent[u][i];
                v = parent[v][i];
            }
        }
        return parent[u][0];
    }
}