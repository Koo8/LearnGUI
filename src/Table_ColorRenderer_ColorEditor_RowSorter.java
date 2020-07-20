import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

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
        // this line will make the table take full height of the viewPort
        table.setFillsViewportHeight(true);
        // put table inside a scrollpane
        // the table header is automatically put at the top of the viewPort by scrollpane
        JScrollPane scrollPane= new JScrollPane(table);

        // colorEditing and colorRendering here
        // getColumnClass() from TableModel is used by the JTable to set up a default renderer and editor for the column.
        // TableColumn constructor has 4 parameters columnIndex, width, cellRender,and cellEditor
        // custom editor and renderer goes hand in hand

        table.setDefaultEditor(Color.class,new MyTableCellEditor());
        table.setDefaultRenderer(Color.class,new MyTableCellRenderer());
        // set TableRowSorter
        table.setRowSorter(new TableRowSorter<>(tableModel));
        add(scrollPane);
        setOpaque(true);

    }

    private class My_TableModel extends AbstractTableModel {
        private String[] columnName = {
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

        /// the first 3 create a simple functional table, they are required by CellEditor
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnName.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        // Name the column with real names instead of A B C ...
        @Override
        public String getColumnName(int column) {
            return columnName[column];
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

        // change to new cell content    - which is a new color
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            // notify all listeners that this cell value has been changed, this connect to TableCellEditor interface
            fireTableCellUpdated(rowIndex,columnIndex);
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

}
