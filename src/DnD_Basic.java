import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DnD_Basic extends JPanel implements ActionListener {
    // Fields
    private JTable table;
    private JColorChooser colorChooser;
    private JCheckBox tickButton;
    private JTextField field;
    private JTextArea area;
    private JList list;
    private JTree tree;


    //Constructor
    DnD_Basic() {
        super(new BorderLayout());
        // need a LeftPanel and a RightPanel
        JPanel leftPanel = createVerticalBoxPanel();
        JPanel rightPanel = createVerticalBoxPanel();

        // leftPanel with JTable and JColorChooser
       //  1st tableModel   - defaulttablemodel
        // DefaultTableModel tableModel = createDefaultTableModel();
        // 2nd tableModle ( oooo  the Boolean class can be shown properly, but not the color class)
        /** for change the Color.clas into color presentation, see MyTableCellRenderer.java and MyTableCellEditor.java */
        AbstractTableModel tableModel = new MyTableModel();
        table = new JTable(tableModel);
        JScrollPane tablePane =new JScrollPane(table); // columnHeader is showing now
        tablePane.setPreferredSize(new Dimension(300, 100));
        leftPanel.add(componentWrappedWithinPanel(tablePane, "JTable"));

        colorChooser = new JColorChooser();
        leftPanel.add(componentWrappedWithinPanel(colorChooser, "JColorChooser"));

        // rightPanel with a JTextField, JTextArea, JList and a JTree
        field = new JTextField(30);
        field.setText("Favourite Food: \nStrawberry, BlueBerry, Raspberry, MoreBerry");
        rightPanel.add(componentWrappedWithinPanel(field, "JTextField"));

        area = new JTextArea(4, 30);
        area.setText("Favourite Colors: \nStarWar \nHarryPotter \nMermaid \nChildhood \nWhat's up? ");
        rightPanel.add(componentWrappedWithinPanel(area, "JTextArea"));

        DefaultListModel listModel = createListModel();
        list = new JList(listModel);
        list.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // since JList doesn't have default transferHandler
        list.setTransferHandler(new MyTransferHandler());
        list.setDropMode(DropMode.ON_OR_INSERT);
        JScrollPane listPane = new JScrollPane(list);
        listPane.setPreferredSize(new Dimension(300, 100));
        rightPanel.add(componentWrappedWithinPanel(listPane, "JList"));


        DefaultTreeModel treeModel = createDefaultTreeModel();
        tree = new JTree(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        JScrollPane treepane = new JScrollPane(tree);
        treepane.setPreferredSize(new Dimension(300, 200));
        rightPanel.add(componentWrappedWithinPanel(treepane, "JTree"));

        // create a togglebutton at the bottom
        tickButton = new JCheckBox("Turn on drag and drop");
        tickButton.setActionCommand("tick");
        tickButton.addActionListener(this);

        // put 2 panels into a splitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);
        add(tickButton, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    private DefaultTreeModel createDefaultTreeModel() {
        // if there are branches create, must instantiate with a name
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Gao Family");
        DefaultMutableTreeNode mother = new DefaultMutableTreeNode("Mother");
        root.add(mother);
        DefaultMutableTreeNode father = new DefaultMutableTreeNode("Father");
        root.add(father);
        mother.add(new DefaultMutableTreeNode("Haiming"));
        father.add(new DefaultMutableTreeNode("Alice"));
        father.add(new DefaultMutableTreeNode("Teagan"));
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        return treeModel;
    }

    class MyTransferHandler extends TransferHandler {

        // canImport
        @Override
        public boolean canImport(TransferSupport support) {
            // we only import Strings
            if (!support.isDataFlavorSupported(DataFlavor.stringFlavor))
                return false;
            return true;
        }

        //importData

        @Override
        public boolean importData(TransferSupport support) {
            // import can start only if it is drop operation
            if (!support.isDrop()) return false;

            // when import is not string, pop up a message pane
            if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                displayMessage("List doesn't accept a drop of this type...");
                return false;
            }
            // code that perform the actual import
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            DefaultListModel listModel = (DefaultListModel)  list.getModel();
            int index = dl.getIndex();

            // Get the string that is being dropped.
            Transferable t = support.getTransferable();
            String data;
            try {
                data = (String) t.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                return false;
            }
            boolean insert = dl.isInsert();
            if (insert) {
                listModel.add(index, data);
            } else {
                listModel.set(index, data);
            }

            return true;

        }


        // getSourceAction

        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }

        // create Transferable

        @Override
        protected Transferable createTransferable(JComponent c) {
            // get selected list part
            JList list = (JList) c;
            Object[] values = list.getSelectedValuesList().toArray();

            // change the list part into a string
            StringBuffer buff = new StringBuffer();
            for (int i = 0; i < values.length; i++) {
                Object value = values[i];
                buff.append(value == null ? "" : value.toString());
                // add "\n" after each value before the end of the selection
                if (i != values.length - 1) {
                    buff.append("\n");
                }

            }
            // use new StringSelection to create Transferable

            return new StringSelection(buff.toString());
        }
    }

    private void displayMessage(String s) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, s);
            }
        });

    }

    private DefaultListModel createListModel() {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        defaultListModel.addElement("Nancy Gan");
        return defaultListModel;
    }

    // all components are wrapped within its own JPanel with titled Border
    private JPanel componentWrappedWithinPanel(JComponent com, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(com, BorderLayout.CENTER);
        if (title != null) {
            panel.setBorder(BorderFactory.createTitledBorder(title));
        }
        return panel;
    }
    private class MyTableModel extends AbstractTableModel{
        private String[] columnNames = {"Column1", "Column12", "Column3", "Column4"};
        private Object[][] data =  {
                {1, 2, Color.RED, Boolean.TRUE},
                {1, 2, Color.RED, Boolean.TRUE},
                {1, 2, Color.RED, Boolean.TRUE},
                {1, 2, Color.RED, Boolean.TRUE}
        };
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        // for class to appear properly inside the table

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0,columnIndex).getClass();
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
    private DefaultTableModel createDefaultTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Column 1");
        tableModel.addColumn("Column 2");
        tableModel.addColumn("Column 3");
        tableModel.addColumn("Column 4");
        tableModel.addRow(new Object[]{1, 2, Color.RED, Boolean.TRUE});
        tableModel.addRow(new Object[]{1, 2, Color.RED, Boolean.TRUE});
        tableModel.addRow(new Object[]{1, 2, Color.RED, Boolean.TRUE});
        tableModel.addRow(new Object[]{1, 2, Color.RED, Boolean.TRUE});
        return tableModel;
    }

    private JPanel createVerticalBoxPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return panel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getActionCommand().equals("tick") ) {
           // two possible states - selected or deSelected
           boolean tickState = tickButton.isSelected();
           table.setDragEnabled(tickState);
           colorChooser.setDragEnabled(tickState);
           field.setDragEnabled(tickState);
           area.setDragEnabled(tickState);
           list.setDragEnabled(tickState);
           tree.setDragEnabled(tickState);
       }
    }

    private static void CASGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DnD_Basic pane = new DnD_Basic();
        pane.setOpaque(true);
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CASGUI();
            }
        });
    }
}
