package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPieceMoveCalculator moves = switch(getPieceType()) {
            case QUEEN -> new QueenMoveCalculator();
            case BISHOP -> new BishopMoveCalculator();
            case KING -> new KingMoveCalculator();
            case ROOK -> new RookMoveCalculator();
            case PAWN -> new PawnMoveCalculator();
            case KNIGHT -> new KnightMoveCalculator();
            default -> throw new RuntimeException("invalid type in pieceMoves");
        };

        return moves.pieceMoves(board, myPosition);
    }


    public interface ChessPieceMoveCalculator {
        /**
         * calculates all possible moves for a chess piece
         *
         * @param board The current chessboard
         * @param position The position of the piece
         * @return A collection of valid moves for the piece
         */
        Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
    }

    public static class RookMoveCalculator implements ChessPieceMoveCalculator {
        @Override
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
            Collection<ChessMove> moves = new ArrayList<>();
            int[][] rookMoveDirections = {
                    {1, 0}, {-1, 0}, {0, 1}, {0, -1}
            };
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            for (int[] direction : rookMoveDirections) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row,col);
                while (board.isPositionValid(newPosition) && !board.isPositionOccupiedByFriendly(newPosition, myPosition)) {
                    if (board.isPositionOccupiedByEnemy(newPosition, myPosition)) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    row += direction[0];
                    col += direction[1];
                    newPosition = new ChessPosition(row, col);
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
            }
            return moves;
        }
    }

    public static class BishopMoveCalculator implements ChessPieceMoveCalculator {
        @Override
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
            Collection<ChessMove> moves = new ArrayList<>();
            int[][] bishopMoveDirections = {
                    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
            };
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            for (int[] direction : bishopMoveDirections) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row,col);
                while (board.isPositionValid(newPosition) && !board.isPositionOccupiedByFriendly(newPosition, myPosition)) {
                    if (board.isPositionOccupiedByEnemy(newPosition, myPosition)) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    row += direction[0];
                    col += direction[1];
                    newPosition = new ChessPosition(row,col);
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
            }
            return moves;
        }
    }


    public static class QueenMoveCalculator implements ChessPieceMoveCalculator {
        @Override
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
            Collection<ChessMove> moves = new ArrayList<>();
            int[][] queenMoveDirections = {
                    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, //bishop like moves
                    {1, 0}, {-1, 0}, {0, 1}, {0, -1}  //rook like moves
            };
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            for (int[] direction : queenMoveDirections) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row,col);
                while (board.isPositionValid(newPosition) && !board.isPositionOccupiedByFriendly(newPosition, myPosition)) {
                    if (board.isPositionOccupiedByEnemy(newPosition, myPosition)) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    row += direction[0];
                    col += direction[1];
                    newPosition = new ChessPosition(row,col);
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
            }
            return moves;
        }
    }


    public static class KnightMoveCalculator implements ChessPieceMoveCalculator {
        @Override
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
            Collection<ChessMove> moves = new ArrayList<>();
            int[][] bishopMoveDirections = {
                    {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, //forward moves
                    {1, 2}, {1, -2}, {-1, 2}, {-1, -2}  //back moves
            };
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            for (int[] direction : bishopMoveDirections) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row,col);
                if (board.isPositionValid(newPosition) && !board.isPositionOccupiedByFriendly(newPosition, myPosition)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
            }
            return moves;
        }
    }


    public static class KingMoveCalculator implements ChessPieceMoveCalculator {
        @Override
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
            Collection<ChessMove> moves = new ArrayList<>();
            int[][] kingMoveDirections = {
                    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, //bishop like moves
                    {1, 0}, {-1, 0}, {0, 1}, {0, -1}  //rook like moves
            };
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            for (int[] direction : kingMoveDirections) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row,col);
                if (board.isPositionValid(newPosition) && !board.isPositionOccupiedByFriendly(newPosition, myPosition)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
            }
            return moves;
        }
    }



    public static class PawnMoveCalculator implements ChessPieceMoveCalculator {
        @Override
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
            Collection<ChessMove> moves = new ArrayList<>();
            int[][] pawnMoves = {
                    {1, 0},
                    {-1, 0}
            };
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            for (int[] direction : pawnMoves) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row, col);
                // if (pieceColor == WHITE) {
                //        if front blocked cant move
                //        if
                //
                //
                //

                //k
                    // if (board.isPositionOccupiedByEnemyPiece) {

                //          new chesposition row, col+1
                //          new chessposition row, col -1
                //          moves.add( direction[0][1]+1 or direction[0][1]-1)
                //     }
                // }
                // if (pieceColor == black) then direction[1][2]
                //      if (board.isPositionOccupiedByEnemyPiece) {
                //          moves.add( direction[1][2]+1 or direction[1][2]-1 )
                //      }
                // }
                if (board.isPositionValid(newPosition) && !board.isPositionOccupiedByFriendly(newPosition, myPosition)) {
                    if (board.isPositionOccupiedByEnemy(newPosition, myPosition)) {

                        moves.add(new ChessMove(myPosition, newPosition, null));


                    }
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
                /// elif (board.isPositionValid(newPosition) && !board.isPositionOccupiedByFriendly(newPosition, myPosition))
            }
            return moves;
        }
    }


//    public static class QueenMoveCalculator implements ChessPieceMoveCalculator {
//
//        @Override
//        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
//            Collection<ChessMove> moves = new ArrayList<>();
//            //Queen combines bishop and rook moves
//            addSlidingMoves(board, position, moves, new int[][]{
//                    {1, 0}, {0, 1}, {-1, 0}, {0, -1}, //rook moves
//                    {1, 1}, {1, -1}, {-1, 1}, {-1, -1} //Bishop moves
//            });
//            return moves;
//        }
//        private void addSlidingMoves(ChessBoard board, ChessPosition position, Collection<ChessMove> moves, int[][] directions) {
//            for (int[] direction : directions) {
//                ChessPosition newPosition = position.offset(direction[0], direction[1]);
//                while (board.isPositionValid(newPosition)) {
//                    if (board.isPositionOccupied(newPosition)) {
//                        if(!board.isPositionOccupiedByFriendly(newPosition, position)) {
//                            moves.add(new ChessMove(position, newPosition, null));
//                        }
//                        break; // stop sliding in this direction
//                    }
//                    moves.add(new ChessMove(position, newPosition, null));
//                    newPosition = newPosition.offset(direction[0], direction[1]);
//                }
//            }
//        }
//    }

//    public static class KingMoveCalculator implements ChessPieceMoveCalculator {
//        @Override
//        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
//            return List.of();
//        }
//    }

//    public static class KnightMoveCalculator implements ChessPieceMoveCalculator {
//
//        @Override
//        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {}
//    }




}
