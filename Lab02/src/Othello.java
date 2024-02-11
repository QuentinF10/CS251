/**
 * Lab02 Othello
 * Course : CS 251
 * Name: Quentin FAYE
 */

import cs251.lab2.*;

import static cs251.lab2.OthelloInterface.Piece.DARK;
import static cs251.lab2.OthelloInterface.Piece.LIGHT;


public class Othello implements OthelloInterface {

    //boolean to know if the computer is enabled or not
    private static boolean computerEnabled = false;

    // Represents the game board
    private OthelloInterface.Piece[][] board;

    // Dark player starts first
    private Piece currentPlayer = DARK;

    @Override
    public int getSize() {
        return DEFAULT_SIZE;
    }

    @Override
    public void initGame() {
        // 8x8 game board
        board = new Piece[DEFAULT_SIZE][DEFAULT_SIZE];

        currentPlayer = DARK;

        //loop to reset the board
        for (int row = 0; row < DEFAULT_SIZE; row++) {
            for (int col = 0; col < DEFAULT_SIZE; col++) {
                board[row][col] = Piece.EMPTY;
            }

            //initialization of the board with 2 black pieces and 2 whites pieces
            int center = DEFAULT_SIZE / 2;
            board[center - 1][center - 1] = LIGHT;
            board[center - 1][center] = DARK;
            board[center][center - 1] = DARK;
            board[center][center] = LIGHT;
        }
    }

    @Override
    public String getBoardString() {
        /** initializing the sb variable to hold the return value, even though you said in lecture there may be others way more convenient,
         * I found it easier to handle it with a StringBuilder
         */

        StringBuilder sb = new StringBuilder();

        //iterate through the board, using StringBuilder
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                sb.append(board[i][j].toChar());

                if (j == DEFAULT_SIZE - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public Piece getCurrentTurn() {
        //get current Player
        return currentPlayer;
    }

    private boolean canAnyPlayerMakeLegalMove() {
        //iterating the board
        for (int row = 0; row < DEFAULT_SIZE; row++) {
            for (int col = 0; col < DEFAULT_SIZE; col++) {
                if (isLegal(row, col)) {
                    // A legal move is found
                    return true;
                }
            }
        }
        // No legal moves found for either player
        return false;
    }

    private boolean isGameOver() {
        // Check for condition : No Legal Moves for either player
        return !canAnyPlayerMakeLegalMove();
    }

    public Result determineGameResult() {
        int darkCount = 0;
        int lightCount = 0;

        // Count the number of DARK and LIGHT pieces on the board
        for (int row = 0; row < DEFAULT_SIZE; row++) {
            for (int col = 0; col < DEFAULT_SIZE; col++) {
                if (board[row][col] == DARK) {
                    darkCount++;
                } else if (board[row][col] == LIGHT) {
                    lightCount++;
                }
            }
        }

        // Determine the game result based on piece counts
        if (darkCount > lightCount) {
            return Result.DARK_WINS;
        } else if (lightCount > darkCount) {
            return Result.LIGHT_WINS;
        } else {
            // Check if the game is over due to no legal moves for both players
            if (!canAnyPlayerMakeLegalMove()) {
                return Result.DRAW;
            }
            return Result.GAME_NOT_OVER; // Game is not over
        }
    }


    @Override
    public Result handleClickAt(int row, int col) {
        // Check if the move is legal
        if (isLegal(row, col)) {
            // Place the current player's piece on the board at the specified row and column
            board[row][col] = currentPlayer;

            // Function to capture opponent's pieces
            captureOpponentPieces(row, col);

            // Switch to the next player's turn
            if (currentPlayer == DARK) {
                currentPlayer = LIGHT;
                // Make a move for the computer player (if enabled)
                if (computerEnabled) {
                    makeComputerMove();
                    // Set currentPlayer back to DARK after the computer's move
                    currentPlayer = DARK;
                }
            } else {
                currentPlayer = DARK;
            }

            // Check if the game is over
            if (isGameOver()) {
                // Determine the winner or if it's a draw
                return determineGameResult();
            } else {
                return Result.GAME_NOT_OVER;
            }
        } else {
            // The move is not legal
            return null;
        }
    }


    private void makeComputerMove() {
        // Iterate through the board to find a legal move
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                if (isLegal(row, col)) {
                    // Make the move for the computer player
                    board[row][col] = currentPlayer;

                    // Flip opponent's pieces if necessary
                    captureOpponentPieces(row, col);

                    // Exit, as the computer has made its move
                    return;
                }
            }
        }
    }

    private void captureOpponentPieces(int row, int col) {
        // Define the opponent's piece
        Piece opponentPiece = (currentPlayer == DARK) ? LIGHT : DARK;

        // Capture pieces in all eight possible directions
        captureInDirection(row, col, -1, -1, opponentPiece); // Up-left
        captureInDirection(row, col, -1, 0, opponentPiece);  // Up
        captureInDirection(row, col, -1, 1, opponentPiece);  // Up-right
        captureInDirection(row, col, 0, -1, opponentPiece);  // Left
        captureInDirection(row, col, 0, 1, opponentPiece);   // Right
        captureInDirection(row, col, 1, -1, opponentPiece);  // Down-left
        captureInDirection(row, col, 1, 0, opponentPiece);   // Down
        captureInDirection(row, col, 1, 1, opponentPiece);   // Down-right
    }

    private void captureInDirection(int row, int col, int deltaRow, int deltaCol, Piece opponentPiece) {
        int newRow = row + deltaRow;
        int newCol = col + deltaCol;

        // Check if the new position is within bounds and contains an opponent's piece
        if (isValidPosition(newRow, newCol) && board[newRow][newCol] == opponentPiece) {
            while (isValidPosition(newRow, newCol) && board[newRow][newCol] == opponentPiece) {
                newRow += deltaRow;
                newCol += deltaCol;
            }

            // Check if the sequence is bounded by the current player's piece
            if (isValidPosition(newRow, newCol) && board[newRow][newCol] == currentPlayer) {
                // Capture the opponent's pieces in between
                newRow = row + deltaRow;
                newCol = col + deltaCol;
                while (board[newRow][newCol] == opponentPiece) {
                    board[newRow][newCol] = currentPlayer;
                    newRow += deltaRow;
                    newCol += deltaCol;
                }
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < DEFAULT_SIZE && col >= 0 && col < DEFAULT_SIZE;
    }

    @Override
    public boolean isLegal(int row, int col) {
        // Check if the specified cell is empty
        if (board[row][col] != Piece.EMPTY) {
            return false;
        }

        // Define the opponent's piece
        Piece opponentPiece = (currentPlayer == Piece.DARK) ? LIGHT : Piece.DARK;

        // Check horizontally, vertically, and diagonally for a legal move
        boolean isLegalMove = false;

        // Check horizontally (left)
        for (int i = col - 1; i >= 0; i--) {
            if (board[row][i] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[row][i] == currentPlayer && i != col - 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        // Check horizontally (right)
        for (int i = col + 1; i < DEFAULT_SIZE; i++) {
            if (board[row][i] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[row][i] == currentPlayer && i != col + 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        // Check vertically (up)
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[i][col] == currentPlayer && i != row - 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        // Check vertically (down)
        for (int i = row + 1; i < DEFAULT_SIZE; i++) {
            if (board[i][col] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[i][col] == currentPlayer && i != row + 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        // Check diagonally (up-left)
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[i][j] == currentPlayer && i != row - 1 && j != col - 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        // Check diagonally (up-right)
        for (int i = row - 1, j = col + 1; i >= 0 && j < DEFAULT_SIZE; i--, j++) {
            if (board[i][j] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[i][j] == currentPlayer && i != row - 1 && j != col + 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        // Check diagonally (down-left)
        for (int i = row + 1, j = col - 1; i < DEFAULT_SIZE && j >= 0; i++, j--) {
            if (board[i][j] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[i][j] == currentPlayer && i != row + 1 && j != col - 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        // Check diagonally (down-right)
        for (int i = row + 1, j = col + 1; i < DEFAULT_SIZE && j < DEFAULT_SIZE; i++, j++) {
            if (board[i][j] == opponentPiece) {
                // Continue checking in the same direction
                continue;
            } else if (board[i][j] == currentPlayer && i != row + 1 && j != col + 1) {
                // Legal move if there are opponent pieces in between
                isLegalMove = true;
            }
            break;
        }

        return isLegalMove;

    }

    @Override
    public void initComputerPlayer(String opponent) {
        if (opponent.equalsIgnoreCase("COMPUTER")) {
            // Enable computer player
            computerEnabled = true;
        } else if (opponent.equalsIgnoreCase("NONE")) {
            // Disable computer player
            computerEnabled = false;
        }
    }

    public static void main(String[] args) {
        Othello game = new Othello();
        if (args.length > 0) {
            // Set the computer player based on command-line argument
            game.initComputerPlayer(args[0]);
            OthelloGUI.showGUI(game);
        } else {
            System.err.println("Expected one argument : NONE or COMPUTER");
        }


    }
};
