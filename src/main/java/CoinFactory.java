/**
 * Creates and returns an object for currency calculation.
 */
public class CoinFactory {

    /**
     * Creates and returns an object for currency calculation according to a specified coinType.
      * @param coinType can be one of enum Coins
     * @return object for that conversion (calculation)
     * @throws Exception. In case the coinType is unknown.
     */
    public static Coin getCoinInstance(Coins coinType) throws Exception {
        switch (coinType) {
            case USD:
                return new USD();
            case ILS:
                return new ILS();
            case ILS_EUR:
                return new ILS(coinType);
            default:
                throw new Exception("No such currency");
        }
    }
}
