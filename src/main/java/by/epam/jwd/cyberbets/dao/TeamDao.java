package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamDao {
    List<Team> findAll() throws DaoException;
    Optional<Team> findTeamById(int teamId) throws DaoException;
    Optional<Team> findTeamByName(String name) throws DaoException;
    Optional<Resource> findLogoResourceByTeamId(int teamId) throws DaoException;
    int createTeam(Team team) throws DaoException;
    void updateTeam(Team team) throws DaoException;
    void deleteTeam(int teamId) throws DaoException;
}
