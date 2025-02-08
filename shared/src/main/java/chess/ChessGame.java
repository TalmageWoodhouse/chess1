package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor turn;

    public ChessGame() {
        this.turn = TeamColor.WHITE;
        this.board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }
    /**
     * Gets valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) return null;
        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        //make copy of the board
        ChessBoard boardCopy = board.boardCopy();
        // iterate through all possible moves
        for (ChessMove move : moves) {
                //make move in the copy
                boardCopy.addPiece(move.getStartPosition(), null);
                boardCopy.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                //check copy if != isincheck
                if (!isInCheck(piece.getTeamColor())) {
                    validMoves.add(move);
                }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // if valid move

        //if move gets promotion piece
        if (move.getPromotionPiece() != null){
            board.addPiece(move.getEndPosition(), new ChessPiece(getTeamTurn(), move.getPromotionPiece()));
        }
        else{
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        }
        board.addPiece(move.getStartPosition(), null);

        //ensure that its the turn of piece being moved
        //do move
        // change turns
    }

    private ChessPosition findKing(TeamColor teamColor) {
        for (int row = 1; row < 9; row++) {
            for (int col= 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);

                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    return pos;
                }
            }
        }
        throw new IllegalStateException("king not found for team: " + teamColor);
    }
    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //locate the king of the given team color
        ChessPosition kingPosition = findKing(teamColor);

        //check if opponents pieces can attack king
        for (int row= 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);

                //skip empty and friendly squares
                if (piece == null || piece.getTeamColor() == teamColor) {
                    continue;
                }
                // if this opponent piece can move to the kings position return true
                Collection<ChessMove> enemyMoves = piece.pieceMoves(board, pos);
                for (ChessMove move : enemyMoves){
                    if (move.getEndPosition().equals(kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //locate king
        ChessPosition kingPosition = findKing(teamColor);

        //iterate over opponents pieces
        for (int row= 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);

                //skip empty and friendly squares
                if (piece == null || piece.getTeamColor() == teamColor) {
                    continue;
                }

                Collection<ChessMove> kingMoves = piece.pieceMoves(board, kingPosition);
                Collection<ChessMove> enemyPieceMoves = piece.pieceMoves(board, pos);
                // if in checkmate & no valid moves
                if (isInCheck(teamColor) && validMoves(pos) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //locate king
        ChessPosition kingPosition = findKing(teamColor);

        //iterate over opponents pieces
        for (int row= 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);

                //skip empty and friendly squares
                if (piece == null || piece.getTeamColor() == teamColor) {
                    continue;
                }

                Collection<ChessMove> kingMoves = piece.pieceMoves(board, kingPosition);
                Collection<ChessMove> enemyPieceMoves = piece.pieceMoves(board, pos);
                // if in checkmate & no valid moves
                if (!isInCheck(teamColor) && validMoves(pos) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


}
