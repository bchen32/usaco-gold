import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Circlecross {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Circlecross\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Circlecross\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("circlecross.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("circlecross.out")));
		int N = Integer.parseInt(in.readLine());
		CircleSort[] input = new CircleSort[N];
		for (int i = 0; i < N; i++) {
			input[i] = new CircleSort(-1, -1);
		}
		for (int i = 0; i < 2 * N; i++) {
			int curr = Integer.parseInt(in.readLine()) - 1;
			if (input[curr].firstInd == -1) {
				input[curr].firstInd = i;
			} else {
				input[curr].secondInd = i;
			}
		}
		Arrays.sort(input);
		CircleBIT BIT = new CircleBIT(2 * N);
		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans += BIT.get(input[i].secondInd) - BIT.get(input[i].firstInd);
			BIT.update(input[i].secondInd, 1);
		}
		out.println(ans);
		
		out.close();
		in.close();
	}
}

class CircleSort implements Comparable<CircleSort> {
	int firstInd;
	int secondInd;
	
	public CircleSort(int firstInd, int secondInd) {
		this.firstInd = firstInd;
		this.secondInd = secondInd;
	}
	
	@Override
	public int compareTo(CircleSort o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.firstInd, o.firstInd);
	}
}

class CircleBIT {
	int BIT[];
	int N;
	
	public CircleBIT(int N) {
		this.N = N;
		BIT = new int[N + 1];
	}
	
	public int get(int index) {
		int sum = 0;
		index++;
		while (index > 0) {
			sum += BIT[index];
			index -= index & (-index);
		}
		return sum;
	}
	
	public void update(int index, int val) {
		index++;
		while (index <= N) {
			BIT[index] += val;
			index += index & (-index);
		}
	}
}