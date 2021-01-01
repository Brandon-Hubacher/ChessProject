import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Pawn extends Piece implements Serializable {

    public Pawn(String image, int row, int col, Side side, ChessBoard b) throws IOException
    {
        super(image, row, col, side, 0, 1, b);
    }
/*
    public String toString()
    {
        return getSide()+" "+"pawn";
    }

 */

    public Square getToCaptureEnPassantAt()
    {
        return toCaptureEnPassantAt;
    }

    public void setToCaptureEnPassantAt(Square s)
    {
        toCaptureEnPassantAt = s;
    }
    public Square getCapturedEnPassantAt()
    {
        return capturedEnPassantAt;
    }

    public void setCapturedEnPassantAt(Square s)
    {
        capturedEnPassantAt = s;
    }

    public int getEnPassantBecameAvailableDepth()
    {
        return enPassantBecameAvailableDepth;
    }

    public void setEnPassantBecameAvailableDepth(int n)
    {
        enPassantBecameAvailableDepth = n;
    }
    public String toString()
    {
        String strSide = (side.equals(Side.WHITE)) ? "w" : "b";
        return strSide+"p";
    }


    public ArrayList<Square> getLegalMoves()
    {
        ArrayList<Square> pawnMoves = new ArrayList<>();
        if(canAdvPawn())
        {
            pawnMoves.add(advPawnSqaure());
        }
        if(canPawnAttackLeftDiag())
        {
            pawnMoves.add(pawnAttackLeftDiag());
        }
        if(canPawnAttackRightDiag())
        {
            pawnMoves.add(pawnAttackRightDiag());
        }
        if(getRow() == 1)
        {
            if(side == Side.BLACK && !board.chessBoard[getRow()+2][getCol()].containsPiece() && canAdvPawn())
            {
                pawnMoves.add(board.chessBoard[getRow()+2][getCol()]);
            }
        }
        else if(getRow() == 6)
        {
            if(side == Side.WHITE && !board.chessBoard[getRow()-2][getCol()].containsPiece() && canAdvPawn())
            {
                pawnMoves.add(board.chessBoard[getRow()-2][getCol()]);
            }
        }
        return pawnMoves;
    }

    public Square advPawnSqaure()
    {
        if(side == Side.BLACK)
        {
            return board.chessBoard[getRow()+1][getCol()];
        }
        else if(side == Side.WHITE)
        {
            return board.chessBoard[getRow()-1][getCol()];
        }
        return null;
    }

    public boolean canAdvPawn()
    {
        if(side == Side.BLACK)
        {
            return getRow()+1 != 8 && !board.chessBoard[getRow()+1][getCol()].containsPiece();
        }
        else if(side == Side.WHITE)
        {
            return getRow()-1 != -1 && !board.chessBoard[getRow()-1][getCol()].containsPiece();
        }
        return false;
    }

    public boolean canPawnAttackLeftDiag()
    {
        if(side == Side.BLACK)
        {
            if(getRow()+1 > 7 || getCol()-1 < 0 || !board.chessBoard[getRow()+1][getCol()-1].containsPiece() || board.chessBoard[getRow()+1][getCol()-1].getPiece().getSide().equals(Side.BLACK))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(side == Side.WHITE)
        {
            if(getRow()-1 < 0 || getCol()-1 < 0 || !board.chessBoard[getRow()-1][getCol()-1].containsPiece() || board.chessBoard[getRow()-1][getCol()-1].getPiece().getSide().equals(Side.WHITE))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    public Square pawnAttackLeftDiag()
    {
        if(side == Side.BLACK)
        {
            return board.chessBoard[getRow()+1][getCol()-1];
        }
        else
        {
            return board.chessBoard[getRow()-1][getCol()-1];
        }
    }

    public Square pawnAttackRightDiag()
    {
        if(side == Side.BLACK)
        {
            return board.chessBoard[getRow()+1][getCol()+1];
        }
        else
        {
            return board.chessBoard[getRow()-1][getCol()+1];
        }
    }

    public boolean canPawnAttackRightDiag()
    {
        if(side == Side.BLACK)
        {
            if(getRow()+1 > 7 || getCol()+1 > 7 || !board.chessBoard[getRow()+1][getCol()+1].containsPiece() || board.chessBoard[getRow()+1][getCol()+1].getPiece().getSide().equals(Side.BLACK))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(side == Side.WHITE)
        {
            if(getRow()-1 < 0 || getCol()+1 > 7 || !board.chessBoard[getRow()-1][getCol()+1].containsPiece() || board.chessBoard[getRow()-1][getCol()+1].getPiece().getSide().equals(Side.WHITE))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return false;
    }
}
