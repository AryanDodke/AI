public class Nqueens {
    private int boardSize;
    private int[] queensPositions;
    private boolean[] occupiedColumns;
    private boolean[] occupiedDiagonals1;
    private boolean[] occupiedDiagonals2;

    public Nqueens(int boardSize) {
        this.boardSize = boardSize;
        this.queensPositions = new int[boardSize];
        this.occupiedColumns = new boolean[boardSize];
        this.occupiedDiagonals1 = new boolean[2 * boardSize - 1];
        this.occupiedDiagonals2 = new boolean[2 * boardSize - 1];
    }

    public boolean solve() {
        return placeQueens(0);
    }

    private boolean placeQueens(int row) {
        if (row == boardSize) {
            return true;
        }
        for (int col = 0; col < boardSize; col++) {
            if (!occupiedColumns[col] && !occupiedDiagonals1[row + col] && !occupiedDiagonals2[row - col + boardSize - 1]) {
                queensPositions[row] = col;
                occupiedColumns[col] = occupiedDiagonals1[row + col] = occupiedDiagonals2[row - col + boardSize - 1] = true;
                if (placeQueens(row + 1)) {
                    return true;
                }
                queensPositions[row] = 0;
                occupiedColumns[col] = occupiedDiagonals1[row + col] = occupiedDiagonals2[row - col + boardSize - 1] = false;
            }
        }
        return false;
    }

    public void printSolution() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (queensPositions[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int boardSize = 8;
        Nqueens solver = new Nqueens(boardSize);
        if (solver.solve()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution exists.");
        }
    }
}
