package parts;

public class CoinValidator {
    private double[] acceptedCoins = {0.50, 1.00, 2.00};

    public boolean validateCoin(double coin) {
        for (double acceptedCoin : acceptedCoins) {
            if (Math.abs(coin - acceptedCoin) < 0.01) {
                return true;
            }
        }
        return false;
    }

    public double calculateTotal(double[] coins) {
        double total = 0.0;
        for (double coin : coins) {
            if (validateCoin(coin)) {
                total += coin;
            }
        }
        return total;
    }
}
