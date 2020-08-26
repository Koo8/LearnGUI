import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * copy of Framework.java from oracle.
 * make multiple same windows, actionListener, JOptionPane, WindowAdapter
 */

public class WindowAdapter_Reproduce_Quit_MoreWindows extends WindowAdapter {
    Point location = null;
    private static int maxX = 500;
    private static int maxY = 500;

    Object[] options = {"Quit", "Cancel"};

    WindowAdapter_Reproduce_Quit_MoreWindows() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        maxX = screenSize.width - 50;
        maxY = screenSize.height - 50;
        makeNewWindow();    // eveytime when "new" is clicked, this method is called
    }

    public void makeNewWindow() {

        JFrame frame = new ThisFrame(this);  // create a new customized frame
        // move right and down 40 each time
        if (location != null) {
            location.translate(40, 40);
            if (location.x > maxX || location.y > maxY) {
                location.setLocation(0, 0);
            }
            frame.setLocation(location);
        } else {
            location = frame.getLocation();
        }
        frame.setVisible(true);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
    }

    public void quit(JFrame frame) {
        if (optionPaneIsYes(frame)) {
            System.exit(0);
        }
    }

    private boolean optionPaneIsYes(JFrame frame) {
        // create a JOptionPane
        int o = JOptionPane.showOptionDialog(frame, "Windows are still open, \nDo you really want to quit?", "Quit Comfirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        // check answer
        if (o == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WindowAdapter_Reproduce_Quit_MoreWindows frame = new WindowAdapter_Reproduce_Quit_MoreWindows();
            }
        });
    }
    // inner class - otherwise, since it refer to WindowAdapter_Repro... interlockingly, a stackoverflowerror will be thrown.
    class ThisFrame extends JFrame {
        WindowAdapter_Reproduce_Quit_MoreWindows model = null;

        ThisFrame(WindowAdapter_Reproduce_Quit_MoreWindows thisModel) {
            super("New Frame");
            model = thisModel;
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            addWindowListener(model);

            // create a menubar - set mnemonics
            JMenu menu = new JMenu("Choose Action");
            menu.setMnemonic(KeyEvent.VK_M);
            // create a JMenuItem that is used for all JmenuItems
            JMenuItem item = null;

            // close
            item = new JMenuItem("Close");
            item.setMnemonic(KeyEvent.VK_C);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ThisFrame.this.setVisible(false);
                    ThisFrame.this.dispose();
                }
            });
            menu.add(item);
            //new
            item = new JMenuItem("New");
            item.setMnemonic(KeyEvent.VK_N);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.makeNewWindow();
                }
            });
            menu.add(item);

            // quit
            item = new JMenuItem("Quit");
            item.setMnemonic(KeyEvent.VK_Q);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.quit(ThisFrame.this);
                }
            });
            menu.add(item);

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(menu);

            setJMenuBar(menuBar);
            setSize(new Dimension(200,200));
        }
    }
}
