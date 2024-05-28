import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Nqueens1 {

    private int N; // Size of the chessboard
    private List<List<Integer>> solutions;

    public Nqueens1(int N) {
        this.N = N;
        this.solutions = new ArrayList<>();
    }

    // Check if placing a queen at position (row, col) is safe
    private boolean isSafe(List<Integer> board, int row, int col) {
        // Check if there is a queen in the same column
        for (int i = 0; i < row; i++) {
            if (board.get(i) == col || Math.abs(board.get(i) - col) == Math.abs(i - row))
                return false;
        }
        return true;
    }

    // Recursive function to find all solutions
    private void solveNQueensUtil(List<Integer> board, int row) {
        if (row == N) {
            solutions.add(new ArrayList<>(board));
            return;
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col)) {
                board.set(row, col);
                solveNQueensUtil(board, row + 1);
                board.set(row, -1); // Backtrack
            }
        }
    }

    // Solve N-Queens problem and return all solutions
    public List<List<Integer>> solveNQueens() {
        List<Integer> board = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            board.add(-1);
        }

        solveNQueensUtil(board, 0);
        return solutions;
    }

    // Print a solution
    private void printSolution(List<Integer> solution) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (solution.get(row) == col)
                    System.out.print("Q ");
                else
                    System.out.print("_ ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of the chessboard: ");
        int N = scanner.nextInt();
        scanner.close();

        Nqueens1 nQueensCSP = new Nqueens1(N);
        List<List<Integer>> solutions = nQueensCSP.solveNQueens();
        
        System.out.println("Number of solutions: " + solutions.size());
        for (List<Integer> solution : solutions) {
            nQueensCSP.printSolution(solution);
        }
    }
}