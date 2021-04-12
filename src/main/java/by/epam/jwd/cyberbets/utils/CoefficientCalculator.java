package by.epam.jwd.cyberbets.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CoefficientCalculator {
    private CoefficientCalculator() {
    }

    public static BigDecimal calculateOdds(BigDecimal totalBetsAmount, BigDecimal upshotBetsAmount, BigDecimal royaltyPercent) {
        if (totalBetsAmount.compareTo(BigDecimal.ZERO) == 0
                || upshotBetsAmount.compareTo(totalBetsAmount) == 0) {
            return BigDecimal.ONE;
        } else {
            BigDecimal upshotPercent = calculateUpshotPercent(totalBetsAmount, upshotBetsAmount);
            BigDecimal halfRoyalty = royaltyPercent.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
            BigDecimal totalChance = upshotPercent.add(halfRoyalty);

            return new BigDecimal(100).divide(totalChance, 2, RoundingMode.HALF_DOWN);
        }
    }

    public static BigDecimal calculateUpshotPercent(BigDecimal totalBetsAmount, BigDecimal upshotBetsAmount) {
        if (totalBetsAmount.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal(0);
        } else {
            return upshotBetsAmount.divide(totalBetsAmount, 2, RoundingMode.HALF_UP).scaleByPowerOfTen(2);
        }
    }
}
