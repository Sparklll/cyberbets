package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import org.apache.commons.lang3.StringUtils;


public class TeamValidator implements Validator<TeamDto> {
    private static final int MAX_NAME_LENGTH = 30;

    @Override
    public boolean isValid(TeamDto teamDto) {
        return isTeamNameValid(teamDto.teamName())
                && isTeamRatingValid(teamDto.teamRating())
                && isTeamDisciplineValid(teamDto.disciplineId());
    }


    private static boolean isTeamRatingValid(String teamRating) {
        return StringUtils.isNumeric(teamRating)
                && Integer.parseInt(teamRating) >= 0;
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
