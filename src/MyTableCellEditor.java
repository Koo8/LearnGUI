import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * mimic DefaultCellEditor constructure
 * To to used by Table_ColorRenderer_ColorEditor_RowSorter.java
 */

public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    /// Fields
    // JComponent - the cell is a button
    JButton button;
    // button open to a ColorChooser JDialog
    JColorChooser colorChooser;
    JDialog dialog;     // createDialog() -- a dialog that display a colorChooser
    Color currentColor;

    ///Constructor
    MyTableCellEditor() {
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
