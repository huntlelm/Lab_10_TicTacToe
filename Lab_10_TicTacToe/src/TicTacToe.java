import java.util.Scanner;

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];
    private static Scanner pipe = new Scanner(System.in);
    private static int moveCounter = 0;

    public static void main(String[] args) {
        boolean playAgain = true;

        do {
            // Reset board and move counter for a new game
            clearBoard();
            moveCounter = 0;
            String player = "X"; // X always goes first
            boolean gameOver = false;

            while (!gameOver) {
                display();

                int row = 0;
                int col = 0;
                boolean validMove = false;

                do {
                    System.out.printf("\nPlayer %s's turn.", player);
                    int rawRow = SafeInput.getRangedInt(pipe, "Enter row number", 1, 3);
                    int rawCol = SafeInput.getRangedInt(pipe, "Enter col number", 1, 3);

                    // Convert 1-3 user input to 0-2 array indices
                    row = rawRow - 1;
                    col = rawCol - 1;

                    if (isValidMove(row, col)) {
                        validMove = true;
                    } else {
                        System.out.println("That spot is already taken! Choose an empty space.");
                    }
                } while (!validMove);

                // Record the move on the board
                board[row][col] = player;
                moveCounter++;

                // Check for a win starting from move 5
                if (moveCounter >= 5) {
                    if (isWin(player)) {
                        display();
                        System.out.printf("\nPlayer %s wins!\n", player);
                        gameOver = true;
                        break;
                    }
                }

                // Check for a tie if all 9 moves are made without a winner
                if (moveCounter == 9) {
                    display();
                    System.out.println("\nIt's a tie!");
                    gameOver = true;
                    break;
                }

                // Toggle player (X becomes O, O becomes X)
                player = player.equals("X") ? "O" : "X";
            }

            // Prompt to play again using SafeInput
            playAgain = SafeInput.getYNConfirm(pipe, "Would you like to play again");

        } while (playAgain);

        System.out.println("Thanks for playing Tic Tac Toe!");
    }

    // Clears the board by setting all cells to a space character

    private static void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = " ";
            }
        }
    }

    // Displays the TicTacToe game board in the console

    private static void display() {
        System.out.println("\n    1   2   3");
        for (int r = 0; r < ROWS; r++) {
            System.out.print(" " + (r + 1) + " ");
            for (int c = 0; c < COLS; c++) {
                System.out.print("[" + board[r][c] + "]");
                if (c < COLS - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    // Returns true if the proposed move coordinates point to an empty space
    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    // Checks if the specified player has achieved a win state
    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    // Checks for a row win for the specified player
    private static boolean isRowWin(String player) {
        for (int r = 0; r < ROWS; r++) {
            if (board[r][0].equals(player) && board[r][1].equals(player) && board[r][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    // Checks for a column win for the specified player
    private static boolean isColWin(String player) {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c].equals(player) && board[1][c].equals(player) && board[2][c].equals(player)) {
                return true;
            }
        }
        return false;
    }

    //Checks for a diagonal win for the specified player
    private static boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }
}