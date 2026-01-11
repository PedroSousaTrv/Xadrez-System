package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;

    private List <Piece>piecesOntheBoard = new ArrayList<>();
    private List <Piece>capturedPieces = new ArrayList<>();

    public ChessMatch() {
        turn = 1;
        currentPlayer = Color.WHITE;
        board = new Board(8,8);
        initialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean getCheck(){
        return check;
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

        for(int i=0; i<board.getRows(); i++){
            for (int j=0;j<board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }

    private Color oponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color){
        List<Piece> list = piecesOntheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).toList();

        for (Piece p : list){
            if(p instanceof King){
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("There is no "+color+" on the board");
    }

    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOntheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == oponent(color)).toList();

        for (Piece p : opponentPieces){
            boolean [][] mat = p.possibleMoves();

            if(mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOntheBoard.add(piece);
    }

    public boolean[][] possibleMoves(ChessPosition sourcePositon){
        Position position = sourcePositon.toPosition();
        validateSoucePosition(position);

        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition,ChessPosition targetPosition){

        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSoucePosition(source);
        validateTargetPosition(source,target);

        Piece capturedPiece = makeMove(source, target);

        if(testCheck(currentPlayer)){
            undoMove(source,target,capturedPiece);
            throw new ChessException("You can't put yourself in check.");
        }

        if(testCheck(oponent(currentPlayer))){
            check = true;
        }else{
            check = false;
        }

        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source,Position target){

        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p,target);

        if( capturedPieces != null ){
            piecesOntheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece captured){

        Piece p = board.removePiece(target);
        board.placePiece(p,source);

        if(captured != null){
            board.placePiece(captured,target);
            capturedPieces.remove(captured);
            piecesOntheBoard.add(captured);
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private void validateSoucePosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("The chosen piece isn't yours");
        }
        if(!board.piece(position).isThereAnyPossibleMoves()){
            throw new ChessException("There is no possible moves for the chose piece.");
        }
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
