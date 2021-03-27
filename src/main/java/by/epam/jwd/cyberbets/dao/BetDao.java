package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.dto.BetDto;

import java.util.List;
import java.util.Optional;

public interface BetDao {
    List<Bet> findBetsByAccountId(int accountId);
    Optional<Bet> findBetById(int betId);
    void createBet(BetDto betDto);
    void updateBet(Bet bet);
    void deleteBet(int betId);
}
