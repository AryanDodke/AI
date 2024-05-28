import java.util.*;

class PuzzleNode implements Comparable<PuzzleNode> {
    int[][] state;
    PuzzleNode parent;
    String move;
    int costFromStart;
    int heuristic;
    int totalCost;

    public PuzzleNode(int[][] state, PuzzleNode parent, String move) {
        this.state = state;
        this.parent = parent;
        this.move = move;
        this.costFromStart = parent == null ? 0 : parent.costFromStart + 1;
        this.heuristic = calculateHeuristic();
        this.totalCost = costFromStart + heuristic;
    }

    private int calculateHeuristic() {
        int totalDistance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = state[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / 3;
                    int targetCol = (value - 1) % 3;
                    totalDistance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return totalDistance;
    }

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(this.totalCost, other.totalCost);
    }
}

public class EightPuzzleSolver1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter initial state:");
        int[][] initialState = getUserInput(scanner);

        System.out.println("Enter goal state:");
        int[][] goalState = getUserInput(scanner);

        PuzzleNode solutionNode = solvePuzzle(initialState, goalState);

        if (solutionNode != null) {
            System.out.println("Path:");
            printSolutionPath(solutionNode);
        } else {
            System.out.println("No solution found");
        }

        scanner.close();
    }

    public static int[][] getUserInput(Scanner scanner) {
        int[][] state = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = scanner.nextInt();
            }
        }
        return state;
    }

    public static void printSolutionPath(PuzzleNode solutionNode) {
        PuzzleNode current = solutionNode;
        while (current != null) {
            System.out.println(current.totalCost + " - " +
                    current.costFromStart + " - " +
                    current.move + " - " +
                    Arrays.deepToString(current.state));
            current = current.parent;
        }
    }

    public static PuzzleNode solvePuzzle(int[][] initial, int[][] goal) {
        PriorityQueue<PuzzleNode> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        PuzzleNode initialNode = new PuzzleNode(initial, null, "");
        openSet.add(initialNode);

        while (!openSet.isEmpty()) {

            PuzzleNode current = openSet.poll();
            closedSet.add(Arrays.deepToString(current.state));

            if (Arrays.deepEquals(current.state, goal)) {
                return current;
            }

            int zeroRow = 0;
            int zeroCol = 0;
            outerLoop: for (zeroRow = 0; zeroRow < 3; zeroRow++) {
                for (zeroCol = 0; zeroCol < 3; zeroCol++) {
                    if (current.state[zeroRow][zeroCol] == 0) {
                        break outerLoop;
                    }
                }
            }

            int[][] possibleMoves = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
            String[] moveNames = { "Up", "Down", "Left", "Right" };

            for (int i = 0; i < possibleMoves.length; i++) {

                int newRow = zeroRow + possibleMoves[i][0];
                int newCol = zeroCol + possibleMoves[i][1];

                if (newRow >= 0 && newRow < 3 &&
                        newCol >= 0 && newCol < 3) {

                    int[][] newState = cloneState(current.state);
                    newState[zeroRow][zeroCol] = newState[newRow][newCol];
                    newState[newRow][newCol] = 0;

                    PuzzleNode child = new PuzzleNode(newState, current, moveNames[i]);

                    if (!closedSet.contains(Arrays.deepToString(newState))) {
                        openSet.add(child);
                    }
                }
            }
        }

        return null; // Placeholder, replace with actual implementation
    }

    public static int[][] cloneState(int[][] state) {
        int[][] clone = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, clone[i], 0, 3);
        }
        return clone;
    }
}
