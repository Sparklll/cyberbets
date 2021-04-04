package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.LeagueDao;
import by.epam.jwd.cyberbets.dao.ResourceDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.League;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.dto.LeagueDto;
import by.epam.jwd.cyberbets.service.LeagueService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.utils.ResourceManager;
import by.epam.jwd.cyberbets.utils.ResourceManager.ResourceType;
import by.epam.jwd.cyberbets.utils.exception.UtilException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class LeagueServiceImpl implements LeagueService {
    private final LeagueDao leagueDao = DaoProvider.INSTANCE.getLeagueDao();
    private final ResourceDao resourceDao = DaoProvider.INSTANCE.getResourceDao();

    LeagueServiceImpl() {

    }

    @Override
    public List<League> findAll() throws ServiceException {
        try {
            return leagueDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<League> findLeagueById(int leagueId) throws ServiceException {
        try {
            return leagueDao.findLeagueById(leagueId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<League> findLeagueByName(String name) throws ServiceException {
        try {
            return leagueDao.findLeagueByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Resource> findIconResourceByLeagueId(int leagueId) throws ServiceException {
        try {
            return leagueDao.findIconResourceByLeagueId(leagueId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int createLeague(LeagueDto leagueDto) throws ServiceException {
        try {
            String name = leagueDto.leagueName();
            String leagueIconDataUrl = leagueDto.leagueIcon();
            Discipline discipline = Discipline.getDisciplineById(leagueDto.disciplineId()).get();

            String resourcePath = ResourceManager.uploadImage(ResourceType.LEAGUE_ICON, leagueIconDataUrl);
            int resourceId = resourceDao.createResource(resourcePath);

            League league = new League(
                    name,
                    discipline,
                    new Resource(resourceId, resourcePath)
            );
            return leagueDao.createLeague(league);
        } catch (DaoException | UtilException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateLeague(LeagueDto leagueDto) throws ServiceException {
        try {
            int id = leagueDto.id();
            String name = leagueDto.leagueName();
            Discipline discipline = Discipline.getDisciplineById(leagueDto.disciplineId()).get();
            String leagueIconDataUrl = leagueDto.leagueIcon();
            Resource leagueIconResource = leagueDao.findIconResourceByLeagueId(id).get();

            if (StringUtils.isNotBlank(leagueIconDataUrl)) {
                ResourceManager.updateImage(ResourceType.LEAGUE_ICON, leagueIconResource, leagueIconDataUrl);
                resourceDao.updateResource(leagueIconResource);
            }

            League league = new League(id, name, discipline, leagueIconResource);
            leagueDao.updateLeague(league);
        } catch (DaoException | UtilException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteLeague(int leagueId) throws ServiceException {
        try {
            leagueDao.deleteLeague(leagueId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
