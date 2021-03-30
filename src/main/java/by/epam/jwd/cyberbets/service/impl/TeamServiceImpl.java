package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.DaoProvider;
import by.epam.jwd.cyberbets.dao.ResourceDao;
import by.epam.jwd.cyberbets.dao.TeamDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import by.epam.jwd.cyberbets.service.TeamService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.utils.ResourceSaver;
import by.epam.jwd.cyberbets.utils.exception.UtilException;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private final TeamDao teamDao = DaoProvider.INSTANCE.getTeamDao();
    private final ResourceDao resourceDao = DaoProvider.INSTANCE.getResourceDao();

    @Override
    public List<Team> findAll() throws ServiceException {
        try {
            return teamDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Team> findTeamById(int teamId) throws ServiceException {
        try {
            return teamDao.findTeamById(teamId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Team> findTeamByName(String name) throws ServiceException {
        try {
            return teamDao.findTeamByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void createTeam(TeamDto teamDto) throws ServiceException {
        try {
            String teamName = teamDto.teamName();
            int teamRating = Integer.parseInt(teamDto.teamRating());
            Discipline discipline = Discipline.getDisciplineById(Integer.parseInt(teamDto.disciplineId())).get();
            String teamLogoDataUrl = teamDto.teamLogo();

            String resourcePath = ResourceSaver.uploadImage(ResourceSaver.Resource.TEAM_LOGO, teamLogoDataUrl);
            int resourceId = resourceDao.createResource(resourcePath);

            Team team = new Team(
                    teamName,
                    teamRating,
                    discipline,
                    new Resource(resourceId, resourcePath)
            );
            teamDao.createTeam(team);
        } catch (DaoException | UtilException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTeam(Team team) throws ServiceException {
        try {
            teamDao.updateTeam(team);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteTeam(int teamId) throws ServiceException {
        try {
            teamDao.deleteTeam(teamId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
