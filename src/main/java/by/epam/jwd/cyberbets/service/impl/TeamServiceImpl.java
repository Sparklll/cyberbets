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
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

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
            Discipline discipline = Discipline.getDisciplineById(parseInt(teamDto.disciplineId())).get();
            String teamLogoDataUrl = teamDto.teamLogo();

            String resourcePath = ResourceSaver.uploadImage(ResourceSaver.Resource.TEAM_LOGO, teamLogoDataUrl);
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
            Discipline discipline = Discipline.getDisciplineById(
                    parseInt(teamDto.disciplineId()
                    )).get();
            String teamLogo = teamDto.teamLogo();
            Resource prevLogoResource = teamDao.findLogoResourceByTeamId(id).get();

            if(StringUtils.isNotBlank(teamLogo)) {
                String prevLogoResourcePath = prevLogoResource.getPath();
                ResourceSaver.updateImage(prevLogoResourcePath, teamLogo);
            }

            Team team = new Team(id, name, rating, discipline, prevLogoResource);
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
