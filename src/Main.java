import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        //JFrame frame = new ChessBoard();
        //frame.setSize(1920, 1080);
        //Board b = new Board();
        ChessBoard b = new ChessBoard();
        frame.add(b);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Zobrist.zobristFillArray();
        Zobrist.createInitialHash();

        MinMax m = new MinMax();
        //ChessBoard board = new ChessBoard(b);
        //System.out.println("!!!!!!!!!!!!!"+m.minMax(b, Integer.MIN_VALUE, Integer.MAX_VALUE, 3, true)+"!!!!!!!!!");

        //System.out.println("!!!!!!!!!!!!!"+m.miniMax(b, Integer.MIN_VALUE, Integer.MAX_VALUE, 3, true)+"!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!"+m.officialMinMax(b, Integer.MIN_VALUE, Integer.MAX_VALUE, 6, true)+"!!!!!!!!!");
/*
        JFrame overlay = new JFrame("Transparent Window");
        overlay.setUndecorated(true);
        overlay.setBackground(new Color(0,0, 0, 0));
        overlay.setAlwaysOnTop(true);
        overlay.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
        overlay.getContentPane().setLayout(new java.awt.BorderLayout());
        //overlay.getContentPane().add(new JTextField("text field north"), java.awt.BorderLayout.NORTH);
        //overlay.getContentPane().add(new JTextField("text field south"), java.awt.BorderLayout.SOUTH);
        overlay.setVisible(true);
        overlay.pack();
        //Board b = new Board();
        //Square s = new Square();
        //s.makeSquares(b);

 */
    }
}
