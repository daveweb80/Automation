public class CoinFactory {

    public static Coin getCoinInstance(Coins coinType) throws Exception {
        switch (coinType) {
            case USD:
                return new USD();
            case ILS:
                return new ILS();
            default:
                throw new Exception("No such currency");
        }
    }
}
