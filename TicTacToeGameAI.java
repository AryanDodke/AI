import java.util.Scanner;

class TicTacToeAI {
    private char[][] gameBoard;
    private char currentPlayer;
    private String playerName;

    public TicTacToeAI(String playerName) {
        gameBoard = new char[3][3];
        currentPlayer = 'X';
        this.playerName = playerName;
        initializeGameBoard();
    }

    private void initializeGameBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = '-';
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0] == player && gameBoard[i][1] == player && gameBoard[i][2] == player) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (gameBoard[0][i] == player && gameBoard[1][i] == player && gameBoard[2][i] == player) {
                return true;
            }
        }
        if (gameBoard[0][0] == player && gameBoard[1][1] == player && gameBoard[2][2] == player) {
            return true;
        }
        if (gameBoard[0][2] == player && gameBoard[1][1] == player && gameBoard[2][0] == player) {
            return true;
        }
        return false;
    }

    private double euclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private double evaluateEuclidean() {
        double scoreX = 0;
        double scoreO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == 'X') {
                    scoreX += euclideanDistance(i, j, 1, 1); // Center of the board
                } else if (gameBoard[i][j] == 'O') {
                    scoreO += euclideanDistance(i, j, 1, 1); // Center of the board
                }
            }
        }
        return scoreO - scoreX; // We reverse the scores since AI is 'X'
    }

    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin('X')) {
            return 10 - depth;
        } else if (checkWin('O')) {
            return depth - 10;
        } else if (isGameBoardFull()) {
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard[i][j] == '-') {
                        gameBoard[i][j] = 'X';
                        int score = minimax(depth + 1, false);
                        gameBoard[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard[i][j] == '-') {
                        gameBoard[i][j] = 'O';
                        int score = minimax(depth + 1, true);
                        gameBoard[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        if (bestScore == Integer.MIN_VALUE || bestScore == Integer.MAX_VALUE) {
            return 0;
        }
        return bestScore;
    }

    private void makeMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == '-') {
                    gameBoard[i][j] = 'X';
                    int score = minimax(0, false);
                    gameBoard[i][j] = '-';
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
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

            System.out.println("Your turn (row [0-2] and column [0-2]):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row > 2 || col < 0 || col > 2 || gameBoard[row][col] != '-') {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            gameBoard[row][col] = 'O';
            displayBoard();
            double aiScore = evaluateEuclidean();
            System.out.println("AI's Heuristic Score: " + aiScore);

            if (!checkWin('O') && !isGameBoardFull()) {
                makeMove();
                System.out.println("AI's move:");
                displayBoard();
            }
        }
    }

    private void displayBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(gameBoard[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
}

public class TicTacToeGameAI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();

        TicTacToeAI game = new TicTacToeAI(playerName);
        game.displayPlayerName();
        game.playGame();
    }
}
