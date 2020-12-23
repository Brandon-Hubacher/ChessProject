import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
/*
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    public static Square[][] board = new Square[8][8];
    public static Square[][] promoteOverlay = new Square[8][8];
    JPanel f = new JPanel();
    public static boolean whiteTurn;
    public static boolean whiteInCheck = false;
    public static boolean blackInCheck = false;
    public Square whiteKing;
    public Square blackKing;
    public ArrayList<Square> thisIstheWay;
    public ArrayList<Square> expCheck;
    public ArrayList<Square> availableKingMoves;
    public ArrayList<Square> availablePieceMoves;
    public boolean promotionDone = true;
    public ArrayList<Pieces> original;
    public boolean blackKingSideRookMoved = false;
    public boolean blackQueenSideRookMoved = false;
    public boolean blackKingMoved = false;
    public boolean whiteKingSideRookMoved = false;
    public boolean whiteQueenSideRookMoved = false;
    public boolean whiteKingMoved = false;
    public Square enPassant = null;
    public int fiftyMoveDraw = 0;
    public int threeFoldRep = 1;
    public int fiveFoldRep = 1;
    public int modFour = 0;
    public Square[][] repetition = new Square[8][8];
    public boolean canEnPassant = false;
    public ArrayList<Pieces> whitesCapturedPieces = new ArrayList<>();
    public ArrayList<Pieces> blacksCapturedPieces = new ArrayList<>();
    public Square olds = null;
    boolean isPressed = false;
    public Square news;

    public Board() {
        whiteTurn = true;
        Dimension size = new Dimension(1000, 1000);
        f.setLayout(new GridLayout(8, 8));
        f.setPreferredSize(size);
        add(f); // what does this do?
        addMouseMotionListener(this);
        addMouseListener(this);
        Color color;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Square(row, col);
                if (row % 2 == 0) {
                    color = (col % 2 != 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
                } else {
                    color = (col % 2 == 0) ? Color.decode("#769656") : Color.decode("#eeeed2");
                }
                board[row][col].setBackground(color);
                try{
                    if(row == 0)
                    {
                        if(col == 0 || col == 7) { board[row][col].set(new Pieces("Pictures/red rook.png", new int[]{row,col}, "black", "rook"));}
                        if(col == 1 || col == 6) { board[row][col].set(new Pieces("Pictures/red knight.png", new int[]{row,col}, "black", "knight"));}
                        if(col == 2 || col == 5) { board[row][col].set(new Pieces("Pictures/red bishop.png", new int[]{row,col}, "black", "bishop"));}
                        if(col == 3) { board[row][col].set(new Pieces("Pictures/red queen.png", new int[]{row,col}, "black", "queen"));}
                        if(col == 4)
                        {
                            board[row][col].set(new Pieces("Pictures/red king.png", new int[]{row,col}, "black", "king"));
                            blackKing = board[row][col];
                        }
                    }
                    else if(row == 1)
                    {
                        board[row][col].set(new Pieces("Pictures/red pawn.png", new int[]{row,col}, "black", "pawn"));
                    }
                    else if(row == 6)
                    {
                        board[row][col].set(new Pieces("Pictures/pawn.png", new int[]{row,col}, "white", "pawn"));
                    }
                    else if(row == 7)
                    {
                        if(col == 0 || col == 7) { board[row][col].set(new Pieces("Pictures/rook.png", new int[]{row,col}, "white", "rook"));}
                        if(col == 1 || col == 6) { board[row][col].set(new Pieces("Pictures/knight.png", new int[]{row,col}, "white", "knight"));}
                        if(col == 2 || col == 5) { board[row][col].set(new Pieces("Pictures/bishop.png", new int[]{row,col}, "white", "bishop"));}
                        if(col == 3) { board[row][col].set(new Pieces("Pictures/queen.png", new int[]{row,col}, "white", "queen"));}
                        if(col == 4)
                        {
                            board[row][col].set(new Pieces("Pictures/king.png", new int[]{row,col}, "white", "king"));
                            whiteKing = board[row][col];
                        }
                    }
                }
                catch (IOException ex)
                {
                    System.out.println("lol ioexception");
                }
                f.add(board[row][col]);
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                Square s = board[x][y];
                s.paintComponent(g);
            }
        }
    }

    public void mouseMoved(MouseEvent e){}
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    public void mouseClicked(MouseEvent e)
    {
        int row = e.getY()/(e.getComponent().getHeight() / 8);
        int col = e.getX()/(e.getComponent().getWidth() / 8);
        Square pressed = board[row][col];

        if(!isPressed && promotionDone)
        {
            if(pressed.containsPiece())
            {
                Pieces pressedPiece = pressed.getPiece();
                String pressedType = pressedPiece.getType();
                String pressedSide = pressedPiece.getSide();

                if((pressedSide.equalsIgnoreCase("white") && !whiteTurn) || ((pressedSide.equalsIgnoreCase("black") && whiteTurn)))
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
                if(pressed.containsPiece() && olds.getPiece().getSide().equalsIgnoreCase(pressed.getPiece().getSide()) && promotionDone)
                {
                    Pieces pressedPiece = pressed.getPiece();

                    if(!olds.equalsSquare(pressed))
                    {
                        System.out.println("you're pressing a diff piece");
                        pressDiffPiece(pressed);
                        repaint();
                    }
                    else
                    {
                        System.out.println("you're pressing the same piece");
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
                    Pieces pressedPiece = pressed.getPiece();
                    String pressedType = pressedPiece.getType();
                    String pressedSide = pressedPiece.getSide();

                    if(pressedSide.equalsIgnoreCase("black"))
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
                if(news.getPiece().getType().equalsIgnoreCase("pawn") && promotionDone && (news.getSquareRow() == 0 || news.getSquareRow() == 7))
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
    }

// when checking legal moves, if opponent King is on one of the squares put the square at i = 0 in arraylist. then check to see if any of the squares along the path of the checking piece to the king are shared by any pieces on the checked side

    public void captureEnPassant()
    {
        Square removePiece;
        if(olds.getPiece().getSide().equalsIgnoreCase("white") && olds.getPiece().getType().equalsIgnoreCase("pawn"))
        {
            removePiece = enPassant.getDown(1);
            removePiece.removePiece();
        }
        else if(olds.getPiece().getSide().equalsIgnoreCase("black") && olds.getPiece().getType().equalsIgnoreCase("pawn"))
        {
            removePiece = enPassant.getUp(1);
            removePiece.removePiece();
        }
    }

    public void clickAway()
    {
        olds.setBackground(olds.revertColor());
        olds.revertColor(olds.getPiece().getLegalMoves());
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
        if(pressed.getPiece().getType().equalsIgnoreCase("king"))
        {
            availableKingMoves = whereKingCanGo(olds);
            ArrayList<Square> castleMoves = getCastleMoves();
            availableKingMoves.addAll(castleMoves);
            moves = availableKingMoves;
        }
        else
        {
            if(enPassant != null && pressed.getPiece().getType().equalsIgnoreCase("pawn"))
            {
                int rowTrav = (pressed.getPiece().getSide().equalsIgnoreCase("white")) ? -1 : 1;
                if((pressed.getSquareRow() + rowTrav == enPassant.getSquareRow()) && ((pressed.getSquareCol() + 1 == enPassant.getSquareCol()) || (pressed.getSquareCol() - 1 == enPassant.getSquareCol())))
                {
                    //enPassant.setBackground(Color.BLUE);
                    //repaint();
                    canEnPassant = true;
                    moves.add(enPassant);
                }
            }
            ArrayList<Square> blocking = isBlocking(olds);
            if(blocking == null)
            {
                moves.addAll(olds.getPiece().getLegalMoves());
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
            System.out.println("checking for stalemate");
            boolean inStaleMate = noAvailableMoves();
            System.out.println("inStaleMate: "+inStaleMate);
            if(inStaleMate)
            {
                System.out.println("STALEMATE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
    }

    public void promotePawn()
    {
        // TODO: get rid of cancelButton
        int rowTrav = (news.getPiece().getSide().equalsIgnoreCase("white")) ? 1 : -1;
        ArrayList<Square> toRevert = new ArrayList<>();
        int origRow = olds.getSquareRow() + (-1 * rowTrav);
        int origCol = news.getSquareCol();
        for(int i = 0; i < original.size(); i++)
        {
            if(i == 0)
            {
                toRevert.add(board[origRow + (i * rowTrav)][origCol]);
                Pieces temp = news.getPiece();
                board[origRow + (i * rowTrav)][origCol].removePiece();
                board[origRow + (i * rowTrav)][origCol].set(temp);
                board[origRow + (i * rowTrav)][origCol].getPiece().setPlacement(origRow + (i * rowTrav), origCol);
            }
            else
            {
                toRevert.add(board[origRow + (i * rowTrav)][origCol]);
                board[origRow + (i * rowTrav)][origCol].removePiece();
                if(original.get(i) != null)
                {
                    board[origRow + (i * rowTrav)][origCol].set(original.get(i));
                    board[origRow + (i * rowTrav)][origCol].getPiece().setPlacement(origRow + (i * rowTrav), origCol);
                }
            }
        }
        revertColor(toRevert);
        repaint();
        olds = news = toRevert.get(0);
        promotionDone = true;
        isPressed = false;
    }

    public void updateCastleRights()
    {
        if(olds.getPiece().getType().equalsIgnoreCase("rook"))
        {
            if(olds.getSquareRow() == 0 && olds.getSquareCol() == 0)
            {
                blackQueenSideRookMoved = true;
            }
            else if(olds.getSquareRow() == 0 && olds.getSquareCol() == 7)
            {
                blackKingSideRookMoved = true;
            }
            else if(olds.getSquareRow() == 7 && olds.getSquareCol() == 7)
            {
                whiteKingSideRookMoved = true;
            }
            else if(olds.getSquareRow() == 7 && olds.getSquareCol() == 0)
            {
                whiteQueenSideRookMoved = true;
            }
        }
        else if(olds.getPiece().getType().equalsIgnoreCase("king"))
        {
            if(olds.getSquareRow() == 0 && olds.getSquareCol() == 4)
            {
                blackKingMoved = true;
            }
            else if(olds.getSquareRow() == 7 && olds.getSquareCol() == 4)
            {
                whiteKingMoved = true;
            }
        }
    }

    public ArrayList<Square> getCastleMoves()
    {
        //boolean canCastle = false;
        ArrayList<Square> kingSideCastle = new ArrayList<>();
        ArrayList<Square> queenSideCastle = new ArrayList<>();
        if(olds.getPiece().getSide().equalsIgnoreCase("white"))
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
                                Square kingPass = olds.getRight(z);
                                kingPass.set(whiteKing.getPiece());
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
                        if(olds.getRight(i).containsPiece() && i != 3)
                        {
                            kingSideCastle.clear();
                            break;
                        }
                        if(i != 3)
                        {
                            kingSideCastle.add(olds.getRight(i));
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
                                Square kingPass = olds.getLeft(z);
                                kingPass.set(whiteKing.getPiece());
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
                        if(olds.getLeft(i).containsPiece() && i != 4)
                        {
                            queenSideCastle.clear();
                            break;
                        }
                        if(i != 3 && i != 4)
                        {
                            queenSideCastle.add(olds.getLeft(i));
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
                                Square kingPass = olds.getRight(z);
                                kingPass.set(blackKing.getPiece());
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
                        if(olds.getRight(i).containsPiece() && i != 3)
                        {
                            kingSideCastle.clear();
                            break;
                        }
                        if(i != 3)
                        {
                            kingSideCastle.add(olds.getRight(i));
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
                                Square kingPass = olds.getLeft(z);
                                kingPass.set(blackKing.getPiece());
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
                        if(olds.getLeft(i).containsPiece() && i != 4)
                        {
                            queenSideCastle.clear();
                            break;
                        }
                        if(i != 3 && i != 4)
                        {
                            queenSideCastle.add(olds.getLeft(i));
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
        if(pressed.getSquareRow() == 7 && pressed.getSquareCol() == 6 && pressed.getBackground() == Color.BLUE)
        {
            updateKing(pressed);
            Square newRookSquare = whiteKing.getLeft(1);
            Pieces tempRookPiece = whiteKing.getRight(1).getPiece();
            whiteKing.getRight(1).removePiece();
            newRookSquare.set(tempRookPiece);
            newRookSquare.getPiece().setPlacement(newRookSquare.getSquareRow(), newRookSquare.getSquareCol());


            return true;
        }
        else if(pressed.getSquareRow() == 7 && pressed.getSquareCol() == 2 && pressed.getBackground() == Color.BLUE)
        {
            updateKing(pressed);
            Square newRookSquare = whiteKing.getRight(1);
            Pieces tempRookPiece = whiteKing.getLeft(2).getPiece();
            whiteKing.getLeft(2).removePiece();
            newRookSquare.set(tempRookPiece);
            newRookSquare.getPiece().setPlacement(newRookSquare.getSquareRow(), newRookSquare.getSquareCol());
            return true;
        }
        else if(pressed.getSquareRow() == 0 && pressed.getSquareCol() == 6 && pressed.getBackground() == Color.BLUE)
        {
            updateKing(pressed);
            Square newRookSquare = blackKing.getLeft(1);
            Pieces tempRookPiece = blackKing.getRight(1).getPiece();
            blackKing.getRight(1).removePiece();
            newRookSquare.set(tempRookPiece);
            newRookSquare.getPiece().setPlacement(newRookSquare.getSquareRow(), newRookSquare.getSquareCol());
            return true;
        }
        else if(pressed.getSquareRow() == 0 & pressed.getSquareCol() == 2 && pressed.getBackground() == Color.BLUE)
        {
            updateKing(pressed);
            Square newRookSquare = blackKing.getRight(1);
            Pieces tempRookPiece = blackKing.getLeft(2).getPiece();
            blackKing.getLeft(2).removePiece();
            newRookSquare.set(tempRookPiece);
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
            }
        }
    }

    public void pressDiffPiece(Square pressed)
    {
        ArrayList<Square> moves = new ArrayList<>();
        olds.setBackground(olds.revertColor());
        if(whiteInCheck || blackInCheck)
        {
            //olds.setBackground(olds.revertColor());
            if(olds.getPiece().getType().equalsIgnoreCase("king"))
            {
                olds.revertColor(availableKingMoves);
                //olds.revertColor(availablePieceMoves);

            }
            else
            {
                olds.revertColor(olds.getPiece().getLegalMoves());
            }
            olds = pressed;
            olds.set(pressed.getPiece());
            olds.setBackground(Color.black);
            if(pressed.getPiece().getType().equalsIgnoreCase("king"))
            {
                availableKingMoves = movesIfInCheck(pressed);
                paintMoves(availableKingMoves);
            }
            else
            {
                availablePieceMoves = movesIfInCheck(olds);
                paintMoves(availablePieceMoves);
                //ArrayList<Square> m = ifInCheck(olds);
                //paintMoves(m);
            }
        }
        else
        {
            if(olds.getPiece().getType().equalsIgnoreCase("king"))
            {
                olds.revertColor(availableKingMoves);
            }
            else
            {
                olds.revertColor(availablePieceMoves);
            }
            //olds.revertColor(olds.getPiece().getLegalMoves());
            olds = pressed;
            moves = getMoves(pressed);
            paintMoves(moves);
            isPressed = true;
        }
    }

    public void switchTurn(String oldsSide)
    {
        if(oldsSide.equalsIgnoreCase("white"))
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
        Pieces p = olds.getPiece();
        if(p.getType().equalsIgnoreCase("king"))
        {
            if(p.getSide().equalsIgnoreCase("black"))
            {
                blackKing = s;
                blackKing.set(p);
                blackKing.getPiece().setPlacement(blackKing.getSquareRow(), blackKing.getSquareCol());
            }
            else
            {
                whiteKing = s;
                whiteKing.set(p);
                whiteKing.getPiece().setPlacement(whiteKing.getSquareRow(), whiteKing.getSquareCol());
            }
        }
    }


    public void movePiece(Square pressed, int row, int col)
    {
        olds.setBackground(olds.revertColor());
        if(!olds.getPiece().getType().equalsIgnoreCase("pawn") && !pressed.containsPiece())
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


            fiftyMoveDraw = 0;
        }
        if(olds.getPiece().getType().equalsIgnoreCase("king"))
        {
            olds.revertColor(availableKingMoves);
            updateKing(pressed);
        }
        else
        {
            olds.revertColor(availablePieceMoves);
        }
        if(olds.getPiece().getType().equalsIgnoreCase("pawn"))
        {
            if(Math.abs(olds.getSquareRow() - pressed.getSquareRow()) == 2)
            {
                if(olds.getPiece().getSide().equalsIgnoreCase("black"))
                {
                    enPassant = board[pressed.getSquareRow() - 1][pressed.getSquareCol()];
                }
                else
                {
                    enPassant = board[pressed.getSquareRow() + 1][pressed.getSquareCol()];
                }
            }
        }
        if(whiteInCheck || blackInCheck)
        {
            noLongerInCheck();
        }
        news = pressed;
        Pieces oldsPiece = olds.getPiece();
        String oldsSide = oldsPiece.getSide();

        news.set(oldsPiece);
        news.getPiece().setPlacement(new int[]{row, col});
        switchTurn(oldsSide);
        olds.removePiece();
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


        isPressed = false;
        repaint();
    }

    public void noLongerInCheck()
    {
        thisIstheWay = null;
        expCheck = null;
        availableKingMoves = null;
        whiteInCheck = false;
        blackInCheck = false;
    }

    public void paintMoves(ArrayList<Square> moves)
    {
        olds.setBackground(Color.BLACK);
        for(Square s : moves)
        {
            if(s.containsPiece())
            {
                if(!s.getPiece().getType().equalsIgnoreCase("king"))
                {
                    s.setBackground(Color.BLUE);
                }
            }
            else
            {
                s.setBackground(Color.BLUE);
            }
        }
    }

    public ArrayList<Square> movesIfInCheck(Square pressed)
    {
        Square king = (Board.whiteInCheck) ? whiteKing : blackKing;
        String pressedType = pressed.getPiece().getType();
        if(pressedType.equalsIgnoreCase("king"))
        {
            availableKingMoves = whereKingCanGo(pressed);
            return availableKingMoves;
        }
        else
        {
            ArrayList<Square> pathway = new ArrayList<>();
            for(int i = 0; i < thisIstheWay.size(); i++)
            {
                pathway.add(thisIstheWay.get(i));
            }
            //ArrayList<Square> limit = limitMoves(pathway, pressed);
            //return limit;
            availablePieceMoves = limitMoves(pathway, pressed);
            return availablePieceMoves;
        }
    }

    public ArrayList<Square> ifBlocking(ArrayList<Square> blocking, boolean canEnPassant)
    {
        ArrayList<Square> legalMoves = olds.getPiece().getLegalMoves();
        ArrayList<Square> output = new ArrayList<>();
        if(olds.getPiece().getType().equalsIgnoreCase("pawn") && canEnPassant)
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
        for(Square s : check)
        {
            if(s.containsPiece())
            {
                if(s.getPiece().getType().equalsIgnoreCase("king"))
                {
                    ArrayList<Square> path = new ArrayList<>();
                    int pRow = checkingPiece.getSquareRow();
                    int pCol = checkingPiece.getSquareCol();
                    Square king = s.getPiece().getSide().equalsIgnoreCase("white") ? whiteKing : blackKing;
                    if(king.getPiece().getSide().equalsIgnoreCase("white"))
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
                    if(checkingPiece.getPiece().getType().equalsIgnoreCase("knight"))
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
                                        path.add(board[pRow+1][pCol+2]);
                                    }
                                }
                                else if(colDif == 2)
                                {
                                    if(!(pRow+2 > 7) && !(pCol+1 > 7))
                                    {
                                        path.add(board[pRow+2][pCol+1]);
                                    }
                                }
                            }
                            else if(colDif < 0)
                            {
                                if(rowDif == 2)
                                {
                                    if(!(pRow+1 > 7) && !(pCol-2 < 0))
                                    {
                                        path.add(board[pRow+1][pCol-2]);
                                    }
                                }
                                else if(colDif == -2)
                                {
                                    if(!(pRow+2 > 7) && !(pCol-1 < 0))
                                    {
                                        path.add(board[pRow+2][pCol-1]);
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
                                        path.add(board[pRow-1][pCol+2]);
                                    }
                                }
                                else if(colDif == 2)
                                {
                                    if(!(pRow-2 < 0) && !(pCol+1 > 7))
                                    {
                                        path.add(board[pRow-2][pCol+1]);
                                    }
                                }
                            }
                            else if(colDif < 0)
                            {
                                if(rowDif == -2)
                                {
                                    if(!(pRow-1 < 0) && !(pCol-2 < 0))
                                    {
                                        path.add(board[pRow-1][pCol-2]);
                                    }
                                }
                                else if(colDif == -2)
                                {
                                    if(!(pRow-2 < 0) && !(pCol-1 < 0))
                                    {
                                        path.add(board[pRow-2][pCol-1]);
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
                                    path.add(board[pRow][pCol+i]);
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
                                    path.add(board[pRow][pCol-i]);
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
                                    path.add(board[pRow+i][pCol]);
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
                                    path.add(board[pRow-i][pCol]);
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
                                        path.add(board[pRow+i][pCol+i]);
                                    }
                                }
                            }
                            else
                            {
                                for(int i = 1; i <= rowDif+1; i++)
                                {
                                    if(!(pRow+i > 7) && !(pCol-i < 0))
                                    {
                                        path.add(board[pRow+i][pCol-i]);
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
                                        path.add(board[pRow-i][pCol+i]);
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
                                        path.add(board[pRow-i][pCol-i]);
                                    }
                                }
                            }
                        }
                    }
                    if(path.get(path.size()-1).containsPiece())
                    {
                        if(!path.get(path.size()-1).getPiece().getType().equalsIgnoreCase("king"))
                        {
                            path.remove(path.size()-1);
                        }
                    }
                    else if(checkingPiece.getPiece().getType().equalsIgnoreCase("pawn"))
                    {
                        path.remove(path.size()-1);
                    }
                    return path;
                }
            }
        }
        return null;
    }

    public ArrayList<Square> whereKingCanGo(Square kingSquare)
    {
        Square enemyKingSquare = (kingSquare.equalsSquare(whiteKing)) ? blackKing : whiteKing;
        String enemyKingSide = enemyKingSquare.getPiece().getSide();
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

                    if(thisIstheWay.get(0).getPiece().getType().equalsIgnoreCase("knight") && s.getSquareRow() == thisIstheWay.get(thisIstheWay.size()-1).getSquareRow() && s.getSquareCol() == thisIstheWay.get(thisIstheWay.size()-1).getSquareCol())
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
                s.set(kingSquare.getPiece());
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
                kingSquare.set(s.getPiece());
                s.getPiece().setPlacement(kingSquare.getSquareRow(), kingSquare.getSquareCol());
                s.removePiece();
            }
            else
            {
                Pieces temporary = s.getPiece();
                s.set(kingSquare.getPiece());
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
                kingSquare.set(s.getPiece());
                kingSquare.getPiece().setPlacement(kingSquare.getSquareRow(), kingSquare.getSquareCol());
                s.set(temporary);
                s.getPiece().setPlacement(s.getSquareRow(), s.getSquareCol());
            }
        }
        return kingLegalMoves;
    }

    public ArrayList<Square> limitMoves(ArrayList<Square> path, Square pressed)
    {
        // not sure what this was for but it tries to allow king to escape where it's still in line with attacking piece
        //thisIstheWay = path;
        Square escapeSquare = path.get(path.size()-1);

        if(!path.get(path.size()-1).containsPiece())
        {
            path.remove(path.size()-1);
        }
        ArrayList<Square> legalMoves = new ArrayList<>();
        ArrayList<Square> output = new ArrayList<>();

        Pieces piece = pressed.getPiece();
        String attackLine = (path.get(0).getSquareCol() - path.get(path.size()-1).getSquareCol() == 0) ? "vertical" : (path.get(0).getSquareRow() - path.get(path.size()-1).getSquareRow() == 0) ? "horizontal" : "diagonal";

        int rowTrav;
        int colTrav;
        int mirRowTrav;
        int mirColTrav;

        if(path.get(0).getPiece().getType().equalsIgnoreCase("knight"))
        {
            attackLine = "knight";
            Square temp = path.get(0);
            path.clear();
            path.add(temp);
        }
        if(piece.getType().equalsIgnoreCase("knight"))
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
        else if(piece.getType().equalsIgnoreCase("pawn"))
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
                if(enPassant != null && pressed.getPiece().getType().equalsIgnoreCase("pawn"))
                {
                    rowTrav = (pressed.getPiece().getSide().equalsIgnoreCase("white")) ? -1 : 1;
                    if((pressed.getSquareRow() + rowTrav == enPassant.getSquareRow()) && ((pressed.getSquareCol() + 1 == enPassant.getSquareCol()) || (pressed.getSquareCol() - 1 == enPassant.getSquareCol())))
                    {
                        if(thisIstheWay.get(0).getPiece().getType().equalsIgnoreCase("pawn") && enPassant.getDown(rowTrav * -1).containsPiece())
                        {
                            if(enPassant.getDown(rowTrav * -1).getPiece().getType().equalsIgnoreCase("pawn"))
                            {
                                int pawnRow = olds.getSquareRow();
                                int pawnCol = olds.getSquareCol();
                                for(int i = 1; i < 8; i++)
                                {
                                    Square isBlocking = board[pawnRow + (i * rowTrav)][pawnCol];
                                    if(pawnRow < 8 && pawnRow > -1)
                                    {
                                        if(isBlocking.containsPiece())
                                        {
                                            Pieces isBlockingPiece = isBlocking.getPiece();
                                            if(!isBlockingPiece.getSide().equalsIgnoreCase(olds.getPiece().getSide()))
                                            {
                                                if(isBlockingPiece.getType().equalsIgnoreCase("queen") || isBlockingPiece.getType().equalsIgnoreCase("rook"))
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

        else if(piece.getType().equalsIgnoreCase("bishop") && attackLine.equalsIgnoreCase("diagonal"))
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
                            Square looking = board[pressed.getSquareRow() + (rowTrav * i)][pressed.getSquareCol() + (colTrav * i)];
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
                if((piece.getType().equalsIgnoreCase("rook") && interceptionLine.equalsIgnoreCase("diagonal")) || (piece.getType().equalsIgnoreCase("bishop") && (interceptionLine.equalsIgnoreCase("horizontal") || interceptionLine.equalsIgnoreCase("vertical"))))
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
                            looking = board[rowForLooking][colForLooking];
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

                        if(!attackLine.equalsIgnoreCase("diagonal") && (piece.getType().equalsIgnoreCase("queen") || piece.getType().equalsIgnoreCase("bishop")))
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
                                lookingTwo = board[pressed.getSquareRow() + (mirRowTrav * i)][pressed.getSquareCol() + (mirColTrav * i)];
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
                                if(piece.getType().equalsIgnoreCase("bishop"))
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
            ArrayList<Square> blocking = isBlocking(olds);
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
        if(newSquare.getPiece().getSide().equalsIgnoreCase("white"))
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
                Square looking = board[kingRow + (i * rowReverse)][kingCol + (i * colReverse)];
                if(looking.containsPiece())
                {
                    Pieces lookingPiece = looking.getPiece();
                    if(looking.getPiece().getSide().equalsIgnoreCase(newSquare.getPiece().getSide()))
                    {
                        if((lookingPiece.getType().equalsIgnoreCase("rook") || lookingPiece.getType().equalsIgnoreCase("queen")) && (rowReverse == 0 || colReverse == 0))
                        {
                            path.add(0, looking);
                            if(lookingPiece.getSide().equalsIgnoreCase("black"))
                            {
                                whiteInCheck = true;
                            }
                            else
                            {
                                blackInCheck = true;
                            }

                            if(kingRow + (rowUnoReverse ) < 8 && kingRow + (rowUnoReverse) > -1 && kingCol + (colUnoReverse) < 8 && kingCol + (colUnoReverse) > -1)
                            {
                                Square cantEscapeSquare = board[kingRow + (rowUnoReverse)][kingCol + (colUnoReverse)];
                                if(!cantEscapeSquare.containsPiece())
                                {
                                    path.add(cantEscapeSquare);
                                }
                            }
                            return path;
                        }
                        else if(rowReverse != 0 && colReverse != 0)
                        {
                            if(lookingPiece.getType().equalsIgnoreCase("bishop") || lookingPiece.getType().equalsIgnoreCase("queen"))
                            {
                                path.add(0, looking);
                                if(lookingPiece.getSide().equalsIgnoreCase("black"))
                                {
                                    whiteInCheck = true;
                                }
                                else
                                {
                                    blackInCheck = true;
                                }

                                if(kingRow + (rowUnoReverse ) < 8 && kingRow + (rowUnoReverse) > -1 && kingCol + (colUnoReverse) < 8 && kingCol + (colUnoReverse) > -1)
                                {
                                    Square cantEscapeSquare = board[kingRow + (rowUnoReverse)][kingCol + (colUnoReverse)];
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
        olds = square;
        int bpRow = olds.getSquareRow();
        int bpCol = olds.getSquareCol();
        int rowReverse;
        int colReverse;
        int kingRow;
        int kingCol;
        int rowDif;
        int colDif;
        ArrayList<Square> availableMoves = new ArrayList<>();
        if(olds.getPiece().getSide().equalsIgnoreCase("black"))
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
                Square looking = board[bpRow + (i * rowReverse)][bpCol + (i * colReverse)];
                if(looking.containsPiece()) // also check if in bounds
                {
                    Pieces lookingPiece = looking.getPiece();
                    if(lookingPiece.getSide().equalsIgnoreCase(olds.getPiece().getSide()) || lookingPiece.getType().equalsIgnoreCase("pawn") || lookingPiece.getType().equalsIgnoreCase("knight") || lookingPiece.getType().equalsIgnoreCase("king"))
                    {
                        return null;
                    }
                    if(!looking.getPiece().getSide().equalsIgnoreCase(olds.getPiece().getSide()))
                    {
                        if((lookingPiece.getType().equalsIgnoreCase("rook") || lookingPiece.getType().equalsIgnoreCase("queen")) && (rowReverse == 0 || colReverse == 0))
                        {
                            availableMoves.add(looking);
                            break;
                        }
                        else if(rowReverse != 0 && colReverse != 0)
                        {
                            if(lookingPiece.getType().equalsIgnoreCase("bishop") || lookingPiece.getType().equalsIgnoreCase("queen"))
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
                Square looking = board[bpRow + (i * rowReverse)][bpCol + (i * colReverse)];
                if(!looking.containsPiece())
                {
                    availableMoves.add(looking);
                }
                else if(looking.getPiece().getSide().equalsIgnoreCase(olds.getPiece().getSide()) && !looking.getPiece().getType().equalsIgnoreCase("king"))
                {
                    return null;
                }
                else if(looking.getPiece().getType().equalsIgnoreCase("king"))
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
        String sideToCheck = null;
        // check the side of who's turn it is bc changeside earlier flips it
        if(kingSquare == null)
        {
            kingSquare = (whiteTurn) ? whiteKing : blackKing;
            sideToCheck = (whiteTurn) ? "white" : "black";
        }
        olds = kingSquare;
        ArrayList<Square> kingMoves = whereKingCanGo(kingSquare);
        if(kingMoves.size() != 0)
        {
            return false;
        }
        else
        {
            for(Square[] row : board)
            {
                for(Square square : row)
                {
                    if(square.containsPiece())
                    {
                        if(square.getPiece().getType().equalsIgnoreCase("king"))
                        {
                            continue;
                        }
                        if(sideInCheck != null && square.getPiece().getSide().equalsIgnoreCase(sideInCheck))
                        {
                            ArrayList<Square> moves = movesIfInCheck(square);
                            if(!moves.isEmpty())
                            {
                                return false;
                            }
                        }
                        else if(sideToCheck != null && square.getPiece().getSide().equalsIgnoreCase(sideToCheck))
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

    public ArrayList<Pieces> generatePawnPromotion(Square pawnSquare)
    {
        promotionDone = false;
        isPressed = true;
        ArrayList<Pieces> original = new ArrayList<>();
        ArrayList<String> overlayPicList = new ArrayList<>();
        if(pawnSquare.getPiece().getSide().equalsIgnoreCase("white"))
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
        int rowTrav = (pawnSquare.getPiece().getSide().equalsIgnoreCase("white")) ? 1 : -1;
        for(int i = 0; i < 4; i++)
        {
            Square overlaySquare = board[pRow + (i * rowTrav)][pCol];
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
            Square overlaySquare = board[pRow + (i * rowTrav)][pCol];
            overlaySquare.setBackground(Color.WHITE);
            try{
                overlaySquare.set(new Pieces(overlayPicList.get(i)));
            }
            catch (IOException ex)
            {
                System.out.println("lol ioexception");
            }

        }
        return original;
    }
}


 */