package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.dto.BetDto;
import by.epam.jwd.cyberbets.service.BetService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class BetServiceImpl implements BetService {
    private final BetDao betDao = DaoProvider.INSTANCE.getBetDao();

    BetServiceImpl() {
    }

    @Override
    public List<Bet> findAllBetsByAccountId(int accountId) throws ServiceException {
        try {
            return betDao.findAllBetsByAccountId(accountId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByEventId(int eventId) throws ServiceException {
        try {
            return betDao.findAllBetsByEventId(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByAccountIdAndEventId(int accountId, int eventId) throws ServiceException {
        try {
            return betDao.findAllBetsByAccountIdAndEventId(accountId, eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByEventResultId(int eventResultId) throws ServiceException {
        try {
            return betDao.findAllBetsByEventResultId(eventResultId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Bet> findBetById(int betId) throws ServiceException {
        try {
            return betDao.findBetById(betId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int createBet(BetDto betDto) throws ServiceException {
        try {
            return betDao.createBet(betDto);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBet(Bet bet) throws ServiceException {
        try {
            betDao.updateBet(bet);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBet(int betId) throws ServiceException {
        try {
            betDao.deleteBet(betId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
