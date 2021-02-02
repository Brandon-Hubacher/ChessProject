import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Square extends JPanel implements Serializable {
    private int[] squareCoord = new int[2];
    private Piece currentPiece;
    private boolean containsPiece = false;
    private int color;
    private Color squareColor;
    JLabel dotMove;
    JLabel attackedPieceOverlayLabel;
    protected transient Image tempImage;
    protected Point p;
    protected transient Image attackedPieceOverlayImage;
    protected String squareColorType;
    protected ChessBoard board;

    public Square(int row, int col, ChessBoard b) throws IOException
    {
        board = b;
        if (row % 2 == 0) {
            tempImage = (col % 2 == 0) ? ImageIO.read(new File("Pictures/gimpDot3.png")) : ImageIO.read(new File("Pictures/gimpDotDARK.png"));
            squareColorType = (col % 2 != 0) ? "dark" : "light";
            attackedPieceOverlayImage = (col % 2 != 0) ? ImageIO.read(new File("Pictures/attackedPieceLight.png")) : ImageIO.read(new File("Pictures/attackedPieceDark.png"));
        } else {
            tempImage = (col % 2 != 0) ? ImageIO.read(new File("Pictures/gimpDot3.png")) : ImageIO.read(new File("Pictures/gimpDotDARK.png"));
            squareColorType = (col % 2 == 0) ? "dark" : "light";
            attackedPieceOverlayImage = (col % 2 == 0) ? ImageIO.read(new File("Pictures/attackedPieceLight.png")) : ImageIO.read(new File("Pictures/attackedPieceDark.png"));
        }

        dotMove = new JLabel(new ImageIcon(tempImage));
        dotMove.setBounds(45, 45, 45, 45);

        attackedPieceOverlayLabel = new JLabel(new ImageIcon(attackedPieceOverlayImage));
        attackedPieceOverlayLabel.setBounds(125, 125, 125, 125);

        if(!(row > 7) && !(row < 0) && !(col > 7) && !(col < 0))
        {
            squareCoord[0] = row;
            squareCoord[1] = col;
            this.setVisible(true);
            if (row % 2 == 0)
            {
                color = (col % 2 != 0) ? 0 : 1;
            }
            else
            {
                color = (col % 2 == 0) ? 0 : 1;
            }
        }
        else
        {
            System.out.println("that square is out of bounds bro");
        }
        int side = (int) b.getSize().getHeight()/8;
        p = new Point((side * getSquareCol()) + ((side/2) - (dotMove.getWidth()/2)), (side * getSquareRow()) + ((side/2) - (dotMove.getWidth()/2)));

        dotMove.setLocation(p);
        attackedPieceOverlayLabel.setLocation((side * getSquareCol()) + ((side/2) - (attackedPieceOverlayLabel.getWidth()/2)), (side * getSquareRow()) + ((side/2) - (attackedPieceOverlayLabel.getWidth()/2)));

        ChessBoard.layeredPane.add(dotMove, JLayeredPane.POPUP_LAYER);
        ChessBoard.layeredPane.add(attackedPieceOverlayLabel, JLayeredPane.POPUP_LAYER);

        dotMove.setOpaque(false);
        dotMove.setVisible(false);
        attackedPieceOverlayLabel.setOpaque(false);
        attackedPieceOverlayLabel.setVisible(false);
    }

    public String getSquareColorType()
    {
        return squareColorType;
    }

    public void setSquareColor(Color c)
    {
        squareColor = c;
    }

    public int getColor()
    {
        return color;
    }

    public boolean containsPiece()
    {
        return containsPiece;
    }

    public void setContainsPiece(boolean b)
    {
        containsPiece = b;
    }

    public boolean equalsSquare(Square s)
    {
        return getSquareRow() == s.getSquareRow() && getSquareCol() == s.getSquareCol();
    }

    public void setPiece(Piece p)
    {
        currentPiece = p;
        containsPiece = true;
        p.setPlacement(getSquareRow(), getSquareCol());
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(currentPiece != null)
        {
            int coord = (this.getWidth()/2)-30;
            g.drawImage(currentPiece.getImage(), coord, coord, null);
        }
    }

    public int getSquareRow()
    {
        return squareCoord[0];
    }

    public int getSquareCol()
    {
        return squareCoord[1];
    }

    public Piece getPiece()
    {
        if(currentPiece == null)
        {
            System.out.println(getSquareRow()+", "+getSquareCol());
            throw new NullPointerException("There is no piece there silly at: "+getSquareRow()+", "+getSquareCol()+", counter: "+MinMax.counter);
        }
        else
        {
            return currentPiece;
        }
    }

    public void removePiece()
    {
        currentPiece = null;
        containsPiece = false;
    }

    public Color revertColor()
    {
        if (squareCoord[0] % 2 == 0) {
            return (squareCoord[1] % 2 != 0) ? Color.decode("#B58863") : Color.decode("#F0D9B5");
        } else {
            return (squareCoord[1] % 2 == 0) ? Color.decode("#B58863") : Color.decode("#F0D9B5");
        }
    }

    public void revertColor(ArrayList<Square> s)
    {
        if(s.isEmpty())
        {
            return;
        }
        for(Square square : s)
        {
            if(square != null)
            {
                square.dotMove.setVisible(false);
                square.attackedPieceOverlayLabel.setVisible(false);
            }
        }
    }

    public Square getLeft(int num)
{
    return board.chessBoard[squareCoord[0]][squareCoord[1]-num];
}

    public Square getRight(int num)
    {
        return board.chessBoard[squareCoord[0]][squareCoord[1]+num];
    }

    public Square getUp(int num)
    {
        return board.chessBoard[squareCoord[0]-num][squareCoord[1]];
    }

    public Square getDown(int num)
    {
        return board.chessBoard[squareCoord[0]+num][squareCoord[1]];
    }

    public Square getUpLeft(int num)
    {
        return board.chessBoard[squareCoord[0]-num][squareCoord[1]-num];
    }

    public Square getUpRight(int num)
    {
        return board.chessBoard[squareCoord[0]-num][squareCoord[1]+num];
    }

    public Square getDownLeft(int num)
    {
        return board.chessBoard[squareCoord[0]+num][squareCoord[1]-num];
    }

    public Square getDownRight(int num)
    {
        return board.chessBoard[squareCoord[0]+num][squareCoord[1]+num];
    }

    public boolean leftInBounds(int num)
    {
        return getSquareCol()-num >= 0;
    }

    public boolean rightInBounds(int num)
    {
        return getSquareCol()+num <= 7;
    }

    public boolean upInBounds(int num)
    {
        return getSquareRow()-num >= 0;
    }

    public boolean downInBounds(int num)
    {
        return getSquareRow()+num <= 7;
    }
}