package LengthConverter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * this class mimic DefaultBoundedRangeModel.java, but change value from int to double
 */

public class My_ConverterRangeModel implements BoundedRangeModel {
    /**
     * Only one <code>ChangeEvent</code> is needed per model instance since the
     * event's only (read-only) state is the source property.  The source
     * of events generated
     * here is always "this".
     */
    protected transient ChangeEvent changeEvent = null;

    /** The listeners waiting for model changes. */
    protected EventListenerList listenerList = new EventListenerList();

    private double multiplier=1.0;

    private double value = 0.0;
    private int extent = 0;
    private int min = 0;
    private int max = 10000;
    private boolean isAdjusting = false;
    public My_ConverterRangeModel (){

    }
    @Override
    public int getMinimum() {
        return(int) min;
    }

    @Override
    public void setMinimum(int newValue){
        System.out.println("in set Minimum " );
        // do nothing
    }

    @Override
    public int getMaximum() {
        return max;
    }

    @Override
    public void setMaximum(int newMaximum) {
        setRangeProperties(value,extent,min, newMaximum, isAdjusting);
    }
    @Override
    public int getValue() {
        return (int) getDoubleValue();
    }

    @Override
    public void setValue(int newValue) {
        setDoubleValue((double)newValue);
    }

    @Override
    public void setValueIsAdjusting(boolean b) {
        setRangeProperties(value,extent,min,max,b);
    }

    @Override
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }

    @Override
    public int getExtent() {
        return (int)extent;
    }

    @Override
    public void setExtent(int newExtent) {
        // do nothing
    }

    @Override
    public void setRangeProperties(int value, int extent, int min, int max, boolean adjusting) {
        setRangeProperties((double)value, extent, min, max, adjusting);
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
        fireStateChanged();
    }

    public double getMultiplier(){
        return multiplier;
    }

    public double getDoubleValue() {
        return value;
    }

    public void setDoubleValue(double newValue) {
        setRangeProperties(newValue,extent,min, max,isAdjusting);
    }

    public void setRangeProperties(double newValue, int unusedExtent, int unusedMin, int newMax, boolean adjusting) {
        if (newMax <= min) {
            newMax = min + 1;
        }
        if (Math.round(newValue) > newMax) { //allow some rounding error
            newValue = newMax;
        }

        boolean changeOccurred = false;
        if (newValue != value) {
            value = newValue;
            changeOccurred = true;
        }
        if (newMax != max) {
            max = newMax;
            changeOccurred = true;
        }
        // adjusting is not used
//        if (adjusting != isAdjusting) {
//            max = newMax;
//            isAdjusting = adjusting;
//            changeOccurred = true;
//        }

        if (changeOccurred) {
            fireStateChanged();
        }
    }
   // don't need this method
  //  public ChangeListener[] getChangeListeners() {
   //     return listenerList.getListeners(ChangeListener.class);
  //  }

    protected void fireStateChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -=2 ) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

}
