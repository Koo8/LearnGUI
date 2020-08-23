package LengthConverter;

import javax.swing.*;
import java.awt.*;

public class MyConverter {

    // FIELDS
    My_ConversionPanel metricPanel, imperialPanel;
    My_ConverterRangeModel leadModel;
    My_FollowerRangeModel followModel;

    My_Unit[] metricUnits = {
            new My_Unit("Centimeters", 0.01),
            new My_Unit("Meter", 1.0),
            new My_Unit("Kilometers", 1000.01)};
    My_Unit[] imperialUnits = {
            new My_Unit("Inches", 0.0254),
            new My_Unit("Feet", 0.305),
            new My_Unit("Yards", 0.914),
            new My_Unit("Miles", 1613.0)};

    JPanel mainPanel;
    // CONSTRCTOR
    MyConverter() {
        leadModel = new My_ConverterRangeModel();
        followModel = new My_FollowerRangeModel(leadModel);
        // instantiate two panels
        metricPanel = new My_ConversionPanel(this, leadModel, metricUnits,"Metric System");
        imperialPanel = new My_ConversionPanel(this, followModel, imperialUnits, "Imperial System");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        mainPanel.setOpaque(true);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(metricPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(imperialPanel);
        mainPanel.add(Box.createGlue());

        // set the max and current value
        resetMaxValueThroughTwoMultipliers(true);

    }


    public void resetMaxValueThroughTwoMultipliers(boolean resetCurrentToMaxAtStart) {
        // get two multipliers from two panes
        double metricMultiplier = metricPanel.getMultiplier();
        double imperialMultiplier = imperialPanel.getMultiplier();

        // calculate new max for metricPane (the lead pane) only
        // when metricMultiplier is bigger, set the imperialMultiplier as MAX(10000)
        int max = My_ConversionPanel.MAX;
        // when meet the following situation - change the max number, set it for theModel
        if (metricMultiplier > imperialMultiplier) {
            max = (int) (My_ConversionPanel.MAX * (1 / (leadModel.getMultiplier()/followModel.getMultiplier())));
        }
        // always setMaximum for theModel, followerModel will follow
        leadModel.setMaximum(max);

        if(resetCurrentToMaxAtStart) {
            // get theModel currentValue
            leadModel.setDoubleValue(max);
        }
    }

    public static void GUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyConverter converter = new MyConverter();
        frame.setContentPane(converter.mainPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyConverter::GUI);
    }

}
