import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Piece extends JComponent {
    protected Image image;
    protected int row;
    protected int col;
    //protected String side;
    protected Side side;
    protected ImageIcon imageIcon;
    public enum Side {WHITE, BLACK}

    /*
    public Piece()
    {
        image = null;
        row = 0;
        col = 0;
        side = null;
    }

     */
    public Piece(String imageFile) throws IOException
    {
        image = ImageIO.read(new File(imageFile));
        imageIcon = new ImageIcon(image);
        //type = imageFile.split();
        String tempType = imageFile.substring(9, imageFile.length()-4);
        side = (tempType.contains("red")) ? Side.BLACK : Side.WHITE;
    }

    public Piece(String imageFile, int row, int col, Side side) throws IOException
    {
        image = ImageIO.read(new File(imageFile));
        this.row = row;
        this.col = col;
        this.side = side;
    }

    public Piece(int row, int col, Side side)
    {
        this.row = row;
        this.col = col;
        this.side = side;
    }

    public ImageIcon getImageIcon()
    {
        return imageIcon;
    }
/*
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int coord = 300;
        g.drawImage(image, coord, coord, null);
    }


 */


    public Square getLeft(int num)
    {
        return ChessBoard.chessBoard[getRow()][getCol()-num];
    }

    public Square getRight(int num)
    {
        return ChessBoard.chessBoard[getRow()][getCol()+num];
    }

    public Square getUp(int num)
    {
        return ChessBoard.chessBoard[getRow()-num][getCol()];
    }

    public Square getDown(int num)
    {
        return ChessBoard.chessBoard[getRow()+num][getCol()];
    }

    public Square getUpLeft(int num)
    {
        return ChessBoard.chessBoard[getRow()-num][getCol()-num];
    }

    public Square getUpRight(int num)
    {
        return ChessBoard.chessBoard[getRow()-num][getCol()+num];
    }

    public Square getDownLeft(int num)
    {
        return ChessBoard.chessBoard[getRow()+num][getCol()-num];
    }

    public Square getDownRight(int num)
    {
        return ChessBoard.chessBoard[getRow()+num][getCol()+num];
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

    public void canMoveUp(int i)
    {

    }

    public void canMoveUpRight(int i)
    {

    }

    public void canMoveRight(int i)
    {

    }

    public void canMoveDownRight(int i)
    {

    }

    public void canMoveDown(int i)
    {

    }

    public void canMoveDownLeft(int i)
    {

    }

    public void canMoveLeft(int i)
    {

    }

    public void canMoveUpLeft(int i)
    {

    }

    abstract public ArrayList<Square> getLegalMoves();


    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public void setRow(int i)
    {
        row = i;
    }

    public void setColumn(int i)
    {
        col = i;
    }

    public void setPlacement(int r, int c)
    {
        row = r;
        col = c;
    }

    public Side getSide()
    {
        return side;
    }

    public void setSide(Side s)
    {
        side = s;
    }

    public Image getImage()
    {
        return image;
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
                Piece p = getUp(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    upDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Rook || p instanceof Queen))
                    {
                        moves.add(getUp(i));
                    }
                }
            }

            if(!leftDone && leftInBounds(i) && getLeft(i).containsPiece())
            {
                Piece p = getLeft(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    leftDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Rook || p instanceof Queen))
                    {
                        moves.add(getLeft(i));
                    }
                }
            }

            if(!upLeftDone && upLeftInBounds(i) && getUpLeft(i).containsPiece())
            {
                Piece p = getUpLeft(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    upLeftDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Bishop || p instanceof Queen))
                    {
                        moves.add(getUpLeft(i));
                    }
                    else if(i == 1 && p.getSide().equals(Side.BLACK) && !p.getSide().equals(side) && p instanceof Pawn)
                    {
                        moves.add(getUpLeft(i));
                    }
                }
            }

            if(!downLeftDone && downLeftInBounds(i) && getDownLeft(i).containsPiece())
            {
                Piece p = getDownLeft(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    downLeftDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Bishop || p instanceof Queen))
                    {
                        moves.add(getDownLeft(i));
                    }
                    else if(i == 1 && p.getSide().equals(Side.WHITE) && !p.getSide().equals(side) && p instanceof Pawn)
                    {
                        moves.add(getDownLeft(i));
                    }
                }
            }

            if(!downDone && downInBounds(i) && getDown(i).containsPiece())
            {
                Piece p = getDown(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    downDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Rook || p instanceof Queen))
                    {
                        moves.add(getDown(i));
                    }
                }
            }

            if(!downRightDone && downRightInBounds(i) && getDownRight(i).containsPiece())
            {
                Piece p = getDownRight(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    downRightDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Bishop) || p instanceof Queen)
                    {
                        moves.add(getDownRight(i));
                    }
                    else if(i == 1 && p.getSide().equals(Side.WHITE) && !p.getSide().equals(side) && p instanceof Pawn)
                    {
                        moves.add(getDownRight(i));
                    }
                }
            }

            if(!rightDone && rightInBounds(i) && getRight(i).containsPiece())
            {
                Piece p = getRight(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    rightDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Rook || p instanceof Queen))
                    {
                        moves.add(getRight(i));
                    }
                }
            }

            if(!upRightDone && upRightInBounds(i) && getUpRight(i).containsPiece())
            {
                Piece p = getUpRight(i).getPiece();
                if(!p.getSide().equals(side) || p.getSide().equals(side))
                {
                    upRightDone = true;
                    if(!p.getSide().equals(side) && (p instanceof Bishop || p instanceof Queen))
                    {
                        moves.add(getUpRight(i));
                    }
                    else if(i == 1 && p.getSide().equals(Side.BLACK) && !p.getSide().equals(side) && p instanceof Pawn)
                    {
                        moves.add(getUpRight(i));
                    }
                }
            }
        }
        if(moves.size() == 0)
        {
            // TODO: changing piece type might break
            Side sideForKnight = (side.equals(Side.WHITE)) ? Side.WHITE : Side.BLACK;
            Piece knightCheck = new Knight(row, col, sideForKnight);
            //setType("knight");
            //ArrayList<Square> knightMoves = getLegalMoves();
            ArrayList<Square> knightMoves = knightCheck.getLegalMoves();



            for(Square square : knightMoves)
            {
                if(square.containsPiece())
                {
                    if(square.getPiece() instanceof Knight && !square.getPiece().getSide().equals(side))
                    {
                        moves.add(square);
                    }
                }
            }
            //setType("king");
        }
        return moves;
    }
}