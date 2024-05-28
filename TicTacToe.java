import java.util.*;

public class TicTacToe {
    
    static Scanner input = new Scanner(System.in);

    public static void displayBoard(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printAvailable(String[][] board) {
        System.out.print("Available: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("_")) {
                    System.out.print("[" + i + "," + j + "] ");
                }
            }
        }
        System.out.println();
    }

    public static boolean computerMove(String[][] board, String symbol) {
        if (board[1][1].equals("_")) {
            board[1][1] = symbol;
            return true;
        }

        int[] []cornerMoves = { { 0, 0 }, { 0, 2 }, { 2, 0 }, { 2, 2 } };
        for (int[] move : cornerMoves) 
        {
            int row = move[0];
            int col = move[1];
            if (board[row][col].equals("_")) {
                board[row][col] = symbol;
                return true;
            }
        }

        String playerSymbol = (symbol.equals("X")) ? "O" : "X";
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (board[i][0].equals(playerSymbol) && board[i][1].equals(playerSymbol) && board[i][2].equals("_")) {
                board[i][2] = symbol;
                return true;
            }

            // Check columns
            if (board[0][i].equals(playerSymbol) && board[1][i].equals(playerSymbol) && board[2][i].equals("_")) {
                board[2][i] = symbol;
                return true;
            }
        }

        // check diagonals
        if (board[0][0].equals(playerSymbol) && board[1][1].equals(playerSymbol) && board[2][2].equals("_")) {
            board[2][2] = symbol;
            return true;
        }
        if (board[0][2].equals(playerSymbol) && board[1][1].equals(playerSymbol) && board[2][0].equals("_")) {
            board[2][0] = symbol;
            return true;
        }

        // take any available side square
        int[] [] sideMoves = { { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 } };
        for (int[] move : sideMoves) {
            int row = move[0];
            int col = move[1];
            if (board[row][col].equals("_")) {
                board[row][col] = symbol;
                return true;
            }
        }
        
        return false; 
    }

    public static String checkWin(String[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals("_")) {
                return board[i][0];
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals("_")) {
                return board[0][i];
            }
        }

        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals("_")) {
            return board[0][0];
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals("_")) {
            return board[0][2];
        }

        // Check for a draw
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("_")) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw) {
            return "Draw";
        }

        // No winner yet
        return "_";
    }

    public static void main(String[] args) {

        String[][] board = { { "_", "_", "_" }, { "_", "_", "_" }, { "_", "_", "_" } };
        String player = "X";
        String computer = "O";

        while (true) {

            displayBoard(board);
            printAvailable(board);

            System.out.print("Enter your move (row and column, e.g., 0 0): ");
            int playerRow = input.nextInt();
            int playerCol = input.nextInt();

            if (board[playerRow][playerCol].equals("_")) {
                board[playerRow][playerCol] = player;
            } else {
                System.out.println("Invalid move. The chosen cell is not available. Try again.");
                continue; 
            }

            String winner = checkWin(board);
            if (winner.equals(player)) {
                displayBoard(board);
                System.out.println("Congratulations! You win!");
                break; 
            } else if (winner.equals("Draw")) {
                displayBoard(board);
                System.out.println("It's a draw!");
                break;
            }

            if (!computerMove(board, computer)) {
                displayBoard(board);
                System.out.println("It's a draw!");
                break;
            }

            // Check for a win
            winner = checkWin(board);
            if (winner.equals(computer)) {
                displayBoard(board);
                System.out.println("Computer wins. Better luck next time!");
                break; 
            }
        }
    }
}
