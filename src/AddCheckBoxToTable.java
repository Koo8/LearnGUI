import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddCheckBoxToTable extends JFrame {
    JTable table;
    JTextField text;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                AddCheckBoxToTable form = new AddCheckBoxToTable();
                form.setVisible(true);
            }
        });
    }

    // constructor
    // oo use setBounds to define the layout of components
    public AddCheckBoxToTable() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(new Dimension(500, 100));
        setTitle("Table with CheckBox");
        setBounds(200, 200, 800, 500); // set the position for the window
        getContentPane().setLayout(null); // set bounds doesn't work well with layou

        // the table
        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(70, 80, 600, 200);
        getContentPane().add(scroll);
        //the model of our table
        DefaultTableModel model = new DefaultTableModel() {


            @Override // define 5 columns, each of its class name
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return String.class;
                    case 1:
                        return Boolean.class; // when Boolean.class, this column will use checkbox for result automatically
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        //assign the model to the table
        table.setModel(model);
        // add column
        model.addColumn("Select");
        model.addColumn("Position");
        model.addColumn("Team");
        model.addColumn("Points");
        model.addColumn("Manager");

        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(100);
            }
        }


        //add 7 rows
        for (int i = 0; i < 7; i++) {
            model.addRow(new Object[0]);
            model.setValueAt(false, i, 1);
            model.setValueAt("Row" + (i + 1), i, 0);
            model.setValueAt("Column 2", i, 2);
            model.setValueAt("Column 3", i, 3);
            model.setValueAt("Column 4", i, 4);
        }

        // add TextField to show result
        text = new JTextField();
        text.setEditable(false);
        text.setText("");
        text.setBounds(70, 350, 300, 30);
        getContentPane().add(text);

        // listen to the change
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if (column == 1) {
                    Boolean checked = Boolean.valueOf(table.getValueAt(row, 1).toString());
                    String newString = "row " + row + " is " + (checked == true ? "checked" : "unchecked.");
                    text.setText(text.getText() + "    " + newString);
                }
            }
        });
    }

    // todo make cell clickable

}
