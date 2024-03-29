import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Bishop extends Piece implements Serializable {

    public Bishop(String image, int row, int col, Side side, ChessBoard b) throws IOException
    {
        super(image, row, col, side, 2, 3, b);
    }

    public Bishop(String image, ChessBoard board) throws IOException
    {
        super(image, board, 2);
    }
    public String toString()
    {
        String strSide = (side.equals(Side.WHITE)) ? "B" : "b";
        return strSide;
    }

    public ArrayList<Square> getLegalMoves()
    {
        boolean stopSearchUpLeft = false;
        boolean stopSearchUpRight = false;
        boolean stopSearchDownLeft = false;
        boolean stopSearchDownRight = false;
        ArrayList<Square> moves = new ArrayList<>();
        for(int i = 1; i < 9; i++)
        {
            if(upLeftInBounds(i) && !getUpLeft(i).containsPiece() && !stopSearchUpLeft)
            {
                moves.add(getUpLeft(i));
            }
            else if(upLeftInBounds(i) && getUpLeft(i).containsPiece() && !stopSearchUpLeft)
            {
                stopSearchUpLeft = true;
                if(!getUpLeft(i).getPiece().getSide().equals(side))
                {
                    moves.add(getUpLeft(i));
                }
            }

            if(upRightInBounds(i) && !getUpRight(i).containsPiece() && !stopSearchUpRight)
            {
                moves.add(getUpRight(i));
            }
            else if(upRightInBounds(i) && getUpRight(i).containsPiece() && !stopSearchUpRight)
            {
                stopSearchUpRight = true;
                if(!getUpRight(i).getPiece().getSide().equals(side))
                {
                    moves.add(getUpRight(i));
                }
            }

            if(downLeftInBounds(i) && !getDownLeft(i).containsPiece() && !stopSearchDownLeft)
            {
                moves.add(getDownLeft(i));
            }
            else if(downLeftInBounds(i) && getDownLeft(i).containsPiece() && !stopSearchDownLeft)
            {
                stopSearchDownLeft = true;
                if(!getDownLeft(i).getPiece().getSide().equals(side))
                {
                    moves.add(getDownLeft(i));
                }
            }

            if(downRightInBounds(i) && !getDownRight(i).containsPiece() && !stopSearchDownRight)
            {
                moves.add(getDownRight(i));
            }
            else if(downRightInBounds(i) && getDownRight(i).containsPiece() && !stopSearchDownRight)
            {
                stopSearchDownRight = true;
                if(!getDownRight(i).getPiece().getSide().equals(side))
                {
                    moves.add(getDownRight(i));
                }
            }
            if(stopSearchUpLeft && stopSearchUpRight && stopSearchDownLeft && stopSearchDownRight)
            {
                return moves;
            }
        }
        return moves;
    }
}
