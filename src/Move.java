public class Move {
    ChessBoard board;
    Square fromHere;
    Square toHere;
    Piece movedPiece;
    Piece capturedPiece;
    boolean moveMadeWhileInCheck;
    int enPassantAvailableAtDepth;
    int depth;

    public Move(ChessBoard board, Square fromHere, Square toHere, Piece movedPiece, boolean moveMadeWhileInCheck/*, Piece capturedPiece*/, int depth)
    {
        //this.board = board;
        this.fromHere = fromHere;
        this.toHere = toHere;
        if(movedPiece == null)
        {
            throw new NullPointerException("you're trying to move a piece from "+fromHere.getSquareRow()+", "+fromHere.getSquareCol()+" to "+toHere.getSquareRow()+", "+toHere.getSquareCol());
        }
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.moveMadeWhileInCheck = moveMadeWhileInCheck;
        if(movedPiece instanceof Pawn && (Math.abs(fromHere.getSquareRow() - toHere.getSquareRow()) == 2))
        {
            enPassantAvailableAtDepth = depth;
        }
        this.depth = depth;
    }

    public Square getFromHere()
    {
        return fromHere;
    }

}
