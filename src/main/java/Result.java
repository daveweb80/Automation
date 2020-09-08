import java.io.Serializable;

/**
 * A bean object to store calculated amount of the currency conversion process.
 */
public class Result implements Serializable {

    private double calculatedAmount;
    private String conversionDescription;

    public Result(double calculatedAmount, String conversionDescription)
    {
        this.calculatedAmount = calculatedAmount;
        this.conversionDescription = conversionDescription;
    }

    public double getCalculatedAmount ()
    {
        return this.calculatedAmount;
    }

    public void setCalculatedAmount (double calculatedAmount)
    {
        this.calculatedAmount = calculatedAmount;
    }

    public String getConversionDescription ()
    {
        return conversionDescription;
    }

    public void setConversionDescription (String conversionDescription)
    {
        this.conversionDescription = conversionDescription;
    }
}
