package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.dto.LeagueDto;
import org.apache.commons.lang3.StringUtils;

public class LeagueValidator implements Validator<LeagueDto> {
    private static final int MAX_NAME_LENGTH = 50;

    @Override
    public boolean isValid(LeagueDto leagueDto) {
        return isIdValid(leagueDto.id())
                && isLeagueNameValid(leagueDto.leagueName())
                && isLeagueDisciplineValid(leagueDto.disciplineId());
    }

    public boolean isIdValid(int id) {
        return id >= 0;
    }

    private boolean isLeagueNameValid(String leagueName) {
        return StringUtils.isNotBlank(leagueName)
                && leagueName.length() <= MAX_NAME_LENGTH;
    }

    private boolean isLeagueDisciplineValid(int disciplineId) {
        return Discipline.getDisciplineById(disciplineId).isPresent();
    }
}
