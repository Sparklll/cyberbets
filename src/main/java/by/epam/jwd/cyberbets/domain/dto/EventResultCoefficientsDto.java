package by.epam.jwd.cyberbets.domain.dto;

import java.math.BigDecimal;

public record EventResultCoefficientsDto(BigDecimal firstUpshotOdds, BigDecimal firstUpshotPercent,
                                         BigDecimal secondUpshotOdds, BigDecimal secondUpshotPercent) {
}
