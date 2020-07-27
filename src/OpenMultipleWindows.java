import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpenMultipleWindows {

    // Fields
    private int counter;
    private int maxX;
    private int maxY;
    private Point location = null;

    // Constructor
    OpenMultipleWindows() {

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        maxX = windowSize.width - 450;
        maxY = windowSize.height - 250;
        makeANewWindow();
    }

    private static void run() {
        OpenMultipleWindows window = new OpenMultipleWindows();
    }

    // make a jFrame with a 3-item menu, its location is keeping on changing.
    private void makeANewWindow() {

        // make a frame with menubar
        JFrame frame = new NewWindow();

        // change location
        if (location != null) {
            location.x += 200;
            location.y += 40;
            //   location.translate(40,40);
            frame.setLocation(location);
            resetLocation();
        } else {
            location = frame.getLocation();
        }

    }

    private void resetLocation() {
        if (location.x>maxX || location.y > maxY) {
            System.out.println("out of bound");
            location = null;
            JOptionPane.showMessageDialog(null,"We need to restart from the left", "Notice",JOptionPane.WARNING_MESSAGE);
            //or
           // location.setLocation(0,0);
        }
    }

    private class NewWindow extends JFrame {
        NewWindow() {
            super("NEW WINDOW " + counter);
            counter++;
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(new Dimension(300, 200));
            setVisible(true);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            makeANewWindow();
                        }
                    });
                }
            });
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    JOptionPane.showMessageDialog(null,"You have typed something", "INFO dialog",JOptionPane.INFORMATION_MESSAGE,null);

                }

            });
        }
    }

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(OpenMultipleWindows::run);
    }
}
