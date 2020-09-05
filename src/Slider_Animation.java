import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Hashtable;

/**
 * Use Slider to control the speed of the animation, Slider
 * has LabelTable, Timer has initialDelay
 */

public class Slider_Animation extends JPanel implements ActionListener, ChangeListener, WindowListener {

    JLabel pictureLabel;

    static final int FPS_MIN = 0;
    static final int FPS_MAX = 30;
    static final int FPS_INIT = 15;

    private static final int FRAME_NUMBER = 14;
    int frameIndex = 0;
    ImageIcon[] images = new ImageIcon[FRAME_NUMBER];
    static  Timer timer;
    int delay;
    boolean frozen;


    Slider_Animation() {
        setLayout(new BorderLayout());
        JSlider slider = new JSlider(JSlider.VERTICAL,FPS_MIN, FPS_MAX, FPS_INIT);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);  // to paint the ticks
        // make a labelTable for the slider labels
         /* The hashtable key must be of an Integer type and must
        have a value within the slider's range at which to place the label.
        The hashtable value associated with each key must be a Component object.
        This demo uses JLabel instances with text only. An interesting
        modification would be to use JLabel instances with icons or buttons
        that move the knob to the label's position.*/

        /*Use the createStandardLabels method of the JSlider class to create a
        set of numeric labels positioned at a specific interval. You can also
        modify the table returned by the createStandardLabels method in order to
        customize it.*/
        Hashtable<Integer,JLabel> table = new Hashtable<>();
        table.put(0,new JLabel("Stop"));
        table.put(FPS_MAX/10, new JLabel("Slow"));
        table.put(FPS_MAX,new JLabel("Fast"));
        slider.setLabelTable(table);
        slider.setPaintLabels(true);  // for painting the number label beside the major tick marks
                                      // you can also customize your label with a labelTable(HashTable) - see above

        slider.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));

        // add changeListener
        slider.addChangeListener(this);

        // picture JLabel
        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pictureLabel.setAlignmentX(CENTER_ALIGNMENT);
        pictureLabel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLoweredBevelBorder(),
                        BorderFactory.createEmptyBorder(10,10,10,10)
                ));
        updatePicture(0);

        // put everything together
        add(slider,BorderLayout.LINE_START);
        add(pictureLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // a timer to run the animationo
        delay = 1000/FPS_INIT; // each frame interval
        timer = new Timer(delay, this);
        // setInitialDelay will occur every time timer restart() too
        timer.setInitialDelay(delay*10);
        timer.setCoalesce(true); // cancel event if too many piled up
        // timer is initially invoked in GUI
    }

    /** Add a listener for window events. */
    /*TODO: not quite understand why windowlistener is added this way */
    
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    //React to window events.
    public void windowIconified(WindowEvent event) {
        stopAnimation();
    }
    public void windowDeiconified(WindowEvent e) {
        startAnimation();
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
     // update image according to the frameIndex
    private void updatePicture(int index) {
        if(images[index] == null) {
            images[index] = Utility.createImageIcon(this,
                    "images/doggy/T" + index + ".gif");
        }
        // set image to pictureLabel
        if(images[index] != null) {
            pictureLabel.setIcon(images[index]);
        } else {
            pictureLabel.setText("picture #"+ index + " not found");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // increment frame index, so that animation is created
        // called when timer fires
        if(frameIndex ==(FRAME_NUMBER-1)) { // the last frame
            frameIndex = 0;
        } else {
            frameIndex++;
        }
        updatePicture(frameIndex);
        // restart timer twice to have continuous animation and have a gap effect at the beginning and the middle
        if(frameIndex == (FRAME_NUMBER-1) || frameIndex ==(FRAME_NUMBER/2 -1)) {
            timer.restart();
        }


    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        // only when isAdjusting is done, update the fps according to the getValue()
        if(!source.getValueIsAdjusting()) {
            int fps = source.getValue();
            // when fps == 0
            if(fps == 0 ){
                //stop animation if it is not
                if(!frozen)  {
                    stopAnimation();
                }
            } else {
                delay = 1000/fps; // change each frame interval
                timer.setDelay(delay);
                timer.setInitialDelay(delay*10);
                // if it is from fps==0, and in stopAnimation condition
                if(frozen) startAnimation();
            }
        }


    }

    private void startAnimation() {
        timer.start();
        frozen = false;
    }

    private void stopAnimation() {
        timer.stop();
        frozen = true;
    }

    public static void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Slider_Animation pane = new Slider_Animation();
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);

        timer.start();
    }

    public static void main(String[] args) {
        Utility.turnOffTheBoldFont();
        SwingUtilities.invokeLater(Slider_Animation::GUI);
    }

}
