import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.security.Key;
import java.util.HashMap;

public class TextPane_Menu_CaretListener_DocumentListener extends JFrame {
    private JTextPane textPane;
    private AbstractDocument doc;
    private JTextArea logArea;

    // menu Actions
    private UndoAction undo;
    private RedoAction redo;
    private UndoManager undoManager = new UndoManager();


    private TextPane_Menu_CaretListener_DocumentListener() {
        super("TextPaneWithMenuForListeners");

        // this JFrame has 3 parts, textpane, textarea and JLabel. It also has a menubar
        ////// Firstly --- add JTextPane - get its model - styledDocument, set documentFilter

        textPane = new JTextPane();
        textPane.setMargin(new Insets(5, 5, 5, 5));
        doc = (AbstractDocument) textPane.getStyledDocument();  // get model
        // AbstractDocument use documentFilter if available for text related doc mutation such as insertString,replace or remove

        // todo ???
        doc.setDocumentFilter(new DocumentSizeFilter(300)); // set filter
        // put textPane inside a ScrollPane
        JScrollPane paneTop = new JScrollPane(textPane);
        paneTop.setPreferredSize(new Dimension(300, 200));

        // put initial text into text pane
        initTextPane();

        // add listeners to document - the model
        ///** add undoableEditListener to listen to the undo and redo action through undoManager
        doc.addDocumentListener(new ThisDocumentListener());
        doc.addUndoableEditListener(new ThisUnDoableEditListener());


        ////// Secondly --- add JTextArea
        logArea = new JTextArea("No input yet");
        logArea.setEditable(false);
        //logArea.setOpaque(true);
        logArea.setBackground(Color.RED);
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("serif", Font.BOLD, 20));

        JScrollPane paneBottom = new JScrollPane(logArea);
        paneBottom.setPreferredSize(new Dimension(300, 100));
        // add to splitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, paneTop, paneBottom);
        splitPane.setOneTouchExpandable(true);

        ////// Thirdly -- add JLabel and CaretListener //
        // for this part, oracle doc use a CaretListenerLabel that extends JLabel and implements CaretListener class
        // see https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextComponentDemoProject/src/components/TextComponentDemo.java
        JLabel labelForCaretPosition = new JLabel("Hello text area for caret position");
        textPane.addCaretListener(new CaretListener() {

            @Override
            public void caretUpdate(CaretEvent e) {
                int dot = e.getDot();
                int mark = e.getMark();

                // setText() and modelToView2D() must run on event dispatching event.
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // **to display caret info in the JLabel
                        // when no selection is conducted
                        if (dot == mark) {
                            //Converts the given location in the model to a place in
                            // the view coordinate system.
                            try {
                                Rectangle2D caretViewPosition = textPane.modelToView2D(dot);
                                labelForCaretPosition.setText("Good! caret position is:   " + dot +
                                        ", view location = [" +
                                        caretViewPosition.getX() + ", " +
                                        caretViewPosition.getY() + "]" + "\n");
                            } catch (BadLocationException ex) {
                                labelForCaretPosition.setText("Bad! caret: textPosition : " + dot + "\n");
                            }
                        } else if (dot < mark) { // when selection is start from left to right
                            labelForCaretPosition.setText("selection from: " + dot + " to " + mark + "\n");
                        } else {
                            labelForCaretPosition.setText("selection from: " + mark + " to " + dot + "\n");
                        }
                         // **to catch the change event from textpane
                        textPane.getSelectedText();
                    }
                });
            }
        });

        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(labelForCaretPosition, BorderLayout.PAGE_END);

        ////// Forth part - set JMenubar for JFrame
        JMenu editMenu = new JMenu("edit");
        JMenu styleMenu = new JMenu("style");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(editMenu);
        menuBar.add(styleMenu);
        setJMenuBar(menuBar);

        //// define JmenuItems ////
        // ****for editMenu - undoAction, redoAction, seperator, selectAll
        undo = new UndoAction();
        redo = new RedoAction();
        // add two this program made actions
        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.addSeparator();
        // add 4 actions from default editor menu  -- cut, copy, past and selectAll
        // highlight: DefaultEditorKit.selectAllAction is a "String" name for the so called acion.
        // create a "getActionByItsName" method to return Action. The menu needs to add an Acion to function
        // for those nested class Actions, the new keyword can create them directly without binding to any JtextComponent
        editMenu.add(new DefaultEditorKit.CutAction());
        editMenu.add(new DefaultEditorKit.CopyAction());
        editMenu.add(new DefaultEditorKit.PasteAction());
        // selectAllAction is not a inner class
        editMenu.add(getActionByItsName(DefaultEditorKit.selectAllAction));

        //*** for styleMenu --
        Action action = new StyledEditorKit.BoldAction();
        //action.putValue(Action.NAME, "Bold");   //highlight: without customize the name for the action, the default name from its TextAction superClass is "font-bold" on display, run to check the program
        styleMenu.add(action);

        action = new StyledEditorKit.ItalicAction();
        action.putValue(Action.NAME, "Italic***");
        styleMenu.add(action);

        action = new StyledEditorKit.UnderlineAction();
        action.putValue(Action.NAME, "Underline");
        styleMenu.add(action);

        styleMenu.addSeparator();

        styleMenu.add(new StyledEditorKit.FontSizeAction("font size 12", 12));
        styleMenu.add(new StyledEditorKit.FontSizeAction("14", 14));
        styleMenu.add(new StyledEditorKit.FontSizeAction("18", 18));

        styleMenu.addSeparator();

        styleMenu.add(new StyledEditorKit.FontFamilyAction("Serif",
                "Serif"));
        styleMenu.add(new StyledEditorKit.FontFamilyAction("SansSerif",
                "SansSerif"));

        styleMenu.addSeparator();

        styleMenu.add(new StyledEditorKit.ForegroundAction("Red",
                Color.red));
        styleMenu.add(new StyledEditorKit.ForegroundAction("Green",
                Color.green));
        styleMenu.add(new StyledEditorKit.ForegroundAction("Blue",
                Color.blue));
        styleMenu.add(new StyledEditorKit.ForegroundAction("Black",
                Color.black));

        // add keybindings for textPane
        addKeyBindingsToTextPane();
    }

    private void addKeyBindingsToTextPane() {
        // get to inputMap of JComponent, use .put() to bind the keyStroke with ActionMap
        InputMap inputMap = textPane.getInputMap();
        // pair ctrl+b to backward action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B,KeyEvent.CTRL_DOWN_MASK),DefaultEditorKit.backwardAction);
        // pair ctrl+f to forward action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F,KeyEvent.CTRL_DOWN_MASK), DefaultEditorKit.forwardAction);
        // pair ctrl+p to up action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), DefaultEditorKit.upAction);
        // pair ctrl+n to down action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), DefaultEditorKit.downAction);
    }

    // compare with TextComponent_AllTypes_EditorPane_TextPane.java line 194 addStylesTODocument()
    // attributesets in an array with array[index] as the index key.
    private void initTextPane() {
        // because document.insertString() needs string as one parameter  and attributeSet as another parameter
        // set string[]
        String initString[] =
                {"Use the mouse to place the caret.",
                        "Use the edit menu to cut, copy, paste, and select text.",
                        "Also to undo and redo changes.",
                        "Use the style menu to change the style of the text.",
                        "Use the arrow keys on the keyboard or these emacs key bindings to move the caret:",
                        "Ctrl-f, Ctrl-b, Ctrl-n, Ctrl-p."};

        // set style[](interface) - simpleAttributeSet[](class)
        SimpleAttributeSet[] styles = new SimpleAttributeSet[initString.length]; // 0 - 5

        styles[0] = new SimpleAttributeSet(); // use simpleAsttributeSet() first constructor
        StyleConstants.setFontFamily(styles[0], "SansSerif");
        StyleConstants.setFontSize(styles[0], 16);

        styles[1] = new SimpleAttributeSet(styles[0]); // use simpleAttributeSet() second constructor
        StyleConstants.setBold(styles[1], true);

        styles[2] = new SimpleAttributeSet(styles[0]);
        StyleConstants.setItalic(styles[2], true);

        styles[3] = new SimpleAttributeSet(styles[0]);
        StyleConstants.setFontSize(styles[3], 26);

        styles[4] = new SimpleAttributeSet();
        StyleConstants.setFirstLineIndent(styles[4], 5f);

        styles[5] = new SimpleAttributeSet();
        StyleConstants.setForeground(styles[5], Color.YELLOW);

        // iterate through to doc.insertString

        for (int i = 0; i < initString.length; i++) {
            try {
                doc.insertString(doc.getLength(), initString[i], styles[i]);
            } catch (BadLocationException e) {
                System.err.println("Couldn't insert text into the text pane.");
            }
        }
    }

    private Action getActionByItsName(String actionName) {
        HashMap<String, Action> actionArray = new HashMap<>();
        DefaultEditorKit kit = new DefaultEditorKit();
        Action[] actions = kit.getActions();
        for (int i = 0; i < actions.length; i++) {
            actionArray.put((String) actions[i].getValue(Action.NAME), actions[i]);
        }
        return actionArray.get(actionName);
    }


    // Actions Class
    class UndoAction extends AbstractAction {
        // constractor
        public UndoAction() {
            super("Undo"); // create an action with the set Name
            setEnabled(false); // initially false state
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            undoManager.undo();
            updateUndoActionState();
            redo.updateRedoActionState();
        }

        private void updateUndoActionState() {
            if (undoManager.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undoManager.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "undo");
            }

        }
    }

    class RedoAction extends AbstractAction {
        //constructor
        public RedoAction() {
            super("redo");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            undoManager.redo();
            updateRedoActionState();
            undo.updateUndoActionState();
        }

        public void updateRedoActionState() {
            if (undoManager.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undoManager.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "redo");
            }
        }
    }

    public static void CreateAndShowGUI() {
        TextPane_Menu_CaretListener_DocumentListener frame = new TextPane_Menu_CaretListener_DocumentListener();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CreateAndShowGUI();
            }
        });
    }

    private class ThisDocumentListener implements DocumentListener {
        // all methods display the same result
        @Override
        public void insertUpdate(DocumentEvent e) {
            System.out.println("insertUpdated");
            displayDocUpdates(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            System.out.println("removeUpdated");
            displayDocUpdates(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("changeUpdated");
            displayDocUpdates(e);
        }

        private void displayDocUpdates(DocumentEvent e) {
            Document document = e.getDocument();
            int length = e.getLength();
            System.out.println("document " + document + " has changed " + length);

            try {
                logArea.setText((document.getText(textPane.getSelectionStart(), length)) + " " +e.getType());
            } catch (BadLocationException ex) {
                System.out.println("Bad Location inside documentEventListener");
                ex.printStackTrace();
            }
        }
    }

    private class ThisUnDoableEditListener implements UndoableEditListener {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            UndoableEdit edit = e.getEdit();
            undoManager.addEdit(edit);
            // check if canUndo and canRedo to set setEditable accordingly
            undo.updateUndoActionState();
            redo.updateRedoActionState();
        }
    }
}
