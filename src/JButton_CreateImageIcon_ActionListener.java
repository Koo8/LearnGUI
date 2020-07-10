import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

public class JButton_CreateImageIcon_ActionListener extends JPanel implements ActionListener {
    private JButton b1,b2,b3;
    private ImageIcon img1, img2, img3;

    public JButton_CreateImageIcon_ActionListener() {
        setOpaque(true);
        // Instantiate JButton needs ImageIcon as a parameter
        img1 = createImageIcon("images/copyicon.png");
        img2 = createImageIcon("images/cuticon.png");
        img3 = createImageIcon("images/pasteicon.png");

        b1 = new JButton("Disable Middle Button", img1);
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("disable");
        b1.setToolTipText("click to disable the middle button");
        b1.setHorizontalTextPosition(AbstractButton.CENTER);  // comment out these two to see how the icon is layouted with regard to the text
        b1.setVerticalTextPosition(AbstractButton.CENTER);

        b2 = new JButton("Middle Button" , img2);
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);   // icon won't show if the text position is not defined
        b2.setMnemonic(KeyEvent.VK_M);

        b3 = new JButton("Enable middle button", img3);
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setHorizontalTextPosition(AbstractButton.LEADING);
        b3.setEnabled(false);
        b3.setActionCommand("endable");

        b1.addActionListener(this);
        b3.addActionListener(this);

        add(b1);
        add(b2);
        add(b3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("disable".equals(e.getActionCommand())) {
            b2.setEnabled(false);
            b3.setEnabled(true);
            b1.setEnabled(false);
        }else {
            b3.setEnabled(false);
            b1.setEnabled(true);
            b2.setEnabled(true);
        }
    }

    private ImageIcon createImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon img = new ImageIcon(imgURL); // or return new ImageIcon(imgURL);
            return img;
        } else {
            System.out.println("The file path " + path + " can't be found.");
            return null;
        }

    }

    private static void creatAndShowGUI () {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new JButton_CreateImageIcon_ActionListener());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                creatAndShowGUI();
            }
        });
    }
}
