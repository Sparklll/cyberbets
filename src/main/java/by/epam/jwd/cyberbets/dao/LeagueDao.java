package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.League;
import by.epam.jwd.cyberbets.domain.Resource;

import java.util.List;
import java.util.Optional;

public interface LeagueDao {
    List<League> findAll() throws DaoException;
    Optional<League> findLeagueById(int leagueId) throws DaoException;
    Optional<League> findLeagueByName(String leagueName) throws DaoException;
    Optional<Resource> findIconResourceByLeagueId(int leagueId) throws DaoException;
    int createLeague(League league) throws DaoException;
    void updateLeague(League league) throws DaoException;
    void deleteLeague(int leagueId) throws DaoException;
}
