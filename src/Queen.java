import java.io.IOException;
import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(String image, int row, int col, Side side) throws IOException
    {
        super(image, row, col, side);
    }

    public Queen(String image) throws IOException
    {
        super(image);
    }

    public ArrayList<Square> getLegalMoves()
    {
        boolean stopSearchLeft = false;
        boolean stopSearchRight = false;
        boolean stopSearchUp = false;
        boolean stopSearchDown = false;
        ArrayList<Square> rookMoves = new ArrayList<>();
        ArrayList<Square> bishopMoves = new ArrayList<>();
        for(int i = 1; i < 9; i++)
        {
            if(leftInBounds(i) && !getLeft(i).containsPiece() && !stopSearchLeft)
            {
                rookMoves.add(getLeft(i));
            }
            else if(leftInBounds(i) && getLeft(i).containsPiece() && !stopSearchLeft)
            {
                stopSearchLeft = true;
                if(!getLeft(i).getPiece().getSide().equals(side))
                {
                    rookMoves.add(getLeft(i));
                }
            }

            if(rightInBounds(i) && !getRight(i).containsPiece() && !stopSearchRight)
            {
                rookMoves.add(getRight(i));
            }
            else if(rightInBounds(i) && getRight(i).containsPiece() && !stopSearchRight)
            {
                stopSearchRight = true;
                if(!getRight(i).getPiece().getSide().equals(side))
                {
                    rookMoves.add(getRight(i));
                }
            }

            if(upInBounds(i) && !getUp(i).containsPiece() && !stopSearchUp)
            {
                rookMoves.add(getUp(i));
            }
            else if(upInBounds(i) && getUp(i).containsPiece() && !stopSearchUp)
            {
                stopSearchUp = true;
                if(!getUp(i).getPiece().getSide().equals(side))
                {
                    rookMoves.add(getUp(i));
                }
            }

            if(downInBounds(i) && !getDown(i).containsPiece() && !stopSearchDown)
            {
                rookMoves.add(getDown(i));
            }
            else if(downInBounds(i) && getDown(i).containsPiece() && !stopSearchDown)
            {
                stopSearchDown = true;
                if(!getDown(i).getPiece().getSide().equals(side))
                {
                    rookMoves.add(getDown(i));
                }
            }
            /*
            if(stopSearchDown && stopSearchLeft && stopSearchRight && stopSearchUp)
            {
                return rookMoves;
            }

             */
        }

        boolean stopSearchUpLeft = false;
        boolean stopSearchUpRight = false;
        boolean stopSearchDownLeft = false;
        boolean stopSearchDownRight = false;
        for(int i = 1; i < 9; i++)
        {
            if(upLeftInBounds(i) && !getUpLeft(i).containsPiece() && !stopSearchUpLeft)
            {
                bishopMoves.add(getUpLeft(i));
            }
            else if(upLeftInBounds(i) && getUpLeft(i).containsPiece() && !stopSearchUpLeft)
            {
                stopSearchUpLeft = true;
                if(!getUpLeft(i).getPiece().getSide().equals(side))
                {
                    bishopMoves.add(getUpLeft(i));
                }
            }

            if(upRightInBounds(i) && !getUpRight(i).containsPiece() && !stopSearchUpRight)
            {
                bishopMoves.add(getUpRight(i));
            }
            else if(upRightInBounds(i) && getUpRight(i).containsPiece() && !stopSearchUpRight)
            {
                stopSearchUpRight = true;
                if(!getUpRight(i).getPiece().getSide().equals(side))
                {
                    bishopMoves.add(getUpRight(i));
                }
            }

            if(downLeftInBounds(i) && !getDownLeft(i).containsPiece() && !stopSearchDownLeft)
            {
                bishopMoves.add(getDownLeft(i));
            }
            else if(downLeftInBounds(i) && getDownLeft(i).containsPiece() && !stopSearchDownLeft)
            {
                stopSearchDownLeft = true;
                if(!getDownLeft(i).getPiece().getSide().equals(side))
                {
                    bishopMoves.add(getDownLeft(i));
                }
            }

            if(downRightInBounds(i) && !getDownRight(i).containsPiece() && !stopSearchDownRight)
            {
                bishopMoves.add(getDownRight(i));
            }
            else if(downRightInBounds(i) && getDownRight(i).containsPiece() && !stopSearchDownRight)
            {
                stopSearchDownRight = true;
                if(!getDownRight(i).getPiece().getSide().equals(side))
                {
                    bishopMoves.add(getDownRight(i));
                }
            }
            /*
            if(stopSearchUpLeft && stopSearchUpRight && stopSearchDownLeft && stopSearchDownRight)
            {
                return bishopMoves;
            }

             */
        }
        rookMoves.addAll(bishopMoves);
        return rookMoves;
    }
}
