import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class JFileChooser_FileViewWithIcon_FileFilterWithImageFileExtension extends JPanel implements ActionListener {

    JButton button;
    JTextArea textArea;
    JFileChooser fileChooser;
    String[] exts = {
            "tiff","tif","gif","jpeg", "jpg","png","html"
    };
    
    //construct the JPanel layout
    JFileChooser_FileViewWithIcon_FileFilterWithImageFileExtension() {
        super(new BorderLayout());

        button = new JButton("Attach File");
        button.addActionListener(this);
        add(button, BorderLayout.PAGE_START);

        textArea = new JTextArea( 5, 20);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(10,5,10, 5));
        textArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane,BorderLayout.CENTER);
       
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // create filechooser and call showDialog
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        // set Filechooser to open from My PC not from default document
        fileChooser.setCurrentDirectory(
                fileChooser.getFileSystemView().getParentDirectory(new File("c:\\")));

        // define fileChooser features
        fileChooser.addChoosableFileFilter(new MyImageFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        // add custom icon for different file type
        fileChooser.setFileView(new MyIconFileView());
        // add image pane
        fileChooser.setAccessory(new ImagePreviewPane(fileChooser));


        //  the return state of the file chooser on popdown:
        //JFileChooser.CANCEL_OPTION
        //JFileChooser.APPROVE_OPTION
        //JFileChooser.ERROR_OPTION if an error occurs or the dialog is dismissed
        int res =  fileChooser.showDialog(this,"attach");
        if(res == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textArea.append("The attached file is " + file.getName() + "\n" );
        } else {
            textArea.append("You cancelled attaching action" + "\n");
        }

        // no diffenerce with or without this line
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private static void CASGUI() {
        JFrame frame = new JFrame("JChooseFile Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFileChooser_FileViewWithIcon_FileFilterWithImageFileExtension panel =
                new JFileChooser_FileViewWithIcon_FileFilterWithImageFileExtension();
        frame.setContentPane(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JFileChooser_FileViewWithIcon_FileFilterWithImageFileExtension::CASGUI);
    }

    // only allow some specific types of file to show in the fileChooser
    private class MyImageFileFilter extends FileFilter {
        @Override
        public boolean accept(File f) {
            ////Accept all directories and all gif, jpg, tiff, or png files, and html files.
            if (f.isDirectory()) return true;
            // check all extensions

            String extension = Utility.getFileExtension(f);
            if (extension != null) {
                if (extension.equals(exts[0]) ||
                        extension.equals(exts[1]) ||
                        extension.equals(exts[2]) ||
                        extension.equals(exts[3]) ||
                        extension.equals(exts[4]) ||
                        extension.equals(exts[5]) ||
                        extension.equals(exts[6]))
                    return true;
                else return false;
            }
            return false;
        }
        // this shows at the bottom of the filechooser, by default it's "All Files"
        @Override
        public String getDescription() {
            return "Images and html Files";
        }
    }
    private class MyIconFileView extends FileView{

//        @Override
//        public String getName(File f) {
//            return super.getName(f);
//        }

//        @Override
//        public String getDescription(File f) {
//            return super.getDescription(f);
//        }
        //A human readable description of the type of the file.
        // highlight: not sure where to use this method
//        @Override
//        public String getTypeDescription(File f) {
//            String type = null;
//            String exten = Utility.getFileExtension(f);
//            if (exten != null) {
//                for (int i = 0; i <exts.length ; i++) {
//                    if(exten.equals(exts[i])) {
//                        type = exts[i] + "image";
//                        break;
//                    }
//                }
//            }
//            return type;
//        }

        @Override
        public Icon getIcon(File f) {
            String exten = Utility.getFileExtension(f);
            Icon icon = null;
            if(exten != null) {
                for (int i = 0; i <exts.length ; i++) {
                    if(exten.equals(exts[i]))  {
                        icon = Utility.createImageIcon(this,"images/"+exts[i]+"Icon.gif");
                        break;
                    }
                }
            }
            return icon;
        }
//
//        @Override
//        public Boolean isTraversable(File f) {
//            return super.isTraversable(f);
//        }
    }
     //////*****************
    private class ImagePreviewPane extends JComponent implements PropertyChangeListener {

        ImageIcon thumbnail;
//        int x;
//        int y;
        File file;

        public ImagePreviewPane(JFileChooser fc) {
//            x = getWidth()/2;
//            y = getHeight()/2;
           // System.out.println(this.isShowing()+ ", the Jcomponent is showing - within constructor");
            setPreferredSize(new Dimension(200, 50)); // change the dimension to see the filechooser size changed
           // setBackground(Color.WHITE);
            fc.addPropertyChangeListener(this);
        }
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          //  System.out.println(this.isShowing()+ ", the Jcomponent is showing - property change event");
            System.out.println("property change event listening....");
            System.out.println(evt.getPropertyName());
            /**
             * when first click "Attach" button in JPanel
             * AccessoryChangedProperty;
             * ApproveButtonTextChangedProperty;
             * DialogTypeChangedProperty;
             * AccessibleDescription;
             * graphicsConfiguration;
             * ancestor;
             *
             * when click a file
             * SelectedFileChangedProperty;
             * when click Open
             * directoryChanged;
             * when further select a file
             * SelectedFileChangedProperty;
             * when open this file again
             * directoryChanged;
             * whenever look through and highlight a file
             * SelectedFileChangedProperty;
             * when narrow down to the file and clikc "attach"
             *   JFileChooserDialogIsClosingProperty;
             *   ancestor;
             *   graphicsConfiguration;
             *
             * when click the ATTACH button and go back to fileChooser screen
             *    directoryChanged;
             *    ChoosableFileFilterChangedProperty;
             *    fileViewChanged;
             *    AccessoryChangedProperty;
             *    AccessoryChangedProperty;
             *    graphicsConfiguration;
             *    graphicsConfiguration;
             *    ancestor;
             *    ancestor;
             */

            String propName = evt.getPropertyName();
            if(propName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                System.out.println("Inside selectedFileChangedProperty ---- ");
               // System.out.println(this.isShowing()+ ", the Jcomponent is showing - within selected file change property");
                file = (File) evt.getNewValue();
                System.out.println("file is null ? " + (file == null));
                if(file == null) {
                    return;
                }
                ImageIcon icon = new ImageIcon(file.getPath());
                System.out.println("icon is  " + icon + " for file " + file);
                System.out.println("icon is null ? " + (icon==null));
                if(icon != null){
                    System.out.println("since icon != null , so I am here to trim the icon");
                    if(icon.getIconWidth()>90) {
                        thumbnail = new ImageIcon(icon.getImage().getScaledInstance(90,-1,Image.SCALE_DEFAULT));
                    }
                    else {
                        thumbnail = icon;
                    }

                    System.out.println(thumbnail.getIconWidth()+ " is the thumbnail width");
                    this.repaint();  // paintComponent only happen once from starting the class, for more painting, repaint needs to be called
                }
            }
            if (propName.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
                thumbnail = null;
                file = null;
                this.repaint();
            }
        }


        @Override
        protected void paintComponent(Graphics g) {
            System.out.println("in paintComponent ....");
            System.out.println(this.isShowing()+ ", the Jcomponent is showing - within PaintComponent");
            // isShowing() only turn true within paintComponent() method
          //  super.paintComponent(g);
            // center the icon
            if(thumbnail != null) {
               System.out.println(this.isShowing()+ ", the Jcomponent is showing - within deep level of thumbnail is not null");
                int  x = getWidth()/2 - thumbnail.getIconWidth()/2;
                System.out.println("x " + x);

                int y = getWidth()/2 - thumbnail.getIconHeight()/2;
                System.out.println("y " + y);
                if(x<5) x=5;
                if(y<0) y=0;

                thumbnail.paintIcon(this,g,x,y);    // this is fileChooser
            }

        }
    }
}
