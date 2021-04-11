package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.LeagueDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.League;
import by.epam.jwd.cyberbets.domain.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;
import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.PATH;

public class LeagueDaoImpl implements LeagueDao {
    private static final String FIND_ALL_LEAGUES = """
            select l.id, l.name, l.discipline_id, l.icon_resource_id, r.path
            from league l
            inner join discipline d on d.id = l.discipline_id
            inner join resource r on r.id = l.icon_resource_id
            """;
    private static final String FIND_LEAGUE_BY_ID = """
            select l.id, l.name, l.discipline_id, l.icon_resource_id, r.path
            from league l
            inner join discipline d on d.id = l.discipline_id
            inner join resource r on r.id = l.icon_resource_id
            where l.id = ?
            """;
    private static final String FIND_LEAGUE_BY_NAME = """
            select l.id, l.name, l.discipline_id, l.icon_resource_id, r.path
            from league l
            inner join discipline d on d.id = l.discipline_id
            inner join resource r on r.id = l.icon_resource_id
            where  l.name = ?
            """;
    private static final String FIND_ICON_RESOURCE_BY_LEAGUE_ID = """
            select r.id, r.path
            from league l
            inner join resource r on r.id = l.icon_resource_id
            where l.id = ?
            """;
    private static final String CREATE_LEAGUE = "insert into league (name, discipline_id, icon_resource_id) values (?, ?, ?) returning id";
    private static final String UPDATE_LEAGUE = "update league set name = ?, discipline_id = ?, icon_resource_id = ? where id = ?";
    private static final String DELETE_LEAGUE = "delete from league where id = ?";

    LeagueDaoImpl() {

    }

    @Override
    public List<League> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_LEAGUES)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<League> leagues = new ArrayList<>();
                while (rs.next()) {
                    League league = mapRow(rs);
                    leagues.add(league);
                }
                return leagues;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<League> findLeagueById(int leagueId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_LEAGUE_BY_ID)) {
            ps.setInt(1, leagueId);
            return getLeague(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<League> findLeagueByName(String leagueName) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_LEAGUE_BY_NAME)) {
            ps.setString(1, leagueName);
            return getLeague(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Optional<League> getLeague(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            Optional<League> leagueOptional = Optional.empty();
            if (rs.next()) {
                League league = mapRow(rs);
                leagueOptional = Optional.of(league);
            }
            return leagueOptional;
        }
    }

    @Override
    public Optional<Resource> findIconResourceByLeagueId(int leagueId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ICON_RESOURCE_BY_LEAGUE_ID)) {
            ps.setInt(1, leagueId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Resource> resourceOptional = Optional.empty();
                if (rs.next()) {
                    Resource resource = new Resource(rs.getInt(ID), rs.getString(PATH));
                    resourceOptional = Optional.of(resource);
                }
                return resourceOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int createLeague(League league) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_LEAGUE)) {
            ps.setString(1, league.getName());
            ps.setInt(2, league.getDiscipline().getId());
            ps.setInt(3, league.getIconResource().getId());
            ps.execute();
            try (ResultSet rs = ps.getResultSet()) {
                rs.next();
                return rs.getInt(ID);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateLeague(League league) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_LEAGUE)) {
            ps.setString(1, league.getName());
            ps.setInt(2, league.getDiscipline().getId());
            ps.setInt(3, league.getIconResource().getId());
            ps.setInt(4, league.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteLeague(int leagueId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_LEAGUE)) {
            ps.setInt(1, leagueId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private League mapRow(ResultSet rs) throws SQLException {
        return new League(
                rs.getInt(ID),
                rs.getString(NAME),
                Discipline.getDisciplineById(rs.getInt(DISCIPLINE_ID)).get(),
                new Resource(rs.getInt(ICON_RESOURCE_ID), rs.getString(PATH))
        );
    }
}
