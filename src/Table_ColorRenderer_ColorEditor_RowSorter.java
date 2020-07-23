import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table_ColorRenderer_ColorEditor_RowSorter extends JPanel {

    // Constructor
    Table_ColorRenderer_ColorEditor_RowSorter() {
        super(new GridLayout(1,0));

        // construct the table
        TableModel tableModel = new My_TableModel();
        JTable table = new JTable(tableModel);
        // set table size
        // can't use setPreferredSizes() from JComponent, that is only when the Jcomponent is not requiring any size demands from viewPort
        table.setPreferredScrollableViewportSize(new Dimension(500,70));
        // this line will make the table take full height of the viewPort    // but not sure, should test
        table.setFillsViewportHeight(true);
        // put table inside a scrollpane
        // the table header is automatically put at the top of the viewPort by scrollpane
        JScrollPane scrollPane= new JScrollPane(table);
       //
        initColumeSizes(table);

        // colorEditing and colorRendering here
        // getColumnClass() from TableModel is used by the JTable to set up a default renderer and editor for the column.
        // TableColumn constructor has 4 parameters columnIndex, width, cellRender,and cellEditor
        // custom editor and renderer goes hand in hand
        //** render and editor for Color.class
        table.setDefaultEditor(Color.class,new My_TableCellEditor());
        table.setDefaultRenderer(Color.class,new My_TableCellRenderer());

        // setUp a specific column
        setUpSportsColumn(table, table.getColumnModel().getColumn(2));
        // set TableRowSorter
        table.setRowSorter(new TableRowSorter<>(tableModel));
        add(scrollPane);
        setOpaque(true);

    }
    // set up sports column with default cell editor (combobox as a parameter ) and default table cell renderer
    private void setUpSportsColumn(JTable table, TableColumn column) {
           column.setCellEditor(new DefaultCellEditor(createComboBox()));
           DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
           renderer.setToolTipText("Click for More Options");
           column.setCellRenderer(renderer);
    }

    private JComboBox<String> createComboBox() {
        String[] sports = {"swiming", "jogging", "basketball", "dancing", "soccer"};
        JComboBox box = new JComboBox();
        for (int i = 0; i <sports.length ; i++) {
            box.addItem(sports[i]);
        }
        return box;

    }

    private class My_TableModel extends AbstractTableModel {
        private String[] columnTitle = {
                "First Name",
                "Favorite Color",
                "Sport",
                "# of Years",
                "Vegetarian"
        };
        private Object[][] data = {
                {"Nancy", new Color(153,203,15), "Swimming", 5, Boolean.TRUE},
                {"Josh", new Color(100, 100, 230), "Basketball", 10, Boolean.FALSE},
                {"Rachel", new Color(25, 33, 140), "Jogging", 7, Boolean.FALSE},
                {"Darcy", new Color(200, 35, 135), "Soccer", 9, Boolean.TRUE},
                {"Melody", new Color(60, 180,250), "Baseball", 3, Boolean.FALSE}
        };
        // need a longValue to set the max of width
        Object[] longValue = {"Helloworld", "xxxxxxxxxxx", "None of the above", 20, Boolean.FALSE};


        /// the first 3 methods create a simple functional table, they are required by CellEditor
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnTitle.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        // Name the column with real names instead of A B C ...
        @Override
        public String getColumnName(int column) {
            return columnTitle[column];
        }

        /**
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         * @param columnIndex
         * @return
         */
        // to make each column render in its proper format, such as color into color, true / false into checkbox
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            // header is not counted as a row
            return getValueAt(0,columnIndex).getClass();
        }
        // by default, false, to make button.addActionListener() clickable, make editable true;
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) { // default is false
           if (columnIndex<1) {  // name is not editable
               return false;
           }
           return true;
        }
        // overide this if your table data can change
        // change to new cell content    - which is a new color
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            // notify all listeners that this cell value has been changed, this connect to TableCellEditor interface
            fireTableCellUpdated(rowIndex,columnIndex);
        }
    }

    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumeSizes(JTable table) {
        // get header and column width from My_TableModel
        My_TableModel model = new My_TableModel();
        int numOfColumn = model.getColumnCount();
        TableColumn theColumn;
        Component comp;
        int headerWidth, cellWidth;
        // for each column compare width of Header and cell
        for (int i = 0; i < numOfColumn; i++) {
            theColumn = table.getColumnModel().getColumn(i) ;
            comp = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(null,theColumn.getHeaderValue(),false, false,0,0);
            headerWidth = comp.getPreferredSize().width; // todo: compare with getWidth();
            // reassign comp with longValue
            comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table,model.longValue[i], false,false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            // set column width
            theColumn.setPreferredWidth(Math.max(headerWidth,cellWidth));
        }


        
    }

    //CASGUI
    private static void CASGUI() {
        JFrame frame = new JFrame("Table With Cell Editor and Cell Renderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Table_ColorRenderer_ColorEditor_RowSorter pane = new Table_ColorRenderer_ColorEditor_RowSorter();
        pane.setOpaque(true);
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }

    //main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CASGUI();
            }
        });
    }

    // inner Class
/**
 * mimic DefaultCellEditor constructure
 * To to used by Table_ColorRenderer_ColorEditor_RowSorter.java
 */

    private class My_TableCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        /// Fields
        // JComponent - the cell is a button
        JButton button;
        // button open to a ColorChooser JDialog
        JColorChooser colorChooser;
        JDialog dialog;     // createDialog() -- a dialog that display a colorChooser
        Color currentColor;

        ///Constructor
        My_TableCellEditor() {
            button = new JButton(); // cell is a JButton.
            button.setActionCommand("edit");
            button.addActionListener(this);
            // currentColor is retrieved from below method
            // create once the dialog for colorChooser
            colorChooser = new JColorChooser();
            dialog = JColorChooser.createDialog(button,"pick a color", true, colorChooser,this,null); // no cancel listener was implemented
        }

        // from TableCellEditor, return the Jcomponent which acts as an editor (from JTable point of view)
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            currentColor = (Color) value;
            return button;
        }
        // from CellEditor methods - to get the currentColor
        @Override
        public Object getCellEditorValue() {
            return currentColor;
        }

        // listen to two actions, from the button(cell) and from OK in colorChooser
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("edit".equals(e.getActionCommand())) {
                button.setBackground(currentColor);
                // create dialog once inside the constructor
//        colorChooser = new JColorChooser();
//        dialog =  colorChooser.createDialog(button,"Pick a new color",true,colorChooser,this,null); // don't have cancel listener
                colorChooser.setColor(currentColor);
                dialog.setVisible(true);
                // must have! to inform all listeners the editing is finished. // this will recall the renderer
                fireEditingStopped();

            } else {
                currentColor = colorChooser.getColor();
            }
        }
    }


    /**
     * this is a JLabel, must set Opaque for showing background color
     * this is for Table_ColorRenderer_ColorEditor_RowSorter.java
     */

    private class My_TableCellRenderer extends JLabel implements TableCellRenderer {
        //constructor
        // render a jLabel as the cell component
        My_TableCellRenderer(){
            setOpaque(true);  // must set to opaque for background to be seen
        }

        // return this jComponent
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // when only need to draw the new background color, the first line is enough
            setBackground((Color) value);
            // when border decoration is needed
            // border change appearance when isSelected
            if (isSelected) {
                setBorder(BorderFactory.createMatteBorder(2,5,2,5,table.getSelectionBackground()));
            } else {
                setBorder(BorderFactory.createMatteBorder(2,5,2,5,table.getBackground()));
            }
            return this;// the button is a JLabel being painted
        }
    }



}
