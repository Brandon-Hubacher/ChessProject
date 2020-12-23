import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
/*
public class Pieces extends JComponent{
    public Image image;
    private int[] pieceCoord = new int[2];
    private String side;
    private String type;

    public Pieces(String imageFile) throws IOException
    {
        image = ImageIO.read(new File(imageFile));
        //type = imageFile.split();
        String tempType = imageFile.substring(9, imageFile.length()-4);
        side = (tempType.contains("red")) ? "black" : "white";
        if(tempType.contains("red"))
        {
            type = tempType.split("\\s+")[1];
        }
        else
        {
            type = tempType;
        }
    }


    public Pieces(String imageFile, int[] place, String side, String type) throws IOException
    {
        image = ImageIO.read(new File(imageFile));
        pieceCoord[0] = place[0];
        pieceCoord[1] = place[1];
        this.side = side;
        this.type = type;
    }

    public void setImage(Image i)
    {
        image = i;
    }

    public Image getImage()
    {
        return image;
    }

    public void setPlacement(int[] place)
    {
        pieceCoord[0] = place[0];
        pieceCoord[1] = place[1];
    }

    public void setPlacement(int one, int two)
    {
        pieceCoord[0] = one;
        pieceCoord[1] = two;
    }

    public String getType()
    {
        return type;
    }

    public String getSide()
    {
        return side;
    }

    public int[] getPlacement()
    {
        return pieceCoord;
    }

    public void setType(String t)
    {
        type = t;
    }

    public void setSide(String s)
    {
        side = s;
    }


    public ArrayList<Square> getLegalMoves()
    {
        if(type.equalsIgnoreCase("pawn"))
        {
            return getPawnLegalMoves();
        }
        else if(type.equalsIgnoreCase("rook"))
        {
            return getRookLegalMoves();
        }
        else if(type.equalsIgnoreCase("bishop"))
        {
            return getBishopLegalMoves();
        }
        else if(type.equalsIgnoreCase("queen"))
        {
            return getQueenLegalMoves();
        }
        else if(type.equalsIgnoreCase("knight"))
        {
            return getKnightLegalMoves();
        }
        else if(type.equalsIgnoreCase("king"))
        {
            return getKingLegalMoves();
        }
        return null;
    }
    public ArrayList<Square> getKingLegalMoves()
    {
        ArrayList<Square> moves = new ArrayList<>();
        if(upInBounds(1) && !getUp(1).containsPiece())
        {
            moves.add(getUp(1));
        }
        else if(upInBounds(1) && !getUp(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getUp(1));
        }

        if(leftInBounds(1) && !getLeft(1).containsPiece())
        {
            moves.add(getLeft(1));
        }
        else if(leftInBounds(1) && !getLeft(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getLeft(1));
        }

        if(upLeftInBounds(1) && !getUpLeft(1).containsPiece())
        {
            moves.add(getUpLeft(1));
        }
        else if(upLeftInBounds(1) && !getUpLeft(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getUpLeft(1));
        }

        if(downLeftInBounds(1) && !getDownLeft(1).containsPiece())
        {
            moves.add(getDownLeft(1));
        }
        else if(downLeftInBounds(1) && !getDownLeft(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getDownLeft(1));
        }

        if(downInBounds(1) && !getDown(1).containsPiece())
        {
            moves.add(getDown(1));
        }
        else if(downInBounds(1) && !getDown(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getDown(1));
        }

        if(downRightInBounds(1) && !getDownRight(1).containsPiece())
        {
            moves.add(getDownRight(1));
        }
        else if(downRightInBounds(1) && !getDownRight(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getDownRight(1));
        }

        if(rightInBounds(1) && !getRight(1).containsPiece())
        {
            moves.add(getRight(1));
        }
        else if(rightInBounds(1) && !getRight(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getRight(1));
        }

        if(upRightInBounds(1) && !getUpRight(1).containsPiece())
        {
            moves.add(getUpRight(1));
        }
        else if(upRightInBounds(1) && !getUpRight(1).getPiece().getSide().equalsIgnoreCase(side))
        {
            moves.add(getUpRight(1));
        }
        return moves;
    }

    public ArrayList<Square> getKnightLegalMoves()
    {
        ArrayList<Square> moves = new ArrayList<>();
        //moves.set(0, null);
        //Square pieceSq = Board.board[getRow()][getCol()];
        //pieceSq = getUpLeft(1);
        if(upLeftInBounds(1))
        {
            if(getUpLeft(1).upInBounds(1))
            {
                if(!getUpLeft(1).getUp(1).containsPiece())
                {
                    moves.add(getUpLeft(1).getUp(1));
                }
                else if(!getUpLeft(1).getUp(1).getPiece().getSide().equalsIgnoreCase(side))
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
                else if(!getUpLeft(1).getLeft(1).getPiece().getSide().equalsIgnoreCase(side))
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
                else if(!getDownLeft(1).getLeft(1).getPiece().getSide().equalsIgnoreCase(side))
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
                else if(!getDownLeft(1).getDown(1).getPiece().getSide().equalsIgnoreCase(side))
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
                else if(!getDownRight(1).getDown(1).getPiece().getSide().equalsIgnoreCase(side))
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
                else if(!getDownRight(1).getRight(1).getPiece().getSide().equalsIgnoreCase(side))
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
                else if(!getUpRight(1).getRight(1).getPiece().getSide().equalsIgnoreCase(side))
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
                else if(!getUpRight(1).getUp(1).getPiece().getSide().equalsIgnoreCase(side))
                {
                    moves.add(getUpRight(1).getUp(1));
                }
            }
        }
/*
        if(isKnightInBounds(upLeftInBounds(1), getUpLeft(1).upInBounds(1), getUpLeft(1).getUp(1)))
        {
            getUpLeft(1).getUp(1).setBackground(Color.BLUE);
            moves.add(getUpLeft(1).getUp(1));
        }
        if(isKnightInBounds(upLeftInBounds(1), getUpLeft(1).leftInBounds(1), getUpLeft(1).getUp(1)))
        {
            getUpLeft(1).getLeft(1).setBackground(Color.BLUE);
            moves.add(getUpLeft(1).getLeft(1));
        }
        if(isKnightInBounds(downLeftInBounds(1), getDownLeft(1).leftInBounds(1), getDownLeft(1).getLeft(1)))
        {
            getDownLeft(1).getLeft(1).setBackground(Color.BLUE);
            moves.add(getDownLeft(1).getLeft(1));
        }
        if(isKnightInBounds(downLeftInBounds(1), getDownLeft(1).downInBounds(1), getDownLeft(1).getDown(1)))
        {
            getDownLeft(1).getDown(1).setBackground(Color.BLUE);
            moves.add(getDownLeft(1).getDown(1));
        }
        if(isKnightInBounds(downRightInBounds(1), getDownRight(1).downInBounds(1), getDownRight(1).getDown(1)))
        {
            getDownRight(1).getDown(1).setBackground(Color.BLUE);
            moves.add(getDownRight(1).getDown(1));
        }
        if(isKnightInBounds(downRightInBounds(1), getDownRight(1).rightInBounds(1), getDownRight(1).getRight(1)))
        {
            getDownRight(1).getRight(1).setBackground(Color.BLUE);
            moves.add(getDownRight(1).getRight(1));
        }
        if(isKnightInBounds(upRightInBounds(1), getUpRight(1).rightInBounds(1), getUpRight(1).getRight(1)))
        {
            getUpRight(1).getRight(1).setBackground(Color.BLUE);
            moves.add((getUpRight(1).getRight(1)));
        }
        if(isKnightInBounds(upRightInBounds(1), getUpRight(1).upInBounds(1), getUpRight(1).getUp(1)))
        {
            getUpRight(1).getUp(1).setBackground(Color.BLUE);
            moves.add((getUpRight(1).getUp(1)));
        }


        return moves;
    }

    public boolean isKnightInBounds(boolean firstBoolean, boolean secondBoolean, Square secondSquare)
    {
        if(firstBoolean)
        {
            if(secondBoolean)
            {
                if(!secondSquare.containsPiece())
                {
                    return true;
                }
                else if(!secondSquare.getPiece().getSide().equalsIgnoreCase(side))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Square> getBishopLegalMoves()
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
                if(!getUpLeft(i).getPiece().getSide().equalsIgnoreCase(side))
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
                if(!getUpRight(i).getPiece().getSide().equalsIgnoreCase(side))
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
                if(!getDownLeft(i).getPiece().getSide().equalsIgnoreCase(side))
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
                if(!getDownRight(i).getPiece().getSide().equalsIgnoreCase(side))
                {
                    moves.add(getDownRight(i));
                }
            }
            if(stopSearchUpLeft == true && stopSearchUpRight == true && stopSearchDownLeft == true && stopSearchDownRight == true)
            {
                return moves;
            }
        }
        return moves;
    }

    public Square getUpLeft(int num)
    {
        return Board.board[getRow()-num][getCol()-num];
    }

    public Square getUpRight(int num)
    {
        return Board.board[getRow()-num][getCol()+num];
    }

    public Square getDownLeft(int num)
    {
        return Board.board[getRow()+num][getCol()-num];
    }

    public Square getDownRight(int num)
    {
        return Board.board[getRow()+num][getCol()+num];
    }

    public boolean upLeftInBounds(int num)
    {
        return getCol()-num >= 0 && getRow()-num >= 0;
    }

    public boolean upRightInBounds(int num)
    {
        return getCol()+num <= 7 && getRow()-num >= 0;
    }

    public boolean downLeftInBounds(int num)
    {
        return getRow()+num <= 7 && getCol()-num >= 0;
    }

    public boolean downRightInBounds(int num)
    {
        return getRow()+num <= 7 && getCol()+num <= 7;
    }

    public ArrayList<Square> getQueenLegalMoves()
    {
        ArrayList<Square> rookMoves = getRookLegalMoves();
        ArrayList<Square> bishopMoves = getBishopLegalMoves();
        for(Square square : bishopMoves)
        {
            rookMoves.add(square);
        }
        return rookMoves;
    }

    public ArrayList<Square> getRookLegalMoves()
    {
        boolean stopSearchLeft = false;
        boolean stopSearchRight = false;
        boolean stopSearchUp = false;
        boolean stopSearchDown = false;
        ArrayList<Square> moves = new ArrayList<>();
        for(int i = 1; i < 9; i++)
        {
            //Square s = Board.board[getRow()+i][getCol()];
            if(leftInBounds(i) && !getLeft(i).containsPiece() && !stopSearchLeft)
            {
                moves.add(getLeft(i));
            }
            else if(leftInBounds(i) && getLeft(i).containsPiece() && !stopSearchLeft)
            {
                stopSearchLeft = true;
                if(!getLeft(i).getPiece().getSide().equalsIgnoreCase(side))
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
                if(!getRight(i).getPiece().getSide().equalsIgnoreCase(side))
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
                if(!getUp(i).getPiece().getSide().equalsIgnoreCase(side))
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
                if(!getDown(i).getPiece().getSide().equalsIgnoreCase(side))
                {
                    moves.add(getDown(i));
                }
            }
            if(stopSearchDown == true && stopSearchLeft == true && stopSearchRight == true && stopSearchUp == true)
            {
                return moves;
            }
        }
        return moves;
    }

    public Square getLeft(int num)
    {
        return Board.board[getRow()][getCol()-num];
    }

    public Square getRight(int num)
    {
        return Board.board[getRow()][getCol()+num];
    }

    public Square getUp(int num)
    {
        return Board.board[getRow()-num][getCol()];
    }

    public Square getDown(int num)
    {
        return Board.board[getRow()+num][getCol()];
    }

    public boolean leftInBounds(int num)
    {
        return getCol()-num >= 0;
    }

    public boolean rightInBounds(int num)
    {
        return getCol()+num <= 7;
    }

    public boolean upInBounds(int num)
    {
        return getRow()-num >= 0;
    }

    public boolean downInBounds(int num)
    {
        return getRow()+num <= 7;
    }

    public ArrayList<Square> getPawnLegalMoves()
    {
        ArrayList<Square> pawnMoves = new ArrayList<>();
        //Square[] pawnMoves = new Square[4];
        if(canAdvPawn())
        {
            pawnMoves.add(advPawnSqaure());
        }
        if(canPawnAttackLeftDiag())
        {
            pawnMoves.add(pawnAttackLeftDiag());
            //pawnMoves[1] = pawnAttackLeftDiag();
        }
        if(canPawnAttackRightDiag())
        {
            //pawnMoves[2] = pawnAttackRightDiag();
            pawnMoves.add(pawnAttackRightDiag());
        }
        if(getRow() == 1)
        {
            //System.out.println("inside 2 square pawn move");
            if(side.equalsIgnoreCase("black") && !Board.board[getRow()+2][getCol()].containsPiece() && canAdvPawn())
            {
                //System.out.println("inside next if");
                //pawnMoves[3] = Board.board[getRow()+2][getCol()];
                pawnMoves.add(Board.board[getRow()+2][getCol()]);
            }
        }
        else if(getRow() == 6)
        {
            if(side.equalsIgnoreCase("white") && !Board.board[getRow()-2][getCol()].containsPiece() && canAdvPawn())
            {
                //pawnMoves[3] = Board.board[getRow()-2][getCol()];
                pawnMoves.add(Board.board[getRow()-2][getCol()]);
            }
        }
        //repaint();
        return pawnMoves;
    }

    public Square advPawnSqaure()
    {
        if(side.equalsIgnoreCase("black"))
        {
            return Board.board[getRow()+1][getCol()];
        }
        else if(side.equalsIgnoreCase("white"))
        {
            return Board.board[getRow()-1][getCol()];
        }
        return null;
    }

    public boolean canAdvPawn()
    {
        if(side.equalsIgnoreCase("black"))
        {
            return getRow()+1 != 8 && !Board.board[getRow()+1][getCol()].containsPiece();
        }
        else if(side.equalsIgnoreCase("white"))
        {
            return getRow()-1 != -1 && !Board.board[getRow()-1][getCol()].containsPiece();
        }
        return false;
    }

    public boolean canPawnAttackLeftDiag()
    {
        if(side.equalsIgnoreCase("black"))
        {
            if(getRow()+1 > 7 || getCol()-1 < 0 || !Board.board[getRow()+1][getCol()-1].containsPiece() || Board.board[getRow()+1][getCol()-1].getPiece().getSide().equalsIgnoreCase("black"))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(side.equalsIgnoreCase("white"))
        {
            if(getRow()-1 < 0 || getCol()-1 < 0 || !Board.board[getRow()-1][getCol()-1].containsPiece() || Board.board[getRow()-1][getCol()-1].getPiece().getSide().equalsIgnoreCase("white"))
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
        if(side.equalsIgnoreCase("black"))
        {
            return Board.board[getRow()+1][getCol()-1];
        }
        else
        {
            return Board.board[getRow()-1][getCol()-1];
        }
    }

    public Square pawnAttackRightDiag()
    {
        if(side.equalsIgnoreCase("black"))
        {
            return Board.board[getRow()+1][getCol()+1];
        }
        else
        {
            return Board.board[getRow()-1][getCol()+1];
        }
    }

    public boolean canPawnAttackRightDiag()
    {
        if(side.equalsIgnoreCase("black"))
        {
            if(getRow()+1 > 7 || getCol()+1 > 7 || !Board.board[getRow()+1][getCol()+1].containsPiece() || Board.board[getRow()+1][getCol()+1].getPiece().getSide().equalsIgnoreCase("black"))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(side.equalsIgnoreCase("white"))
        {
            if(getRow()-1 < 0 || getCol()+1 > 7 || !Board.board[getRow()-1][getCol()+1].containsPiece() || Board.board[getRow()-1][getCol()+1].getPiece().getSide().equalsIgnoreCase("white"))
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

    public int getRow()
    {
        return pieceCoord[0];
    }

    public int getCol()
    {
        return pieceCoord[1];
    }


    public ArrayList<Square> isInCheckHorrible()
    {
        boolean upDone = false;
        boolean upLeftDone = false;
        boolean leftDone = false;
        boolean downLeftDone = false;
        boolean downDone = false;
        boolean downRightDone = false;
        boolean rightDone = false;
        boolean upRightDone = false;
        ArrayList<Square> moves = new ArrayList<>();
        for(int i = 1; i < 8; i++)
        {
            if(!upDone && upInBounds(i) && getUp(i).containsPiece())
            {
                Pieces p = getUp(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    upDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("rook") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getUp(i));
                    }
                }
            }

            if(!leftDone && leftInBounds(i) && getLeft(i).containsPiece())
            {
                Pieces p = getLeft(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    leftDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("rook") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getLeft(i));
                    }
                }
            }

            if(!upLeftDone && upLeftInBounds(i) && getUpLeft(i).containsPiece())
            {
                Pieces p = getUpLeft(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    upLeftDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("bishop") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getUpLeft(i));
                    }
                    else if(i == 1 && p.getSide().equalsIgnoreCase("black") && !p.getSide().equalsIgnoreCase(side) && p.getType().equalsIgnoreCase("pawn"))
                    {
                        moves.add(getUpLeft(i));
                    }
                }
            }

            if(!downLeftDone && downLeftInBounds(i) && getDownLeft(i).containsPiece())
            {
                Pieces p = getDownLeft(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    downLeftDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("bishop") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getDownLeft(i));
                    }
                    else if(i == 1 && p.getSide().equalsIgnoreCase("white") && !p.getSide().equalsIgnoreCase(side) && p.getType().equalsIgnoreCase("pawn"))
                    {
                        moves.add(getDownLeft(i));
                    }
                }
            }

            if(!downDone && downInBounds(i) && getDown(i).containsPiece())
            {
                Pieces p = getDown(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    downDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("rook") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getDown(i));
                    }
                }
            }

            if(!downRightDone && downRightInBounds(i) && getDownRight(i).containsPiece())
            {
                Pieces p = getDownRight(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    downRightDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("bishop") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getDownRight(i));
                    }
                    else if(i == 1 && p.getSide().equalsIgnoreCase("white") && !p.getSide().equalsIgnoreCase(side) && p.getType().equalsIgnoreCase("pawn"))
                    {
                        moves.add(getDownRight(i));
                    }
                }
            }

            if(!rightDone && rightInBounds(i) && getRight(i).containsPiece())
            {
                Pieces p = getRight(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    rightDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("rook") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getRight(i));
                    }
                }
            }

            if(!upRightDone && upRightInBounds(i) && getUpRight(i).containsPiece())
            {
                Pieces p = getUpRight(i).getPiece();
                if(!p.getSide().equalsIgnoreCase(side) || p.getSide().equalsIgnoreCase(side))
                {
                    upRightDone = true;
                    if(!p.getSide().equalsIgnoreCase(side) && (p.getType().equalsIgnoreCase("bishop") || p.getType().equalsIgnoreCase("queen")))
                    {
                        moves.add(getUpRight(i));
                    }
                    else if(i == 1 && p.getSide().equalsIgnoreCase("black") && !p.getSide().equalsIgnoreCase(side) && p.getType().equalsIgnoreCase("pawn"))
                    {
                        moves.add(getUpRight(i));
                    }
                }
            }
        }
        if(moves.size() == 0)
        {
            setType("knight");
            ArrayList<Square> knightMoves = getLegalMoves();


            for(Square square : knightMoves)
            {
                if(square.containsPiece())
                {
                    if(square.getPiece().getType().equalsIgnoreCase("knight") && !square.getPiece().getSide().equalsIgnoreCase(side))
                    {
                        moves.add(square);
                    }
                }
            }
            setType("king");
        }
        return moves;
    }


}
*/