import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws AWTException {
        JFrame frame = new JFrame();
        ChessBoard b = new ChessBoard();
        frame.add(b);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
