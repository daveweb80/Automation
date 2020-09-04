import java.io.Serializable;

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
