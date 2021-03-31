package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import org.apache.commons.lang3.StringUtils;


public class TeamValidator implements Validator<TeamDto> {
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MAX_TEAM_RATING = 1000000;

    @Override
    public boolean isValid(TeamDto teamDto) {
        return isTeamNameValid(teamDto.teamName())
                && isTeamRatingValid(teamDto.teamRating())
                && isTeamDisciplineValid(teamDto.disciplineId());
    }


    private static boolean isTeamRatingValid(int teamRating) {
        return teamRating >= 0
                && teamRating <= MAX_TEAM_RATING;
    }

    private static boolean isTeamNameValid(String teamName) {
        return StringUtils.isNotBlank(teamName)
                && teamName.length() <= MAX_NAME_LENGTH;
    }

    private static boolean isTeamDisciplineValid(String disciplineId) {
        return StringUtils.isNumeric(disciplineId)
                && Discipline.getDisciplineById(Integer.parseInt(disciplineId)).isPresent();
    }
}
