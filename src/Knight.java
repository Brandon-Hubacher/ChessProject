import java.io.IOException;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(String image, int row, int col, Side side) throws IOException
    {
        super(image, row, col, side);
    }

    public Knight(int row, int col, Side side)
    {
        super(row, col, side);
    }

    public Knight(String image) throws IOException
    {
        super(image);
    }

    public ArrayList<Square> getLegalMoves()
    {
        ArrayList<Square> moves = new ArrayList<>();
        if(upLeftInBounds(1))
        {
            if(getUpLeft(1).upInBounds(1))
            {
                if(!getUpLeft(1).getUp(1).containsPiece())
                {
                    moves.add(getUpLeft(1).getUp(1));
                }
                else if(!getUpLeft(1).getUp(1).getPiece().getSide().equals(side))
                {
                    moves.add(getUpLeft(1).getUp(1));
                }
            }
            if(getUpLeft(1).leftInBounds(1))
            {
                if(!getUpLeft(1).getLeft(1).containsPiece())
                {
                    moves.add(getUpLeft(1).getLeft(1));
                }
                else if(!getUpLeft(1).getLeft(1).getPiece().getSide().equals(side))
                {
                    moves.add(getUpLeft(1).getLeft(1));
                }
            }


        }
        if(downLeftInBounds(1))
        {
            if(getDownLeft(1).leftInBounds(1))
            {
                if(!getDownLeft(1).getLeft(1).containsPiece())
                {
                    moves.add(getDownLeft(1).getLeft(1));
                }
                else if(!getDownLeft(1).getLeft(1).getPiece().getSide().equals(side))
                {
                    moves.add(getDownLeft(1).getLeft(1));
                }
            }
            if(getDownLeft(1).downInBounds(1))
            {
                if(!getDownLeft(1).getDown(1).containsPiece())
                {
                    moves.add(getDownLeft(1).getDown(1));
                }
                else if(!getDownLeft(1).getDown(1).getPiece().getSide().equals(side))
                {
                    moves.add(getDownLeft(1).getDown(1));
                }
            }
        }
        if(downRightInBounds(1))
        {
            if(getDownRight(1).downInBounds(1))
            {
                if(!getDownRight(1).getDown(1).containsPiece())
                {
                    moves.add(getDownRight(1).getDown(1));
                }
                else if(!getDownRight(1).getDown(1).getPiece().getSide().equals(side))
                {
                    moves.add(getDownRight(1).getDown(1));
                }
            }
            if(getDownRight(1).rightInBounds(1))
            {
                if(!getDownRight(1).getRight(1).containsPiece())
                {
                    moves.add(getDownRight(1).getRight(1));
                }
                else if(!getDownRight(1).getRight(1).getPiece().getSide().equals(side))
                {
                    moves.add(getDownRight(1).getRight(1));
                }
            }
        }

        if(upRightInBounds(1))
        {
            if(getUpRight(1).rightInBounds(1))
            {
                if(!getUpRight(1).getRight(1).containsPiece())
                {
                    moves.add(getUpRight(1).getRight(1));
                }
                else if(!getUpRight(1).getRight(1).getPiece().getSide().equals(side))
                {
                    moves.add(getUpRight(1).getRight(1));
                }
            }
            if(getUpRight(1).upInBounds(1))
            {
                if(!getUpRight(1).getUp(1).containsPiece())
                {
                    moves.add(getUpRight(1).getUp(1));
                }
                else if(!getUpRight(1).getUp(1).getPiece().getSide().equals(side))
                {
                    moves.add(getUpRight(1).getUp(1));
                }
            }
        }
        return moves;
    }
}
