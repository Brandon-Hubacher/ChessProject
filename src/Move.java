public class Move {
    ChessBoard board;
    Square fromHere;
    Square toHere;
    Piece movedPiece;
    Piece capturedPiece;
    boolean moveMadeWhileInCheck;
    int enPassantAvailableAtDepth;
    int depth;
    boolean isEnPassantMove;
    boolean pawnPromotion;
    Piece promotionPiece;

    /*
    public Move()
    {
        this.fromHere = null;
        this.toHere = null;
        isEnPassantMove = false;
    }

     */

    public Move(ChessBoard board, Square fromHere, Square toHere, Piece movedPiece, boolean moveMadeWhileInCheck, int depth, boolean isEnPassantMove, boolean pawnPromotion, Piece promotionPiece)
    {
        this.fromHere = fromHere;
        this.toHere = toHere;
        if(movedPiece == null)
        {
            throw new NullPointerException("you're trying to move a piece from "+fromHere.getSquareRow()+", "+fromHere.getSquareCol()+" to "+toHere.getSquareRow()+", "+toHere.getSquareCol());
        }
        this.movedPiece = movedPiece;
        if(toHere.containsPiece())
        {
            this.capturedPiece = toHere.getPiece();
        }
        this.moveMadeWhileInCheck = moveMadeWhileInCheck;
        if(movedPiece instanceof Pawn && (Math.abs(fromHere.getSquareRow() - toHere.getSquareRow()) == 2))
        {
            enPassantAvailableAtDepth = depth;
        }
        this.depth = depth;
        this.isEnPassantMove = isEnPassantMove;
        this.pawnPromotion = pawnPromotion;
        this.promotionPiece = promotionPiece;
    }

    public Piece getPromotionPiece()
    {
        return promotionPiece;
    }

    public Square getFromHere()
    {
        return fromHere;
    }

}
