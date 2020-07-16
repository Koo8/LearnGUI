import javax.swing.*;
import java.awt.*;
import java.util.zip.DeflaterInputStream;

public class JListSelectionMode extends JFrame {
    // constructor
    JListSelectionMode (){
        setTitle("JList: Single_Interval_selection vs. Single_selelction");
        setLayout(new FlowLayout());
        setSize(new Dimension(1000,300));
        createJList1();
        createJList2();
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void createJList2() {
        String[] fruits = {"apple", "pear", "date", "watermelon", "grapes"};
        JList jlist1= new JList(fruits);
        jlist1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jlist1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        /**
         * If the visibleRowCount property is less than or equal to zero,
         * wrapping is determined by the width of the list; otherwise
         * wrapping is done in such a way as to ensure visibleRowCount
         * rows in the list.
         */
        jlist1.setVisibleRowCount(-1);
        jlist1.setPrototypeCellValue("needthesespace");
        // add to jScrollpane
        JScrollPane scrollPane1 = new JScrollPane(jlist1);
        scrollPane1.setPreferredSize(new Dimension(200, 200));
        add(new JLabel("Single_InterVal"));
        add(scrollPane1);
    }

    private void createJList1() {
        String[] colors = {"red","blue","yellow","white","pink","gray","brown"};
        JList jlist2 = new JList(colors);
        jlist2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane2  = new JScrollPane(jlist2);
        scrollPane2.setPreferredSize(new Dimension(200,200));
        add(new JLabel("single selection"));
        add(scrollPane2);

    }

    public static void main(String[] args) {
        JListSelectionMode frame  =new JListSelectionMode();
    }
}
