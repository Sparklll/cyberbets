package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.dto.BetDto;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BetDaoImpl implements BetDao {
    private final Connection transactionConnection;

    BetDaoImpl() {
        transactionConnection = null;
    }

    public BetDaoImpl(Connection transactionConnection) {
        this.transactionConnection = transactionConnection;
    }

    @Override
    public List<Bet> findBetsByAccountId(int accountId) {
        return null;
    }

    @Override
    public Optional<Bet> findBetById(int betId) {
        return Optional.empty();
    }

    @Override
    public void createBet(BetDto betDto) {

    }

    @Override
    public void updateBet(Bet bet) {

    }

    @Override
    public void deleteBet(int betId) {

    }
}
