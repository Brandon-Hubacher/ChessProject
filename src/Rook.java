import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Rook extends Piece implements Serializable {

    public Rook(String image, int row, int col, Side side, ChessBoard b) throws IOException
    {
        super(image, row, col, side, 3, 5, b);
    }

    public String toString()
    {
        String strSide = (side.equals(Side.WHITE)) ? "R" : "r";
        return strSide;
    }

    public Rook(String image, ChessBoard board) throws IOException
    {
        super(image, board, 3);
    }

    public ArrayList<Square> getLegalMoves()
    {
        boolean stopSearchLeft = false;
        boolean stopSearchRight = false;
        boolean stopSearchUp = false;
        boolean stopSearchDown = false;
        ArrayList<Square> moves = new ArrayList<>();
        for(int i = 1; i < 9; i++)
        {
            if(leftInBounds(i) && !getLeft(i).containsPiece() && !stopSearchLeft)
            {
                moves.add(getLeft(i));
            }
            else if(leftInBounds(i) && getLeft(i).containsPiece() && !stopSearchLeft)
            {
                stopSearchLeft = true;
                if(!getLeft(i).getPiece().getSide().equals(side))
                {
                    moves.add(getLeft(i));
                }
            }

            if(rightInBounds(i) && !getRight(i).containsPiece() && !stopSearchRight)
            {
                moves.add(getRight(i));
            }
            else if(rightInBounds(i) && getRight(i).containsPiece() && !stopSearchRight)
            {
                stopSearchRight = true;
                if(!getRight(i).getPiece().getSide().equals(side))
                {
                    moves.add(getRight(i));
                }
            }

            if(upInBounds(i) && !getUp(i).containsPiece() && !stopSearchUp)
            {
                moves.add(getUp(i));
            }
            else if(upInBounds(i) && getUp(i).containsPiece() && !stopSearchUp)
            {
                stopSearchUp = true;
                if(!getUp(i).getPiece().getSide().equals(side))
                {
                    moves.add(getUp(i));
                }
            }

            if(downInBounds(i) && !getDown(i).containsPiece() && !stopSearchDown)
            {
                moves.add(getDown(i));
            }
            else if(downInBounds(i) && getDown(i).containsPiece() && !stopSearchDown)
            {
                stopSearchDown = true;
                if(!getDown(i).getPiece().getSide().equals(side))
                {
                    moves.add(getDown(i));
                }
            }
            if(stopSearchDown && stopSearchLeft && stopSearchRight && stopSearchUp)
            {
                return moves;
            }
        }
        return moves;
    }
}
