package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamDao {
    List<Team> findTeamsByDisciplineId(int disciplineId);
    Optional<Team> findTeamById(int teamId);
    Optional<Team> findTeamByName(String name);
    void createTeam(TeamDto teamDto);
    void updateTeam(Team team);
    void deleteTeam(int teamId);
}
