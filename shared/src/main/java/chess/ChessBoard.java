package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    private final ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard() {
        
    }
    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }


    /**
     * Checks if the given position is within the boundaries of the chessboard.
     *
     * @param position The position to check
     * @return true if the position is valid; false otherwise.
     */
    public boolean isPositionValid(ChessPosition position) {
        if (position == null) {
            return false; // null position would be off the board
        }
        int row = position.getRow();
        int column = position.getColumn();

        // Check if the row and column are within the valid range
        return row >= 1 && row <= 8 && column >= 1 && column <= 8;
    }

    /**
     * Checks if the given position is occupied by a friendly piece.
     *
     * @param position The position to check
     * @param myPosition The position of the piece calling this method
     * @return true if the position is occupied by the friendly piece; else false.
     */
    public boolean isPositionOccupiedByFriendly(ChessPosition position, ChessPosition myPosition) {
        if (position == null || myPosition == null) {
            return false; //invalid input
        }
        ChessPiece myPiece = getPiece(myPosition);
        ChessPiece targetPiece = getPiece(position);
        // Checks edge case for myPiece and if there is no piece at target position.
        if (myPiece == null || targetPiece == null) {
            return false;
        }
        return myPiece.getTeamColor() == targetPiece.getTeamColor();
    }

    /**
     * Checks if the given position is occupied by a friendly piece.
     *
     * @param position The position to check
     * @return true if the position is occupied by the friendly piece; else false.
     */
    public boolean isPositionOccupiedByEnemy(ChessPosition position, ChessPosition myPosition) {
        if (position == null || myPosition == null) {
            return false; //invalid input
        }
        ChessPiece myPiece = getPiece(myPosition);
        ChessPiece targetPiece = getPiece(position);
        // Checks edge case for myPiece and if there is no piece at target position.
        if (myPiece == null || targetPiece == null) {
            return false;
        }
        return myPiece.getTeamColor() != targetPiece.getTeamColor();
    }
}
