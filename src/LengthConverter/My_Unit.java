package LengthConverter;

public class My_Unit {

    String description;
    double multiplier;  // multiplier = description/meter
    public My_Unit (String description, double multiplier) {
        this.description = description;
        this.multiplier = multiplier;
    }
    // this shows how the multiplier is calculated
    // all measure unit is comparig with meter
    @Override
    public String toString() {
        return description;  // this is used by combobox initializer
    }
}
