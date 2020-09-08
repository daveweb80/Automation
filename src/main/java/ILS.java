import java.io.Serializable;

/**
 * Do the currency conversion from Shekels (ILS) to Dollars (USD)
 */
public class ILS extends Coin implements Serializable {

    private final double rateValue; // Rate value to Dollars

    public ILS() {
        rateValue = 0.28;
    }
    public ILS (Coins coin) throws Exception {
             if (coin.equals(Coins.ILS_EUR))
                rateValue = 4.23;
             else
                 throw new Exception("No conversion found for currency: " +  coin);
    }

    @Override
    public double getValue() {
        return rateValue;
    }

    @Override
    public double calculate(double amount) {
        return super.calculate(amount);
    }
}
