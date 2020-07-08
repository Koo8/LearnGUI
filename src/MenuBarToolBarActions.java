import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBarToolBarActions extends JPanel {

    // has 3 Actions cut, copy and paste
    ImageIcon cutIcon, copyIcon, pasteIcon; // for action icons
    Action cutAction, copyAction, pasteAction;//
    double fromX, fromY, toX, toY;
    JTextPane textPane;

    // constructor
    public MenuBarToolBarActions() {
        super(new BorderLayout());
        createActions();
        add(createToolBar(), BorderLayout.PAGE_START);
        add(createTextPane(), BorderLayout.CENTER);
    }

    private JTextPane createTextPane() {
        textPane = new JTextPane();
        AbstractDocument doc = (AbstractDocument) textPane.getStyledDocument();

        // prepare for doc.insertString() parameters string[] and attributeSet[];
        String[] texts = {
                "I know it is wrong to not follow the rules. ",
                "But ",
                "can I ask for one ",
                "MORE",
                " time to do this task. ",
                ""};
        SimpleAttributeSet[] styles = initAttributes(texts.length);
        for (int i = 0; i < texts.length; i++) {
            try {
                doc.insertString(doc.getLength(), texts[i], styles[i]);
            } catch (BadLocationException e) {
                System.err.println("Couldn't insert the line " + i);
            }
        }
        // use caretListener to pick up selectedText 
        textPane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                textPane.getSelectedText();
            }
        });
        return textPane;
    }

    private SimpleAttributeSet[] initAttributes(int length) {
        SimpleAttributeSet[] attrs = new SimpleAttributeSet[length];
        attrs[0] = new SimpleAttributeSet();
        StyleConstants.setFirstLineIndent(attrs[0], 20);
        StyleConstants.setBold(attrs[0], true);

        attrs[1] = new SimpleAttributeSet(attrs[0]);
        StyleConstants.setBackground(attrs[1], new Color(255, 100, 255));

        attrs[2] = new SimpleAttributeSet(attrs[0]);
        StyleConstants.setStrikeThrough(attrs[2], true);

        attrs[3] = new SimpleAttributeSet(attrs[0]);
        StyleConstants.setFontSize(attrs[3], 30);

        attrs[4] = new SimpleAttributeSet(attrs[0]);
        StyleConstants.setIcon(attrs[4], cutIcon);

        return attrs;
    }

    private void createActions() {
        // get imageIcon from URL
        copyIcon = new ImageIcon(getClass().getResource("images/copyicon.png"));
        cutIcon = new ImageIcon(getClass().getResource("images/cuticon.png"));
        pasteIcon = new ImageIcon(getClass().getResource("images/pasteicon.png"));

        cutAction = new CutAction("Cut", cutIcon, "cut staff onto the clipboard", KeyEvent.VK_CUT);
        copyAction = new CopyAction("Copy", copyIcon, "Copy stuff to the clipboard", KeyEvent.VK_COPY);
        pasteAction = new PasteAction("Paste", pasteIcon, "Paste whatever is on the clipboard", KeyEvent.VK_PASTE);
    }

    private class CutAction extends AbstractAction {
        public CutAction(String text, ImageIcon icon, String shortDescription, int mnemonickey) {
            super(text, icon);   // this is the super construction
            //public AbstractAction(String name, Icon icon) {
//            this(name);
//            putValue(Action.SMALL_ICON, icon);
//        }
            putValue(SHORT_DESCRIPTION, shortDescription);
            putValue(MNEMONIC_KEY, mnemonickey);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e != null) {
                textPane.cut();
            }
        }
    }

    private class CopyAction extends AbstractAction {
        public CopyAction(String text, ImageIcon icon, String shortDescription, int mnemonickey) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, shortDescription);
            putValue(MNEMONIC_KEY, mnemonickey);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e != null) {
                textPane.copy();
            }

        }
    }

    private class PasteAction extends AbstractAction {
        public PasteAction(String text, ImageIcon icon, String shortDescription, int mnemonickey) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, shortDescription);
            putValue(MNEMONIC_KEY, mnemonickey);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e != null) {
                textPane.paste();
            }
        }
    }

    // create Toolbar
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        // toolbar needs 3 buttons
        JButton cutButton = new JButton(cutAction);
        cutButton.setMargin(new Insets(3, 3, 3, 10));
        JButton copyButton = new JButton(copyAction);
        copyButton.setMargin(new Insets(3, 3, 3, 10));
        JButton pasteButton = new JButton(pasteAction);
        pasteButton.setMargin(new Insets(3, 3, 3, 10));
        // add buttons to the toolbar
        toolBar.add(pasteButton);
        toolBar.add(copyButton);
        toolBar.add(cutButton);
        return toolBar;
    }

    // create MenuBar
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu actionMenu = new JMenu("Action");
        menuBar.add(fileMenu);
        menuBar.add(actionMenu);

        JMenuItem copyItem = new JMenuItem(copyAction);
        JMenuItem pasteItem = new JMenuItem(pasteAction);
        JMenuItem cutItem = new JMenuItem(cutAction);

        actionMenu.add(copyItem);
        actionMenu.add(cutItem);
        actionMenu.add(pasteItem);

        return menuBar;
    }

    // create listeners

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Action Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(450, 300));

        MenuBarToolBarActions panel = new MenuBarToolBarActions();
        frame.setContentPane(panel);
        frame.setJMenuBar(panel.createMenuBar());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
