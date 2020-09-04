import java.io.Serializable;

public class ILS extends Coin implements Serializable {

    private final double rateValue = 0.28; // Rate value to Dollars

    @Override
    public double getValue() {
        return rateValue;
    }

    @Override
    public double calculate(double amount) {
        return super.calculate(amount);
    }
}
