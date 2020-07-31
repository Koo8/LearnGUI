import javax.swing.*;
import java.io.File;
import java.net.URL;

public class Utility {
    public static ImageIcon createImageIcon(Object obj, String path) {
        URL imgURL = obj.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    public static String getFileExtension(File f) {
        String ext = null;
        String name = f.getName();
        int index = name.lastIndexOf('.');
        if(index > 0 && index <name.length()-1 /* not the last char */) {
            ext = name.substring(index+1).toLowerCase();
        }
        return ext;
    }
}
