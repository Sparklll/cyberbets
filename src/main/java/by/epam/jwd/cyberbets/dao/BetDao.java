package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.Bet.Upshot;
import by.epam.jwd.cyberbets.domain.dto.BetDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BetDao {
    List<Bet> findAllBetsByAccountId(int accountId) throws DaoException;
    List<Bet> findAllBetsByEventId(int eventId) throws DaoException;
    List<Bet> findAllBetsByEventResultId(int eventResultId) throws DaoException;
    Optional<Bet> findBetById(int betId) throws DaoException;
    BigDecimal getTotalAmountOfBets(int eventResultId, Upshot upshot) throws DaoException;
    int createBet(BetDto betDto) throws DaoException;
    void updateBet(Bet bet) throws DaoException;
    void deleteBet(int betId) throws DaoException;
}
