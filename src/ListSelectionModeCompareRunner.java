import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.FlowLayout;

class ListSelectionModeCompare extends JFrame {

    ListSelectionModeCompare()
    {
        setTitle("JList : SINGLE_INTERVAL_SELECTION and SINGLE_SELECTION");
        setLayout(new FlowLayout());
        setJList1();
        setJList2();
        setSize(1000,250);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setJList1()
    {
        String[] names = {"Windows","Ubuntu","Macintosh","Linux","Fedora"};
        JList jl = new JList(names);
        jl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);   // change this to single_interval_selection to see the difference
        JScrollPane js = new JScrollPane(jl);
        js.setPreferredSize(new Dimension(200,200));
        add(new JLabel("SINGLE_INTERVAL_SELECTION"));
        add(js);
    }

    private void setJList2()
    {
        String[] names = {"Symbian","Android","Apple iOS","BlackBerry","Windows Mobile"};
        JList jl = new JList(names);
        jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane js = new JScrollPane(jl);
        js.setPreferredSize(new Dimension(200,200));
        add(new JLabel("SINGLE_SELECTION"));
        add(js);
    }
}

public class ListSelectionModeCompareRunner {

    public static void main(String[] args) {

        ListSelectionModeCompare fr = new ListSelectionModeCompare();
    }
}
