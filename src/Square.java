import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Square extends JPanel { // extends JPanel was originally extends JComponent
    //private String c;
    //private String pieceColor;
    //private String piece;

    //private int row;
    //private int col;
    //private Piece piece;
    private int[] squareCoord = new int[2];
    //private Pieces currentPieces;
    private Piece currentPiece;
    private boolean getRidOf;
    private boolean containsPiece = false;
    private int color;
    private Color squareColor;
    JLabel dotMove;
    JLabel attackedPieceOverlayLabel;
    protected Image tempImage;
    //protected Image tempImage;
    protected Point p;
    protected Image attackedPieceOverlayImage;
    protected Image lastMoveImage;
    protected JLabel lastMoveLabel;
    protected String squareColorType;

    public Square(int row, int col) throws IOException
    {
        //dotMove = new JLabel(new imageIcon)
        //tempImage = ImageIO.read(new File("Pictures/gimpDot1.png"));
        //tempImageLight = ImageIO.read(new File("Pictures/gimpDot3.png"));
        //dotMoveLight = new JLabel(new ImageIcon(tempImageLight));
        //dotMoveLight.setBounds(45, 45, 45, 45);

        //tempImageDark = ImageIO.read(new File("Pictures/gimpDotDARK.png"));
        //dotMoveDark = new JLabel(new ImageIcon(tempImageDark));
        //dotMoveDark.setBounds(45, 45, 45, 45);

        if (row % 2 == 0) {
            //color = (col % 2 != 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
            tempImage = (col % 2 != 0) ? ImageIO.read(new File("Pictures/gimpDot3.png")) : ImageIO.read(new File("Pictures/gimpDotDARK.png"));
            squareColorType = (col % 2 != 0) ? "light" : "dark";
            attackedPieceOverlayImage = (col % 2 != 0) ? ImageIO.read(new File("Pictures/attackedPieceDark.png")) : ImageIO.read(new File("Pictures/attackedPieceLight.png"));
            lastMoveImage = (col % 2 != 0) ? ImageIO.read(new File("Pictures/lastMoveOverlay.png")) : ImageIO.read(new File("Pictures/lastMoveOverlay.png"));
        } else {
            //color = (col % 2 == 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
            tempImage = (col % 2 == 0) ? ImageIO.read(new File("Pictures/gimpDot3.png")) : ImageIO.read(new File("Pictures/gimpDotDARK.png"));
            squareColorType = (col % 2 == 0) ? "light" : "dark";
            attackedPieceOverlayImage = (col % 2 != 0) ? ImageIO.read(new File("Pictures/attackedPieceDark.png")) : ImageIO.read(new File("Pictures/attackedPieceLight.png"));
            lastMoveImage = (col % 2 != 0) ? ImageIO.read(new File("Pictures/lastMoveOverlay.png")) : ImageIO.read(new File("Pictures/lastMoveOverlay.png"));
        }

        dotMove = new JLabel(new ImageIcon(tempImage));
        dotMove.setBounds(45, 45, 45, 45);

        attackedPieceOverlayLabel = new JLabel(new ImageIcon(attackedPieceOverlayImage));
        attackedPieceOverlayLabel.setBounds(125, 125, 125, 125);

        lastMoveLabel = new JLabel(new ImageIcon(lastMoveImage));
        lastMoveLabel.setBounds(125, 125, 125, 125);

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
        System.out.println(ChessBoard.size.getHeight());
        int side = (int) ChessBoard.size.getHeight()/8;
        p = new Point((side * getSquareCol()) + ((side/2) - (dotMove.getWidth()/2)), (side * getSquareRow()) + ((side/2) - (dotMove.getWidth()/2)));

        dotMove.setLocation(p);
        attackedPieceOverlayLabel.setLocation((side * getSquareCol()) + ((side/2) - (attackedPieceOverlayLabel.getWidth()/2)), (side * getSquareRow()) + ((side/2) - (attackedPieceOverlayLabel.getWidth()/2)));
        lastMoveLabel.setLocation((side * getSquareCol()) + ((side/2) - (attackedPieceOverlayLabel.getWidth()/2)), (side * getSquareRow()) + ((side/2) - (attackedPieceOverlayLabel.getWidth()/2)));

        ChessBoard.layeredPane.add(dotMove, JLayeredPane.POPUP_LAYER);
        ChessBoard.layeredPane.add(attackedPieceOverlayLabel, JLayeredPane.POPUP_LAYER);
        //ChessBoard.layeredPane.add(lastMoveLabel, JLayeredPane.DEFAULT_LAYER, 0);


        dotMove.setOpaque(false);
        dotMove.setVisible(false);
        attackedPieceOverlayLabel.setOpaque(false);
        attackedPieceOverlayLabel.setVisible(false);
    }

    public String getSquareColorType()
    {
        return squareColorType;
    }

    public Color getSquareColor() { return squareColor; }

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
    /*
    public boolean contains(Pieces p)
    {
        return containsPiece && p == currentPieces;
    }

     */

    public void setContainsPiece(boolean b)
    {
        containsPiece = b;
    }

    public boolean equalsSquare(Square s)
    {
        return getSquareRow() == s.getSquareRow() && getSquareCol() == s.getSquareCol();
    }
/*
    public void set(Pieces p)
    {
        currentPieces = p;
        containsPiece = true;
    }

 */

    public void setPiece(Piece p)
    {
        currentPiece = p;
        containsPiece = true;
        //p.setPlacement(getSquareRow(), getSquareCol());
    }

    /*
    public void draw(Graphics2D g)
    {
        g.fillOval(20, 20, 20, 20);
    }

     */

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(currentPiece != null)
        {
            int coord = (this.getWidth()/2)-30;
            g.drawImage(currentPiece.getImage(), coord, coord, null);
        }

        //for(Square s : ChessBoard.currentLegalMoves)
        //{
        //    g.drawOval(62,62, 5, 5);
        //}
        /*
        if(getRidOf)
        {
            g.setColor(Color.BLACK);
            getRidOf = false;
        }

         */
    }

    public void paintComponent(Graphics g, JLabel dotMove)
    {
        //super.paintComponent(g);
        //g.drawImage(new , 62,62,null);
        //g.drawOval(62,62, 30, 30);
        //g.fillOval(62, 62, 30, 30);
    }


/*
public void paintComponent(Graphics g)
{
    super.paintComponent(g);
    if(currentPieces != null)
    {
        int coord = 62-30;
        g.drawImage(currentPieces.getImage(), coord, coord, null);
    }

    if(getRidOf)
    {
        g.setColor(Color.BLACK);
        getRidOf = false;
    }




}

 */


    public int getSquareRow()
    {
        return squareCoord[0];
    }

    public int getSquareCol()
    {
        return squareCoord[1];
    }
/*
    public Pieces getPiece()
    {
        if(currentPieces == null)
        {
            throw new NullPointerException("There is no piece there silly");
        }
        else
        {
            return currentPieces;
        }
    }

 */
    public Piece getPiece()
    {
        if(currentPiece == null)
        {
            throw new NullPointerException("There is no piece there silly");
        }
        else
        {
            return currentPiece;
        }
    }
/*
    public void removePiece()
    {
        currentPieces = null;
        containsPiece = false;
    }

 */
    public void removePiece()
    {
        currentPiece = null;
        containsPiece = false;
    }

    public Color revertColor()
    {
        if (squareCoord[0] % 2 == 0) {
            //return (squareCoord[1] % 2 != 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
            //color = (col % 2 != 0) ? Color.decode("#F0D9B5") : Color.decode("#B58863");
            return (squareCoord[1] % 2 != 0) ? Color.decode("#F0D9B5") : Color.decode("#B58863");
        } else {
            //return (squareCoord[1] % 2 == 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
            return (squareCoord[1] % 2 == 0) ? Color.decode("#F0D9B5") : Color.decode("#B58863");
        }
    }



    public void revertColor(ArrayList<Square> s)
    {
        for(Square square : s)
        {
            if(square != null)
            {
                //square.setBackground(square.revertColor());
                square.dotMove.setVisible(false);
                square.attackedPieceOverlayLabel.setVisible(false);
            }
        }
    }

    public Square getLeft(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]][squareCoord[1]-num];
    }

    public Square getRight(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]][squareCoord[1]+num];
    }

    public Square getUp(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]-num][squareCoord[1]];
    }

    public Square getDown(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]+num][squareCoord[1]];
    }

    public Square getUpLeft(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]-num][squareCoord[1]-num];
    }

    public Square getUpRight(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]-num][squareCoord[1]+num];
    }

    public Square getDownLeft(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]+num][squareCoord[1]-num];
    }

    public Square getDownRight(int num)
    {
        return ChessBoard.chessBoard[squareCoord[0]+num][squareCoord[1]+num];
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