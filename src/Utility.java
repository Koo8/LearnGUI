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


   // Mortgage loan calculator
    public static double getPaymentAmount(double loan, double rate, int year) {
        // M = P [ i(1 + i)^n ] / [ (1 + i)^n – 1] ---> i = rate /100/12;
        System.out.println("start calculating in getPaymentAmount...");
        System.out.println("rate is " + rate); // rate is 7.5 not 0.075
        double i = rate/100/12.0;
        double a =  Math.pow((1 + i), year * 12);//(1 + i)^n
        double upperPart = loan * (i* a) ;
        double lowerPart = a -1;
        double payment = -(upperPart/lowerPart); // output payment as a negative number

        return payment;
    }
}
