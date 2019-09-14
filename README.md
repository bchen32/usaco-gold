# USACO-Gold
Explanations for Gold Problems

Angry
N <= 50,000
Max Radius = 1,000,000,000
Firstly multiply all cow positions by two so everything is always an integer.
Read in the cow positions to a TreeSet (fast ceiling and floor function are very useful).
We have two functions, checkLeft and checkRight, that check if exploding from a given starting point with certain radius can reach all the way left or right respectively. Ceiling and floor are used to check the next haybale affected by an explosion with certain radius and starting from a certain haybale.

Run a binary search on possible radii.
Then, given the radius, binary search for the starting point.
We find the best starting point by testing our radius and start point with checkLeft.
If we reach the left, then move up the lower bound. If we don't, then move down the upper bound.
After we finish we know we have the greatest point that can still reach left. We then use checkRight, knowing that if isn't
successful, that radius will never work. If checkRight succeeds, that radius is viable.

We use nested binary searches dependent on max radius and then a simulation nested within that. Floor function is LogN and worst case we traverse the whole line every time checkLeft is called. That should give us (Log(Max Radius)^2)(NLogN). However, almost all of the checkLeft calls end within a few iterations, so the real time complexity should be somewhere between (Log(Max Radius)^2)(NLogN) and (Log(Max Radius)^2)(LogN). The worst test case runs in ~3800ms.

Art2
Use a stack and simulate. While reading in input, we want to keep track of the first and last occurence of each color. In order to properly process empty canvas, we add one spot of empty on the beginning and end of the sequence. Also, observe that the depth of the stack at a given point is layers + 1 (remember that we are counting empty canvas as a layer, so we need to subtract 1). Otherwise, the logic is
Loop through the sequence
If i is the first occurence of that currColor, add it to the stack and update maxlayers
If currColor is not equal to the top of the stack, then this is not an authentic picowso painting (try 1 4 1 4 to see why this works)
If i is the last occurence of currColor, then pop it from the stack.

Balance
Simple simulation
Swapping pieces on one side of the board only changes delta by 1
Swapping central pieces is more complicated, think of it as moving ones across the divide, either move ones left, or right, doesn't make sense to do both:
if swapping ones from left to right
left inversions -= left ones;
right inversions -= (N - right ones);
if swapping ones from right to left
i1 += ones left - 1;
i2 += (N - ones right) - 1;
Then calculate number of moves needed to set up the central swap
Loop through number of central swaps for left and right separately, take min

BalancedPhoto
Sort the cows by their height from largest to smallest. Looping through the sorted array of cows, and every time you process a cow, set it's index in the BIT to 1. This lets you arrive at a fairly common usage of the BIT. In a given iteration of the loop, all cows taller than the current cow will have their indices set to 1 in the BIT. If you find sum(currCow.index), then you will have the number of cows taller and to the left of the current cow. Then, i (the number of cows already processed, aka the total number of cows taller than currCow) - sum will equal the number of taller cows to the right of the current cow. This comes out to a complexity of O(NlogN)

BarnPainting
Another dp problem
State is root vert num and current color
Do this recursively
curr : adjList[root]
        long sub = 0;
        c = 0 : 3
                if (c == color)
                        continue
                sub += recurse(curr, c, root, color) % MOD;
        }
        ans = (ans * sub) % MOD;
}
dp[root][color] = ans;
Then, just check that no colorings are being violated and be careful of mod/overflow

Censoring
Gave up
Some weird hashing stuff

Checklist
Dp solution
State is num H cows visited and num G cows visited, as well as whether the last cow is H or G. dp[h][g][isH]
dp[h][g][true] = dp[h - 1][g][false] + dp[h - 1][g][true];
dp[h][g][false] = dp[h][g - 1][true] + dp[h][g - 1][false];

CircleBarn
First, use running sum to find the "least correct" spot. Starting there, loop through the barn and do a greedy. If you're on a zero, go back until the first non zero and move that cow. Calculate the distance and add it up.

CircleBarn2
More dp. Instead of a circular barn, think of it as linear and loop through every possible starting positions.
State is dp[k][i] where k is the number of doors used and i is the position of the last door
Transition:
start = 0 : N
        "rotate" doors
        initialize dp
        k = 1 : K
                j = k + 1 : N
                        sum = 0
                        curr = rotated[j]
                        lastDoor = j - 1 : 0
                                sum += curr
                                dp[k][j] = min(dp[k][j], dp[k - 1][lastDoor - 1] + sum)
                                curr += rotated[lastDoor]
Essentially turning circular barn into linear first, and then adding up the distance cows would have to walk from last door to current dp state

Circlecross
BIT problem
Store input as pairs (firstInd, secondInd) and sort by firstInd
Loop through and do BIT.get(secondInd) - BIT.get(firstInd) which gives you the number of cows whos firstInd is less than curr firstInd and whose secondInd is greater than curr firstInd but less than curr secondInd, aka an interescting pair. Then do BIT.update(secondInd, 1)

ClosingFarm
DSU problem data structure. Trick is to go backwards, start with fully closed barn and add edges. Also remember that if a graph has numVerts - 1 unique edges, it is fully connected. So keep track of verts - 1 of the current state of the graph, and increment it every time you "open" a new barn and decrement it every time you add an edge.


Cow248
Fairly weird dp problem with a small constraint (N <= 248)
State is the largest number possible in the sub interval starting at i with a length of j
Transition:
l = 0 : N
        i = 0 : N - l
                k = 0 : l
                        if dp[i][k] == dp[i + k + 1][l - k - 1]
                                dp[i][l] = Math.max(dp[i][l], dp[i][k] + 1;
Essentially looping over every sub interval, and then seeing if any sub intervals within that interval can be combined


CowAtLarge
Graph problem
First of all, bessie should always move away from the root and farmers should always move towards the root. If a farmer gets to a barn before Bessie, than that barn's subtree is completely closed off. We want the minimum amount of farmers which can close off all leaves. Another way to think of this is that if a node is closer or equally close to the nearest leaf, but it's parent is closer to the root, then there needs to be a farmer at that leaf to stop Bessie from escaping into that subtree. Thus, our solution is finding all nodes who satisfy this condition. First run a bfs from root to calculate distances from root. Then run a multi source bfs from every leaf to calculate distances to nearest leaf. Simply compare these to find the number of nodes satisfying the above conditions, and that's your answer.

CowNav
Finnicky bfs problem
Visualize 2 possibilities as moving 2 cows simultaneously
State is position of both potential spots dp[x1][y1][x2][y2]
At every state, can either turn left or right, or move forwards, which requires some additional checking

Cownomics
A rolling hash problem
Keep an index j to mark the front of the hash, and an index i to mark the back of the hash. If overlapping cows > 0, advance j, if overlapping cows <= 0, advance i. Your answer is min(ans, j - i).

Cowpatibility
Crazy hard problem
Realize that cows are fixed at 5 flavors, meaning there are only 31 possible subsets of a given cows flavors
Keep an array of HashMaps subsets = new HashMap[5]
subsets[i] = HashMap<HashedArray of length i, Long>
HashedArray is a sorted subset of flavors and the value is the number of cows
Loop through each input cow
First sort that cows flavors
Then loop through every possible subset of that cows flavors
Create a HashedArray for that subset and add it to subsets[size] where size is the number of flavors in that subset. If it's already in subsets[size], then increment that value.
After you do this for every cow, you will have HashMaps where key = subset of flavors and value = number of cows who like that subset, where the HashMaps are broken up by size of subset.
If x cows like a certain subset, there are x(x - 1) / 2 pairs of cows who like that subset. Thus, we can find the number of pairs of cows who share subsets of size 1, size 2, size 3, size 4, and size 5. Now, we use PIE to calculate the number of compatible pairs, which can easily be turned into our answer. By PIE the compatible cows are subsetSum1 - subsetSum2 + subsetSum3 - subsetSum4 + subsetSum 5.

CowPoetry
More dp
If we know the number of ways to end a line with every rhyme class, and we know the amount of times every rhyme class shows up in the overall scheme, then we can calculate the answer. For example, in the sample case there are 8 ways to end with r1 and 4 ways to end with r2. R1 shows up twice, and r2 shows up once, so the final answer is (r1 ^ f1)(r1 ^ f2) + (r2 ^ f1)(r1 ^ f2) + (r1 ^ f1)(r2 ^ f2) + (r2 ^ f1)(r2 ^ f2) which factors into (r1 ^ f1 + r2 ^ f1)(r1 ^ f2 + r2 ^ f2). Keep in mind that a scheme of ABA only means that A lines end with the same thing, and B lines end with the same thing, not that B lines and A lines have to end with different things. Calculating f's is straightforwards, but calculating r's will need to use dp.
First, we run a standard dp to see the number of ways to build a line with i syllables with dp[i]
i = 0 : K + 1
        j = 0 : N
                syllables = words[j].syllables
                rhyme = words[j].rhyme
                if i + syllables > K
                        continue
		if i + syllables == K
			rhymes[rhyme] = rhymes[rhyme] + dp[i]
		dp[i + syllables] = dp[i + syllables] + dp[i]
Apart from using dp to calculate rhymes, which is not an obvious technique, this is pretty textbook

Dining
Dijkstra with a twist
First, run dijkstra from the home barn to calculate all distances with no haybales.
Going to a haybale is like subtracting the yumminess value of the haybale from your distance traveled. So in order to calculate the "after" values we will create a new fictitious node and create edges between it and all haybales. These edges should have weight before[haybale] - haybale yumminess. Then run dijkstra from this new fictitious node. This essentially forces all cows to go through a haybale. Then, we can simply compare the before and afters for the answer.

Dishwashing
Another kind of weird simulation problem
First realize, that if we are placing a plate x, then x should be in the leftmost stack where all plates to the left are smaller than x. This becomes a little trickier if the top plate on that optimal stack is also less than x. In that case, we need to clear out the stacks until x can be placed. If x is less than the greatest plate which has been cleaned by elsie so far, then we stop.
     |?|
|1|  |4|  |7|
|2|  |6|  |9|  |10|  |15|
There are also a few implementation tricks for this. We will keep track of the soapy stacks as an array of LinkedLists. To quickly know what the optimal stack is, we maintain an array placeStack[N + 1] where placeStack[i] tells us the index of the optimal stack for i.
i = 0 : N
	toPlace = plates[i]
	j = toPlace : 0 and placeStack[j] == 0
		placeStack[j] = toPlace
The exact mechanics of placeStack are hard to explain, but working through a few examples yourself will probably make it somewhat clear.

Dream
Another weird bfs. Fairly straightforwards though, just a pain to implement.

Feast
Basic knapsack with one modification.
State - dp[0][t] is true if Bessie can reach t fullness without drinking, and dp[1][t] is the same, but if Bessie has drunk
Transition is really straightforwards, and is left as an excercise to the reader. Just make sure to compute dp[0] first.

FencedIn
Textbook MST
Kruskal's or Prim's will both run in time
Tricky thing is converting the graph into something which can be processed by an MST alg. This can be done in O(NM) time and runs just fast enough to stay in the limit.

FileDirectory
Another cancerous graph problem. We calculate subtrees and supertrees separately, giving this a similar feel to CowAtLarge. This is necessary because up and down weights aren't the same. Maintain arrays of subNodes and superNodes, where nodes are defined by the total distance to all files in their sub/supertree and the number of files in their sub/supertree. We first run dfs using iterative post order traversal of a stack and update subNodes as we do it. Specifically, we loop through each nodes children and update sum and numSubfiles of the current node this way. Work this manually if the formula isn't clear.

sum += child.numFiles * (names[i].length() + 1) + child.sum
numSubfiles += child.numFiles

The formula for supertree is a lot more complex. This, time, we are using an iterative preorder traversal. At every step, we update the current node's children in the following way. There is a baseline sum, which is simply the curr node's super and subtree sums added together. But, to get the supertree sum for a child, we need to subtract the child's own sum from the baseline, as well as the child folder's name and the ../ contributed by that child folder. The child's numSuperfiles is simply the sum of the curr nodes super and sub files, but subtracting the child's own files. The easiest way to understand is to work some examples on your own.

aSuper = supertreeFDNodes[curr]
aSub = subtreeFDNodes[curr]
baselineSum = aSuper.sum + (3 * aSuper.numFiles) + aSub.sum + (3 * aSub.numFiles)
i : adjList[curr]
	bSub = subtreeFDNodes[i]
	numSuperfiles = aSuper.numFiles + aSub.numFiles - bSub.numFiles
	sum = baselineSum -bSub.sum - (bSub.numFiles * (names[i].length() + 4))
	
The node with the best combined super and sub sum is the best node.

HaybaleFeast
Two pointers problem
Pretty straightforwards when you realize that it's two pointers. Not all problems have to be complex. Maintain 2 pointers, which represent either end of your interval. Loop the right pointer from 0 to N, and increment the left pointer as long as the total flavor is > M. In order to keep track of the maximum spiciness over a given interval, we also maintain a treeset of haybales, sorted by spiciness.

HPS
More dp problems
State is dp[m][k][n] where m is Bessie's current move, k is the number of changes Bessie has made, and n is the number of games played
Transition is straightforwards so it's going to be left as an excercise for the reader.

Lasers
In a somewhat unintuitive turn of events, the fence posts in this problem are actually the edges, while the lines are vertices. We need to realize that the laser will never revisit a given line. There is no situation where something like this is desirable.
      ↑------>|	
      |       |
----->|       ↓--------> Barn
This obviously doesn't accomplish anything. But, you might ask, what if we want to make the laser go backwards?
              ↑------->|
Barn ↑------->|   <----↓
     |
     |
But this is also pointless, because we could've simply done this.
Barn <----↑
          |
          |
Now that we know we only want to travel on a given line a single time, it starts to become clear why the lines are vertices and the fence posts are edges. Now the only challenge is converting the lines from (x = 100000000) or (y = 1295001) into vertex numbers so we can run bfs normally. Use a 2 hashmaps<coord, vertex num> to convert vertical/horizontal lines separately.

LightsOut
Just simulate the problem
First calculate the shortest distance from each point to the exit in O(N) to get the distances in a lit barn. Then, loop through each point as a start point, maintaining a list of possible points as well as distance travelled, and move clockwise until the list is empty or the point has reached the exit. If the list is empty before Bessie reaches the exit, then just add the lit distance from the current point to the exit. This runs in O(N^2). Then just compare the dark distances to the lit distances.

MilkingOrder
Binary Search and Topological Sort
We want the most amount of conditions which can still construct a valid topological sort, and we want the lexicographically smallest topological sort. Because we want to use the first X conditions, we can simply run a binary search on X, and at every step check if a topological sort is possible.

MooCast
Textbook DSU problem
We are asked to find the maximum edge value needed to let us connect all cows. This can be done by looping over all pairs of cows in O(N^2) and generating edges between them. Then, after sorting the edge list from smallest to largest, we loop through all edges, and if they aren't already connected, then we connect them and update the maximum edge value.

MooTube
Another textbook DSU
Sort edges from largest to smallest relevance, and sort queries from largest to smallest threshold. We maintain a DSU, and whenever an edge is valid, we add it. We check the size of the connected component at every query, and that's our answer.

NoCross
Dp problem
I did this iteratively, but recursively might be much more intuitive.
State dp[i][j] is the max number of crosswalks which can be built using i from the left side of the road and j from the right side
Transition is dp[i][j] = max(dp[i - 1][j - 1] + abs(fields[0][i] - fields[1][j]) <= 4 ? 1 : 0, dp[i - 1][j], dp[i][j - 1])
Either we can go to dp[i - 1][j - 1] and then try to add a new crosswalk, or just go to dp[i - 1][j] or dp[j - 1][i] without adding anything.

OutOfSorts
Difficult BIT problem
Bessie's sort algorithm basically drags one non sorted item from left to right, and one from right to left. We create a number that can measure how many values need to move from left to right and vice versa at a given point. If we a draw lines between every position i and i + 1, then measure how many values to the left of that line in the original input are supposed to be to the right of the input, we can get the amount of flow that needs to happen to "sort" the array from the perspective of that line. The answer to the problem is simply just the maximum out of those values, or 1 in the case that the array is already sorted. To calculate that, we use a BIT to calculate the number of items to the left of a given line which should've been there but aren't.
1|8|5|3|2
1|2|3|5|8
 0 1 2 1
 
PalPath
Hard dp
Because palindromes are symmetrical, we know the palindrome's middle needs to be in the middle diagonal (feel free to convince yourself of this on paper). This means that our dp needs to go iteratively outwards from diagonals. Another catch is that one we know which diagonal we are on, we only need to know the row to determine the column position, thus letting us only store one part of the current pos.
Number of palindromes starting at row i and ending at row j is dp[i][j].
for (int i = N - 1; i >= 1; i--) {
long[][] next = new long[N][N];
int j = 0 : N
	int rA = j
	int cA = i - j - 1
	if cA < 0
		break
	k = 0 : N
		rB = k
		cB = 2 * N - i - rB - 1
		if cB >= N || grid[rA][cA] != grid[rB][cB]
			continue
		next[rA][rB] += dp[rA][rB]
		if rA + 1 < N
			next[rA][rB] += dp[rA + 1][rB]
		if rB - 1 >= 0
			next[rA][rB] += dp[rA][rB - 1]
		if rA + 1 < N && rB - 1 >= 0
			next[rA][rB] += dp[rA + 1][rB - 1]
		next[rA][rB] %= MOD
	dp = next

PieForPie
BFS + binary search
This is really just a multi source bfs problem, where we want to know the shortest distance to each of the ending pies. We also need a fast way to find a valid pie to give, which we do with binary search.

Radio
Recursive dp problem
Minimum energy used after f steps from farmer John and c steps from Bessie is dp[f][c]
Transition is pretty straight forwards recursive dp

Shortcut
Dijkstra Problem
If we calculate the distance of all fields to the barn and also the number of cows who pass through each field, then we can calculate the distance saved by each shortcut as (distBefore[i] - T) * numCows[i]. The simplest way to calculate number of cows who pass through each field is to record the parent of each node in the shortest path tree, and then run O(N) backtracking on every node.

SleepySort
BIT problem
First of all, if the last x numbers are in order, then k = n - x. After finding the length of K, we want to figure out the instructions for each cow. The amount of spaces each cow needs to move is the number of cows between it and the start of the sorted sub interval, which can be calculated with index subtraction, and the number of cows who are already in the sorted sub interval and smaller, which is calculated with BIT.

Snakes
Another dp problem
Minimum wasted space with i snakes, j net changes, and with current net size of snakes[k] is dp[i][j][k]
Looping through each snake h and setting current net size as snakes[h], the min amount of wasted space if we don't change net size is dp[n - 1][k][h] + snakes[h] - snakes[n - 1]
If we do change net size, then it's bestVal + snakes[h] - snakes[n - 1] where bestVal is the minimum of dp[n - 1][k - 1][x] and x loops 0 through N

Snowboots
Two pointers problem
Sort the tiles and boots by depth, then loop through the boots from deepest and farthest to shallowest and shortest. At every step, remove tiles until the deepest tile can be stepped in by the current boot. Then, keep track of the "gaps" in the path, by using a separate TreeSet of tiles, which sorts tiles by index. When removing the deepest tile, also remove it from the index sorted list, and then take the ceiling and floor to find the gap size. If the boot can cross the biggest gap, then it can make it all the way.

Split
Line sweep
First realize that there must be some line that is horizontal or vertical that separates the cows optimally. Do a line sweep vertically first, using TreeMaps to keep track of the heights (the width of the enclosures are fixed on the vertical line). Then reflect everything over y = x and run another vertical sweep (the same thing as running a horizontal sweep) and take the best value.

StampPainting
More dp
The last stamp will always leave a consecutive interval of size K. Any other spot can basically have whatever color we want. We can stamp spot 1 with desired color, then stamp spot 2 with the desired color, going all the way down, and then back. This means that there only has to be one interval of K, and the rest of the units can be any color. So we use complementary counting where dp[i] is the number of ways to create a painting with a max consecutive interval of strictly less than K using the first i units. Then, subtract dp[N] from M ^ N for the actual answer.
c = 1 : K
	dp[i] += dp[i - c] * (M - 1)
However, this is O(NK) time, and isn't fast enough for the whole solution. What if we had a separate array sums such that sums[i] = sum of dp[0] through dp[i]? Then we know that sums[i] = sums[i - 1] + dp[i]. We can rewrite dp[i] as (M - 1) * (sums[i] - sums[i - K]). Thus, after substitution we have that sums[i] = sums[i - 1] + (M - 1) * (sums[i] - sums[i - K]). This reduces our algorithm to O(N) time, and the answer now becomes M ^ N - (sums[i] - sums[i - 1]). The last thing to do, is to throw % MOD everywhere, where MOD = 1000000007 so as not to have overflow issues.

Talent
Dp
The minimum weight for i talent is dp[i]
i = 0 : N
	j = maxTalent : talent[i] - 1
		dp[j] = Math.min(dp[j], dp[j - talent[i]] + weights[i])
Pretty standard knapsack

Taming
Another pretty standard dp
Minimum altered logs after i days, last breakout at j, and k total breakouts is dp[i][j][k]
N is less than 100, so the transition is pretty bashy. It's easy enough to figure out on your own.

Teamwork
Even more dp
The max sum of skills of the first i cows is dp[i]
If the last team has a size of x, then dp[i] = dp[i - x] + highestSkillInLastTeam * numCowsInLastTeam

VisitFarmer
Basically just BFS, keep track of how many times Bessie can move before eating again

Walk
There is a hack math solution to solve in O(1), but I'm in USACO because I took Ls in AMC, so we're not doing that. Instead, we use an MST based solution. All we have to do to get the optimal split is to build an MST, and remove the K - 1 largest weights. Kruskal's is too slow, as this is a dense graph, so we use prim's
