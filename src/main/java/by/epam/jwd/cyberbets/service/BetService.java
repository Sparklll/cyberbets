package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.dto.BetDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BetService {
    List<Bet> findAllBetsByAccountId(int accountId) throws ServiceException;
    List<Bet> findAllBetsByEventId(int eventId) throws ServiceException;
    List<Bet> findAllBetsByAccountIdAndEventId(int accountId, int eventId) throws ServiceException;
    List<Bet> findAllBetsByEventResultId(int eventResultId) throws ServiceException;
    Optional<Bet> findBetById(int betId) throws ServiceException;
    int createBet(BetDto betDto) throws ServiceException;
    void updateBet(Bet bet) throws ServiceException;
    void deleteBet(int betId) throws ServiceException;
}
