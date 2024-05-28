import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HillClimbing {

    private static final int N = 3;

    private static final int[][] goalState = {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };

    static class PuzzleState {
        int[][] state;
        int cost;

        PuzzleState(int[][] state, int cost) {
            this.state = state;
            this.cost = cost;
        }
    }

    private static int calculateCost(int[][] state) {
        int cost = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (state[i][j] != goalState[i][j] && state[i][j] != 0) {
                    cost++;
                }
            }
        }
        return cost;
    }

    private static void printState(int[][] state) {
        for (int[] row : state) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[][] generateNeighbor(int[][] state, int row, int col, int newRow, int newCol) {
        int[][] neighbor = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(state[i], 0, neighbor[i], 0, N);
        }
        int temp = neighbor[row][col];
        neighbor[row][col] = neighbor[newRow][newCol];
        neighbor[newRow][newCol] = temp;
        return neighbor;
    }

    private static PuzzleState hillClimbing(int[][] initial, int maxIterations) {
        PuzzleState current = new PuzzleState(initial, calculateCost(initial));
        int iterations = 0;

        System.out.println("Initial State:");
        printState(current.state);
        System.out.println("----------------------------------------");

        while (iterations < maxIterations) {
            int[][] bestNeighbor = null;
            int bestNeighborCost = current.cost;

            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (current.state[row][col] == 0) {
                        int[][] moves = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} }; // Up, Down, Left, Right

                        for (int[] move : moves) {
                            int newRow = row + move[0];
                            int newCol = col + move[1];

                            if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N) {
                                int[][] neighborState = generateNeighbor(current.state, row, col, newRow, newCol);
                                int neighborCost = calculateCost(neighborState);

                                if (neighborCost < bestNeighborCost) {
                                    bestNeighbor = neighborState;
                                    bestNeighborCost = neighborCost;
                                }
                            }
                        }
                    }
                }
            }

            if (bestNeighborCost >= current.cost) {
                break; // No better neighbor found, exit
            }

            current.state = bestNeighbor;
            current.cost = bestNeighborCost;

            iterations++;

            System.out.println("Iteration " + iterations + " - Cost: " + bestNeighborCost);
            printState(current.state);
            System.out.println("----------------------------------------");
        }

        return current;
    }

    public static void main(String[] args) {
        int[][] initialState = {
            {1, 2, 4},
            {8, 0, 5},
            {7, 6, 3}
        };

        System.out.println("Initial State Cost: " + calculateCost(initialState)); // Calculate and print the cost
        System.out.println("----------------------------------------");

        PuzzleState finalPuzzleState = hillClimbing(initialState, 100);

        System.out.println("Final State:");
        printState(finalPuzzleState.state);
        System.out.println("Cost: " + finalPuzzleState.cost);
    }
}