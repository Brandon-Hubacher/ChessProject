import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ChessBoard extends JPanel implements MouseListener, MouseMotionListener, Serializable {
    //public static Square[][] chessBoard = new Square[8][8];
    public Square[][] chessBoard = new Square[8][8];
    //public Square[][] chessBoard = new Square[8][8];
    JPanel f = new JPanel();
    public static boolean whiteTurn;
    public static boolean whiteInCheck = false;
    public static boolean blackInCheck = false;
    public Square whiteKing;
    public Square blackKing;
    public ArrayList<Square> thisIstheWay = new ArrayList<>();
    public ArrayList<Square> expCheck = new ArrayList<>();
    public ArrayList<Square> availableKingMoves = new ArrayList<>();
    public ArrayList<Square> availablePieceMoves = new ArrayList<>();
    public boolean promotionDone = true;
    public ArrayList<Piece> original;
    public boolean blackKingSideRookMoved = false;
    public boolean blackQueenSideRookMoved = false;
    public boolean blackKingMoved = false;
    public boolean whiteKingSideRookMoved = false;
    public boolean whiteQueenSideRookMoved = false;
    public boolean whiteKingMoved;
    public int blackKingSideRookMovedDepth;
    public int blackQueenSideRookMovedDepth;
    public int blackKingMovedDepth;
    public int whiteKingSideRookMovedDepth;
    public int whiteQueenSideRookMovedDepth;
    public int whiteKingMovedDepth;
    public ArrayList<Integer> kingInCheckDepth = new ArrayList<>();

    public Square enPassant = null;
    public int fiftyMoveDraw = 0;
    public int threeFoldRep = 1;
    public int fiveFoldRep = 1;
    public int modFour = 0;
    public Square[][] repetition = new Square[8][8];
    public boolean canEnPassant = false;
    public ArrayList<Piece> whitesCapturedPiecesList = new ArrayList<>();
    public ArrayList<Piece> blacksCapturedPiecesList = new ArrayList<>();

    public HashMap<Piece, Move> whitesCapturedPiecesMap = new HashMap<>();
    public HashMap<Piece, Move> blacksCapturedPiecesMap = new HashMap<>();

    public Square from = null;
    boolean isPressed = false;
    public Square news;
    Point offset;
    public Square pressed;
    static JLayeredPane layeredPane;
    JLabel chessPiece;
    JLabel cover;
    boolean fromDrag;
    boolean mouseClickOnly;
    boolean runDragAndRelease;
    boolean runClicked;
    public ArrayList<Square> currentLegalMoves = new ArrayList<>();
    public static Dimension size;
    public Square lastMovedFrom;
    public Square lastMovedTo;

    public ChessBoard(ChessBoard b)
    {
        from = b.from;
        news = b.news;
        pressed = b.pressed;
        blackKing = b.blackKing;
        whiteKing = b.whiteKing;
        thisIstheWay = b.thisIstheWay;
        expCheck = b.expCheck;
        availableKingMoves = b.availableKingMoves;
        availablePieceMoves = b.availablePieceMoves;
        promotionDone = b.promotionDone;
        original = b.original;
        blackKingSideRookMoved = b.blackKingSideRookMoved;
        blackQueenSideRookMoved = b.blackQueenSideRookMoved;
        blackKingMoved = b.blackKingMoved;
        whiteKingSideRookMoved = b.whiteKingSideRookMoved;
        whiteQueenSideRookMoved = b.whiteQueenSideRookMoved;
        whiteKingMoved = b.whiteKingMoved;
        blackKingSideRookMovedDepth = b.blackKingSideRookMovedDepth;
        blackQueenSideRookMovedDepth = b.blackQueenSideRookMovedDepth;
        blackKingMovedDepth = b.blackKingMovedDepth;
        whiteKingSideRookMovedDepth = b.whiteKingSideRookMovedDepth;
        whiteQueenSideRookMovedDepth = b.whiteQueenSideRookMovedDepth;
        whiteKingMovedDepth = b.whiteKingMovedDepth;
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                //[i][z] = b.board[i][z];
                chessBoard[i][z] = b.chessBoard[i][z];
            }
        }
    }

    public ChessBoard() {
        whiteTurn = true;
        size = new Dimension(1000, 1000);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(size);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
        add(layeredPane);

        f.setLayout(new GridLayout(8, 8));
        f.setPreferredSize(size);
        f.setBounds(0, 0, size.width, size.height);
        //f.setBounds(0, 0, 1500, 1000);
        //add(f); // what does this do?
        layeredPane.add(f, JLayeredPane.DEFAULT_LAYER, 0);
        //addMouseMotionListener(this);
        //addMouseListener(this);
        Color color;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                try{
                    chessBoard[row][col] = new Square(row, col, this);
                    if (row % 2 == 0) {
                        //color = (col % 2 != 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
                        color = (col % 2 != 0) ? Color.decode("#B58863") : Color.decode("#F0D9B5");
                    } else {
                        //color = (col % 2 == 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
                        color = (col % 2 == 0) ? Color.decode("#B58863") : Color.decode("#F0D9B5");
                    }
                    chessBoard[row][col].setBackground(color);
                    chessBoard[row][col].setSquareColor(color);
                }
                catch(IOException e)
                {
                    System.out.println("ioexception in making square and coloring");
                }
                try{
                    if(row == 0)
                    {
                        if(col == 0 || col == 7) {chessBoard[row][col].setPiece(new Rook("Pictures/red rook.png", row, col, Piece.Side.BLACK, this));}
                        if(col == 1 || col == 6) { chessBoard[row][col].setPiece(new Knight("Pictures/red knight.png", row, col, Piece.Side.BLACK, this));}
                        if(col == 2 || col == 5) { chessBoard[row][col].setPiece(new Bishop("Pictures/red bishop.png", row, col, Piece.Side.BLACK, this));}
                        if(col == 3) { chessBoard[row][col].setPiece(new Queen("Pictures/red queen.png", row, col, Piece.Side.BLACK, this));}
                        if(col == 4)
                        {
                            chessBoard[row][col].setPiece(new King("Pictures/red king.png", row, col, Piece.Side.BLACK, this));
                            blackKing = chessBoard[row][col];
                        }
                    }
                    else if(row == 1)
                    {
                        chessBoard[row][col].setPiece(new Pawn("Pictures/red pawn.png", row, col, Piece.Side.BLACK, this));
                    }
                    else if(row == 6)
                    {
                        chessBoard[row][col].setPiece(new Pawn("Pictures/pawn.png", row, col, Piece.Side.WHITE, this));
                    }
                    else if(row == 7)
                    {
                        if(col == 0 || col == 7) { chessBoard[row][col].setPiece(new Rook("Pictures/rook.png", row, col, Piece.Side.WHITE, this));}
                        if(col == 1 || col == 6) { chessBoard[row][col].setPiece(new Knight("Pictures/knight.png", row, col, Piece.Side.WHITE, this));}
                        if(col == 2 || col == 5) { chessBoard[row][col].setPiece(new Bishop("Pictures/bishop.png", row, col, Piece.Side.WHITE, this));}
                        if(col == 3) { chessBoard[row][col].setPiece(new Queen("Pictures/queen.png", row, col, Piece.Side.WHITE, this));}
                        if(col == 4)
                        {
                            chessBoard[row][col].setPiece(new King("Pictures/king.png", row, col, Piece.Side.WHITE, this));
                            whiteKing = chessBoard[row][col];
                        }
                    }
                }
                catch (IOException ex)
                {
                    System.out.println("lol ioexception");
                }
                //layeredPane.add(chessBoard[row][col]);
                //chessBoard[row][col] = chessBoard[row][col];
                f.add(chessBoard[row][col]);
            }
        }

    }



    public ArrayList<Square> getAllMoves()
    {
        ArrayList<Square> allMoves = new ArrayList<>();
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                if(chessBoard[i][z].containsPiece())
                {
                    if(chessBoard[i][z].getPiece().getSide().equals(Piece.Side.WHITE) && whiteTurn)
                    {
                        allMoves.addAll(getMoves(chessBoard[i][z]));
                    }
                    else if(chessBoard[i][z].getPiece().getSide().equals(Piece.Side.BLACK) && !whiteTurn)
                    {
                        allMoves.addAll(getMoves(chessBoard[i][z]));
                    }
                }
            }
        }
        return allMoves;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                Square s = chessBoard[x][y];
                s.paintComponent(g);
            }
        }
        /*
        for(Square s : currentLegalMoves)
        {
            JLabel dotMove = new JLabel();
            dotMove.setVisible(true);
            dotMove.setOpaque(true);
            layeredPane.add(dotMove, JLayeredPane.POPUP_LAYER, 0);
            System.out.println("painting this legal move: "+s.getSquareRow()+", "+s.getSquareCol());
            s.paintComponent(g, dotMove);
        }

         */
    }

    /*
    public void paintComponent(Graphics2D g)
    {
        super.paintComponent(g);
        for(Square s : currentLegalMoves)
        {
            s.paintComponent(g, s);
        }
    }

     */


    // TODO: Implementing dragging piece
    // TODO: Make highlighted transparent circles rather than solid colored squares

    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
    public void mousePressed(MouseEvent e)
    {
        runClicked = true;
        runDragAndRelease = true;
        int row = e.getY()/(e.getComponent().getHeight() / 8);
        int col = e.getX()/(e.getComponent().getWidth() / 8);
        pressed = chessBoard[row][col];
        if(pressed.containsPiece())
        {
            // TODO: make sure to account for blue squares
            if (((pressed.getPiece().getSide().equals(Piece.Side.WHITE) && !whiteTurn) || ((pressed.getPiece().getSide().equals(Piece.Side.BLACK) && whiteTurn))) && !isPressed /*pressed.getBackground() != Color.BLUE && pressed.getBackground() != Color.WHITE*/)
            {
                runDragAndRelease = false;
                return;
            }
        }
        if(!isPressed && (!pressed.dotMove.isVisible() && !pressed.attackedPieceOverlayLabel.isVisible()) /*pressed.getBackground() != Color.BLUE*/ && pressed.getBackground() != Color.WHITE && !pressed.containsPiece())
        {
            runDragAndRelease = false;
            return;
        }
        if(isPressed && (!pressed.dotMove.isVisible() && !pressed.attackedPieceOverlayLabel.isVisible())/*pressed.getBackground() != Color.BLUE*/ && pressed.getBackground() != Color.WHITE && !pressed.containsPiece())
        {
            runDragAndRelease = false;
            clickAway();
            return;
        }
        if(isPressed && (!pressed.dotMove.isVisible() && !pressed.attackedPieceOverlayLabel.isVisible()) /*pressed.getBackground() != Color.BLUE*/ && pressed.getBackground() != Color.WHITE && pressed.containsPiece() && !pressed.getPiece().getSide().equals(from.getPiece().getSide()))
        {
            runDragAndRelease = false;
            clickAway();
            return;
        }

        Piece pressedPiece;
        Piece.Side pressedSide;
        //isPressed = false;
        if((!pressed.dotMove.isVisible() && !pressed.attackedPieceOverlayLabel.isVisible()) /*pressed.getBackground() != Color.BLUE*/ && pressed.getBackground() != Color.WHITE && pressed.containsPiece())
        {
            if(isPressed && from.getPiece().getSide().equals(pressed.getPiece().getSide()) && !from.equalsSquare(pressed))
            {
                pressDiffPiece(pressed);
                //runDragAndRelease = false;
                //runClicked = false;
                //return;
            }

            chessPiece = null;
            cover = null;
            pressedPiece = pressed.getPiece();
            pressedSide = pressedPiece.getSide();

            //olds = pressed;
            chessPiece = new JLabel(new ImageIcon(pressed.getPiece().getImage()));
            chessPiece.setBounds(60, 60, 60, 60);
            Component c = f.findComponentAt(e.getX(), e.getY());

            Point parentLocation = c.getLocation();
            chessPiece.setOpaque(false);
            chessPiece.setVisible(true);
            cover = new JLabel();
            cover.setBounds(pressed.getHeight(), pressed.getHeight(), pressed.getHeight(), pressed.getHeight());
            cover.setLocation(parentLocation);
            //cover.setBackground(pressed.getSquareColor());
            //cover.setBackground(Color.BLACK);
            cover.setBackground(Color.decode("#646D40"));
            //cover.getBackground().darker();
            repaint();
            cover.setVisible(true);
            cover.setOpaque(true);

            if(pressed.getPiece() instanceof Pawn)
            {
                chessPiece.setLocation(e.getX() - (chessPiece.getHeight()/2) - 1, e.getY() - (chessPiece.getHeight()/2));
            }
            else
            {
                chessPiece.setLocation(e.getX() - (chessPiece.getHeight()/2), e.getY() - (chessPiece.getHeight()/2));
            }

            layeredPane.add(cover, JLayeredPane.POPUP_LAYER, 0);
            layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER, 0);
            layeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }

        if(!isPressed && pressed.containsPiece() && promotionDone)
        {
            from = pressed;
            ArrayList<Square> moves = getMoves(pressed);
            currentLegalMoves = moves;
            paintMoves(moves);
            isPressed = true;
            //repaint();
        }
        else if(isPressed)
        {
            news = pressed; // might break
            if(pressed.containsPiece() && pressed.getBackground() != Color.WHITE && pressed.attackedPieceOverlayLabel.isVisible() /*pressed.getBackground() == Color.BLUE*/)
            {
                pressedPiece = pressed.getPiece();
                //String pressedType = pressedPiece.getType();
                pressedSide = pressedPiece.getSide();

                if(pressedSide.equals(Piece.Side.BLACK))
                {
                    whitesCapturedPiecesList.add(pressedPiece);
                }
                else
                {
                    blacksCapturedPiecesList.add(pressedPiece);
                }
            }

            if(promotionDone && (pressed.dotMove.isVisible() || pressed.attackedPieceOverlayLabel.isVisible()))
            {
                updateCastleRights();
                if(enPassant != null && !pressed.containsPiece())
                {
                    if(pressed.equalsSquare(enPassant))
                    {
                        captureEnPassant();
                    }
                    enPassant.setBackground(enPassant.revertColor());
                }
                enPassant = null;
                movePiece(pressed, row, col);
                //updateCastleRights();
                expCheck = exposedCheck(from, news);
            }
            //following movePiece, news contains the piece that was moved
            if(news.getPiece() instanceof Pawn && promotionDone && (news.getSquareRow() == 0 || news.getSquareRow() == 7))
            {
                original = generatePawnPromotion(news);
                runDragAndRelease = false;
            }
            else if(pressed.getBackground() == Color.WHITE)
            {
                runDragAndRelease = false;
                promotePawn();
            }
            if(promotionDone)
            {
                ArrayList<Square> check = news.getPiece().getLegalMoves();
                thisIstheWay = isInCheck(check, news);
                assessCheckMateAndStaleMate();
            }
        }
    }
    public void mouseDragged(MouseEvent e)
    {
        if(!runDragAndRelease)
        {
            return;
        }
        fromDrag = true;
        if(pressed.containsPiece())
        {
            if(pressed.getPiece() instanceof Pawn)
            {
                chessPiece.setLocation(e.getX() - (chessPiece.getHeight()/2) - 1, e.getY() - (chessPiece.getHeight()/2));
            }
            else
            {
                chessPiece.setLocation(e.getX() - (chessPiece.getHeight()/2), e.getY() - (chessPiece.getHeight()/2));
            }
        }
    }
    public void mouseReleased(MouseEvent e)
    {
        layeredPane.setCursor(null);
        if(chessPiece == null) return;
        chessPiece.setVisible(false);
        cover.setVisible(false);
        layeredPane.remove(cover);
        layeredPane.remove(chessPiece);
        chessPiece.setVisible(true);
        if(!runDragAndRelease/* || !fromDrag*/)
        {
            return;
        }
        int row = e.getY()/(e.getComponent().getHeight() / 8);
        int col = e.getX()/(e.getComponent().getWidth() / 8);
        pressed = chessBoard[row][col];
        news = pressed; // might break
        if(pressed.equalsSquare(from))
        {
            return;
        }
        // TODO: and pressed background isn't that green color
        if(!pressed.dotMove.isVisible() && !pressed.attackedPieceOverlayLabel.isVisible()/*pressed.getBackground() != Color.BLUE*/ && pressed.getBackground() != Color.WHITE && !pressed.getBackground().equals(Color.decode("#646D40")) && from != null)
        {
            clickAway();
        }
        else if(pressed.getBackground().equals(Color.decode("#646D40"))) //TODO: background is that green color
        {
            isPressed = true;
        }
        else
        {
            if(pressed.containsPiece() && pressed.getBackground() != Color.WHITE)
            {
                Piece pressedPiece = pressed.getPiece();
                //String pressedType = pressedPiece.getType();
                Piece.Side pressedSide = pressedPiece.getSide();

                if(pressedSide.equals(Piece.Side.BLACK))
                {
                    whitesCapturedPiecesList.add(pressedPiece);
                }
                else
                {
                    blacksCapturedPiecesList.add(pressedPiece);
                }
            }

            if(promotionDone && (pressed.dotMove.isVisible() || pressed.attackedPieceOverlayLabel.isVisible()))
            {
                updateCastleRights();
                if(enPassant != null && !pressed.containsPiece())
                {
                    if(pressed.equalsSquare(enPassant))
                    {
                        captureEnPassant();
                    }
                    enPassant.setBackground(enPassant.revertColor());
                }
                enPassant = null;
                movePiece(pressed, row, col);
                expCheck = exposedCheck(from, news);
            }
            //following movePiece, news contains the piece that was moved
            if(promotionDone && (news.getPiece() instanceof Pawn) && (news.getSquareRow() == 0 || news.getSquareRow() == 7))
            {
                original = generatePawnPromotion(news);
            }
            else if(pressed.getBackground() == Color.WHITE)
            {
                promotePawn();
            }
            if(promotionDone)
            {
                ArrayList<Square> check = news.getPiece().getLegalMoves();
                thisIstheWay = isInCheck(check, news);
                assessCheckMateAndStaleMate();
            }
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        /*
        if(!runClicked)
        {
            return;
        }
        System.out.println("mouse clicked!!!");
        int row = e.getY()/(e.getComponent().getHeight() / 8);
        int col = e.getX()/(e.getComponent().getWidth() / 8);
        pressed = chessBoard[row][col];
        if(!isPressed && pressed.getBackground() != Color.BLUE && pressed.getBackground() != Color.WHITE && !pressed.containsPiece())
        {
            System.out.println("pressing nothing");
            return;
        }
        else if(isPressed && pressed.getBackground() != Color.BLUE && pressed.getBackground() != Color.WHITE && !pressed.containsPiece())
        {
            System.out.println("clicking away in mousePressed");
            clickAway();
            return;
        }
        if(pressed.getBackground() != Color.BLUE && pressed.getBackground() != Color.WHITE && !pressed.containsPiece())
        {
            System.out.println("clicking nothing");
            if(isPressed)
            {
                clickAway();
            }
            return;
        }

        if(!isPressed && promotionDone)
        {
            if(pressed.containsPiece())
            {
                Piece pressedPiece = pressed.getPiece();
                Piece.Side pressedSide = pressedPiece.getSide();

                if((pressedSide.equals(Piece.Side.WHITE) && !whiteTurn) || ((pressedSide.equals(Piece.Side.BLACK) && whiteTurn)))
                {
                    System.out.println("wait ur turn");
                }
                else // it's your turn
                {
                    olds = pressed;
                    ArrayList<Square> moves = getMoves(pressed);
                    paintMoves(moves);
                    isPressed = true;
                    repaint();
                }
            }
            else if(!pressed.containsPiece())
            {
                System.out.println("have fun clicking around");
            }
        }
        else if(isPressed)
        {
            news = pressed; // might break
            if(pressed.getBackground() != Color.BLUE && pressed.getBackground() != Color.WHITE)
            {
                if(pressed.containsPiece() && olds.getPiece().getSide().equals(pressed.getPiece().getSide()) && promotionDone)
                {
                    Piece pressedPiece = pressed.getPiece();

                    if(!olds.equalsSquare(pressed))
                    {
                        System.out.println("you're pressing a diff piece");
                        pressDiffPiece(pressed);
                        repaint();
                    }
                    else
                    {
                        System.out.println("you're pressing the same piece CLICK");
                    }
                }
                else if(promotionDone)
                {
                    clickAway();
                }
                else
                {
                    System.out.println("promote your pawn pwease");
                }
            }
            else
            {
                if(pressed.containsPiece() && pressed.getBackground() != Color.WHITE)
                {
                    Piece pressedPiece = pressed.getPiece();
                    //String pressedType = pressedPiece.getType();
                    Piece.Side pressedSide = pressedPiece.getSide();

                    if(pressedSide.equals(Piece.Side.BLACK))
                    {
                        whitesCapturedPieces.add(pressedPiece);
                    }
                    else
                    {
                        blacksCapturedPieces.add(pressedPiece);
                    }
                }

                if(promotionDone)
                {
                    updateCastleRights();
                    if(enPassant != null && !pressed.containsPiece())
                    {
                        if(pressed.equalsSquare(enPassant))
                        {
                            captureEnPassant();
                        }
                        enPassant.setBackground(enPassant.revertColor());
                    }
                    enPassant = null;
                    movePiece(pressed, row, col);
                    expCheck = exposedCheck(olds, news);
                }
                //following movePiece, news contains the piece that was moved
                if(news.getPiece() instanceof Pawn && promotionDone && (news.getSquareRow() == 0 || news.getSquareRow() == 7))
                {
                    original = generatePawnPromotion(news);
                }
                else if(pressed.getBackground() == Color.WHITE)
                {
                    promotePawn();
                }
                if(promotionDone)
                {
                    ArrayList<Square> check = news.getPiece().getLegalMoves();
                    thisIstheWay = isInCheck(check, news);
                    assessCheckMateAndStaleMate();
                }
            }
        }
        */
    }


    public void captureEnPassant()
    {
        Square removePiece;
        if(from.getPiece().getSide().equals(Piece.Side.WHITE) && from.getPiece() instanceof Pawn)
        {
            removePiece = enPassant.getDown(1);
            removePiece.removePiece();
        }
        else if(from.getPiece().getSide().equals(Piece.Side.BLACK) && from.getPiece() instanceof Pawn)
        {
            removePiece = enPassant.getUp(1);
            removePiece.removePiece();
        }
        ((Pawn) from.getPiece()).setCapturedEnPassantAt(enPassant);
    }

    public void clickAway()
    {
        /*
        if(lastMovedFrom == null && lastMovedTo == null)
        {
            lastMovedFrom = olds;
            lastMovedTo = pressed;
        }

         */
        /*
        else
        {
            lastMovedFrom.setBackground(lastMovedFrom.revertColor());
            lastMovedTo.setBackground(lastMovedTo.revertColor());
            lastMovedFrom = olds;
            lastMovedTo = pressed;
        }

         */

        from.setBackground(from.revertColor());
        from.revertColor(from.getPiece().getLegalMoves());
        if(lastMovedFrom != null && lastMovedTo != null)
        {
            if(lastMovedFrom.getSquareColorType().equalsIgnoreCase("light"))
            {
                lastMovedFrom.setBackground(Color.decode("#CED26E"));
            }
            else
            {
                //olds.setBackground(Color.decode("#BBBF63"));
                lastMovedFrom.setBackground(Color.decode("#A2A600"));
            }
            if(lastMovedTo.getSquareColorType().equalsIgnoreCase("light"))
            {
                lastMovedTo.setBackground(Color.decode("#CED26E"));
            }
            else
            {
                //olds.setBackground(Color.decode("#BBBF63"));
                lastMovedTo.setBackground(Color.decode("#A2A600"));
            }
        }
        repaint();
        isPressed = false;
    }

    public ArrayList<Square> getMoves(Square pressed)
    {
        ArrayList<Square> moves;
        if(whiteInCheck || blackInCheck)
        {
            moves = movesIfInCheck(pressed);
        }
        else
        {
            //TODO: en passant might not be accounted for in isBlocking or ifBlocking
            moves = movesIfNotInCheck(pressed);
        }
        return moves;
    }

    public ArrayList<Square> movesIfNotInCheck(Square pressed)
    {
        ArrayList<Square> moves = new ArrayList<>();
        if(pressed.getPiece() instanceof King)
        {
            availableKingMoves = whereKingCanGo(from);
            ArrayList<Square> castleMoves = getCastleMoves();
            availableKingMoves.addAll(castleMoves);
            moves = availableKingMoves;
        }
        else
        {
            boolean enPassantCheck = false;
            if(pressed.getPiece() instanceof Pawn)
            {
                Piece.Side movingSide = pressed.getPiece().getSide();
                if(pressed.getSquareCol() == 0)
                {
                    if(pressed.getRight(1).containsPiece())
                    {
                        if(pressed.getRight(1).getPiece() instanceof Pawn && !pressed.getRight(1).getPiece().getSide().equals(movingSide))
                        {
                            enPassantCheck = true;
                        }
                    }
                }
                else if(pressed.getSquareCol() == 7)
                {
                    if(pressed.getLeft(1).containsPiece())
                    {
                        if(pressed.getLeft(1).getPiece() instanceof Pawn && !pressed.getLeft(1).getPiece().getSide().equals(movingSide))
                        {
                            enPassantCheck = true;
                        }
                    }
                }
                else
                {
                    if(pressed.getRight(1).containsPiece())
                    {
                        if(pressed.getRight(1).getPiece() instanceof Pawn && !pressed.getRight(1).getPiece().getSide().equals(movingSide))
                        {
                            enPassantCheck = true;
                        }
                    }
                    else if(pressed.getLeft(1).containsPiece())
                    {
                        if(pressed.getLeft(1).getPiece() instanceof Pawn && !pressed.getLeft(1).getPiece().getSide().equals(movingSide))
                        {
                            enPassantCheck = true;
                        }
                    }
                }
            }
            if(enPassantCheck && /*enPassant != null*/ ((Pawn) pressed.getPiece()).getToCaptureEnPassantAt() != null && pressed.getPiece() instanceof Pawn)
            {
                int rowTrav = (pressed.getPiece().getSide().equals(Piece.Side.WHITE)) ? -1 : 1;
                if((pressed.getSquareRow() + rowTrav == enPassant.getSquareRow()) && ((pressed.getSquareCol() + 1 == enPassant.getSquareCol()) || (pressed.getSquareCol() - 1 == enPassant.getSquareCol())))
                {
                    if((enPassant.getSquareRow() == 2 && !pressed.getPiece().getSide().equals(Piece.Side.BLACK)) || (enPassant.getSquareRow() == 5 && !pressed.getPiece().getSide().equals(Piece.Side.WHITE)))
                    {
                        //enPassant.setBackground(Color.BLUE);
                        //repaint();
                        canEnPassant = true;
                        moves.add(enPassant);
                    }
                }
            }
            ArrayList<Square> blocking = isBlocking(from);
            if(blocking == null)
            {
                moves.addAll(from.getPiece().getLegalMoves());
                availablePieceMoves = moves;
            }
            else
            {
                moves = ifBlocking(blocking, canEnPassant);
                availablePieceMoves = moves;
            }
        }
        return moves;
    }

    public void assessCheckMateAndStaleMate()
    {
        if(whiteInCheck || blackInCheck)
        {
            boolean inCheckMate = noAvailableMoves();
            if(inCheckMate)
            {
                System.out.println("CHECKMATEEEEEEE GAMEE OVERERERERERER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        else
        {
            boolean inStaleMate = noAvailableMoves();
            if(inStaleMate)
            {
                System.out.println("STALEMATE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
    }

    public void promotePawn()
    {
        // TODO: get rid of cancelButton
        int rowTrav = (news.getPiece().getSide().equals(Piece.Side.WHITE)) ? 1 : -1;
        ArrayList<Square> toRevert = new ArrayList<>();
        int origRow = from.getSquareRow() + (-1 * rowTrav);
        int origCol = news.getSquareCol();
        for(int i = 0; i < original.size(); i++)
        {
            if(i == 0)
            {
                toRevert.add(chessBoard[origRow + (i * rowTrav)][origCol]);
                Piece temp = news.getPiece();
                chessBoard[origRow + (i * rowTrav)][origCol].removePiece();
                chessBoard[origRow + (i * rowTrav)][origCol].setPiece(temp);
                chessBoard[origRow + (i * rowTrav)][origCol].getPiece().setPlacement(origRow + (i * rowTrav), origCol);
            }
            else
            {
                toRevert.add(chessBoard[origRow + (i * rowTrav)][origCol]);
                chessBoard[origRow + (i * rowTrav)][origCol].removePiece();
                if(original.get(i) != null)
                {
                    chessBoard[origRow + (i * rowTrav)][origCol].setPiece(original.get(i));
                    chessBoard[origRow + (i * rowTrav)][origCol].getPiece().setPlacement(origRow + (i * rowTrav), origCol);
                }
            }
        }
        revertColor(toRevert);
        repaint();
        //System.out.println("toRevert.get(0): "+toRevert.get(0).getSquareRow()+", "+toRevert.get(0).getSquareCol());
        //System.out.println(toRevert.get(0).getPiece());
        from = news = toRevert.get(0);
        //System.out.println("news set to torevert: "+news.getPiece());
        promotionDone = true;
        isPressed = false;
    }

    public void updateCastleRights()
    {
        //System.out.println("update castle rights");
        if(from.getPiece() instanceof Rook)
        {
            if(from.getSquareRow() == 0 && from.getSquareCol() == 0)
            {
                blackQueenSideRookMoved = true;
            }
            else if(from.getSquareRow() == 0 && from.getSquareCol() == 7)
            {
                blackKingSideRookMoved = true;
            }
            else if(from.getSquareRow() == 7 && from.getSquareCol() == 7)
            {
                whiteKingSideRookMoved = true;
            }
            else if(from.getSquareRow() == 7 && from.getSquareCol() == 0)
            {
                whiteQueenSideRookMoved = true;
            }
        }
        else if(from.getPiece() instanceof King)
        {
            if(from.getSquareRow() == 0 && from.getSquareCol() == 4)
            {
                blackKingMoved = true;
            }
            else if(from.getSquareRow() == 7 && from.getSquareCol() == 4)
            {
                whiteKingMoved = true;
            }
        }
    }

    public ArrayList<Move> getAllMoves(Piece.Side sideToMove, int depth)
    {
        ArrayList<Move> output = new ArrayList<>();
        boolean madeWhileInCheck = false;
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                Square checking = chessBoard[i][z];
                if(checking.containsPiece())
                {
                    if(checking.getPiece().getSide().equals(sideToMove))
                    {
                        from = checking;
                        ArrayList<Square> legalMoves = getMoves(checking);
                        if(whiteInCheck || blackInCheck)
                        {
                            madeWhileInCheck = true;
                        }
                        if(legalMoves.isEmpty())
                        {
                            continue;
                        }
                        for(Square legal : legalMoves)
                        {
                            output.add(new Move(this, checking, legal, checking.getPiece(), madeWhileInCheck, depth));
                        }
                    }
                }
            }
        }
        return output;
    }

    public void updateCastleRights(int depth)
    {
        //System.out.println("update castle rights");
        if(from.getPiece() instanceof Rook)
        {
            if(from.getSquareRow() == 0 && from.getSquareCol() == 0)
            {
                blackQueenSideRookMovedDepth = depth;
                blackQueenSideRookMoved = true;
            }
            else if(from.getSquareRow() == 0 && from.getSquareCol() == 7)
            {
                blackKingSideRookMovedDepth = depth;
                blackKingSideRookMoved = true;
            }
            else if(from.getSquareRow() == 7 && from.getSquareCol() == 7)
            {
                whiteKingSideRookMovedDepth = depth;
                whiteKingSideRookMoved = true;
            }
            else if(from.getSquareRow() == 7 && from.getSquareCol() == 0)
            {
                whiteQueenSideRookMovedDepth = depth;
                whiteQueenSideRookMoved = true;
            }
        }
        else if(from.getPiece() instanceof King)
        {
            if(from.getSquareRow() == 0 && from.getSquareCol() == 4)
            {
                blackKingMovedDepth = depth;
                blackKingMoved = true;
            }
            else if(from.getSquareRow() == 7 && from.getSquareCol() == 4)
            {
                whiteKingMovedDepth = depth;
                whiteKingMoved = true;
            }
        }
    }

    public ArrayList<Square> getCastleMoves()
    {
        //boolean canCastle = false;
        ArrayList<Square> kingSideCastle = new ArrayList<>();
        ArrayList<Square> queenSideCastle = new ArrayList<>();
        if(from.getPiece().getSide().equals(Piece.Side.WHITE))
        {
            if(!whiteKingMoved && (!whiteKingSideRookMoved || !whiteQueenSideRookMoved))
            {
                if(!whiteKingSideRookMoved)
                {
                    for(int i = 1; i < 4; i++)
                    {
                        if(i == 3)
                        {
                            for(int z = 1; z < 4; z++)
                            {
                                if(z == 3)
                                {
                                    //paintMoves(kingSide);
                                    //canCastle = true;
                                    break;
                                }
                                Square kingPass = from.getRight(z);
                                kingPass.setPiece(whiteKing.getPiece());
                                kingPass.getPiece().setPlacement(kingPass.getSquareRow(), kingPass.getSquareCol());
                                ArrayList<Square> isKingInCheck = kingPass.getPiece().isInCheckHorrible();
                                kingPass.removePiece();
                                if(!isKingInCheck.isEmpty())
                                {
                                    //canCastle = false;
                                    kingSideCastle.clear();
                                    break;
                                }
                            }
                        }
                        if(from.getRight(i).containsPiece() && i != 3)
                        {
                            kingSideCastle.clear();
                            break;
                        }
                        if(i != 3)
                        {
                            kingSideCastle.add(from.getRight(i));
                        }

                    }
                }
                if(!whiteQueenSideRookMoved)
                {
                    for(int i = 1; i < 5; i++)
                    {
                        if(i == 4)
                        {
                            for(int z = 1; z < 4; z++)
                            {
                                if(z == 3)
                                {
                                    //paintMoves(queenSide);
                                    break;
                                }
                                Square kingPass = from.getLeft(z);
                                kingPass.setPiece(whiteKing.getPiece());
                                kingPass.getPiece().setPlacement(kingPass.getSquareRow(), kingPass.getSquareCol());
                                ArrayList<Square> isKingInCheck = kingPass.getPiece().isInCheckHorrible();
                                kingPass.removePiece();
                                if(!isKingInCheck.isEmpty())
                                {
                                    queenSideCastle.clear();
                                    break;
                                }
                            }
                        }
                        if(from.getLeft(i).containsPiece() && i != 4)
                        {
                            queenSideCastle.clear();
                            break;
                        }
                        if(i != 3 && i != 4)
                        {
                            queenSideCastle.add(from.getLeft(i));
                        }
                    }
                }
            }
        }
        else
        {
            if(!blackKingMoved && (!blackKingSideRookMoved || !blackQueenSideRookMoved))
            {
                if(!blackKingSideRookMoved)
                {
                    for(int i = 1; i < 4; i++)
                    {
                        if(i == 3)
                        {
                            for(int z = 1; z < 4; z++)
                            {
                                if(z == 3)
                                {
                                    //paintMoves(kingSide);
                                    break;
                                }
                                Square kingPass = from.getRight(z);
                                kingPass.setPiece(blackKing.getPiece());
                                kingPass.getPiece().setPlacement(kingPass.getSquareRow(), kingPass.getSquareCol());
                                ArrayList<Square> isKingInCheck = kingPass.getPiece().isInCheckHorrible();
                                kingPass.removePiece();
                                if(!isKingInCheck.isEmpty())
                                {
                                    kingSideCastle.clear();
                                    break;
                                }
                            }
                        }
                        if(from.getRight(i).containsPiece() && i != 3)
                        {
                            kingSideCastle.clear();
                            break;
                        }
                        if(i != 3)
                        {
                            kingSideCastle.add(from.getRight(i));
                        }

                    }
                }
                if(!blackQueenSideRookMoved)
                {
                    for(int i = 1; i < 5; i++)
                    {
                        if(i == 4)
                        {
                            for(int z = 1; z < 4; z++)
                            {
                                if(z == 3)
                                {
                                    //paintMoves(queenSide);
                                    break;
                                }
                                Square kingPass = from.getLeft(z);
                                kingPass.setPiece(blackKing.getPiece());
                                kingPass.getPiece().setPlacement(kingPass.getSquareRow(), kingPass.getSquareCol());
                                ArrayList<Square> isKingInCheck = kingPass.getPiece().isInCheckHorrible();
                                kingPass.removePiece();
                                if(!isKingInCheck.isEmpty())
                                {
                                    queenSideCastle.clear();
                                    break;
                                }
                            }
                        }
                        if(from.getLeft(i).containsPiece() && i != 4)
                        {
                            queenSideCastle.clear();
                            break;
                        }
                        if(i != 3 && i != 4)
                        {
                            queenSideCastle.add(from.getLeft(i));
                        }
                    }
                }
            }
        }
        kingSideCastle.addAll(queenSideCastle);
        return kingSideCastle;
    }

    public boolean castle(Square pressed)
    {
        if(whiteInCheck || blackInCheck)
        {
            return false;
        }
        if(pressed.getSquareRow() == 7 && pressed.getSquareCol() == 6 && pressed.dotMove.isVisible() /*pressed.getBackground() == Color.BLUE*/)
        {
            updateKing(pressed);
            Square newRookSquare = whiteKing.getLeft(1);
            Piece tempRookPiece = whiteKing.getRight(1).getPiece();
            whiteKing.getRight(1).removePiece();
            newRookSquare.setPiece(tempRookPiece);
            newRookSquare.getPiece().setPlacement(newRookSquare.getSquareRow(), newRookSquare.getSquareCol());


            return true;
        }
        else if(pressed.getSquareRow() == 7 && pressed.getSquareCol() == 2 && pressed.dotMove.isVisible()/*pressed.getBackground() == Color.BLUE*/)
        {
            updateKing(pressed);
            Square newRookSquare = whiteKing.getRight(1);
            Piece tempRookPiece = whiteKing.getLeft(2).getPiece();
            whiteKing.getLeft(2).removePiece();
            newRookSquare.setPiece(tempRookPiece);
            newRookSquare.getPiece().setPlacement(newRookSquare.getSquareRow(), newRookSquare.getSquareCol());
            return true;
        }
        else if(pressed.getSquareRow() == 0 && pressed.getSquareCol() == 6 && pressed.dotMove.isVisible()/*pressed.getBackground() == Color.BLUE*/)
        {
            updateKing(pressed);
            Square newRookSquare = blackKing.getLeft(1);
            Piece tempRookPiece = blackKing.getRight(1).getPiece();
            blackKing.getRight(1).removePiece();
            newRookSquare.setPiece(tempRookPiece);
            newRookSquare.getPiece().setPlacement(newRookSquare.getSquareRow(), newRookSquare.getSquareCol());
            return true;
        }
        else if(pressed.getSquareRow() == 0 & pressed.getSquareCol() == 2 && pressed.dotMove.isVisible()/*pressed.getBackground() == Color.BLUE*/)
        {
            updateKing(pressed);
            Square newRookSquare = blackKing.getRight(1);
            Piece tempRookPiece = blackKing.getLeft(2).getPiece();
            blackKing.getLeft(2).removePiece();
            newRookSquare.setPiece(tempRookPiece);
            newRookSquare.getPiece().setPlacement(newRookSquare.getSquareRow(), newRookSquare.getSquareCol());
            return true;
        }
        return false;
    }


    public void revertColor(ArrayList<Square> s)
    {
        for(Square square : s)
        {
            if(square != null)
            {
                square.setBackground(square.revertColor());
                square.dotMove.setVisible(false);
            }
        }
    }

    public void pressDiffPiece(Square pressed)
    {
        ArrayList<Square> moves = new ArrayList<>();
        from.setBackground(from.revertColor());
        if(whiteInCheck || blackInCheck)
        {
            //olds.setBackground(olds.revertColor());
            if(from.getPiece() instanceof King)
            {
                from.revertColor(availableKingMoves);
                //olds.revertColor(availablePieceMoves);

            }
            else
            {
                from.revertColor(from.getPiece().getLegalMoves());
            }
            from = pressed;
            from.setPiece(pressed.getPiece());
            //olds.setBackground(Color.black);
            from.setBackground(Color.decode("#646D40"));
            if(pressed.getPiece() instanceof King)
            {
                //TODO: not sure if this accounts for castling moves
                availableKingMoves = movesIfInCheck(pressed);
                paintMoves(availableKingMoves);
            }
            else
            {
                availablePieceMoves = movesIfInCheck(from);
                paintMoves(availablePieceMoves);
                //ArrayList<Square> m = ifInCheck(olds);
                //paintMoves(m);
            }
        }
        else
        {
            if(from.getPiece() instanceof King)
            {
                from.revertColor(availableKingMoves);
            }
            else
            {
                from.revertColor(availablePieceMoves);
            }
            //olds.revertColor(olds.getPiece().getLegalMoves());
            from = pressed;
            moves = getMoves(pressed);
            currentLegalMoves = moves;
            paintMoves(moves);
            isPressed = true;
            repaint();
        }
    }

    public void switchTurn(Piece.Side oldsSide)
    {
        if(oldsSide.equals(Piece.Side.WHITE))
        {
            whiteTurn = false;
        }
        else
        {
            whiteTurn = true;
        }
    }

    public void updateKing(Square s)
    {
        Piece p = from.getPiece();
        if(p instanceof King)
        {
            if(p.getSide().equals(Piece.Side.BLACK))
            {
                blackKing = s;
                //blackKing.setPiece(p);
                //blackKing.getPiece().setPlacement(blackKing.getSquareRow(), blackKing.getSquareCol());
            }
            else
            {
                whiteKing = s;
                //whiteKing.setPiece(p);
                //whiteKing.getPiece().setPlacement(whiteKing.getSquareRow(), whiteKing.getSquareCol());
            }
        }
    }

    // TODO: issue might be with making pressed equal to some square and pressed is used for different things?
    public void undoMove(Square fromHere, Square toHere, int fromDepth, int toDepth, boolean madeWhileInCheck)
    {
        // TODO: undo en passant!!!
        /*
        if(from.getPiece() instanceof King && pressed.getSquareRow() == 4 && pressed.getSquareCol() == 0)
        {
            System.out.println("depth when king moves to forbidden square: "+depth);
            System.out.println("king moves from: "+from.getSquareRow()+", "+from.getSquareCol());
        }

         */
        Piece uncapturePiece = null;
        if(!whitesCapturedPiecesMap.isEmpty() && fromHere.getPiece().getSide().equals(Piece.Side.WHITE)
                && fromHere.equalsSquare(whitesCapturedPiecesMap.get(whitesCapturedPiecesList.get(whitesCapturedPiecesList.size()-1)).toHere)
                && toHere.equalsSquare(whitesCapturedPiecesMap.get(whitesCapturedPiecesList.get(whitesCapturedPiecesList.size()-1)).fromHere)
                && whitesCapturedPiecesMap.get(whitesCapturedPiecesList.get(whitesCapturedPiecesList.size()-1)).depth == fromDepth)
        {
            //System.out.println("You've captured a black piece");
            uncapturePiece = whitesCapturedPiecesList.get(whitesCapturedPiecesList.size()-1);
            whitesCapturedPiecesMap.remove(whitesCapturedPiecesList.get(whitesCapturedPiecesList.size()-1));
            whitesCapturedPiecesList.remove(whitesCapturedPiecesList.size()-1);
        }
        else if(!blacksCapturedPiecesMap.isEmpty() && fromHere.getPiece().getSide().equals(Piece.Side.BLACK)
                && fromHere.equalsSquare(blacksCapturedPiecesMap.get(blacksCapturedPiecesList.get(blacksCapturedPiecesList.size()-1)).toHere)
                && toHere.equalsSquare(blacksCapturedPiecesMap.get(blacksCapturedPiecesList.get(blacksCapturedPiecesList.size()-1)).fromHere)
                && blacksCapturedPiecesMap.get(blacksCapturedPiecesList.get(blacksCapturedPiecesList.size()-1)).depth == fromDepth)
        {
            //System.out.println("You've captured a white piece");
            uncapturePiece = blacksCapturedPiecesList.get(blacksCapturedPiecesList.size()-1);
            blacksCapturedPiecesMap.remove(blacksCapturedPiecesList.get(blacksCapturedPiecesList.size()-1));
            blacksCapturedPiecesList.remove(blacksCapturedPiecesList.size()-1);
        }
        // TODO: en passant and all the checks for blocking and being in check and stuff
        //System.out.println("\n");
        //System.out.println("fromHere: "+fromHere.getSquareRow()+", "+fromHere.getSquareCol());
        //System.out.println("toHere: "+toHere.getSquareRow()+", "+toHere.getSquareCol());
        //System.out.println("fromDepth: "+fromDepth);
        //System.out.println("toDepth: "+toDepth);
        //System.out.println(chessBoard[fromHere.getSquareRow()][fromHere.getSquareCol()].getPiece());
        if(whiteInCheck)
        {
            whiteInCheck = false;
        }
        else if(blackInCheck)
        {
            blackInCheck = false;
        }
        else if(madeWhileInCheck)
        {
            if(fromHere.getPiece().getSide().equals(Piece.Side.BLACK))
            {
                blackInCheck = true;
            }
            else
            {
                whiteInCheck = true;
            }
        }
        if(fromHere.getPiece() instanceof King)
        {
            if(Math.abs(fromHere.getSquareCol() - toHere.getSquareCol()) > 1)
            {
                if(fromHere.getSquareRow() == 7)
                {
                    if(fromHere.getSquareCol() == 6)
                    {
                        chessBoard[7][4].setPiece(fromHere.getPiece());
                        fromHere.removePiece();
                        chessBoard[7][7].setPiece(fromHere.getLeft(1).getPiece());
                        fromHere.getLeft(1).removePiece();
                        whiteKingSideRookMoved = false;
                    }
                    else if(fromHere.getSquareCol() == 2)
                    {
                        chessBoard[7][4].setPiece(fromHere.getPiece());
                        fromHere.removePiece();
                        chessBoard[7][0].setPiece(fromHere.getRight(1).getPiece());
                        fromHere.getRight(1).removePiece();
                        whiteQueenSideRookMoved = false;
                    }
                    whiteKing = chessBoard[7][4];
                    whiteKingMoved = false;
                }
                else if(fromHere.getSquareRow() == 0)
                {
                    if(fromHere.getSquareCol() == 6)
                    {
                        chessBoard[0][4].setPiece(fromHere.getPiece());
                        fromHere.removePiece();
                        chessBoard[0][7].setPiece(fromHere.getLeft(1).getPiece());
                        fromHere.getLeft(1).removePiece();
                        blackKingSideRookMoved = false;
                    }
                    else if(fromHere.getSquareCol() == 2)
                    {
                        chessBoard[0][4].setPiece(fromHere.getPiece());
                        fromHere.removePiece();
                        chessBoard[0][0].setPiece(fromHere.getRight(1).getPiece());
                        fromHere.getRight(1).removePiece();
                        blackQueenSideRookMoved = false;
                    }
                    blackKing = chessBoard[0][4];
                    blackKingMoved = false;
                }
            }
            else
            {
                if(fromHere.getPiece().getSide().equals(Piece.Side.WHITE))
                {
                    if(whiteKingMoved)
                    {
                        if(whiteKingMovedDepth == toDepth)
                        {
                            whiteKingMoved = false;
                        }
                    }
                    whiteKing = toHere;
                }
                else
                {
                    if(blackKingMoved)
                    {
                        if(blackKingMovedDepth == toDepth)
                        {
                            blackKingMoved = false;
                        }
                    }
                    blackKing = toHere;
                }
            }
        }
        else if(fromHere.getPiece() instanceof Rook)
        {
            if(fromHere.getPiece().getSide().equals(Piece.Side.WHITE))
            {
                if(toHere.getSquareRow() == 7 && toHere.getSquareCol() == 7 && whiteKingSideRookMovedDepth == toDepth) //whiteKingSideRook
                {
                    whiteKingSideRookMoved = false;
                }
                else if(toHere.getSquareRow() == 7 && toHere.getSquareCol() == 0 && whiteQueenSideRookMovedDepth == toDepth)
                {
                    whiteQueenSideRookMoved = false;
                }
            }
            else if(fromHere.getPiece().getSide().equals(Piece.Side.BLACK))
            {
                if(toHere.getSquareRow() == 0 && toHere.getSquareCol() == 7 && blackKingSideRookMovedDepth == toDepth)
                {
                    blackKingSideRookMoved = false;
                }
                else if(toHere.getSquareRow() == 0 && toHere.getSquareCol() == 0 && blackQueenSideRookMovedDepth == toDepth)
                {
                    blackQueenSideRookMoved = false;
                }
            }
        }
        toHere.setPiece(fromHere.getPiece());
        chessBoard[toHere.getSquareRow()][toHere.getSquareCol()].setPiece(fromHere.getPiece());
        chessBoard[fromHere.getSquareRow()][fromHere.getSquareCol()].removePiece();
        //toHere.getPiece().setPlacement(toHere.getSquareRow(), toHere.getSquareCol());
        //System.out.println("depth 0: "+pressed.getSquareRow()+", "+pressed.getSquareCol());
        //copy.news.getPiece().setPlacement(squareToEval.getSquareRow(), squareToEval.getSquareCol());
        fromHere.removePiece();
        if(uncapturePiece != null)
        {
            if(toHere.getPiece() instanceof Pawn && ((Pawn) toHere.getPiece()).getCapturedEnPassantAt() != null && ((Pawn) toHere.getPiece()).getCapturedEnPassantAt().equalsSquare(fromHere))
            {
                if(toHere.getPiece().getSide().equals(Piece.Side.WHITE))
                {
                    ((Pawn) toHere.getPiece()).getCapturedEnPassantAt().getDown(1).setPiece(uncapturePiece);
                    chessBoard[fromHere.getSquareRow()+1][fromHere.getSquareCol()].setPiece(uncapturePiece);
                }
                else
                {
                    ((Pawn) toHere.getPiece()).getCapturedEnPassantAt().getUp(1).setPiece(uncapturePiece);
                    chessBoard[fromHere.getSquareRow()-1][fromHere.getSquareCol()].setPiece(uncapturePiece);
                }
                System.out.println("undo en passant");
                ((Pawn) toHere.getPiece()).setCapturedEnPassantAt(null);
            }
            else
            {
                fromHere.setPiece(uncapturePiece);
                chessBoard[fromHere.getSquareRow()][fromHere.getSquareCol()].setPiece(uncapturePiece);
            }
        }
        from = pressed = news = toHere;
    }

    public void movePieceAI(Square from, Square pressed, int depth, Move move)
    {
        //System.out.println("MOVE FROM: "+from.getSquareRow()+", "+from.getSquareCol());
        //System.out.println("MOVE TO: "+pressed.getSquareRow()+", "+pressed.getSquareCol());
        if(!(from.getPiece() instanceof Pawn) && !pressed.containsPiece())
        {
            modFour++;
            fiftyMoveDraw++;
            if(fiftyMoveDraw == 100)
            {
                System.out.println("DRAW SILLY");
            }
        }
        else
        {
            fiftyMoveDraw = 0;
        }
        //TODO: ISISSISUEIEUEIUSEIUEIUIOSEURISEU maybe also in future with castling
        if(from.getPiece() instanceof King)
        {
            if(!castle(pressed))
            {
                //updateKing(pressed);
            }
            else
            {
                System.out.println("CASTLING PIECES!!!!!");
            }
        }
        if(from.getPiece() instanceof Pawn)
        {
            boolean canEnPassant = false;
            Piece.Side movingSide = from.getPiece().getSide();
            Square left = null;
            Square right = null;
            if((Math.abs(from.getSquareRow() - pressed.getSquareRow()) == 2))
            {
                if(from.getSquareCol() == 0)
                {
                    if(from.getRight(1).containsPiece())
                    {
                        if(from.getRight(1).getPiece() instanceof Pawn && !from.getRight(1).getPiece().getSide().equals(movingSide))
                        {
                            right = from.getRight(1);
                            canEnPassant = true;
                        }
                    }
                }
                else if(from.getSquareCol() == 7)
                {
                    if(from.getLeft(1).containsPiece())
                    {
                        if(from.getLeft(1).getPiece() instanceof Pawn && !from.getLeft(1).getPiece().getSide().equals(movingSide))
                        {
                            left = from.getLeft(1);
                            canEnPassant = true;
                        }
                    }
                }
                else
                {
                    if(from.getRight(1).containsPiece())
                    {
                        if(from.getRight(1).getPiece() instanceof Pawn && !from.getRight(1).getPiece().getSide().equals(movingSide))
                        {
                            right = from.getRight(1);
                            canEnPassant = true;
                        }
                    }
                    else if(from.getLeft(1).containsPiece())
                    {
                        if(from.getLeft(1).getPiece() instanceof Pawn && !from.getLeft(1).getPiece().getSide().equals(movingSide))
                        {
                            left = from.getLeft(1);
                            canEnPassant = true;
                        }
                    }
                }
                if(canEnPassant)
                {
                    if(from.getPiece().getSide().equals(Piece.Side.BLACK))
                    {
                        enPassant = chessBoard[pressed.getSquareRow() - 1][pressed.getSquareCol()];
                        if(left != null)
                        {
                            ((Pawn)left.getPiece()).setToCaptureEnPassantAt(chessBoard[pressed.getSquareRow() - 1][pressed.getSquareCol()]);
                        }
                        if(right != null)
                        {
                            ((Pawn)right.getPiece()).setToCaptureEnPassantAt(chessBoard[pressed.getSquareRow() - 1][pressed.getSquareCol()]);
                        }
                    }
                    else
                    {
                        enPassant = chessBoard[pressed.getSquareRow() + 1][pressed.getSquareCol()];
                        if(left != null)
                        {
                            ((Pawn)left.getPiece()).setToCaptureEnPassantAt(chessBoard[pressed.getSquareRow() + 1][pressed.getSquareCol()]);
                        }
                        if(right != null)
                        {
                            ((Pawn)right.getPiece()).setToCaptureEnPassantAt(chessBoard[pressed.getSquareRow() + 1][pressed.getSquareCol()]);
                        }
                    }
                    ((Pawn) from.getPiece()).setEnPassantBecameAvailableDepth(depth);
                    //canEnPassant = false;
                }
            }
        }
        if(whiteInCheck || blackInCheck)
        {
            noLongerInCheck();
        }
        news = pressed;
        //Piece oldsPiece = this.from.getPiece();
        Piece oldsPiece = from.getPiece();

        Piece.Side oldsSide = oldsPiece.getSide();

        /*
        if(oldsPiece instanceof King && ((Math.abs(from.getSquareRow() - pressed.getSquareRow()) > 1) || (Math.abs(from.getSquareCol() - pressed.getSquareCol()) > 1)))
        {
            System.out.println("SHOULD BE AN IMPOSSIBLE MOVE FOR KING");
            System.out.println("from: "+from.getSquareRow()+", "+from.getSquareCol());
            System.out.println("to: "+pressed.getSquareRow()+", "+pressed.getSquareCol());
            throw new IndexOutOfBoundsException();
        }

         */

        if(pressed.containsPiece())
        {
            Piece pressedPiece = pressed.getPiece();
            Piece.Side pressedSide = pressed.getPiece().getSide();
            if(pressedSide.equals(from.getPiece().getSide()))
            {
                //System.out.println(from.getPiece().getSide()+" "+from.getPiece()+" is trying to take a "+pressedSide+" "+pressedPiece);
            }
            if(pressedPiece instanceof King)
            {
                //System.out.println("capturing a king!");
                //System.out.println(from.getPiece()+" on "+from.getSquareRow()+", "+from.getSquareCol()+" takes king on "+pressed.getSquareRow()+", "+pressed.getSquareCol());
                //System.out.println("sides are: "+from.getPiece().getSide()+" and "+pressed.getPiece().getSide());
            }
            if(pressedSide.equals(Piece.Side.BLACK))
            {
                whitesCapturedPiecesList.add(pressedPiece);
                whitesCapturedPiecesMap.put(pressedPiece, move);
            }
            else
            {
                blacksCapturedPiecesList.add(pressedPiece);
                blacksCapturedPiecesMap.put(pressedPiece, move);
            }
        }
        news.setPiece(oldsPiece);
        //chessBoard[pressed.getSquareRow()][pressed.getSquareCol()].setPiece(oldsPiece);
        //chessBoard[pressed.getSquareRow()][pressed.getSquareCol()].setBackground(Color.RED);
        //pressed.setPiece(oldsPiece);
        //news.getPiece().setPlacement(pressed.getSquareRow(),pressed.getSquareCol());
        switchTurn(oldsSide);
        //chessBoard[from.getSquareRow()][from.getSquareCol()].removePiece();
        from.removePiece();
        this.from.removePiece();
        // TODO: more stuff for threefoldrepetition that I should come back to, see commented out code above as well
        isPressed = false;
        repaint();
        if(news.getPiece() instanceof King)
        {
            if(news.getPiece().getSide().equals(Piece.Side.WHITE))
            {
                whiteKing = news;
            }
            else
            {
                blackKing = news;
            }
        }
        /*
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                newBoard[i][z] = chessBoard[i][z];
            }
        }

         */
        //return newBoard;
    }

    public String boardToString()
    {
        String output = "";
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                Square s = chessBoard[i][z];
                String pieceStr = (s.containsPiece()) ? s.getPiece().toString() : "-";
                output += pieceStr+", ";
                if(z == 7)
                {
                    output += "\n";
                }
            }
        }
        return output;
    }


    public void movePiece(Square pressed, int row, int col)
    {
        //olds.setBackground(olds.revertColor());
        //olds.setBackground(Color.decode("#CED26E"));
        /*
        if(lastMovedFrom == null && lastMovedTo == null)
        {
            lastMovedFrom = olds;
            lastMovedTo = pressed;
        }
        else
        {
            lastMovedFrom.setBackground(lastMovedFrom.revertColor());
            lastMovedTo.setBackground(lastMovedTo.revertColor());
            lastMovedFrom = olds;
            lastMovedTo = pressed;
        }
        if(lastMovedFrom.getSquareColorType().equalsIgnoreCase("light"))
        {
            lastMovedFrom.setBackground(Color.decode("#CED26E"));
        }
        else
        {
            //olds.setBackground(Color.decode("#BBBF63"));
            lastMovedFrom.setBackground(Color.decode("#A2A600"));
        }
        if(lastMovedTo.getSquareColorType().equalsIgnoreCase("light"))
        {
            lastMovedTo.setBackground(Color.decode("#CED26E"));
        }
        else
        {
            //olds.setBackground(Color.decode("#BBBF63"));
            lastMovedTo.setBackground(Color.decode("#A2A600"));
        }

         */
        if(!(from.getPiece() instanceof Pawn) && !pressed.containsPiece())
        {
            modFour++;
            fiftyMoveDraw++;
            if(fiftyMoveDraw == 100)
            {
                System.out.println("DRAW SILLY");
            }
        }
        else
        {
            /*
            for(int i = 0; i < 8; i++)
            {
                for(int z = 0; z < 8; z++)
                {
                    repetition[i][z] = board[i][z];
                }
            }
            modFour = 1;

             */
            fiftyMoveDraw = 0;
        }
        if(from.getPiece() instanceof King)
        {
            if(!castle(pressed))
            {
                updateKing(pressed);
            }
            from.revertColor(availableKingMoves);
        }
        else
        {
            from.revertColor(availablePieceMoves);
        }
        if(from.getPiece() instanceof Pawn)
        {
            if(Math.abs(from.getSquareRow() - pressed.getSquareRow()) == 2)
            {
                if(from.getPiece().getSide().equals(Piece.Side.BLACK))
                {
                    enPassant = chessBoard[pressed.getSquareRow() - 1][pressed.getSquareCol()];
                }
                else
                {
                    enPassant = chessBoard[pressed.getSquareRow() + 1][pressed.getSquareCol()];
                }
            }
        }
        if(whiteInCheck || blackInCheck)
        {
            noLongerInCheck();
        }
        news = pressed;
        Piece oldsPiece = from.getPiece();
        Piece.Side oldsSide = oldsPiece.getSide();


        news.setPiece(oldsPiece);
        //news.getPiece().setPlacement(new int[]{row, col});
        news.getPiece().setPlacement(row,col);
        //System.out.println("move piece news: "+news.getPiece());
        switchTurn(oldsSide);
        from.removePiece();
        // TODO: more stuff for threefoldrepetition that I should come back to, see commented out code above as well
        /*
        if(!movedPawn && !captureOccurred)
        {
            if(modFour >= 4)
            {
                for(int i = 0; i < 8; i++)
                {
                    for(int z = 0; z < 8; z++)
                    {
                        String repPieceType = (repetition[i][z].containsPiece()) ? repetition[i][z].getPiece().getType() : "null";
                        String boardPieceType = (board[i][z].containsPiece()) ? board[i][z].getPiece().getType() : "null";
                        if(!repPieceType.equalsIgnoreCase(boardPieceType))
                        {
                            threeFoldRep = 0;
                            break;
                        }
                    }
                    if(threeFoldRep == 0)
                    {
                        break;
                    }
                }
                if(threeFoldRep != 0)
                {
                    System.out.println("dejavu");
                    threeFoldRep++;
                    modFour = 1;
                    if(threeFoldRep == 3)
                    {
                        System.out.println("person to move can claim a draw");
                        //pressed.setBackground(Color.GREEN);
                        //repaint();
                    }
                    else if(threeFoldRep == 5)
                    {
                        System.out.println("I'm the arbiter and you MUST draw");
                    }
                }
            }
        }

         */
        isPressed = false;
        repaint();
    }
// TODO: make sure to change back if things are going poorly
    public void noLongerInCheck()
    {
        //thisIstheWay = null;
        //expCheck = null;
        //availableKingMoves = null;
        whiteInCheck = false;
        blackInCheck = false;
    }

    public void paintCircle(Graphics g)
    {
        g.drawOval(10, 10, 10, 10);
    }

    public void paintMoves(ArrayList<Square> moves)
    {
        //olds.setBackground(Color.BLACK);
        from.setBackground(Color.decode("#646D40"));
        //olds.setBackground(new Color(86, 132, 95, 100));
        int counter = 0;
        for(Square s : moves)
        {
            if(s.containsPiece())
            {
                if(!(s.getPiece() instanceof King))
                {
                    //s.setBackground(Color.BLUE);
                    //s.dotMove.setVisible(true);
                    if(s.getBackground().equals(Color.decode("#CED26E")) || s.getBackground().equals(Color.decode("#A2A600")))
                    {
                        s.setBackground(s.revertColor());
                        repaint();
                    }


                    s.attackedPieceOverlayLabel.setVisible(true);
                    //s.paint();
                    //s.setBackground(new Color(118, 144, 113, 10));
                }
            }
            else
            {
                //s.setBackground(Color.BLUE);
                s.dotMove.setVisible(true);
                //s.setBackground(new Color(118, 144, 113, 100));
                //s.setBackground(Color.BLUE);
            }
        }
    }


    public ArrayList<Square> movesIfInCheck(Square pressed)
    {
        Square king = (whiteInCheck) ? whiteKing : blackKing;
        //String pressedType = pressed.getPiece().getType();

        if(pressed.getPiece() instanceof King)
        {
            availableKingMoves = whereKingCanGo(pressed);
            //System.out.println("MOVE KING");
            //System.out.println(pressed.getPiece());
            //System.out.println(pressed.getSquareRow()+", "+pressed.getSquareCol());
            for(Square s : availableKingMoves)
            {
                //System.out.println("possible move!");
                //System.out.println(s.getSquareRow()+", "+s.getSquareCol());
            }
            return availableKingMoves;
        }
        else
        {
            ArrayList<Square> pathway = new ArrayList<>();
            if(thisIstheWay.isEmpty())
            {
                //System.out.println("thisistheway is empty");
            }
            for(int i = 0; i < thisIstheWay.size(); i++)
            {
                pathway.add(thisIstheWay.get(i));
            }
            //ArrayList<Square> limit = limitMoves(pathway, pressed);
            //return limit;
            //System.out.println(pressed.getPiece());
            //System.out.println(pressed.getSquareRow()+", "+pressed.getSquareCol());
            availablePieceMoves = limitMoves(pathway, pressed);
            return availablePieceMoves;
        }
    }

    public ArrayList<Square> ifBlocking(ArrayList<Square> blocking, boolean canEnPassant)
    {
        ArrayList<Square> legalMoves = from.getPiece().getLegalMoves();
        ArrayList<Square> output = new ArrayList<>();
        if(from.getPiece() instanceof Pawn && canEnPassant)
        {
            legalMoves.add(enPassant);
        }
        for(Square legal : legalMoves)
        {
            for(Square path : blocking)
            {
                if(legal.getSquareRow() == path.getSquareRow() && legal.getSquareCol() == path.getSquareCol())
                {
                    output.add(legal);
                }
            }
        }
        return output;
    }

    public ArrayList<Square> isInCheck(ArrayList<Square> check, Square checkingPiece)
    {
        ArrayList<Square> empty = new ArrayList<>();
        for(Square s : check)
        {
            if(s.containsPiece())
            {
                if(s.getPiece() instanceof King)
                {
                    ArrayList<Square> path = new ArrayList<>();
                    int pRow = checkingPiece.getSquareRow();
                    int pCol = checkingPiece.getSquareCol();
                    Square king = s.getPiece().getSide().equals(Piece.Side.WHITE) ? whiteKing : blackKing;
                    if(king.getPiece().getSide().equals(Piece.Side.WHITE))
                    {
                        whiteInCheck = true;
                    }
                    else
                    {
                        blackInCheck = true;
                    }
                    int rowDif = king.getSquareRow() - pRow;
                    int colDif = king.getSquareCol() - pCol;
                    // move this below knight
                    path.add(checkingPiece);
                    if(checkingPiece.getPiece() instanceof Knight)
                    {
                        path.add(s);
                        if(rowDif > 0)
                        {
                            if(colDif > 0)
                            {
                                if(rowDif == 2)
                                {
                                    if(!(pRow+1 > 7) && !(pCol+2 > 7))
                                    {
                                        path.add(chessBoard[pRow+1][pCol+2]);
                                    }
                                }
                                else if(colDif == 2)
                                {
                                    if(!(pRow+2 > 7) && !(pCol+1 > 7))
                                    {
                                        path.add(chessBoard[pRow+2][pCol+1]);
                                    }
                                }
                            }
                            else if(colDif < 0)
                            {
                                if(rowDif == 2)
                                {
                                    if(!(pRow+1 > 7) && !(pCol-2 < 0))
                                    {
                                        path.add(chessBoard[pRow+1][pCol-2]);
                                    }
                                }
                                else if(colDif == -2)
                                {
                                    if(!(pRow+2 > 7) && !(pCol-1 < 0))
                                    {
                                        path.add(chessBoard[pRow+2][pCol-1]);
                                    }
                                }
                            }
                        }
                        else if(rowDif < 0)
                        {
                            if(colDif > 0)
                            {
                                if(rowDif == -2)
                                {
                                    if(!(pRow-1 < 0) && !(pCol+2 > 7))
                                    {
                                        path.add(chessBoard[pRow-1][pCol+2]);
                                    }
                                }
                                else if(colDif == 2)
                                {
                                    if(!(pRow-2 < 0) && !(pCol+1 > 7))
                                    {
                                        path.add(chessBoard[pRow-2][pCol+1]);
                                    }
                                }
                            }
                            else if(colDif < 0)
                            {
                                if(rowDif == -2)
                                {
                                    if(!(pRow-1 < 0) && !(pCol-2 < 0))
                                    {
                                        path.add(chessBoard[pRow-1][pCol-2]);
                                    }
                                }
                                else if(colDif == -2)
                                {
                                    if(!(pRow-2 < 0) && !(pCol-1 < 0))
                                    {
                                        path.add(chessBoard[pRow-2][pCol-1]);
                                    }
                                }
                            }
                        }
                        return path;
                    }
                    if(rowDif == 0) // horizontal attack
                    {
                        if(colDif > 0)
                        {
                            for(int i = 1; i <= colDif+1; i++)
                            {
                                if(!(pCol+i > 7))
                                {
                                    path.add(chessBoard[pRow][pCol+i]);
                                }
                            }
                        }
                        else
                        {
                            colDif = -1*colDif;
                            for(int i = 1; i <= colDif+1; i++)
                            {
                                if(!(pCol-i < 0))
                                {
                                    path.add(chessBoard[pRow][pCol-i]);
                                }
                            }
                        }
                    }
                    else if(colDif == 0) // vertical attack
                    {
                        if(rowDif > 0)
                        {
                            for(int i = 1; i <= rowDif+1; i++)
                            {
                                if(!(pRow+i > 7))
                                {
                                    path.add(chessBoard[pRow+i][pCol]);
                                }
                            }
                        }
                        else
                        {
                            rowDif = -1*rowDif;
                            for(int i = 1; i <= rowDif+1; i++)
                            {
                                if(!(pRow-i < 0))
                                {
                                    path.add(chessBoard[pRow-i][pCol]);
                                }
                            }
                        }
                    }
                    else // diag attack
                    {
                        if(rowDif > 0)
                        {
                            if(colDif > 0)
                            {
                                for(int i = 1; i <= rowDif+1; i++)
                                {
                                    if(!(pRow+i > 7) && !(pCol+i > 7))
                                    {
                                        path.add(chessBoard[pRow+i][pCol+i]);
                                    }
                                }
                            }
                            else
                            {
                                for(int i = 1; i <= rowDif+1; i++)
                                {
                                    if(!(pRow+i > 7) && !(pCol-i < 0))
                                    {
                                        path.add(chessBoard[pRow+i][pCol-i]);
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(colDif > 0)
                            {
                                for(int i = 1; i <= colDif+1; i++)
                                {
                                    if(!(pRow - i < 0) && !(pCol+i > 7))
                                    {
                                        path.add(chessBoard[pRow-i][pCol+i]);
                                    }
                                }
                            }
                            else
                            {
                                rowDif = -1 * rowDif;
                                for(int i = 1; i <= rowDif+1; i++)
                                {
                                    if(!(pRow-i < 0) && !(pCol-i < 0))
                                    {
                                        path.add(chessBoard[pRow-i][pCol-i]);
                                    }
                                }
                            }
                        }
                    }
                    if(path.get(path.size()-1).containsPiece())
                    {
                        if(!(path.get(path.size()-1).getPiece() instanceof King))
                        {
                            path.remove(path.size()-1);
                        }
                    }
                    else if(checkingPiece.getPiece() instanceof Pawn)
                    {
                        path.remove(path.size()-1);
                    }
                    return path;
                }
            }
        }
        return empty;
    }

    public ArrayList<Square> whereKingCanGo(Square kingSquare)
    {
        Square enemyKingSquare = (kingSquare.equalsSquare(whiteKing)) ? blackKing : whiteKing;
        Piece.Side enemyKingSide = enemyKingSquare.getPiece().getSide();
        enemyKingSquare.getPiece().setSide(kingSquare.getPiece().getSide());
        ArrayList<Square> enemyKingLegalMoves = enemyKingSquare.getPiece().getLegalMoves();
        enemyKingSquare.getPiece().setSide(enemyKingSide);
        ArrayList<Square> kingLegalMoves = kingSquare.getPiece().getLegalMoves();
        ArrayList<Square> movesForKing = new ArrayList<>();
        for(Square s : kingLegalMoves)
        {
            if(thisIstheWay != null)
            {
                if(thisIstheWay.size() > 2)
                {
                    // Exclude square along the pathway in between the king and checking piece
                    if(s.getSquareRow() == thisIstheWay.get(thisIstheWay.size()-2).getSquareRow() && s.getSquareCol() == thisIstheWay.get(thisIstheWay.size()-2).getSquareCol())
                    {
                        continue;
                    }
                    else if(s.equalsSquare(thisIstheWay.get(thisIstheWay.size()-1)))
                    {
                        continue;
                    }

                    if(thisIstheWay.get(0).getPiece() instanceof Knight && s.getSquareRow() == thisIstheWay.get(thisIstheWay.size()-1).getSquareRow() && s.getSquareCol() == thisIstheWay.get(thisIstheWay.size()-1).getSquareCol())
                    {
                        continue;
                    }
                }
            }
            if(expCheck != null)
            {
                if(s.getSquareRow() == expCheck.get(expCheck.size()-2).getSquareRow() && s.getSquareCol() == expCheck.get(expCheck.size()-2).getSquareCol()) // don't include king square in possible moves
                {
                    continue;
                }
                else if(s.equalsSquare(expCheck.get(expCheck.size()-1)))
                {
                    continue;
                }
                else if(s.equalsSquare(expCheck.get(0)))
                {
                    continue;
                }
            }
            movesForKing.add(s);
        }
        kingLegalMoves.clear();
        for(Square s : movesForKing)
        {
            if(!s.containsPiece())
            {
                s.setPiece(kingSquare.getPiece());
                s.getPiece().setPlacement(s.getSquareRow(), s.getSquareCol());
                //kingSquare.removePiece();
                ArrayList<Square> list = s.getPiece().isInCheckHorrible();
                Square cantMoveThere = null;
                if(list.size() == 0)
                {
                    for(Square enemy : enemyKingLegalMoves)
                    {
                        if(s.equalsSquare(enemy))
                        {
                            cantMoveThere = s;
                        }
                    }
                    if(cantMoveThere == null)
                    {
                        kingLegalMoves.add(s);
                    }
                }
                kingSquare.setPiece(s.getPiece());
                s.getPiece().setPlacement(kingSquare.getSquareRow(), kingSquare.getSquareCol());
                s.removePiece();
            }
            else
            {
                Piece temporary = s.getPiece();
                s.setPiece(kingSquare.getPiece());
                s.getPiece().setPlacement(s.getSquareRow(), s.getSquareCol());
                kingSquare.removePiece();
                ArrayList<Square> list = s.getPiece().isInCheckHorrible();
                Square cantMoveThere = null;
                if(list.size() == 0)
                {
                    for(Square enemy : enemyKingLegalMoves)
                    {
                        if(s.equalsSquare(enemy))
                        {
                            cantMoveThere = s;
                        }
                    }
                    if(cantMoveThere == null)
                    {
                        kingLegalMoves.add(s);
                    }
                }
                kingSquare.setPiece(s.getPiece());
                kingSquare.getPiece().setPlacement(kingSquare.getSquareRow(), kingSquare.getSquareCol());
                s.setPiece(temporary);
                s.getPiece().setPlacement(s.getSquareRow(), s.getSquareCol());
            }
        }
        return kingLegalMoves;
    }

    public ArrayList<Square> limitMoves(ArrayList<Square> path, Square pressed)
    {
        // not sure what this was for but it tries to allow king to escape where it's still in line with attacking piece
        //thisIstheWay = path;
        ArrayList<Square> noOutput = new ArrayList<>();
        Square escapeSquare = null;
        if(!path.isEmpty())
        {
            escapeSquare = path.get(path.size()-1);

            if(!path.get(path.size()-1).containsPiece())
            {
                path.remove(path.size()-1);
            }
            else if(path.get(path.size()-1).containsPiece())
            {
                if(path.get(path.size()-1).getPiece() instanceof King)
                {
                    path.remove(path.size()-1);
                }
            }
        }
        else
        {
            return noOutput;
        }
        ArrayList<Square> legalMoves = new ArrayList<>();
        ArrayList<Square> output = new ArrayList<>();

        Piece piece = pressed.getPiece();
        String attackLine = (path.get(0).getSquareCol() - path.get(path.size()-1).getSquareCol() == 0) ? "vertical" : (path.get(0).getSquareRow() - path.get(path.size()-1).getSquareRow() == 0) ? "horizontal" : "diagonal";

        int rowTrav;
        int colTrav;
        int mirRowTrav;
        int mirColTrav;

        if(path.get(0).getPiece() instanceof Knight)
        {
            attackLine = "knight";
            Square temp = path.get(0);
            path.clear();
            path.add(temp);
        }
        if(piece instanceof Knight)
        {
            ArrayList<Square> knightMoves = piece.getLegalMoves();
            for(Square knightMove : knightMoves)
            {
                for(Square pathSquare : path)
                {
                    if(knightMove == pathSquare)
                    {
                        output.add(knightMove);
                    }
                }
            }
            //return output;
        }
        else if(piece instanceof Pawn)
        {
            ArrayList<Square> pawnMoves = piece.getLegalMoves();
            if(attackLine.equalsIgnoreCase("vertical"))
            {
                // TODO: pawn can only attack checking piece
                for(Square legal : pawnMoves)
                {
                    if(legal.equalsSquare(path.get(0)))
                    {
                        output.add(legal);
                        break;
                    }
                }
            }
            else // horizontal and diagonal
            {
                if(enPassant != null && pressed.getPiece() instanceof Pawn)
                {
                    rowTrav = (pressed.getPiece().getSide().equals(Piece.Side.WHITE)) ? -1 : 1;
                    if((pressed.getSquareRow() + rowTrav == enPassant.getSquareRow()) && ((pressed.getSquareCol() + 1 == enPassant.getSquareCol()) || (pressed.getSquareCol() - 1 == enPassant.getSquareCol())))
                    {
                        if(thisIstheWay.get(0).getPiece() instanceof Pawn && enPassant.getDown(rowTrav * -1).containsPiece())
                        {
                            if(enPassant.getDown(rowTrav * -1).getPiece() instanceof Pawn)
                            {
                                int pawnRow = from.getSquareRow();
                                int pawnCol = from.getSquareCol();
                                for(int i = 1; i < 8; i++)
                                {
                                    Square isBlocking = chessBoard[pawnRow + (i * rowTrav)][pawnCol];
                                    if(pawnRow < 8 && pawnRow > -1)
                                    {
                                        if(isBlocking.containsPiece())
                                        {
                                            Piece isBlockingPiece = isBlocking.getPiece();
                                            if(!isBlockingPiece.getSide().equals(from.getPiece().getSide()))
                                            {
                                                if(isBlockingPiece instanceof Queen || isBlockingPiece instanceof Rook)
                                                {
                                                    break;
                                                }
                                            }
                                            output.add(enPassant);
                                        }
                                    }
                                    else
                                    {
                                        output.add(enPassant);
                                    }
                                }
                                return output;
                            }
                        }
                    }
                }
                // also account for if checking piece is knight
                for(Square pathSquare : path)
                {
                    if((pathSquare.equalsSquare(path.get(0)) && pawnMoves.contains(pathSquare))) // if pawn legal moves contains checking piece
                    {
                        //  || pathSquare.equalsSquare(path.get(path.size()-1)) && pawnMoves.contains(pathSquare) idk what this was for
                        output.add(pathSquare);
                    }
                    if(pathSquare.getSquareCol() == pressed.getSquareCol())
                    {
                        for(Square legal : pawnMoves)
                        {
                            if(pathSquare.equalsSquare(legal)) // what is being done by == with squares?
                            {
                                output.add(legal);
                                break;
                                // also break out of outer loop?
                            }
                        }
                    }
                }
            }
        }

        else if(piece instanceof Bishop && attackLine.equalsIgnoreCase("diagonal"))
        {
            if(pressed.getColor() == path.get(0).getColor())
            {
                for(Square pathSquare : path)
                {
                    int rowDif = pathSquare.getSquareRow() - pressed.getSquareRow();
                    int colDif = pathSquare.getSquareCol() - pressed.getSquareCol();
                    if(Math.abs(rowDif) == Math.abs(colDif))
                    {
                        rowTrav = (rowDif < 0) ? -1 : 1;
                        colTrav = (colDif < 0) ? -1 : 1;
                        int distance = Math.abs(rowDif);
                        for(int i = 1; i <= distance; i++)
                        {
                            Square looking = chessBoard[pressed.getSquareRow() + (rowTrav * i)][pressed.getSquareCol() + (colTrav * i)];
                            if(looking.containsPiece())
                            {
                                if(looking.equalsSquare(path.get(0)))
                                {
                                    output.add(looking);
                                }
                                break;
                            }
                            else if(looking.equalsSquare(pathSquare))
                            {
                                output.add(looking);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            // TODO: This excludes the row and column in line with the King and checking piece
            // TODO: Account for queen intercepting diagonally for a diag attack
            // TODO: Check to see if piece is blocking check or not


            for(Square pathSquare : path)
            {
                int rowDif = Math.abs(pathSquare.getSquareRow() - pressed.getSquareRow());
                int colDif = Math.abs(pathSquare.getSquareCol() - pressed.getSquareCol());
                String interceptionLine = pathSquare.getSquareRow() == pressed.getSquareRow() ? "horizontal" : (pathSquare.getSquareCol() == pressed.getSquareCol()) ? "vertical" : (rowDif == colDif) ? "diagonal" : "nope";
                if((piece instanceof Rook && interceptionLine.equalsIgnoreCase("diagonal")) || (piece instanceof Bishop && (interceptionLine.equalsIgnoreCase("horizontal") || interceptionLine.equalsIgnoreCase("vertical"))))
                {
                    continue;
                }
                if(pathSquare.equalsSquare(path.get(0)) && interceptionLine.equalsIgnoreCase(attackLine))
                {
                    int pressedDistFromKingRow = Math.abs(pressed.getSquareRow() - path.get(path.size()-1).getSquareRow());
                    int pressedDistFromKingCol = Math.abs(pressed.getSquareCol() - path.get(path.size()-1).getSquareCol());
                    int pressedDistFromCheckRow = Math.abs(pressed.getSquareRow() - path.get(0).getSquareRow());
                    int pressedDistFromCheckCol = Math.abs(pressed.getSquareCol() - path.get(0).getSquareCol());

                    if(!interceptionLine.equalsIgnoreCase("diagonal"))
                    {
                        if(interceptionLine.equalsIgnoreCase("horizontal"))
                        {
                            if(pressedDistFromKingCol < pressedDistFromCheckCol)
                            {
                                break;
                            }
                        }
                        else if(interceptionLine.equalsIgnoreCase("vertical"))
                        {
                            if(pressedDistFromKingRow < pressedDistFromCheckRow)
                            {
                                break;
                            }
                        }
                    }
                    else
                    {
                        if(pressedDistFromKingRow < pressedDistFromCheckRow)
                        {
                            break;
                        }
                    }
                }

                if(!interceptionLine.equalsIgnoreCase("nope"))
                {
                    colTrav = (interceptionLine.equalsIgnoreCase("horizontal") || interceptionLine.equalsIgnoreCase("diagonal")) ? ((pressed.getSquareCol() - pathSquare.getSquareCol() > 0) ? -1 : 1) : 0;
                    rowTrav = (interceptionLine.equalsIgnoreCase("vertical") || interceptionLine.equalsIgnoreCase("diagonal")) ? ((pressed.getSquareRow() - pathSquare.getSquareRow() > 0) ? -1 : 1) : 0;
                    int distance = (interceptionLine.equalsIgnoreCase("horizontal")) ? Math.abs(pressed.getSquareCol() - pathSquare.getSquareCol()) : (interceptionLine.equalsIgnoreCase("vertical")) ? Math.abs(pressed.getSquareRow() - pathSquare.getSquareRow()) : (Math.abs(pressed.getSquareRow() - pathSquare.getSquareRow()));

                    boolean lookingDone = false;
                    boolean lookingTwoDone = false;
                    boolean nonDiagDone = false;

                    for(int i = 1; i <= distance; i++)
                    {
                        Square looking;
                        Square lookingTwo;
                        int rowForLooking = pressed.getSquareRow() + (rowTrav * i);
                        int colForLooking = pressed.getSquareCol() + (colTrav * i);
                        if(rowForLooking > -1 && rowForLooking < 8 && colForLooking > -1 && colForLooking < 8)
                        {
                            looking = chessBoard[rowForLooking][colForLooking];
                        }
                        else
                        {
                            lookingDone = true;
                            looking = pressed;
                        }
                        if(looking.containsPiece() && !looking.equalsSquare(pathSquare))
                        {
                            lookingDone = true;
                        }
                        if(looking.equalsSquare(pathSquare) && !lookingDone)
                        {
                            output.add(looking);
                            lookingDone = true;
                        }

                        if(!attackLine.equalsIgnoreCase("diagonal") && (piece instanceof Queen || piece instanceof Bishop))
                        {
                            if(attackLine.equalsIgnoreCase("horizontal"))
                            {
                                mirColTrav = -1 * colTrav;
                                mirRowTrav = rowTrav;
                            }
                            else
                            {
                                mirRowTrav = -1 * rowTrav;
                                mirColTrav = colTrav;
                            }
                            int rowForLookingTwo = pressed.getSquareRow() + (mirRowTrav * i);
                            int colForLookingTwo = pressed.getSquareCol() + (mirColTrav * i);
                            if(rowForLookingTwo > -1 && rowForLookingTwo < 8 && colForLookingTwo > -1 && colForLookingTwo < 8)
                            {
                                lookingTwo = chessBoard[pressed.getSquareRow() + (mirRowTrav * i)][pressed.getSquareCol() + (mirColTrav * i)];
                            }
                            else if(rowForLookingTwo <= -1 || rowForLookingTwo >= 8)
                            {
                                lookingTwoDone = true;
                                lookingTwo = pressed;
                            }
                            else
                            {
                                lookingTwoDone = true;
                                lookingTwo = pressed;
                            }

                            if(lookingTwo.containsPiece() && !lookingTwo.equalsSquare(pathSquare))
                            {
                                lookingTwoDone = true;
                            }
                            if(lookingTwo.equalsSquare(pathSquare) && !lookingTwoDone)
                            {
                                output.add(lookingTwo);
                                lookingTwoDone = true;
                            }
                            if(lookingDone && lookingTwoDone)
                            {
                                if(piece instanceof Bishop)
                                {
                                    break;
                                }
                            }

                        }
                        if(true) // account for queen intercepting diagonally for a diag attack
                        {
                            if(looking.containsPiece() && !lookingDone && !lookingTwoDone)
                            {
                                nonDiagDone = true;
                            }
                        }
                    }
                }
            }
        }
        if(!output.isEmpty())
        {
            ArrayList<Square> blocking = isBlocking(from);
            if(blocking == null)
            {
                //path.add(escapeSquare);
                return output;
            }
            else
            {
                for(Square block : blocking)
                {
                    for(Square limit : output)
                    {
                        if(block.equalsSquare(limit))
                        {
                            legalMoves.add(limit);
                        }
                    }
                }
                //path.add(escapeSquare);
                return legalMoves;
            }
        }
        return output;
    }
/*
    public MoveTransition makeMove(Move move, ChessBoard b, int depth)
    {

        ChessBoard transitionBoard = new ChessBoard(b);
        transitionBoard.from = move.fromHere;
        transitionBoard.pressed = move.toHere;
        transitionBoard.news = move.toHere;

        transitionBoard.updateCastleRights(depth);
        if(transitionBoard.enPassant != null && !transitionBoard.pressed.containsPiece())
        {
            if(transitionBoard.pressed.equalsSquare(transitionBoard.enPassant))
            {
                transitionBoard.captureEnPassant();
            }
            transitionBoard.enPassant.setBackground(transitionBoard.enPassant.revertColor());
        }
        transitionBoard.enPassant = null;

        transitionBoard.news.setPiece(transitionBoard.from.getPiece());
        transitionBoard.from.removePiece();

        transitionBoard.expCheck = transitionBoard.exposedCheck(transitionBoard.from, transitionBoard.news);
        if(transitionBoard.news.getPiece() instanceof Pawn && transitionBoard.promotionDone && (transitionBoard.news.getSquareRow() == 0 || transitionBoard.news.getSquareRow() == 7))
        {
            transitionBoard.original = transitionBoard.generatePawnPromotion(transitionBoard.news);
            transitionBoard.runDragAndRelease = false;
        }
        else if(transitionBoard.pressed.getBackground() == Color.WHITE)
        {
            transitionBoard.runDragAndRelease = false;
            transitionBoard.promotePawn();
        }
        if(transitionBoard.promotionDone)
        {
            ArrayList<Square> check = transitionBoard.news.getPiece().getLegalMoves();
            transitionBoard.thisIstheWay = transitionBoard.isInCheck(check, transitionBoard.news);
            //inputBoard.assessCheckMateAndStaleMate();
        }

        return new MoveTransition(b, transitionBoard, move, depth);
    }

 */

    // TODO: make sure piece to expose check isn't moving along the check path (still blocks check)
// TODO: make sure there's a clear path from enemy king to piece that can put it in check
    public ArrayList<Square> exposedCheck(Square oldSquare, Square newSquare)
    {
        int bpOldRow = oldSquare.getSquareRow();
        int bpOldCol = oldSquare.getSquareCol();
        int bpNewRow = newSquare.getSquareRow();
        int bpNewCol = newSquare.getSquareCol();
        int rowReverse;
        int colReverse;
        int oldRowDif;
        int oldColDif;
        int kingRow;
        int kingCol;
        Square kingSquare;
        ArrayList<Square> path = new ArrayList<>();
        if(newSquare.getPiece().getSide().equals(Piece.Side.WHITE))
        {
            kingSquare = blackKing;
            kingRow = blackKing.getSquareRow();
            kingCol = blackKing.getSquareCol();
            oldRowDif = kingRow - bpOldRow;
            oldColDif = kingCol - bpOldCol;
            rowReverse = (oldRowDif > 0) ? -1 : ((oldRowDif == 0) ? 0 : 1);
            colReverse = (oldColDif > 0) ? -1 : ((oldColDif == 0) ? 0 : 1);
        }
        else
        {
            kingSquare = whiteKing;
            kingRow = whiteKing.getSquareRow();
            kingCol = whiteKing.getSquareCol();
            oldRowDif = kingRow - bpOldRow;
            oldColDif = kingCol - bpOldCol;
            rowReverse = (oldRowDif > 0) ? -1 : ((oldRowDif == 0) ? 0 : 1);
            colReverse = (oldColDif > 0) ? -1 : ((oldColDif == 0) ? 0 : 1);
        }
        int newRowDif = kingRow - bpNewRow;
        int newColDif = kingCol - bpNewCol;

        int rowUnoReverse = -1 * rowReverse;
        int colUnoReverse = -1 * colReverse;

        if((oldRowDif != 0 && oldColDif != 0 && Math.abs(oldColDif) != Math.abs(oldRowDif)))
        {
            return null;
        }
        if(oldRowDif == 0 && oldColDif != 0 && newRowDif == 0)
        {
            return null;
        }
        else if(oldColDif == 0 && oldRowDif != 0 && newColDif == 0)
        {
            return null;
        }
        else if(Math.abs(oldRowDif) == Math.abs(oldColDif) && Math.abs(newRowDif) == Math.abs(newColDif))
        {
            return null;
        }
        path.add(kingSquare);
        for(int i = 1; i < 9; i++)
        {
            if(i == 8)
            {
                return null;
            }
            if(kingRow + (i * rowReverse ) < 8 && kingRow + (i * rowReverse) > -1 && kingCol + (i * colReverse) < 8 && kingCol + (i * colReverse) > -1)
            {
                Square looking = chessBoard[kingRow + (i * rowReverse)][kingCol + (i * colReverse)];
                if(looking.containsPiece())
                {
                     Piece lookingPiece = looking.getPiece();
                    if(looking.getPiece().getSide().equals(newSquare.getPiece().getSide()))
                    {
                        if((lookingPiece instanceof Rook || lookingPiece instanceof Queen) && (rowReverse == 0 || colReverse == 0))
                        {
                            path.add(0, looking);
                            if(lookingPiece.getSide().equals(Piece.Side.BLACK))
                            {
                                whiteInCheck = true;
                            }
                            else
                            {
                                blackInCheck = true;
                            }

                            if(kingRow + (rowUnoReverse ) < 8 && kingRow + (rowUnoReverse) > -1 && kingCol + (colUnoReverse) < 8 && kingCol + (colUnoReverse) > -1)
                            {
                                Square cantEscapeSquare = chessBoard[kingRow + (rowUnoReverse)][kingCol + (colUnoReverse)];
                                if(!cantEscapeSquare.containsPiece())
                                {
                                    path.add(cantEscapeSquare);
                                }
                            }
                            return path;
                        }
                        else if(rowReverse != 0 && colReverse != 0)
                        {
                            if(lookingPiece instanceof Bishop || lookingPiece instanceof Queen)
                            {
                                path.add(0, looking);
                                if(lookingPiece.getSide().equals(Piece.Side.BLACK))
                                {
                                    whiteInCheck = true;
                                }
                                else
                                {
                                    blackInCheck = true;
                                }

                                if(kingRow + (rowUnoReverse ) < 8 && kingRow + (rowUnoReverse) > -1 && kingCol + (colUnoReverse) < 8 && kingCol + (colUnoReverse) > -1)
                                {
                                    Square cantEscapeSquare = chessBoard[kingRow + (rowUnoReverse)][kingCol + (colUnoReverse)];
                                    if(!cantEscapeSquare.containsPiece())
                                    {
                                        path.add(cantEscapeSquare);
                                    }
                                }
                                return path;
                            }
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    path.add(0, looking);
                }
            }
        }
        return null;
    }
    // TODO: Probably taken care of elsewhere, but make sure piece can move along path that still blocks check (or takes piece if possible)
    //TODO: does this account for en passant?
    public ArrayList<Square> isBlocking(Square square)
    {
        from = square;
        int bpRow = from.getSquareRow();
        int bpCol = from.getSquareCol();
        int rowReverse;
        int colReverse;
        int kingRow;
        int kingCol;
        int rowDif;
        int colDif;
        ArrayList<Square> availableMoves = new ArrayList<>();
        if(from.getPiece().getSide().equals(Piece.Side.BLACK))
        {
            kingRow = blackKing.getSquareRow();
            kingCol = blackKing.getSquareCol();
            rowDif = kingRow - bpRow;
            colDif = kingCol - bpCol;
            rowReverse = (rowDif > 0) ? -1 : ((rowDif == 0) ? 0 : 1);
            colReverse = (colDif > 0) ? -1 : ((colDif == 0) ? 0 : 1);
        }
        else
        {
            kingRow = whiteKing.getSquareRow();
            kingCol = whiteKing.getSquareCol();
            rowDif = kingRow - bpRow;
            colDif = kingCol - bpCol;
            rowReverse = (rowDif > 0) ? -1 : ((rowDif == 0) ? 0 : 1);
            colReverse = (colDif > 0) ? -1 : ((colDif == 0) ? 0 : 1);
        }


        if((rowDif != 0 && colDif != 0 && Math.abs(colDif) != Math.abs(rowDif)))
        {
            return null;
        }
        for(int i = 1, j= 1; i < 9; i++, j++)
        {
            if(i == 8)
            {
                return null;
            }
            if(bpRow + (i * rowReverse ) < 8 && bpRow + (i * rowReverse) > -1 && bpCol + (i * colReverse) < 8 && bpCol + (i * colReverse) > -1)
            {
                Square looking = chessBoard[bpRow + (i * rowReverse)][bpCol + (i * colReverse)];
                if(looking.containsPiece()) // also check if in bounds
                {
                    Piece lookingPiece = looking.getPiece();
                    if(lookingPiece.getSide().equals(from.getPiece().getSide()) || lookingPiece instanceof Pawn || lookingPiece instanceof Knight || lookingPiece instanceof King)
                    {
                        return null;
                    }
                    if(!looking.getPiece().getSide().equals(from.getPiece().getSide()))
                    {
                        if((lookingPiece instanceof Rook || lookingPiece instanceof Queen) && (rowReverse == 0 || colReverse == 0))
                        {
                            availableMoves.add(looking);
                            break;
                        }
                        else if(rowReverse != 0 && colReverse != 0)
                        {
                            if(lookingPiece instanceof Bishop || lookingPiece instanceof Queen)
                            {
                                availableMoves.add(looking);
                                break;
                            }
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    availableMoves.add(looking);
                }
            }
        }
        rowReverse = -1 * rowReverse;
        colReverse = -1 * colReverse;
        for(int i = 1; i < 9; i++)
        {
            if(bpRow + (i * rowReverse ) < 8 && bpRow + (i * rowReverse) > -1 && bpCol + (i * colReverse) < 8 && bpCol + (i * colReverse) > -1)
            {
                Square looking = chessBoard[bpRow + (i * rowReverse)][bpCol + (i * colReverse)];
                if(!looking.containsPiece())
                {
                    availableMoves.add(looking);
                }
                else if(looking.getPiece().getSide().equals(from.getPiece().getSide()) && !(looking.getPiece() instanceof King))
                {
                    return null;
                }
                else if(looking.getPiece() instanceof King)
                {
                    return availableMoves;
                }
            }
        }
        return null;
    }

    public boolean noAvailableMoves()
    {
        Square kingSquare = (whiteInCheck) ? whiteKing : (blackInCheck) ? blackKing : null;
        String sideInCheck = (whiteInCheck) ? "white" : (blackInCheck) ? "black" : null;
        Piece.Side sideToCheck = null;
        // check the side of who's turn it is bc changeside earlier flips it
        if(kingSquare == null)
        {
            kingSquare = (whiteTurn) ? whiteKing : blackKing;
            //sideToCheck = (whiteTurn) ? "white" : "black";
            sideToCheck = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        }
        from = kingSquare;
        ArrayList<Square> kingMoves = whereKingCanGo(kingSquare);
        if(kingMoves.size() != 0)
        {
            return false;
        }
        else
        {
            for(Square[] row : chessBoard)
            {
                for(Square square : row)
                {
                    if(square.containsPiece())
                    {
                        if(square.getPiece() instanceof King)
                        {
                            continue;
                        }
                        if(sideInCheck != null && square.getPiece().getSide().equals(sideInCheck))
                        {
                            ArrayList<Square> moves = movesIfInCheck(square);
                            if(!moves.isEmpty())
                            {
                                return false;
                            }
                        }
                        else if(sideToCheck != null && square.getPiece().getSide().equals(sideToCheck))
                        {
                            ArrayList<Square> blocking = isBlocking(square);
                            ArrayList<Square> legalMoves = square.getPiece().getLegalMoves();
                            if(blocking == null)
                            {
                                if(!legalMoves.isEmpty())
                                {
                                    return false;
                                }
                            }
                            else
                            {
                                for(Square block : blocking)
                                {
                                    for(Square legal : legalMoves)
                                    {
                                        if(block.equalsSquare(legal))
                                        {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

    }

    public ArrayList<Piece> generatePawnPromotion(Square pawnSquare)
    {
        promotionDone = false;
        isPressed = true;
        ArrayList<Piece> original = new ArrayList<>();
        ArrayList<String> overlayPicList = new ArrayList<>();
        if(pawnSquare.getPiece().getSide().equals(Piece.Side.WHITE))
        {
            overlayPicList.add("Pictures/queen.png");
            overlayPicList.add("Pictures/knight.png");
            overlayPicList.add("Pictures/rook.png");
            overlayPicList.add("Pictures/bishop.png");

        }
        else
        {
            overlayPicList.add("Pictures/red queen.png");
            overlayPicList.add("Pictures/red knight.png");
            overlayPicList.add("Pictures/red rook.png");
            overlayPicList.add("Pictures/red bishop.png");
        }
        int pRow = pawnSquare.getSquareRow();
        int pCol = pawnSquare.getSquareCol();
        int rowTrav = (pawnSquare.getPiece().getSide().equals(Piece.Side.WHITE)) ? 1 : -1;
        // TODO: an array of Squares with some null squares might cause issue
        for(int i = 0; i < 4; i++)
        {
            Square overlaySquare = chessBoard[pRow + (i * rowTrav)][pCol];
            if(overlaySquare.containsPiece())
            {
                original.add(overlaySquare.getPiece());
            }
            else
            {
                original.add(null);
            }
        }
        for(int i = 0; i < 4; i++)
        {
            Square overlaySquare = chessBoard[pRow + (i * rowTrav)][pCol];
            overlaySquare.setBackground(Color.WHITE);
            try{
                if(i == 0)
                {
                    overlaySquare.setPiece(new Queen(overlayPicList.get(i)));
                }
                else if(i == 1)
                {
                    overlaySquare.setPiece(new Knight(overlayPicList.get(i)));
                }
                else if(i == 2)
                {
                    overlaySquare.setPiece(new Rook(overlayPicList.get(i)));
                }
                else if(i == 3)
                {
                    overlaySquare.setPiece(new Bishop(overlayPicList.get(i)));
                }

                //overlaySquare.setPiece(new Piece(overlayPicList.get(i)));
            }
            catch (IOException ex)
            {
                System.out.println("lol ioexception");
            }

        }
        return original;
    }
}
