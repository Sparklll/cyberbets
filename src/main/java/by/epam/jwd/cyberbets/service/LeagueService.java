package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.League;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.dto.LeagueDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface LeagueService {
    List<League> findAll() throws ServiceException;
    Optional<League> findLeagueById(int leagueId) throws ServiceException;
    Optional<League> findLeagueByName(String leagueName) throws ServiceException;
    Optional<Resource> findIconResourceByLeagueId(int leagueId) throws ServiceException;
    int createLeague(LeagueDto leagueDto) throws ServiceException;
    void updateLeague(LeagueDto leagueDto) throws ServiceException;
    void deleteLeague(int leagueId) throws ServiceException;
}
