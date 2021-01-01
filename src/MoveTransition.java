public class MoveTransition {
    private ChessBoard fromBoard;
    private ChessBoard toBoard;
    private Move transitionMove;
    private int depth;

    public MoveTransition(ChessBoard fromBoard, ChessBoard transitionBoard, Move transitionMove, int depth)
    {
        this.fromBoard = fromBoard;
        this.toBoard = transitionBoard;
        this.transitionMove = transitionMove;
        this.depth = depth;
        //System.out.println(transitionMove.fromHere.getSquareRow()+", "+transitionMove.fromHere.getSquareCol());
        //System.out.println(transitionMove.toHere.getSquareRow()+", "+transitionMove.toHere.getSquareCol());
    }

    public ChessBoard getFromBoard() {
        return this.fromBoard;
    }

    public ChessBoard getToBoard() {
        return this.toBoard;
    }

    public Move getTransitionMove() {
        return this.transitionMove;
    }
}
