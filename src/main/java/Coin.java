import Inters.ICalculate;

/**
 * Defines functions Coin (currency) must apply.
 */
public abstract class Coin implements ICalculate {

    public abstract double getValue();

    public double calculate(double amount) {
        return amount * getValue();
    }
}
