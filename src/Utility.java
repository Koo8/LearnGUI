import javax.swing.*;
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
}
