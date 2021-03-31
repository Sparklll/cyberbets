package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> findAll() throws ServiceException;
    Optional<Team> findTeamById(int teamId) throws ServiceException;
    Optional<Team> findTeamByName(String name) throws ServiceException;
    Optional<Resource> findLogoResourceByTeamId(int teamId) throws ServiceException;
    int createTeam(TeamDto teamDto) throws ServiceException;
    void updateTeam(TeamDto team) throws ServiceException;
    void deleteTeam(int teamId) throws ServiceException;
}
