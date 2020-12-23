import java.io.IOException;
import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String image, int row, int col, Side side) throws IOException
    {
        super(image, row, col, side);
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
            System.out.println("adding pawn attack right diag to pawn legal moves in pawn class");
            pawnMoves.add(pawnAttackRightDiag());
        }
        if(getRow() == 1)
        {
            if(side == Side.BLACK && !ChessBoard.chessBoard[getRow()+2][getCol()].containsPiece() && canAdvPawn())
            {
                pawnMoves.add(ChessBoard.chessBoard[getRow()+2][getCol()]);
            }
        }
        else if(getRow() == 6)
        {
            if(side == Side.WHITE && !ChessBoard.chessBoard[getRow()-2][getCol()].containsPiece() && canAdvPawn())
            {
                pawnMoves.add(ChessBoard.chessBoard[getRow()-2][getCol()]);
            }
        }
        return pawnMoves;
    }

    public Square advPawnSqaure()
    {
        if(side == Side.BLACK)
        {
            return ChessBoard.chessBoard[getRow()+1][getCol()];
        }
        else if(side == Side.WHITE)
        {
            return ChessBoard.chessBoard[getRow()-1][getCol()];
        }
        return null;
    }

    public boolean canAdvPawn()
    {
        if(side == Side.BLACK)
        {
            return getRow()+1 != 8 && !ChessBoard.chessBoard[getRow()+1][getCol()].containsPiece();
        }
        else if(side == Side.WHITE)
        {
            return getRow()-1 != -1 && !ChessBoard.chessBoard[getRow()-1][getCol()].containsPiece();
        }
        return false;
    }

    public boolean canPawnAttackLeftDiag()
    {
        if(side == Side.BLACK)
        {
            if(getRow()+1 > 7 || getCol()-1 < 0 || !ChessBoard.chessBoard[getRow()+1][getCol()-1].containsPiece() || ChessBoard.chessBoard[getRow()+1][getCol()-1].getPiece().getSide().equals(Side.BLACK))
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
            if(getRow()-1 < 0 || getCol()-1 < 0 || !ChessBoard.chessBoard[getRow()-1][getCol()-1].containsPiece() || ChessBoard.chessBoard[getRow()-1][getCol()-1].getPiece().getSide().equals(Side.WHITE))
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
            return ChessBoard.chessBoard[getRow()+1][getCol()-1];
        }
        else
        {
            return ChessBoard.chessBoard[getRow()-1][getCol()-1];
        }
    }

    public Square pawnAttackRightDiag()
    {
        if(side == Side.BLACK)
        {
            return ChessBoard.chessBoard[getRow()+1][getCol()+1];
        }
        else
        {
            return ChessBoard.chessBoard[getRow()-1][getCol()+1];
        }
    }

    public boolean canPawnAttackRightDiag()
    {
        System.out.println("inside canpawnattackrightdiag");
        if(side == Side.BLACK)
        {
            System.out.println("side is black");
            if(getRow()+1 > 7 || getCol()+1 > 7 || !ChessBoard.chessBoard[getRow()+1][getCol()+1].containsPiece() || ChessBoard.chessBoard[getRow()+1][getCol()+1].getPiece().getSide().equals(Side.BLACK))
            {
                System.out.println("out of bounds OR diag doesn't contain piece OR contains piece of same side");
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(side == Side.WHITE)
        {
            System.out.println("side is white");
            if(getRow()-1 < 0 || getCol()+1 > 7 || !ChessBoard.chessBoard[getRow()-1][getCol()+1].containsPiece() || ChessBoard.chessBoard[getRow()-1][getCol()+1].getPiece().getSide().equals(Side.WHITE))
            {
                System.out.println("out of bounds OR diag doesn't contain piece OR contains piece of same side");
                return false;
            }
            else
            {
                System.out.println("can attack diag");
                return true;
            }
        }
        System.out.println("not sure why it's false");
        return false;
    }
}
