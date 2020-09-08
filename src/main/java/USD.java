import java.io.Serializable;

/**
 * Do the currency conversion from Dollars (USD) to Shekels (ILS)
 */
public class USD extends Coin implements Serializable {
    private final double rateValue = 3.52; // Rate value to shekels

    @Override
    public double getValue() {
        return rateValue;
    }

    @Override
    public double calculate(double amount) {
        return super.calculate(amount);
    }
}
