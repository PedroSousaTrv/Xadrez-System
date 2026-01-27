package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessmatch) {
        super(board, color);
        this.chessMatch = chessmatch;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat =new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0,0);


        if (getColor() == Color.WHITE){

            //avançar casas
            p.setValues(position.getRow()-1, position.getColumn());
            if(getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow()-2, position.getColumn());
            Position p2 = new Position(position.getRow()-1, position.getColumn());
            if(getBoard().positionExist(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExist(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            //avançar em inimigos
            p.setValues(position.getRow()-1,position.getColumn()-1);
            if(getBoard().positionExist(p) && isThereOpponentInPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow()-1,position.getColumn()+1);
            if(getBoard().positionExist(p) && isThereOpponentInPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;

            }

            //SpecialMove enPassant white;
            if(position.getRow() == 3){
                Position left = new Position(position.getRow(), position.getColumn()-1);
                if(getBoard().positionExist(left) && isThereOpponentInPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable() ){
                    mat[left.getRow() -1 ][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if(getBoard().positionExist(right) && isThereOpponentInPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()){
                    mat[right.getRow()-1][right.getColumn()] = true;
                }


            }

        }

        if (getColor() == Color.BLACK){

            //avançar casas
            p.setValues(position.getRow()+1, position.getColumn());
            if(getBoard().positionExist(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow()+2, position.getColumn());
            Position p2 = new Position(position.getRow()+1, position.getColumn());
            if(getBoard().positionExist(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExist(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            //avançar em inimigos
            p.setValues(position.getRow()+1,position.getColumn()-1);
            if(getBoard().positionExist(p) && isThereOpponentInPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow()+1,position.getColumn()+1);
            if(getBoard().positionExist(p) && isThereOpponentInPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            //SpecialMove enPassant white;
            if(position.getRow() == 4) {
                Position lelf = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExist(lelf) && isThereOpponentInPiece(lelf) && getBoard().piece(lelf) == chessMatch.getEnPassantVulnerable()) {
                    mat[lelf.getRow() + 1][lelf.getColumn()] = true;
                }
                Position rigth = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExist(rigth) && isThereOpponentInPiece(rigth) && getBoard().piece(rigth) == chessMatch.getEnPassantVulnerable()) {
                    mat[rigth.getRow() + 1][rigth.getColumn()] = true;
                }
            }
        }
        return mat;

    }
}
