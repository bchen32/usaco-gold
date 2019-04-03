import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Dishwashing {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Dishwashing\\2.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Dishwashing\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("dishes.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dishes.out")));
		int N = Integer.parseInt(in.readLine());
		int[] plates = new int[N];
		int[] placeStack = new int[N + 1];
		// placeStack[i] is the index of the soapy stack which i should be placed in
		LinkedList<Integer>[] soapy = new LinkedList[N + 1];
		int maxPlaced = -1;
		for (int i = 0; i < N; i++) {
			plates[i] = Integer.parseInt(in.readLine());
			soapy[i] = new LinkedList<Integer>();
		}
		soapy[N] = new LinkedList<Integer>();
		int i;
		for (i = 0; i < N; i++) {
			int toPlace = plates[i];
			if (toPlace < maxPlaced) {
				break;
			}
			for (int j = toPlace; j >= 0 && placeStack[j] == 0; j--) {
				placeStack[j] = toPlace;
			}
			LinkedList<Integer> curr = soapy[placeStack[toPlace]];
			while (!curr.isEmpty() && curr.getLast() < toPlace) {
				maxPlaced = Math.max(maxPlaced, curr.getLast());
				curr.removeLast();
			}
			curr.add(toPlace);
		}
		out.println(i);
		out.close();
		in.close();
	}
}