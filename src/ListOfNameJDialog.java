import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * oo A helper class to return an element from a list.  oo
 * used by ChooseName_FromListDialog.java
 * Function: Use this modal dialog to let the user choose one name from a long
 * list.
 * This class display the JDialog content and have a method to return the selected name. This
 * method will be called by the Runner Class  - ChooseName_FromListDialog.java
 */
public class ListOfNameJDialog extends JDialog implements ActionListener {

    private JList list;
    private static String selectedName;
    private static ListOfNameJDialog dialog;

    // constructor
    protected ListOfNameJDialog(Frame frame,String title, Object[] dataArray, String longString, String listTitle, String initialValue, Component locationComponent) {
        // Creates a dialog with the specified title, owner {@code Frame}
        // and modality - using super(frame, title, modality);
       // Frame frame = JOptionPane.getFrameForComponent(parentComponent);
        super(frame, title, true); // can't add the previous line because super() has to be the first line in the method.

        // **** Create two buttons - cancel and set
        JButton cancelButton = new JButton("Cancel");
        JButton setButton = new JButton("Change Name");
        cancelButton.addActionListener(this);
        setButton.addActionListener(this);
        // to judge which button is clicked later
        setButton.setActionCommand("set");
        // to respond to the mouseClickListener "double_click" wthin the list boundry
        getRootPane().setDefaultButton(setButton);
        // layout the two buttons from left to right
        JPanel buttonPanel = new JPanel();
             ///// use boxLayout
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
        buttonPanel.add(setButton);


        // **** Create the main part of the dialog
        // Subclass JList to workaround bug 4832765 - by overriding one method
        list = new JList(dataArray) {
            //set the unitIncrement
            //of the JScrollBar to a fixed value ( the returned int). You wouldn't get the nice
            //aligned scrolling, but it should work.

            //Returns the distance (the unit increment) to scroll to expose the next or previous
            //row (for vertical scrolling) or column (for horizontal scrolling).


            @Override
            public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
                // add the following code for avoiding this bag on Scrollpane
                int row;
                if (orientation == SwingConstants.VERTICAL &&
                        direction < 0 && (row = getFirstVisibleIndex()) != -1)
                {
                    Rectangle r = getCellBounds(row, row);
                    if ((r.y == visibleRect.y) && (row != 0))
                    {
                        Point loc = r.getLocation();
                        loc.y--;
                        int prevIndex = locationToIndex(loc);
                        Rectangle prevR = getCellBounds(prevIndex, prevIndex);

                        if (prevR == null || prevR.y >= r.y) {
                            return 0;
                        }
                        return prevR.height;
                    }
                }// end of added code for avoiding bug
                return super.getScrollableUnitIncrement(visibleRect, orientation, direction);
            }
        };

        // set 4 properties for the list
        /////  ---- selectionMode
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ////  ----  orientation
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        /////  ---- wrapping feature
        /**
         * If the visibleRowCount property is less than or equal to zero,
         * wrapping is determined by the width of the list; otherwise
         * wrapping is done in such a way as to ensure visibleRowCount
         * rows in the list.
         */
        list.setVisibleRowCount(-1);
        /////  ---- cell value by prototype
        if(longString != null) {
            list.setPrototypeCellValue(longString); // longSting.length is bigger than each of all elements, so that each element can display in full
        }
        /////oooo todo: this will be changed later ---- selectionValue
       // list.setSelectedValue(initialValue, true);

        // add mouseListener to list so that double click within the Jlist equals to setButton clicked.
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setButton.doClick();    // emulate setButton clicked
                }
            }
        });

        // make the JList scrollable
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(250, 90));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        // create a container to hold the scrollPane, create a label as the title
        // for the scollPane, the layout is top-down (Box Page_axis)
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane,BoxLayout.PAGE_AXIS));
        JLabel theListTitle = new JLabel(listTitle);
        listPane.add(theListTitle);
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));
        listPane.add(scrollPane);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        listPane.setBackground(Color.getHSBColor(0.6f,0.4f,0.8f));

        // add two panels to the contentPane - default contentPane layout is borderLayout
        Container container = getContentPane();
        container.add(listPane, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.PAGE_END);

        setValue(initialValue);
        // window methods
        pack();
        setLocationRelativeTo(locationComponent);
       // setVisible(true);    // since this needs to be false later, can't keep this line here. move to CreateDialogReturnSelectedName method

    }

    private void setValue(String initialValue) {
        selectedName = initialValue;
        list.setSelectedValue(selectedName,true);   // when setButton clicked, this value needs to be changed
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("set".equals(e.getActionCommand())) {
            ListOfNameJDialog.selectedName = (String) list.getSelectedValue();
        }
        //close the dialog
        ListOfNameJDialog.dialog.setVisible(false);
    }

    // in order to return a selected Name, instantiate this class and return the name
    public static String createDialogReturnSelectedName(Component parent,String title, Object[] dataArray, String longString, String listTitle, String initialValue, Component locationComponent){
        Frame frame = JOptionPane.getFrameForComponent(parent); // JDialog super require Frame not Component, so use this to get the component's frame
        // this dialog instance is needed for "closing" in actionPerformed
        dialog = new ListOfNameJDialog(frame,title,dataArray,longString, listTitle,initialValue,locationComponent);
        dialog.setVisible(true); // this line has to be here, so that when actionPerformed method, setVisible(false) can be achieved.
        return selectedName;
    }
}
