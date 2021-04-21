package by.epam.jwd.cyberbets.domain.dto;

import java.math.BigDecimal;

public record BetDto(int accountId, int eventResultId, int upshotId, BigDecimal amount) {
    public BetDto(int accountId, int eventResultId) {
        this(accountId, eventResultId, 0, null);
    }
}
