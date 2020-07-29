import com.sun.source.tree.Tree;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Tree_EditorPane_BookLIst extends JPanel implements TreeSelectionListener {
    JTree tree;
    JEditorPane bookContentPane ;
    URL mainURL = getClass().getResource("TreeDemoHelp.html");
    // change the value to play with different look and fee
    static boolean useSystemLookAndFeel = false;
    private Tree_EditorPane_BookLIst() {
        super(new BorderLayout());

        //two jScrollPane holding tree of book and jeditorPane for book content
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");

        constructNodes(top);

        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setCellRenderer(new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(
                    JTree tree, Object value, boolean sel,
                    boolean expanded, boolean leaf, int row, boolean hasFocus) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

//                if(((DefaultMutableTreeNode) value).isRoot())
//                {
                    setBackgroundSelectionColor(Color.ORANGE);
//                }

                return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            }

        });
        tree.addTreeSelectionListener(this);

        JScrollPane s1 = new JScrollPane(tree);

        bookContentPane = new JEditorPane();
        displayURL(mainURL);
        // setEditable true will show some script coding
        bookContentPane.setEditable(false);
        JScrollPane s2 = new JScrollPane(bookContentPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, s1, s2);
        splitPane.setDividerLocation(120);
        splitPane.setPreferredSize(new Dimension(600, 500));

        // check out JTree line style
        //The JTree.lineStyle client property is used only by the Metal look and feel.
        JTree treeSample = new JTree();// a self_built_in JTree
        // 3 options --
        treeSample.putClientProperty("JTree.lineStyle", "None");
        //treeSample.putClientProperty("JTree.lineStyle", "Angled");
      //  treeSample.putClientProperty("JTree.lineStyle", "Horizontal");

        add(splitPane, BorderLayout.CENTER);
        add(treeSample,BorderLayout.PAGE_END);
    }

    private void constructNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Book for Java Programmers");
        top.add(category);
        String[] books = {
                "The Java Tutorial: A short Course on the Basics",
                "The Java Tutorial Continued: The Rest of the JDK",
                "The JFC Swing Tutorial: A Guide to Constructing Guide",
                "Effective Java Programming Language Guide",
                "The Java Programming Language",
                "The Java Developers almanac"
        };
        String[] files = {
                "tutorial.html",
                "tutorialcont.html",
                "swingtutorial.html",
                "bloch.html",
                "arnold.html",
                "chan.html"
        };
        DefaultMutableTreeNode book;
        for (int i = 0; i < books.length; i++) {
            book = new DefaultMutableTreeNode(new TheBook(books[i], files[i]));
            category.add(book);
        }
        category = new DefaultMutableTreeNode("Books for Java Implementers");
        top.add(category);
        String[] morebooks = {
                "The Java Virtual Machine Specification",
                "The Java Language Specification"
        };
        String[] moreFiles = {
                "vm.html","jls.html"
        };
        for (int i = 0; i <morebooks.length ; i++) {
            book = new DefaultMutableTreeNode(new TheBook(morebooks[i], moreFiles[i]));
            category.add(book);

        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        System.out.println("clicked");
        // find the node that's click
        // DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        // or from e to get the path;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
        if (node == null) return;
        Object nodeObject = node.getUserObject();
        if (node.isLeaf()) {
            TheBook book = (TheBook) nodeObject;
//            tree.setCellRenderer(new DefaultTreeCellRenderer(){
//                @Override
//                public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//                   // must use super();
//                    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
//                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
//                    if(((DefaultMutableTreeNode) value).isLeaf() ){
//                        setBackgroundSelectionColor(Color.LIGHT_GRAY);
//                       setTextSelectionColor(Color.WHITE);
//
//                    }
//
//                    return this;
//                }
//            });
            displayURL(book.filePath);
        }  else {
            displayURL(mainURL);
        }
    }

    private void displayURL(URL filePath) {
        try {
            if (filePath != null) {
                bookContentPane.setPage(filePath);
            } else {
                bookContentPane.setText("File can't be found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TheBook {
        String title;
        URL filePath;

        public TheBook(String title, String filePath) {
            this.title = title;
            this.filePath = getClass().getResource(filePath);
        }

        // this is to display only the title in the tree
        @Override
        public String toString() {
            return title;
        }
    }

    private static void CASGUI() {
        // define look and feel first
        if(useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                // note: for system look and feel, line style won't work.
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (UnsupportedLookAndFeelException e) {
//                e.printStackTrace();
//            }
            } catch(Exception e) {
                System.out.println("Couldn't get System Look and feel");
            }
        }


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Tree_EditorPane_BookLIst panel = new Tree_EditorPane_BookLIst();
        frame.setContentPane(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Tree_EditorPane_BookLIst::CASGUI);
    }

}
// todo: add style to a node row, 