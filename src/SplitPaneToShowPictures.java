import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class SplitPaneToShowPictures extends JPanel implements ListSelectionListener {
    String[] data = {"Bird", "Cat", "Dog", "Rabbit", "Pig", "dukeWaveRed",
            "kathyCosmo", "lainesTongue", "middle", "stickerface"};
    JList list;
    String path;
    ImageIcon icon;
    JLabel picLabel;
    ImageIcon[] iconArray = new ImageIcon[data.length];

    // constructor
    SplitPaneToShowPictures() {
        super(new BorderLayout());
        JPanel listPane = new JPanel();
        JPanel picturePane = new JPanel(new BorderLayout());
        // listPane
        list = new JList(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setSelectedIndex(0);
        // highlight: add listener b4 "setSelctedIndex(0) will trigger the listener, must add behind that code
        list.addListSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(list);

        listPane.add(scrollPane);
        //picturePane
        icon = Utility.createImageIcon(this, "images/bird.gif");
        picLabel = new JLabel(icon);
        picturePane.add(picLabel, BorderLayout.CENTER);

        System.out.println(data.length + " is the data length");
        for (int i = 0; i < data.length; i++) {
            path = "images/" + data[i].toLowerCase() + ".gif";
            icon = Utility.createImageIcon(this, path);
            if (icon == null) {
                System.out.println("icon is null");
            }
            iconArray[i] = icon;
        }
        System.out.println(iconArray[2]);

        // set minimumsize of both sides  - this prevent the divider push over the either pane to a bad lcale.
        scrollPane.setMinimumSize(new Dimension(150, 100));
        picturePane.setMinimumSize(new Dimension(150, 100));


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, picturePane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        splitPane.setPreferredSize(new Dimension(400, 400));

        add(splitPane);
        setOpaque(true);
    }

    public static void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(new SplitPaneToShowPictures());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplitPaneToShowPictures::GUI);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("in listener");
        int index = list.getSelectedIndex();
        System.out.println(index);
        picLabel.setIcon(iconArray[index]);
    }
}
