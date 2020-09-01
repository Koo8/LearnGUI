import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

/**
 * Jframe contains JDesktopPane constains many JInternalFrames
 * JFrame has a JMenubar
 * setBounds can't work with pack together
 */
public class InternalFrames_JDesktopPane extends JFrame implements ActionListener {
    JDesktopPane desktopPane;
    // constructor
    InternalFrames_JDesktopPane() {
        super("InternalFrames Demo");
        // set frame size
        int inSet = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       // System.out.println(screenSize.width);
        setBounds(inSet,inSet,screenSize.width - inSet*2, screenSize.height -inSet*2);

        desktopPane = new JDesktopPane();
        createNewFrame();
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        JMenuItem item1 = new JMenuItem("new");
        JMenuItem item2 = new JMenuItem("quit");
        menuBar.add(menu);
        menu.add(item1);
        menu.add(item2);
        setJMenuBar(menuBar);

        // for "new"
        item1.setMnemonic(KeyEvent.VK_N);
        item1.setActionCommand("new");
        item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        item1.addActionListener(this);

        // for "quit"
        item2.setMnemonic(KeyEvent.VK_Q);
        item2.setActionCommand("quit");
        item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        item2.addActionListener(this);

        // put together
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // setLocationRelativeTo(null);
        desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

    }

    private void createNewFrame() {
        CustomInternalFrame frame = new CustomInternalFrame();
        frame.setVisible(true); // this is same as normal JFrame
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("new".equals(e.getActionCommand())){
            createNewFrame();
        }else if ("quit".equals(e.getActionCommand())) {
            System.exit(0);
        }

    }
    public static void GUI(){
        // make sure we have nice window decoration
        // note: it is "JFrame" not "frame"  this should come before frame set up
        JFrame.setDefaultLookAndFeelDecorated(true);

        InternalFrames_JDesktopPane frame = new InternalFrames_JDesktopPane();
        // highlight: can't use pack(), because its bound has been set, its component JDesktopPane has not preferred dimension
        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InternalFrames_JDesktopPane::GUI);
    }
}

class CustomInternalFrame extends JInternalFrame{
    static int frameCount = 0;   // static fields can be referrenced b4 instance is created, therefore can be used in the constructor
    static int offset = 30;

    // constructor
    public CustomInternalFrame(){
        super("Document #" + (++frameCount),true,true, true, true);
        // create GUI and put it in the frame

        // set frame size
        setBounds(offset*frameCount, offset*frameCount,200,200);     // also can use setSize() and setLocation()

    }

}
