import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * this is a JLabel, must set Opaque for showing background color
 * this is for Table_ColorRenderer_ColorEditor_RowSorter.java
 */

public class MyTableCellRenderer extends JLabel implements TableCellRenderer {
    //constructor
    // render a jLabel as the cell component
    MyTableCellRenderer(){
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
