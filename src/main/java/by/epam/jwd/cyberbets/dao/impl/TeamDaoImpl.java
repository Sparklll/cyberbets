package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.TeamDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class TeamDaoImpl implements TeamDao {
    private static final String FIND_ALL_TEAMS = """
            select t.id, t.name, t.rating, t.discipline_id, t.logo_resource_id, r.path
            from team t
            inner join discipline d on d.id = t.discipline_id
            inner join resource r on r.id = t.logo_resource_id
            """;
    private static final String FIND_TEAM_BY_ID = """
            select t.id, t.name, t.rating, t.discipline_id, t.logo_resource_id, r.path
            from team t
            inner join discipline d on d.id = t.discipline_id
            inner join resource r on r.id = t.logo_resource_id
            where t.id = ?
            """;
    private static final String FIND_TEAM_BY_NAME = """
            select t.id, t.name, t.rating, t.discipline_id, t.logo_resource_id, r.path
            from team t
            inner join discipline d on d.id = t.discipline_id
            inner join resource r on r.id = t.logo_resource_id
            where t.name = ?
            """;
    private static final String CREATE_TEAM = "insert into team (name, rating, discipline_id, logo_resource_id) values (?, ?, ?, ?)";
    private static final String UPDATE_TEAM = "update team set name = ?, rating = ?, discipline_id = ?, logo_resource_id = ? where id = ?";
    private static final String DELETE_TEAM = "delete from team where id = ?";


    @Override
    public List<Team> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_TEAMS)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Team> teams = new ArrayList<>();
                while (rs.next()) {
                    Team team = mapRow(rs);
                    teams.add(team);
                }
                return teams;
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Team> findTeamById(int teamId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_TEAM_BY_ID)) {
            ps.setInt(1, teamId);
            return getTeam(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Team> findTeamByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
         PreparedStatement ps = connection.prepareStatement(FIND_TEAM_BY_NAME)) {
            ps.setString(1, name);
            return getTeam(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Optional<Team> getTeam(PreparedStatement ps) throws DaoException {
        try (ResultSet rs = ps.executeQuery()) {
            Optional<Team> teamOptional = Optional.empty();
            while (rs.next()) {
                Team team = mapRow(rs);
                teamOptional = Optional.of(team);
            }
            return teamOptional;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void createTeam(Team team) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_TEAM)) {
            ps.setString(1, team.getName());
            ps.setInt(2, team.getRating());
            ps.setInt(3, team.getDiscipline().getId());
            ps.setInt(4, team.getLogoResource().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateTeam(Team team) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_TEAM)) {
            ps.setString(1, team.getName());
            ps.setInt(2, team.getRating());
            ps.setInt(3, team.getDiscipline().getId());
            ps.setInt(4, team.getLogoResource().getId());
            ps.setInt(5, team.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteTeam(int teamId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_TEAM)) {
            ps.setInt(1, teamId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Team mapRow(ResultSet rs) throws SQLException {
        return new Team(
                rs.getInt(ID),
                rs.getString(NAME),
                rs.getInt(RATING),
                Discipline.getDisciplineById(rs.getInt(DISCIPLINE_ID)).get(),
                new Resource(rs.getInt(LOGO_RESOURCE_ID), rs.getString(PATH))
        );
    }
}
