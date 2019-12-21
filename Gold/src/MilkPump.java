import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class MilkPump {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("pump.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pump.out")));
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(tk.nextToken());
        int M = Integer.parseInt(tk.nextToken());
        @SuppressWarnings("unchecked")
        LinkedList<Edge>[] adjList = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new LinkedList<Edge>();
        }
        for (int i = 0; i < M; i++) {
            tk = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(tk.nextToken()) - 1;
            int b = Integer.parseInt(tk.nextToken()) - 1;
            int cost = Integer.parseInt(tk.nextToken());
            int flow = Integer.parseInt(tk.nextToken());
            adjList[a].add(new Edge(b, cost, flow));
            adjList[b].add(new Edge(a, cost, flow));
        }
        double bestRatio = dijkstra(adjList, 0, N)[N - 1];
        out.println((int) (bestRatio * 1000000));
        out.close();
        in.close();
    }

    static double[] dijkstra(LinkedList<Edge>[] adjList, int root, int N) {
        PriorityQueue<Node> heap = new PriorityQueue<Node>();
        double[] ratios = new double[N];
        int[] costs = new int[N];
        int[] minFlow = new int[N];
        Arrays.fill(minFlow, Integer.MAX_VALUE);
        boolean[] inSet = new boolean[N];
        heap.add(new Node(root, 0));
        ratios[root] = 0;

        while (!heap.isEmpty()) {
            int u = heap.poll().num;
            inSet[u] = true;
            LinkedList<Edge> adj = adjList[u];
            for (Edge currEdge : adj) {
                int v = currEdge.other;
                double newCost = costs[u] + currEdge.cost;
                double ratioThroughU = (currEdge.flow < minFlow[u] ? currEdge.flow : minFlow[u]) / newCost;
                if (!inSet[v]) {
                    if (ratioThroughU > ratios[v]) {
                        ratios[v] = ratioThroughU;
                        costs[v] = (int) newCost;
                        minFlow[v] = currEdge.flow < minFlow[u] ? currEdge.flow : minFlow[u];
                        heap.add(new Node(v, ratioThroughU));
                    }
                }
            }
        }
        return ratios;
    }

    static class Node implements Comparable<Node> {
        int num;
        double ratio;

        public Node(int num, double ratio) {
            this.num = num;
            this.ratio = ratio;
        }

        @Override
        public int compareTo(Node other) {
            if (this.ratio == other.ratio) {
                return Integer.compare(this.num, other.num);
            }
            return Double.compare(other.ratio, ratio);
        }
    }

    static class Edge {
        int other;
        int cost;
        int flow;

        public Edge(int o, int a, int b) {
            other = o;
            cost = a;
            flow = b;
        }
    }
}