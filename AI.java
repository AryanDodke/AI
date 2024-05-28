import java.util.InputMismatchException;
import java.util.Scanner;

public class AI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();

        TicTacToeAI game = new TicTacToeAI(playerName);
        game.displayPlayerName();
        game.playGame();
        scanner.close();
    }
}

class TicTacToeAI {
    private char[][] gameBoard; 
    private String playerName;

    public TicTacToeAI(String playerName) {
        gameBoard = new char[3][3];
        this.playerName = playerName;
        initializeGameBoard();
    }

    private void initializeGameBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col] = '-';
            }
        }
    }
    public String getPlayerName() {
        return playerName;
    }
    public void displayPlayerName() {
        System.out.println("Player's Name: " + playerName);
    }

    private boolean isGameBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin(char player) {
        for (int row = 0; row < 3; row++) { // check rows - horizontal
            if (gameBoard[row][0] == player && gameBoard[row][1] == player && gameBoard[row][2] == player) {
                return true;
            }
        }
        for (int col = 0; col < 3; col++) { // check columns - vertical
            if (gameBoard[0][col] == player && gameBoard[1][col] == player && gameBoard[2][col] == player) {
                return true;
            }
        }
        if (gameBoard[0][0] == player && gameBoard[1][1] == player && gameBoard[2][2] == player) {
            return true; // check diagonal - top left to bottom right
        }
        if (gameBoard[0][2] == player && gameBoard[1][1] == player && gameBoard[2][0] == player) {
            return true;        // check diagonal - top right to bottom left
        }
        return false;
    }

    // minimax value
    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin('X')) {
            return 10 - depth; // Return Positive Score
        } else if (checkWin('O')) {
            return depth - 10; // Return Negative Score
        } else if (isGameBoardFull()) {
            return 0; // Tie
        }

        int bestScore;
        if (isMaximizing) { 
            bestScore = Integer.MIN_VALUE; 

            // check for first empty slot
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (gameBoard[row][col] == '-') {
                        gameBoard[row][col] = 'X';
                        int score = minimax(depth + 1, false); 
                        gameBoard[row][col] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else { // user's turn
            bestScore = Integer.MAX_VALUE; // highest
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (gameBoard[row][col] == '-') {
                        gameBoard[row][col] = 'O';
                        int score = minimax(depth + 1, true); 
                        gameBoard[row][col] = '-';
                        // set bestScore to minimum
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    private void makeMove() {
        // Default Values
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == '-') {
                    gameBoard[row][col] = 'X';
                    int score = minimax(0, false);
                    gameBoard[row][col] = '-';

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }
        gameBoard[bestRow][bestCol] = 'X';
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe - You are 'O', and I am 'X'");
        System.out.println("Here's the initial empty board:");
        displayBoard();

        while (true) {
            if (checkWin('X')) {
                System.out.println("AI won. Better luck next time!");
                break;
            } else if (checkWin('O')) {
                System.out.println("Congratulations, " + getPlayerName() + "! You won!");
                break;
            } else if (isGameBoardFull()) {
                System.out.println("It's a draw! Good game!");
                break;
            }

            try {
            // 2D Input for 2D Array for User's move
            System.out.println("Your turn (row [0-2] and column [0-2]):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // Check if input is valid i.e. in boundaries + empty
            if (row < 0 || row > 2 || col < 0 || col > 2 || gameBoard[row][col] != '-'||!isNumeric(gameBoard[row][col])) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            gameBoard[row][col] = 'O';     
            displayBoard();

            if (!checkWin('O') && !isGameBoardFull()) {
                makeMove(); // AI's move
                System.out.println("AI's move:");
                displayBoard();
            }
            }   catch (InputMismatchException e) {
                System.out.println("Invalid input format! Please enter numeric values.");
                scanner.nextLine();
            }
        }
    }
    private static boolean isNumeric(char c) {
        return !(c >= '0' && c <= '9');
    }
    private void displayBoard() {
        System.out.println("-------------");
        for (int row = 0; row < 3; row++) {
            System.out.print("| ");
            for (int col = 0; col < 3; col++) {
                System.out.print(gameBoard[row][col] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
}