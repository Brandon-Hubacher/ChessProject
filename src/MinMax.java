import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class MinMax implements Serializable {
    private int evaluation;
    public static int counter = 0;
    public static Move bestMove = null;
    public ArrayList<Move> actualMoves = new ArrayList<>();
    boolean actualMovesDone = false;

    // TODO: save original board arrangement somehow
    public MinMax()
    {
        evaluation = 0;
    }

    public String boardToString(ChessBoard b)
    {
        String output = "";
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                Square s = b.chessBoard[i][z];
                String pieceStr = (s.containsPiece()) ? s.getPiece().toString() : "--";
                output += pieceStr+", ";
                if(z == 7)
                {
                    output += "\n";
                }
            }
        }
        return output;
    }

    public Move miniMax(ChessBoard inputBoard, int alpha, int beta, int depth, boolean whiteTurn) throws AWTException {
        counter++;
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        int maxEval = Integer.MIN_VALUE;
        int minEval = Integer.MAX_VALUE;
        int currentValue;
        ArrayList<Move> allMoves = inputBoard.getAllMoves(sideToMove, depth);
        if(!actualMovesDone)
        {
            actualMoves.addAll(allMoves);
            actualMovesDone = true;
        }
        for(Move move : allMoves)
        {
            inputBoard.setFrom(move.fromHere);
            inputBoard.setPressed(move.toHere);

            inputBoard.updateCastleRights(depth);
            inputBoard.movePieceAI(inputBoard.getFrom(), inputBoard.getPressed(), depth, move);

            inputBoard.expCheck = inputBoard.exposedCheck(inputBoard.getFrom(), inputBoard.getNews());

            ArrayList<Square> check = inputBoard.getNews().getPiece().getLegalMoves();
            inputBoard.thisIstheWay = inputBoard.isInCheck(check, inputBoard.getNews());
            if(whiteTurn)
            {
                currentValue = minHelper(inputBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, depth - 1, false);
                inputBoard.undoMove(move.toHere, move.fromHere, depth, depth + 1, move.moveMadeWhileInCheck, move);
                if(currentValue > maxEval)
                {
                    maxEval = currentValue;
                    bestMove = move;
                }
            }
            else
            {
                currentValue = maxHelper(inputBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, depth - 1, true);
                inputBoard.undoMove(move.toHere, move.fromHere, depth, depth + 1, move.moveMadeWhileInCheck, move);
                if(currentValue < minEval)
                {
                    minEval = currentValue;
                }
            }

        }
        System.out.println(counter);
        return bestMove;
    }

    public int minHelper(ChessBoard inputBoard, int alpha, int beta, int depth, boolean whiteTurn) throws AWTException {
        counter++;
        if(depth == 0)
        {
            return evaluationFunction(inputBoard);
        }
        int minValue = Integer.MAX_VALUE;
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        for(Move move : inputBoard.getAllMoves(sideToMove, depth))
        {
            inputBoard.setFrom(move.fromHere);
            inputBoard.setPressed(move.toHere);

            inputBoard.updateCastleRights(depth);
            inputBoard.movePieceAI(inputBoard.getFrom(), inputBoard.getPressed(), depth, move);

            inputBoard.expCheck = inputBoard.exposedCheck(inputBoard.getFrom(), inputBoard.getNews());

            ArrayList<Square> check = inputBoard.getNews().getPiece().getLegalMoves();
            inputBoard.thisIstheWay = inputBoard.isInCheck(check, inputBoard.getNews());
            int currentValue = maxHelper(inputBoard, alpha, beta, depth - 1, true);
            inputBoard.undoMove(move.toHere, move.fromHere, depth, depth + 1, move.moveMadeWhileInCheck, move);

            if(currentValue <= minValue)
            {
                minValue = currentValue;
            }
            //currentValue = min(minValue, currentValue);
            beta = min(beta, currentValue);
            if(beta <= alpha)
            {
                break;
            }
        }
        return minValue;
    }

    public int maxHelper(ChessBoard inputBoard, int alpha, int beta, int depth, boolean whiteTurn) throws AWTException {
        counter++;
        if(depth == 0)
        {
            return evaluationFunction(inputBoard);
        }
        //TODO: if game terminates
        int maxValue = Integer.MIN_VALUE;
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        for(Move move : inputBoard.getAllMoves(sideToMove, depth))
        {
            inputBoard.setFrom(move.fromHere);
            inputBoard.setPressed(move.toHere);

            inputBoard.updateCastleRights(depth);
            inputBoard.movePieceAI(inputBoard.getFrom(), inputBoard.getPressed(), depth, move);

            inputBoard.expCheck = inputBoard.exposedCheck(inputBoard.getFrom(), inputBoard.getNews());

            ArrayList<Square> check = inputBoard.getNews().getPiece().getLegalMoves();
            inputBoard.thisIstheWay = inputBoard.isInCheck(check, inputBoard.getNews());
            int currentValue = minHelper(inputBoard, alpha, beta, depth - 1, false);
            inputBoard.undoMove(move.toHere, move.fromHere, depth, depth+1, move.moveMadeWhileInCheck, move);

            if(currentValue >= maxValue)
            {
                maxValue = currentValue;
            }
            //maxValue = max(maxValue, currentValue);
            alpha = max(alpha, currentValue);
            if(beta <= alpha)
            {
                break;
            }
        }
        return maxValue;
    }

    public int evaluationFunction(ChessBoard input)
    {
        int evaluation = 0;
        for(Square[] row : input.chessBoard)
        {
            for(Square s : row)
            {
                if(s.containsPiece())
                {
                    if(s.getPiece().getSide().equals(Piece.Side.WHITE))
                    {
                        evaluation += s.getPiece().value;
                    }
                    else
                    {
                        evaluation -= s.getPiece().value;
                    }
                }
            }
        }
        return evaluation;
    }
}
