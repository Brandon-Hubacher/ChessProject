import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class MinMax implements Serializable {
    public ChessBoard copy = null;
    private int evaluation;
    public Square[][] copyBoard = new Square[8][8];
    //public ArrayList<ChessBoard> arrangementList = new ArrayList<>();
    //public ArrayList<ArrayList<ByteArrayOutputStream>> arrangementList = new ArrayList<>();
    public ArrayList<byte[]> arrangementList = new ArrayList<>();
    public String bestMove;
    public static int counter = 0;
    boolean stopCreatingBoards = false;
    //boolean onSamePiece = true;
    boolean nextPieceSameSide = false;
    boolean nextPieceDifferentSide = false;
    Square transitionToNextPieceFrom = null;
    boolean breakout = false;
    boolean encounterFirstBackUp = false;
    ArrayList<Move> allMoves = new ArrayList<>();

    // TODO: save original board arrangement somehow
    public MinMax()
    {

        /*
        for(int i = 0; i < 10; i++)
        {
            arrangementList.add(null);
        }

         */


        //System.out.println("building minmax object");
        evaluation = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                //copyBoard[i][z] = copy.
            }
        }
    }

    public String boardToString(ChessBoard b)
    {
        String output = "";
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                Square s = b.chessBoard[i][z];
                String pieceStr = (s.containsPiece()) ? s.getPiece().toString() : "--";
                output += pieceStr+", ";
                if(z == 7)
                {
                    output += "\n";
                }
            }
        }
        return output;
    }
/*
    public int miniMax(ChessBoard inputBoard, int alpha, int beta, int depth, boolean whiteTurn)
    {
        System.out.println("counter: "+counter);
        counter++;
        if(depth == 0)
        {
            stopCreatingBoards = true;
            return evaluationFunction(inputBoard);
        }
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        //inputBoard.getAllMoves(Piece.Side sideToMove);
        int maxEval = Integer.MIN_VALUE;
        int minEval = Integer.MAX_VALUE;
        for(Move move : inputBoard.getAllMoves(sideToMove))
        {
            //System.out.println("from: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
            //System.out.println("to: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
            //System.out.println("piece: "+move.movedPiece);
            //ChessBoard transitionBoard = new ChessBoard(inputBoard);
            MoveTransition moveTransition = inputBoard.makeMove(move, inputBoard, depth);
            //transitionBoard.from = move.fromHere;
            //transitionBoard.pressed = move.toHere;
            //MoveTransition moveTransition = new MoveTransition(inputBoard, transitionBoard, move, depth);

            if(whiteTurn)
            {
                int eval = minHelper(moveTransition.getToBoard(), depth - 1, false);
                inputBoard.undoMove(move.toHere, move.fromHere, depth, depth+1);
                maxEval = max(maxEval, eval);
/*
                alpha = max(alpha, eval);
                if(beta <= alpha)
                {
                    break;
                }




            }
            else {
                int eval = maxHelper(moveTransition.getToBoard(), depth - 1, true);
                inputBoard.undoMove(move.toHere, move.fromHere, depth, depth+1);
                minEval = min(minEval, eval);
/*
                beta = min(beta, eval);
                if(beta <= alpha)
                {
                    break;
                }


            }
        }
        return minEval;
    }

    public int minHelper(ChessBoard board, int depth, boolean whiteTurn)
    {
        counter++;
        System.out.println("counter: "+counter);
        //System.out.println(counter);
        if(depth == 0)
        {
            return evaluationFunction(board);
        }
        int minValue = Integer.MAX_VALUE;
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        for(Move move : board.getAllMoves(sideToMove))
        {
            //System.out.println("from: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
            //System.out.println("to: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
            //System.out.println("piece: "+move.movedPiece);
            //ChessBoard transitionBoard = new ChessBoard(board);
            if(move.toHere.getSquareRow() == 2 && move.toHere.getSquareCol() == 2)
            {
                //System.out.println(boardToString(board));
            }
            MoveTransition moveTransition = board.makeMove(move, board, depth);
            board.undoMove(move.toHere, move.fromHere, depth, depth+1);
            //System.out.println("MinHelper moveFromHere: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
            //transitionBoard.movePieceAI(move.fromHere, move.toHere, depth);
            //MoveTransition moveTransition = new MoveTransition(board, transitionBoard, move, depth);
            int currentValue = maxHelper(moveTransition.getToBoard(), depth - 1, true);
            if(currentValue <= minValue)
            {
                minValue = currentValue;
            }
        }
        return minValue;
    }

    public int maxHelper(ChessBoard board, int depth, boolean whiteTurn)
    {
        counter++;
        System.out.println("counter: "+counter);
        //System.out.println(counter);
        if(depth == 0)
        {
            return evaluationFunction(board);
        }
        int maxValue = Integer.MIN_VALUE;
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        for(Move move : board.getAllMoves(sideToMove))
        {
            //System.out.println("from: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
            //System.out.println("to: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
            //System.out.println("piece: "+move.movedPiece);
            //if()
            MoveTransition moveTransition = board.makeMove(move, board, depth);
            //ChessBoard transitionBoard = new ChessBoard(board);
           // transitionBoard.movePieceAI(move.fromHere, move.toHere, depth);
            //MoveTransition moveTransition = new MoveTransition(board, transitionBoard, move, depth);
            int currentValue = minHelper(moveTransition.getToBoard(), depth - 1, false);
            board.undoMove(move.toHere, move.fromHere, depth, depth+1);
            if(currentValue <= maxValue)
            {
                maxValue = currentValue;
            }
        }
        return maxValue;
    }

 */















    public int officialMinMax(ChessBoard inputBoard, int alpha, int beta, int depth, boolean whiteTurn)
    {


        counter++;
        System.out.println("counter: "+counter);
        if(depth == 0)
        {
            //System.out.println("inside depth 0");
            stopCreatingBoards = true;
            //System.out.println("~~~BOARD FOR EVALUATION~~~");
            //System.out.println(boardToString(inputBoard));
            return evaluationFunction(inputBoard);
        }
        // TODO: account for checkmate and stalemate!
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        int maxEval = Integer.MIN_VALUE;
        int minEval = Integer.MAX_VALUE;
        ArrayList<Move> allMoves = inputBoard.getAllMoves(sideToMove, depth);
        for(Move move : allMoves)
        {
            if(move.fromHere.containsPiece())
            {
                if(move.fromHere.getPiece() instanceof King)
                {
                    //System.out.println("king moves from: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol()+" to: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
                }
            }
            inputBoard.from = move.fromHere;
            inputBoard.pressed = move.toHere;
            /*
            if(((inputBoard.from.getSquareRow() == 0 && inputBoard.from.getSquareCol() == 4) || (inputBoard.from.getSquareRow() == 1 && inputBoard.from.getSquareCol() == 3)) && inputBoard.from.getPiece() instanceof King && inputBoard.chessBoard[4][0].containsPiece())
            {
                if(inputBoard.chessBoard[4][0].getPiece() instanceof Queen)
                {
                    System.out.println(boardToString(inputBoard));
                }
            }


            if(inputBoard.pressed.getSquareRow() == 4 && inputBoard.pressed.getSquareCol() == 0 && inputBoard.from.getPiece() instanceof Queen)
            {
                System.out.println(counter);
                System.out.println(boardToString(inputBoard));
            }

             */
            //counter == 43798
            /*
            if(counter >= 43200 && counter <= 43300)
            {
                System.out.println(counter);
                if(counter == 43291)
                {
                    System.out.println("side to move: "+sideToMove);
                    System.out.println("black is in check: "+inputBoard.blackInCheck);
                    System.out.println("black king is on: "+inputBoard.blackKing.getSquareRow()+", "+inputBoard.blackKing.getSquareCol());
                    System.out.println(move.fromHere.getPiece()+" moves from: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol()+" to: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
                    System.out.println("move made while in check: "+move.moveMadeWhileInCheck);
                }
                System.out.println(boardToString(inputBoard));
                /*
                if(!inputBoard.chessBoard[0][4].containsPiece())
                {
                    System.out.println(counter);
                    System.out.println(boardToString(inputBoard));
                }


            }
        */
            //inputBoard.news = move.toHere;
            inputBoard.updateCastleRights(depth);

            if(inputBoard.enPassant != null && !inputBoard.pressed.containsPiece() && inputBoard.from.getPiece() instanceof Pawn)
            {
                if(inputBoard.pressed.equalsSquare(inputBoard.enPassant))
                {
                    //System.out.println("capturing en passant");
                    //System.out.println("en passant square: "+inputBoard.enPassant.getSquareRow()+", "+inputBoard.enPassant.getSquareCol());
                    //System.out.println(boardToString(inputBoard));
                    //System.out.println(depth);
                    inputBoard.captureEnPassant();
                }
                inputBoard.enPassant.setBackground(inputBoard.enPassant.revertColor());
            }
            inputBoard.enPassant = null;


            if(!inputBoard.pressed.containsPiece() && inputBoard.from.getPiece() instanceof Pawn)
            {
                if((Math.abs(move.fromHere.getSquareRow() - move.toHere.getSquareRow()) == 1) && (Math.abs(move.fromHere.getSquareCol() - move.toHere.getSquareCol()) == 1))
                {
                    if(move.fromHere.getPiece().getSide().equals(Piece.Side.WHITE))
                    {
                        if(((Pawn) move.toHere.getDown(1).getPiece()).getEnPassantBecameAvailableDepth() == depth + 1)
                        {
                            if(MinMax.counter == 274175)
                            {
                                System.out.println("capturing en passant");
                                System.out.println("fromHere: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
                                System.out.println("toHere: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
                            }
                            inputBoard.captureEnPassant();
                        }
                    }
                    else
                    {
                        if(((Pawn) move.toHere.getUp(1).getPiece()).getEnPassantBecameAvailableDepth() == depth + 1)
                        {
                            if(MinMax.counter == 274175)
                            {
                                System.out.println("capturing en passant");
                                System.out.println("fromHere: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
                                System.out.println("toHere: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
                            }
                            inputBoard.captureEnPassant();
                        }
                    }
                }
            }


            //inputBoard.movePieceAI(move.fromHere, move.toHere, depth);
            inputBoard.movePieceAI(inputBoard.from, inputBoard.pressed, depth, move);

            inputBoard.expCheck = inputBoard.exposedCheck(inputBoard.from, inputBoard.news);
            if(inputBoard.news.getPiece() instanceof Pawn && inputBoard.promotionDone && (inputBoard.news.getSquareRow() == 0 || inputBoard.news.getSquareRow() == 7))
            {
                inputBoard.original = inputBoard.generatePawnPromotion(inputBoard.news);
                inputBoard.runDragAndRelease = false;
            }
            else if(inputBoard.pressed.getBackground() == Color.WHITE)
            {
                inputBoard.runDragAndRelease = false;
                inputBoard.promotePawn();
            }
            if(inputBoard.promotionDone)
            {
                ArrayList<Square> check = inputBoard.news.getPiece().getLegalMoves();
                inputBoard.thisIstheWay = inputBoard.isInCheck(check, inputBoard.news);
                //inputBoard.assessCheckMateAndStaleMate();
            }

            if(whiteTurn)
            {
                int eval = officialMinMax(inputBoard, alpha, beta, depth - 1, false);
                //System.out.println("about to undo move at depth: "+depth);
               //System.out.println("fromHere: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
                //System.out.println("toHere: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
                //System.out.println("before undo");
                //System.out.println(boardToString(inputBoard));
                inputBoard.undoMove(move.toHere, move.fromHere, depth, depth+1, move.moveMadeWhileInCheck);
                //System.out.println("after undo");
                //System.out.println(boardToString(inputBoard));

                maxEval = max(maxEval, eval);
/*
                alpha = max(alpha, eval);
                if(beta <= alpha)
                {
                    break;
                }

 */


            }
            else
            {
                int eval = officialMinMax(inputBoard, alpha, beta, depth - 1, true);
                //System.out.println("about to undo move at depth: "+depth);
                //System.out.println("fromHere: "+move.toHere.getSquareRow()+", "+move.toHere.getSquareCol());
                //System.out.println("toHere: "+move.fromHere.getSquareRow()+", "+move.fromHere.getSquareCol());
                if(move.toHere.getPiece() instanceof Pawn)
                {
                    //System.out.println(((Pawn) move.toHere.getPiece()).getEnPassantBecameAvailableDepth());
                }
                //System.out.println("before undo");
                //System.out.println(boardToString(inputBoard));
                inputBoard.undoMove(move.toHere, move.fromHere, depth, depth+1, move.moveMadeWhileInCheck);
                //System.out.println("after undo");
                //System.out.println(boardToString(inputBoard));
                minEval = min(minEval, eval);
/*
                beta = min(beta, eval);
                if(beta <= alpha)
                {
                    break;
                }

 */


            }

        }
        if(whiteTurn)
        {
            return maxEval;
        }
        else
        {
            return minEval;
        }
    }













// TODO: undo move will occur several times in a row.. how to do that?
    /*
    public int minMax(ChessBoard inputBoard, int alpha, int beta, int depth, boolean whiteTurn)
    {
        counter++;
        System.out.println("counter: "+counter);
       // System.out.println("SIZE: "+arrangementList.size());
        // TODO: inserting at depth index is probably screwing things up
        //System.out.println("depth is: "+depth);
        if(depth == 0)
        {
            //System.out.println("inside depth 0");
            stopCreatingBoards = true;
            //System.out.println("~~~BOARD FOR EVALUATION~~~");
            //System.out.println(boardToString(inputBoard));
            return evaluationFunction(inputBoard);
        }
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        int maxEval = Integer.MIN_VALUE;
        int minEval = Integer.MAX_VALUE;
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                Square checking = inputBoard.chessBoard[i][z];
                if(checking.getSquareRow() == 1 && checking.getSquareCol() == 7)
                {
                    System.out.println("!!!!!!!!!");
                    System.out.println(checking.containsPiece());
                }
                if(nextPieceSameSide)
                {
                    nextPieceSameSide = false;
                    inputBoard.undoMove(transitionToNextPieceFrom, inputBoard.from, depth-1, depth, true);
                }
                ArrayList<Square> legalMoves;
                if(checking.containsPiece())
                {
                    if(checking.getPiece().getSide().equals(sideToMove))
                    {
                        Piece checkingPiece = checking.getPiece();
/*
                        if(stopCreatingBoards)
                        {
                            System.out.println("creating board in double loop");
                            try{
                                ByteArrayInputStream bais = new ByteArrayInputStream(arrangementList.get(arrangementList.size()-1));
                                inputBoard = (ChessBoard) new ObjectInputStream(bais).readObject();
                                inputBoard.from = checking;
                            }
                            catch(ClassNotFoundException e)
                            {
                                System.out.println("classnotfound in deserializing");
                            }
                            catch(IOException ioe)
                            {
                                System.out.println("ioexception in deserializing");
                            }
                        }




                        inputBoard.from = checking;

                        legalMoves = inputBoard.getMoves(checking);

                        if(legalMoves.isEmpty())
                        {
                            continue;
                        }

                        inputBoard.updateCastleRights(arrangementList.size()-1);
                        if(inputBoard.enPassant != null && !inputBoard.pressed.containsPiece())
                        {
                            if(inputBoard.pressed.equalsSquare(inputBoard.enPassant))
                            {
                                inputBoard.captureEnPassant();
                            }
                            inputBoard.enPassant.setBackground(inputBoard.enPassant.revertColor());
                        }
                        inputBoard.enPassant = null;
                        try{
                            if(!stopCreatingBoards)
                            {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(bos);
                                oos.writeObject(inputBoard);
                                oos.flush();
                                oos.close();
                                bos.close();
                                byte[] byteData = bos.toByteArray();
                                arrangementList.add(byteData);
                            }
                        }
                        catch(IOException e)
                        {
                            System.out.println(e);
                            System.out.println("serializing exception");
                        }
                        // TODO: maybe use legal moves for each instance, cuz knight might be using legal moves for another piece
                        for(Square legal : legalMoves)
                        {
                            if(!legal.equalsSquare(legalMoves.get(0)))
                            {
                                inputBoard.undoMove(legalMoves.get(legalMoves.indexOf(legal)-1), checking, depth, depth+1, true);
                            }


                            if(stopCreatingBoards)
                            {
                                //onSamePiece = true;
                            }
/*
                            if(stopCreatingBoards && onSamePiece)
                            {
                                System.out.println("onSamePiece true");
                                try{
                                    ByteArrayInputStream bais = new ByteArrayInputStream(arrangementList.get(arrangementList.size()-1));
                                    inputBoard = (ChessBoard) new ObjectInputStream(bais).readObject();
                                    //inputBoard.from = checking;
                                    System.out.println("~~~~~~~~LOOK AT THIS FOR NEXT KNIGHT LEGAL MOVE~~~~~~~~~~~~~");
                                    if(legal.getSquareRow() == 2 && legal.getSquareCol() == 2)
                                    {
                                        System.out.println(inputBoard.from.getSquareRow()+", "+inputBoard.from.getSquareCol());
                                        System.out.println("knight initial square contains piece: "+inputBoard.chessBoard[0][1].containsPiece());
                                        System.out.println("knight first legal move contains piece: "+inputBoard.chessBoard[2][0].containsPiece());
                                    }
                                }
                                catch(ClassNotFoundException e)
                                {
                                    System.out.println("classnotfound in deserializing");
                                }
                                catch(IOException ioe)
                                {
                                    System.out.println("ioexception in deserializing");
                                }
                            }


                            if(depth == 1 && legal == legalMoves.get(legalMoves.size()-1))
                            {
                                //System.out.println("setting onSamePiece to FALSE");
                                //onSamePiece = false;
                                nextPieceSameSide = true;
                                transitionToNextPieceFrom = legal;
                            }
                            inputBoard.pressed = legal;
                            inputBoard.news = legal;
                            inputBoard.movePieceAI(inputBoard.from, inputBoard.news, depth, );
                            inputBoard.expCheck = inputBoard.exposedCheck(inputBoard.from, inputBoard.news);
                            if(inputBoard.news.getPiece() instanceof Pawn && inputBoard.promotionDone && (inputBoard.news.getSquareRow() == 0 || inputBoard.news.getSquareRow() == 7))
                            {
                                inputBoard.original = inputBoard.generatePawnPromotion(inputBoard.news);
                                inputBoard.runDragAndRelease = false;
                            }
                            else if(inputBoard.pressed.getBackground() == Color.WHITE)
                            {
                                inputBoard.runDragAndRelease = false;
                                inputBoard.promotePawn();
                            }
                            if(inputBoard.promotionDone)
                            {
                                ArrayList<Square> check = inputBoard.news.getPiece().getLegalMoves();
                                inputBoard.thisIstheWay = inputBoard.isInCheck(check, inputBoard.news);
                                //inputBoard.assessCheckMateAndStaleMate();
                            }
                            if(whiteTurn)
                            {
                                int eval = minMax(inputBoard, alpha, beta, depth - 1, false);
                                maxEval = max(maxEval, eval);
/*
                                alpha = max(alpha, eval);
                                if(beta <= alpha)
                                {
                                    break;
                                }




                            }
                            else
                            {
                                int eval = minMax(inputBoard, alpha, beta, depth - 1, true);
                                minEval = min(minEval, eval);
/*
                                beta = min(beta, eval);
                                if(beta <= alpha)
                                {
                                    break;
                                }




                            }
                        }
                        if(breakout)
                        {
                            //System.out.println("BREAKING 2");
                            break;
                        }

/*
                        if(whiteTurn)
                        {
                            return maxEval;
                        }
                        else
                        {
                            return minEval;
                        }





                    }
                }
            }
            if(breakout)
            {
                //System.out.println("BREAKING 3");
                break;
            }
        }
        if(whiteTurn)
        {
            return maxEval;
        }
        else
        {
            return minEval;
        }
        //System.out.println("wow there horsy");
        //return 10000;


    }
    */

// TODO: I think you may only need to track depth number of boards in arrangementList that can just be manipulated for different pieces
    /*
    public int minMax(ChessBoard inputBoard, int alpha, int beta, int depth, boolean whiteTurn)
    {
        System.out.println("SIZE: "+arrangementList.size());
        // TODO: inserting at depth index is probably screwing things up
        //inputBoard = arrangementList.get(depth);
        System.out.println("depth is: "+depth);
        //Square[][] board = b.board;
        if(stopCreatingBoards)
        {
            // inputBoard = arrangmentList.get(arrangementList.size()-depth)
            inputBoard = copy;
        }
        if(depth == 0)
        {
            if(copy != null)
            {
                //copy = null;
                //System.out.println("COPY COPY COPY: "+copy.chessBoard[4][0].getPiece());
            }
            try{
                ByteArrayInputStream bais = new ByteArrayInputStream(arrangementList.get(arrangementList.size()-1));
                //(Object) object = (Object) new ObjectInputStream(bais).readObject();
                copy = (ChessBoard) new ObjectInputStream(bais).readObject();
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("classnotfound in deserializing");
            }
            catch(IOException ioe)
            {
                System.out.println("ioexception in deserializing");
            }
            stopCreatingBoards = true;
            return evaluationFunction(inputBoard);
        }
        Piece.Side sideToMove = (whiteTurn) ? Piece.Side.WHITE : Piece.Side.BLACK;
        int maxEval = Integer.MIN_VALUE;
        int minEval = Integer.MAX_VALUE;
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                Square checking = inputBoard.chessBoard[i][z];
                ArrayList<Square> legalMoves;
                if(checking.containsPiece())
                {
                    if(checking.getPiece().getSide().equals(sideToMove))
                    {
                        //copy.from = checking;
                        inputBoard.from = checking;
                        if(copy != null)
                        {
                            copy.from = checking;
                        }



                        legalMoves = inputBoard.getMoves(checking);

                        if(legalMoves.isEmpty())
                        {
                            System.out.println(checking.getPiece()+" has no legal moves");
                            continue;
                        }
                        /*
                        int maxEval = Integer.MIN_VALUE;
                        int minEval = Integer.MAX_VALUE;


                        inputBoard.updateCastleRights(arrangementList.size()-1);
                        if(inputBoard.enPassant != null && !inputBoard.pressed.containsPiece())
                        {
                            if(inputBoard.pressed.equalsSquare(inputBoard.enPassant))
                            {
                                inputBoard.captureEnPassant();
                            }
                            inputBoard.enPassant.setBackground(inputBoard.enPassant.revertColor());
                        }
                        inputBoard.enPassant = null;
                        try{
                            if(!stopCreatingBoards)
                            {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(bos);
                                oos.writeObject(inputBoard);
                                oos.flush();
                                oos.close();
                                bos.close();
                                byte[] byteData = bos.toByteArray();
                                arrangementList.add(byteData);
                            }
                        }
                        catch(IOException e)
                        {
                            System.out.println(e);
                            System.out.println("serializing exception");
                        }
                        // TODO: maybe use legal moves for each instance, cuz knight might be using legal moves for another piece
                        for(Square legal : legalMoves)
                        {

                            if(copy != null)
                            {
                                //copy.from = checking;
                                copy.from = copy.chessBoard[checking.getSquareRow()][checking.getSquareCol()];
                            }


                            ChessBoard boardNew = (copy == null) ? new ChessBoard(inputBoard) : copy;
                            //boardNew.from = checking;
                            boardNew.pressed = legal;
                            inputBoard.pressed = legal;
                            if(copy != null)
                            {
                                //System.out.println(checking.getPiece());
                                System.out.println("copy NOT null, "+copy.from.getSquareRow()+", "+copy.from.getSquareCol()+": "+copy.from.getPiece()+" to "+legal.getSquareRow()+", "+legal.getSquareCol());
                                System.out.println("should be piece to check: "+checking.getSquareRow()+", "+checking.getSquareCol());
                            }
                            //System.out.println("CHECKING THIS PIECE: "+checking.getPiece()+" at "+checking.getSquareRow()+", "+checking.getSquareCol()+" going to "+legal.getSquareRow()+", "+legal.getSquareCol());
                            //ChessBoard boardNew = new ChessBoard(inputBoard);
                            //boardNew.movePieceAI(arrangementList.get(arrangementList.size()-1).from, legal);
                            boardNew.movePieceAI(boardNew.from, legal);
                            boardNew.expCheck = boardNew.exposedCheck(boardNew.from, boardNew.news);
                            if(boardNew.news.getPiece() instanceof Pawn && boardNew.promotionDone && (boardNew.news.getSquareRow() == 0 || boardNew.news.getSquareRow() == 7))
                            {
                                boardNew.original = boardNew.generatePawnPromotion(boardNew.news);
                                boardNew.runDragAndRelease = false;
                            }
                            else if(boardNew.pressed.getBackground() == Color.WHITE)
                            {
                                boardNew.runDragAndRelease = false;
                                boardNew.promotePawn();
                            }
                            if(boardNew.promotionDone)
                            {
                                ArrayList<Square> check = boardNew.news.getPiece().getLegalMoves();
                                boardNew.thisIstheWay = boardNew.isInCheck(check, boardNew.news);
                                //inputBoard.assessCheckMateAndStaleMate();
                            }
                            if(whiteTurn)
                            {
                                int eval = minMax(boardNew, alpha, beta, depth - 1, false);
                                maxEval = max(maxEval, eval);

                                alpha = max(alpha, eval);
                                if(beta <= alpha)
                                {
                                    break;
                                }


                            }
                            else
                            {
                                int eval = minMax(boardNew, alpha, beta, depth - 1, true);
                                minEval = min(minEval, eval);

                                beta = min(beta, eval);
                                if(beta <= alpha)
                                {
                                    break;
                                }


                            }
                        }

                        /*
                        if(whiteTurn)
                        {
                            return maxEval;
                        }
                        else
                        {
                            return minEval;
                        }




                    }
                }
            }
        }
        if(whiteTurn)
        {
            return maxEval;
        }
        else
        {
            return minEval;
        }
        //System.out.println("wow there horsy");
        //return 10000;


    }

     */

/*
    public int minMax(Square[][] transitionBoard, Square squareToEval, Square comingFrom, int alpha, int beta, int depth, boolean maximizingPlayer)
    {
        //System.out.println("inside minMax");
        Square[][] board = new Square[8][8];
        /*
        for(int i = 0; i < 8; i++)
        {
            for(int z = 0; z < 8; z++)
            {
                board[i][z] = ChessBoard.chessBoard[i][z];
            }
        }


        //ArrayList<Square> legalMoves = squareToEval.getPiece().getLegalMoves();
        //TODO: completely undo move piece!
        if(depth == 0)
        {
            /*
            //squareToEval.setPiece(copy.news.getPiece());
            comingFrom.setPiece(copy.news.getPiece());
            comingFrom.getPiece().setPlacement(comingFrom.getSquareRow(), comingFrom.getSquareCol());
            System.out.println("depth 0: "+copy.pressed.getSquareRow()+", "+copy.pressed.getSquareCol());
            //copy.news.getPiece().setPlacement(squareToEval.getSquareRow(), squareToEval.getSquareCol());
            copy.news.removePiece();
            copy.pressed = copy.news;


            copy.undoMove(squareToEval, comingFrom, depth, depth+1);
            //System.out.println("evaluation: "+evaluationFunction(copy.chessBoard));
            return evaluationFunction(copy.chessBoard);
        }
        copy.olds = copy.pressed = squareToEval;

        //copy.olds = copy.pressed;
        //System.out.println(copy.pressed.getPiece());
        ArrayList<Square> legalMoves = copy.getMoves(copy.pressed);
        /*
        for(Square s : legalMoves)
        {
            System.out.println(s.getSquareRow()+", "+s.getSquareCol());
        }


        copy.currentLegalMoves = legalMoves;
        //copy.paintMoves(legalMoves);
        copy.isPressed = true;
        //repaint();

        if(maximizingPlayer)
        {
            System.out.println("Maximizing Player");
            System.out.println(squareToEval.getPiece());
            int maxEval = -10000000;
            for(Square s : legalMoves)
            {
                //System.out.println("inside loop");
                copy.olds = squareToEval;
                //System.out.println("squareToEval: "+copy.olds.getSquareRow()+", "+copy.olds.getSquareCol());
                copy.pressed = s;
                //System.out.println("pressed: "+copy.pressed.getSquareRow()+", "+copy.pressed.getSquareCol());
                for(int i = 0; i < 8; i++)
                {
                    for(int z = 0; z < 8; z++)
                    {
                        board[i][z] = ChessBoard.chessBoard[i][z];
                    }
                }

                //System.out.println("olds before castle rights: "+copy.olds.getPiece());
                copy.updateCastleRights(depth);
                if(copy.enPassant != null && !copy.pressed.containsPiece())
                {
                    if(copy.pressed.equalsSquare(copy.enPassant))
                    {
                        copy.captureEnPassant();
                    }
                    copy.enPassant.setBackground(copy.enPassant.revertColor());
                }
                copy.enPassant = null;
                copy.movePiece(copy.pressed, s.getSquareRow(), s.getSquareCol());
                //updateCastleRights();
                copy.expCheck = copy.exposedCheck(copy.olds, copy.news);
                if(copy.news.getPiece() instanceof Pawn && copy.promotionDone && (copy.news.getSquareRow() == 0 || copy.news.getSquareRow() == 7))
                {
                    copy.original = copy.generatePawnPromotion(copy.news);
                    copy.runDragAndRelease = false;
                }
                else if(copy.pressed.getBackground() == Color.WHITE)
                {
                    copy.runDragAndRelease = false;
                    copy.promotePawn();
                }
                if(copy.promotionDone)
                {
                    ArrayList<Square> check = copy.news.getPiece().getLegalMoves();
                    copy.thisIstheWay = copy.isInCheck(check, copy.news);
                    copy.assessCheckMateAndStaleMate();
                }

                //copy.olds = squareToEval;
                //copy.pressed = s;
                //copy.movePiece(s, s.getSquareRow(), s.getSquareCol());
                int eval = minMax(board, s, comingFrom, alpha, beta, depth - 1, false);
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if(beta <= alpha)
                {
                    break;
                }
            }
            return maxEval;
        }
        else
        {
            for(Square[] row : transitionBoard)
            {
                for(Square s : row)
                {
                    if(s.containsPiece())
                    {
                        if(s.getPiece().getSide().equals(Piece.Side.BLACK))
                        {
                            //System.out.println("main loop for white pieces: "+s.getPiece());
                            //System.out.println("~~~"+s.getPiece()+"~~~");
                            //System.out.println("Eval: "+eval);
                            int eval = minMax(inputBoard, s, s, -10000000, 10000000, 3, true);

                        }
                    }
                }
            }
            System.out.println("Minimizing Player");
            System.out.println(squareToEval.getPiece());
            int minEval = 10000000;
            for(Square s : legalMoves)
            {
                copy.olds = squareToEval;
                copy.pressed = s;
                for(int i = 0; i < 8; i++)
                {
                    for(int z = 0; z < 8; z++)
                    {
                        board[i][z] = ChessBoard.chessBoard[i][z];
                    }
                }



                copy.updateCastleRights(depth);
                if(copy.enPassant != null && !copy.pressed.containsPiece())
                {
                    if(copy.pressed.equalsSquare(copy.enPassant))
                    {
                        copy.captureEnPassant();
                    }
                    copy.enPassant.setBackground(copy.enPassant.revertColor());
                }
                copy.enPassant = null;
                copy.movePiece(copy.pressed, s.getSquareRow(), s.getSquareCol());
                //updateCastleRights();
                copy.expCheck = copy.exposedCheck(copy.olds, copy.news);
                if(copy.news.getPiece() instanceof Pawn && copy.promotionDone && (copy.news.getSquareRow() == 0 || copy.news.getSquareRow() == 7))
                {
                    copy.original = copy.generatePawnPromotion(copy.news);
                    copy.runDragAndRelease = false;
                }
                else if(copy.pressed.getBackground() == Color.WHITE)
                {
                    copy.runDragAndRelease = false;
                    copy.promotePawn();
                }
                if(copy.promotionDone)
                {
                    ArrayList<Square> check = copy.news.getPiece().getLegalMoves();
                    copy.thisIstheWay = copy.isInCheck(check, copy.news);
                    copy.assessCheckMateAndStaleMate();
                }

                //copy.olds = squareToEval;
                //copy.pressed = s;
                //copy.movePiece(s, s.getSquareRow(), s.getSquareCol());
                int eval = minMax(board, s, comingFrom, alpha, beta, depth - 1, true);
                minEval = min(minEval, eval);
                beta = min(beta, eval);
                if(beta <= alpha)
                {
                    break;
                }
            }
            //System.out.println("minEval: "+minEval);
            return minEval;
        }
    }

    */
    public int evaluationFunction(ChessBoard input)
    {
        int evaluation = 0;
        for(Square[] row : input.chessBoard)
        {
            for(Square s : row)
            {
                if(s.containsPiece())
                {
                    if(s.getPiece().getSide().equals(Piece.Side.WHITE))
                    {
                        evaluation += s.getPiece().value;
                    }
                    else
                    {
                        evaluation -= s.getPiece().value;
                    }
                }
            }
        }
        //System.out.println("evaluation: "+evaluation);
        return evaluation;
    }
}
