package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.dto.BetDto;

import java.math.BigDecimal;

public class BetValidator implements Validator<BetDto> {
    private static final BigDecimal MAX_BET_AMOUNT = new BigDecimal(1000000);

    @Override
    public boolean isValid(BetDto betDto) {
        return isAmountValid(betDto.amount());
    }

    private boolean isAmountValid(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0
                && amount.compareTo(MAX_BET_AMOUNT) <= 0;
    }
}
