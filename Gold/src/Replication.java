import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Replication {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(tk.nextToken());
        int D = Integer.parseInt(tk.nextToken());
        int[][] grid = new int[N][N];
        int[][] robots = new int[N][N];
        Point[] starts = new Point[N * N];
        Point[] rocks = new Point[N * N];
        int startInd = 0;
        int rockInd = 0;
        for (int i = 0; i < N; i++) {
            char[] line = in.readLine().toCharArray();
            for (int j = 0; j < N; j++) {
                char ch = line[j];
                if (ch == 'S') {
                    starts[startInd] = new Point(i, j);
                    startInd++;
                }
                if (ch == '.') {
                    grid[i][j] = 1;
                }
                if (ch == '#') {
                    rocks[rockInd] = new Point(i, j);
                    rockInd++;
                    grid[i][j] = 2;
                }
            }
        }
        int[] rChange = { 0, 1, 0, -1 };
        int[] cChange = { 1, 0, -1, 0 };
        boolean[][][][] usedRobots = new boolean[N][N][D][N];
        for (int i = 0; i < startInd; i++) {
            Point start = starts[i];
            if (start.r == 0 && start.c == 0) {
                continue;
            }
            Robot startRobot = new Robot(start, 0, 0);
            LinkedList<Robot> q = new LinkedList<Robot>();
            q.add(startRobot);
            while (!q.isEmpty()) {
                Robot curr = q.poll();
                if (curr.time != 0 && curr.time % D == 0) {
                    curr.expands += 1;
                    boolean expandPossible = true;
                    for (int k = 0; k < rockInd; k++) {
                        Point rock = rocks[k];
                        if (curr.expands >= Math.abs(curr.center.r - rock.r) + Math.abs(curr.center.c - rock.c)) {
                            expandPossible = false;
                        }
                    }
                    if (!expandPossible) {
                        LinkedList<Point> qFill = new LinkedList<Point>();
                        robots[curr.center.r][curr.center.c] = 1;
                        qFill.add(curr.center);
                        while (!qFill.isEmpty()) {
                            Point currPoint = qFill.poll();
                            for (int j = 0; j < 4; j++) {
                                int newR = currPoint.r + rChange[j];
                                int newC = currPoint.c + cChange[j];
                                if (grid[newR][newC] != 2 && robots[newR][newC] != 1 && curr.expands - 1 >= Math.abs(newR - curr.center.r) + Math.abs(newC - curr.center.c)) {
                                    robots[newR][newC] = 1;
                                    qFill.add(new Point(newR, newC));
                                }
                            }
                        }
                        continue;
                    }
                }
                LinkedList<Point> qFill = new LinkedList<Point>();
                boolean[][] traveled = new boolean[N][N];
                robots[curr.center.r][curr.center.c] = 1;
                qFill.add(curr.center);
                while (!qFill.isEmpty()) {
                    Point currPoint = qFill.poll();
                    traveled[currPoint.r][currPoint.c] = true;
                    for (int j = 0; j < 4; j++) {
                        int newR = currPoint.r + rChange[j];
                        int newC = currPoint.c + cChange[j];
                        if (grid[newR][newC] != 2 && !traveled[newR][newC] && curr.expands >= Math.abs(newR - curr.center.r) + Math.abs(newC - curr.center.c)) {
                            robots[newR][newC] = 1;
                            qFill.add(new Point(newR, newC));
                        }
                    }
                }
                for (int j = 0; j < 4; j++) {
                    int newR = curr.center.r + rChange[j];
                    int newC = curr.center.c + cChange[j];
                    boolean movePossible = true;
                    for (int k = 0; k < rockInd; k++) {
                        Point rock = rocks[k];
                        if (curr.expands >= Math.abs(newR - rock.r) + Math.abs(newC - rock.c)) {
                            movePossible = false;
                        }
                    }
                    if (movePossible) {
                        Robot toAdd = new Robot(new Point(newR, newC), curr.time + 1, curr.expands);
                        if (!usedRobots[newR][newC][(curr.time + 1) % D][curr.expands]) {
                            q.add(toAdd);
                            usedRobots[newR][newC][(curr.time + 1) % D][curr.expands] = true;
                        }
                    }
                }
            }
        }
        // System.out.println();
        int numRobots = 0;
        for (int i = 0; i < N; i++) {
            // StringBuilder line = new StringBuilder();
            for (int j = 0; j < N; j++) {
                if (robots[i][j] == 1) {
                    numRobots++;
                    // line.append('x');
                }
                //  else if (grid[i][j] == 2) {
                //     line.append('#');
                // } else {
                //     line.append('.');
                // }
            }
            // System.out.println(line);
        }
        System.out.println(numRobots);
        in.close();
    }

    public static class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static class Robot {
        Point center;
        int time;
        int expands;

        public Robot(Point center, int time, int expands) {
            this.center = center;
            this.time = time;
            this.expands = expands;
        }
    }
}
