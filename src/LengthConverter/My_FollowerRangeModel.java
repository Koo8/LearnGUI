package LengthConverter;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Implements a model whose data is actually in another model (the
 * "source model").  The follower model adjusts the values obtained
 * from the source model (or set in the follower model) to be in
 * a different unit of measure.
 *
 */

public class My_FollowerRangeModel extends My_ConverterRangeModel implements ChangeListener {
    // all data will come from My_ConverterRangeModel
    My_ConverterRangeModel leadModel ;
  //  public static double factor;

    //constructor
    public My_FollowerRangeModel(My_ConverterRangeModel leadModel) {
        this.leadModel = leadModel;
        leadModel.addChangeListener(this);
        //factor = leadModel.getMultiplier() /this.getMultiplier();
    }

    //need to grab the leadmodel currentValue, multiply by the factor, apply the
    // result to followModel as currentValue  - getValue() is not changed, getDoubleValue()
    // getMaximum is changed
    public int getMaximum() {
        int leadMax = leadModel.getMaximum();
        int followMax = (int) (leadMax*(leadModel.getMultiplier()/this.getMultiplier()));
        return followMax;
    }
    public void setMaximum(int newMax) {
        leadModel.setMaximum((int) (newMax * (1/(leadModel.getMultiplier()/this.getMultiplier()))));
    }
    // get double value from lead model
    public double getDoubleValue() {
        return leadModel.getDoubleValue()*(leadModel.getMultiplier()/this.getMultiplier());
    }
    // when this.value is known, set the lead model value
    public void setDoubleValue(double newValue) {
        leadModel.setDoubleValue(newValue * (1/(leadModel.getMultiplier()/this.getMultiplier())));
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        fireStateChanged();
    }
}
