package by.epam.jwd.cyberbets.domain.dto;

import java.math.BigDecimal;

public record BetDto(int accountId, int eventResultId, BigDecimal amount) {
}
