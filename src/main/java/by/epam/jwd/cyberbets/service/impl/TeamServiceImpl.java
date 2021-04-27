package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.dao.ResourceDao;
import by.epam.jwd.cyberbets.dao.TeamDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import by.epam.jwd.cyberbets.service.TeamService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.util.ResourceManager;
import by.epam.jwd.cyberbets.util.ResourceManager.ResourceType;
import by.epam.jwd.cyberbets.util.exception.UtilException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private final TeamDao teamDao = DaoProvider.INSTANCE.getTeamDao();
    private final ResourceDao resourceDao = DaoProvider.INSTANCE.getResourceDao();

    TeamServiceImpl() {

    }

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
    public Optional<Resource> findLogoResourceByTeamId(int teamId) throws ServiceException {
        try {
            return teamDao.findLogoResourceByTeamId(teamId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int createTeam(TeamDto teamDto) throws ServiceException {
        try {
            String teamName = teamDto.teamName();
            int teamRating = teamDto.teamRating();
            Discipline discipline = Discipline.getDisciplineById(teamDto.disciplineId()).get();
            String teamLogoDataUrl = teamDto.teamLogo();

            String resourcePath = ResourceManager.uploadImage(ResourceType.TEAM_LOGO, teamLogoDataUrl);
            int resourceId = resourceDao.createResource(resourcePath);

            Team team = new Team(
                    teamName,
                    teamRating,
                    discipline,
                    new Resource(resourceId, resourcePath)
            );
            return teamDao.createTeam(team);
        } catch (DaoException | UtilException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTeam(TeamDto teamDto) throws ServiceException {
        try {
            int id = teamDto.id();
            String name = teamDto.teamName();
            int rating = teamDto.teamRating();
            Discipline discipline = Discipline.getDisciplineById(teamDto.disciplineId()).get();
            String teamLogoDataUrl = teamDto.teamLogo();
            Resource teamLogoResource = teamDao.findLogoResourceByTeamId(id).get();

            if (StringUtils.isNotBlank(teamLogoDataUrl)) {
                ResourceManager.updateImage(ResourceType.TEAM_LOGO, teamLogoResource, teamLogoDataUrl);
                resourceDao.updateResource(teamLogoResource);
            }

            Team team = new Team(id, name, rating, discipline, teamLogoResource);
            teamDao.updateTeam(team);
        } catch (DaoException | UtilException e) {
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
