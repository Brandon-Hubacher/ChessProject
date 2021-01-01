import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class King extends Piece implements Serializable {

    public King(String image, int row, int col, Side side, ChessBoard b) throws IOException
    {
        super(image, row, col, side, 5, 10000, b);
    }
/*
    public String toString()
    {
        return getSide()+" "+"king";
    }

 */
public String toString()
{
    String strSide = (side.equals(Side.WHITE)) ? "w" : "b";
    return strSide+"@";
}

    public ArrayList<Square> getLegalMoves()
    {
        ArrayList<Square> moves = new ArrayList<>();
        if(upInBounds(1) && !getUp(1).containsPiece())
        {
            moves.add(getUp(1));
        }
        else if(upInBounds(1) && !getUp(1).getPiece().getSide().equals(side))
        {
            moves.add(getUp(1));
        }

        if(leftInBounds(1) && !getLeft(1).containsPiece())
        {
            moves.add(getLeft(1));
        }
        else if(leftInBounds(1) && !getLeft(1).getPiece().getSide().equals(side))
        {
            moves.add(getLeft(1));
        }

        if(upLeftInBounds(1) && !getUpLeft(1).containsPiece())
        {
            moves.add(getUpLeft(1));
        }
        else if(upLeftInBounds(1) && !getUpLeft(1).getPiece().getSide().equals(side))
        {
            moves.add(getUpLeft(1));
        }

        if(downLeftInBounds(1) && !getDownLeft(1).containsPiece())
        {
            moves.add(getDownLeft(1));
        }
        else if(downLeftInBounds(1) && !getDownLeft(1).getPiece().getSide().equals(side))
        {
            moves.add(getDownLeft(1));
        }

        if(downInBounds(1) && !getDown(1).containsPiece())
        {
            moves.add(getDown(1));
        }
        else if(downInBounds(1) && !getDown(1).getPiece().getSide().equals(side))
        {
            moves.add(getDown(1));
        }

        if(downRightInBounds(1) && !getDownRight(1).containsPiece())
        {
            moves.add(getDownRight(1));
        }
        else if(downRightInBounds(1) && !getDownRight(1).getPiece().getSide().equals(side))
        {
            moves.add(getDownRight(1));
        }

        if(rightInBounds(1) && !getRight(1).containsPiece())
        {
            moves.add(getRight(1));
        }
        else if(rightInBounds(1) && !getRight(1).getPiece().getSide().equals(side))
        {
            moves.add(getRight(1));
        }

        if(upRightInBounds(1) && !getUpRight(1).containsPiece())
        {
            moves.add(getUpRight(1));
        }
        else if(upRightInBounds(1) && !getUpRight(1).getPiece().getSide().equals(side))
        {
            moves.add(getUpRight(1));
        }
        return moves;
    }
}
