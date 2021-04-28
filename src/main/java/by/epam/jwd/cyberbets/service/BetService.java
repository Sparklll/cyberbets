package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.dto.BetDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BetService {
    List<Bet> findAllBetsByAccountId(int accountId) throws ServiceException;
    List<Bet> findAllBetsByEventId(int eventId) throws ServiceException;
    List<Bet> findAllBetsByAccountIdAndEventId(int accountId, int eventId) throws ServiceException;
    List<Bet> findAllBetsByEventResultId(int eventResultId) throws ServiceException;
    Optional<Bet> findBetById(int betId) throws ServiceException;
    BigDecimal getTotalAmountOfBets(int eventResultId) throws ServiceException;
    BigDecimal getTotalAmountOfBetsForUpshot(int eventResultId, Bet.Upshot upshot) throws ServiceException;
    BigDecimal getTotalProfitByPeriod(int daysNumber) throws ServiceException;
    BigDecimal getTotalAmountOfBetsOnActiveEvents() throws ServiceException;
    void placeBet(BetDto betDto) throws ServiceException;
    void updateBet(BetDto betDto) throws ServiceException;
    void deleteBet(BetDto betDto) throws ServiceException;
}
