package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.*;
import by.epam.jwd.cyberbets.domain.dto.EventDto;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class EventDaoImpl implements EventDao {
    EventDaoImpl() {

    }

    private static final String FIND_ALL_EVENTS = """
            select e.id,
                   e.discipline_id,

                   e.league_id,
                   l.discipline_id as league_discipline_id,
                   l.name as league_name,
                   l.icon_resource_id as league_icon_resource_id,
                   lr.path as league_icon_resource_path,

                   t1.id as first_team_id,
                   t1.discipline_id as first_team_discipline_id,
                   t1.name as first_team_name,
                   t1.rating as first_team_rating,
                   t1.logo_resource_id as first_team_logo_resource_id,
                   t1r.path as first_team_logo_resource_path,

                   t2.id as second_team_id,
                   t2.discipline_id as second_team_discipline_id,
                   t2.name as second_team_name,
                   t2.rating as second_team_rating,
                   t2.logo_resource_id as second_team_logo_resource_id,
                   t2r.path as second_team_logo_resource_path,

                   e.event_format_id,

                   e.start,
                   e.royalty_percentage,
                   e.status
                   
            from event e
            inner join league l on l.id = e.league_id
            inner join resource lr on lr.id = l.icon_resource_id

            inner join team t1 on t1.id = e.first_team_id
            inner join resource t1r on t1r.id = t1.logo_resource_id

            inner join team t2 on t2.id = e.second_team_id
            inner join resource t2r on t2r.id = t2.logo_resource_id

            inner join event_format ef on ef.id = e.event_format_id
            """;
    private static final String FIND_EVENT_BY_ID = """
            select e.id,
                   e.discipline_id,

                   e.league_id,
                   l.discipline_id as league_discipline_id,
                   l.name as league_name,
                   l.icon_resource_id as league_icon_resource_id,
                   lr.path as league_icon_resource_path,

                   t1.id as first_team_id,
                   t1.discipline_id as first_team_discipline_id,
                   t1.name as first_team_name,
                   t1.rating as first_team_rating,
                   t1.logo_resource_id as first_team_logo_resource_id,
                   t1r.path as first_team_logo_resource_path,

                   t2.id as second_team_id,
                   t2.discipline_id as second_team_discipline_id,
                   t2.name as second_team_name,
                   t2.rating as second_team_rating,
                   t2.logo_resource_id as second_team_logo_resource_id,
                   t2r.path as second_team_logo_resource_path,

                    e.event_format_id,

                   e.start,
                   e.royalty_percentage,
                   e.status
                   
            from event e
            inner join league l on l.id = e.league_id
            inner join resource lr on lr.id = l.icon_resource_id

            inner join team t1 on t1.id = e.first_team_id
            inner join resource t1r on t1r.id = t1.logo_resource_id

            inner join team t2 on t2.id = e.second_team_id
            inner join resource t2r on t2r.id = t2.logo_resource_id

            inner join event_format ef on ef.id = e.event_format_id
            where e.id = ?
            """;

    private static final String CREATE_EVENT = """
            insert into event (discipline_id, league_id, first_team_id, second_team_id, event_format_id, start, royalty_percentage, status) 
            values  (?, ?, ?, ?, ?, ?, ?, ?)
            returning id
            """;
    private static final String UPDATE_EVENT = """
            update event
            set discipline_id      = ?,
                league_id          = ?,
                first_team_id      = ?,
                second_team_id     = ?,
                event_format_id    = ?,
                start              = ?,
                royalty_percentage = ?,
                status = ?
            where id = ?
            """;
    private static final String DELETE_EVENT = "delete from event where id = ?";

    @Override
    public List<Event> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_EVENTS)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Event> events = new ArrayList<>();
                while (rs.next()) {
                    Event event = mapRow(rs);
                    events.add(event);
                }
                return events;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Event> findEventById(int eventId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_EVENT_BY_ID)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Event> eventOptional = Optional.empty();
                if (rs.next()) {
                    Event event = mapRow(rs);
                    eventOptional = Optional.of(event);
                }
                return eventOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int createEvent(EventDto eventDto) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_EVENT)) {
            ps.setInt(1, eventDto.disciplineId());
            ps.setInt(2, eventDto.leagueId());
            ps.setInt(3, eventDto.firstTeamId());
            ps.setInt(4, eventDto.secondTeamId());
            ps.setInt(5, eventDto.formatId());
            ps.setTimestamp(6, Timestamp.from(eventDto.startDate()));
            ps.setBigDecimal(7, eventDto.royalty());
            ps.setInt(8, eventDto.eventStatusId());
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
    public void updateEvent(Event event) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_EVENT)) {
            ps.setInt(1, event.getDiscipline().getId());
            ps.setInt(2, event.getLeague().getId());
            ps.setInt(3, event.getFirstTeam().getId());
            ps.setInt(4, event.getSecondTeam().getId());
            ps.setInt(5, event.getEventFormat().getId());
            ps.setTimestamp(6, Timestamp.from(event.getStartDate()));
            ps.setBigDecimal(7, event.getRoyaltyPercentage());
            ps.setInt(8, event.getStatus().getId());
            ps.setInt(9, event.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteEvent(int eventId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_EVENT)) {
            ps.setInt(1, eventId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Event mapRow(ResultSet rs) throws SQLException {
        Discipline discipline = Discipline.getDisciplineById(rs.getInt(DISCIPLINE_ID)).get();
        League league = new League(
                rs.getInt(LEAGUE_ID),
                rs.getString(LEAGUE_NAME),
                Discipline.getDisciplineById(rs.getInt(LEAGUE_DISCIPLINE_ID)).get(),
                new Resource(rs.getInt(LEAGUE_ICON_RESOURCE_ID), rs.getString(LEAGUE_ICON_RESOURCE_PATH))
        );
        Team firstTeam = new Team(
                rs.getInt(FIRST_TEAM_ID),
                rs.getString(FIRST_TEAM_NAME),
                rs.getInt(FIRST_TEAM_RATING),
                Discipline.getDisciplineById(rs.getInt(FIRST_TEAM_DISCIPLINE_ID)).get(),
                new Resource(rs.getInt(FIRST_TEAM_LOGO_RESOURCE_ID), rs.getString(FIRST_TEAM_LOGO_RESOURCE_PATH))
        );
        Team secondTeam = new Team(
                rs.getInt(SECOND_TEAM_ID),
                rs.getString(SECOND_TEAM_NAME),
                rs.getInt(SECOND_TEAM_RATING),
                Discipline.getDisciplineById(rs.getInt(SECOND_TEAM_DISCIPLINE_ID)).get(),
                new Resource(rs.getInt(SECOND_TEAM_LOGO_RESOURCE_ID), rs.getString(SECOND_TEAM_LOGO_RESOURCE_PATH)));
        EventFormat eventFormat = EventFormat.getEventFormatById(rs.getInt(EVENT_FORMAT_ID)).get();
        Timestamp startDateTimestamp = rs.getTimestamp(START);
        Instant startDate = startDateTimestamp.toInstant();
        BigDecimal royaltyPercentage = rs.getBigDecimal(ROYALTY_PERCENTAGE);
        EventStatus status = EventStatus.getEventStatusById(rs.getInt(STATUS)).get();

        return new Event(
                rs.getInt(ID),
                discipline,
                league,
                firstTeam,
                secondTeam,
                eventFormat,
                startDate,
                royaltyPercentage,
                status
        );
    }
}
