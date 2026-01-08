package boardGame;

import java.security.PublicKey;

public abstract class Piece {

    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMoves(){
        boolean[][] matx = possibleMoves();
        for (int i=0; i<matx.length; i++){
            for (int j=0; j< matx.length; j++){
                if(matx[i][j]){
                    return true;
                }
            }
        }
        return false;
    }

}
