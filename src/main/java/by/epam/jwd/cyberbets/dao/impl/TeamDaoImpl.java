package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.TeamDao;
import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {
    @Override
    public List<Team> findTeamsByDisciplineId(int disciplineId) {
        return null;
    }

    @Override
    public Optional<Team> findTeamById(int teamId) {
        return Optional.empty();
    }

    @Override
    public Optional<Team> findTeamByName(String name) {
        return Optional.empty();
    }

    @Override
    public void createTeam(TeamDto teamDto) {

    }

    @Override
    public void updateTeam(Team team) {

    }

    @Override
    public void deleteTeam(int teamId) {

    }
}
