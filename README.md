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

Dream

Feast

FileDirectory

HPS

MooCast

MooTube

PieForPie

Radio

Split

Talent

VisitFarmer
