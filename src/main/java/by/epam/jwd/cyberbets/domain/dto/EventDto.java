package by.epam.jwd.cyberbets.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record EventDto(int eventId, int disciplineId, int leagueId, int firstTeamId, int secondTeamId,
                       int formatId, BigDecimal royalty, Instant startDate, int eventStatusId) {
    public EventDto(int disciplineId, int leagueId, int firstTeamId, int secondTeamId,
                    int formatId, BigDecimal royalty, Instant startDate, int eventStatusId) {
        this(0, disciplineId, leagueId, firstTeamId, secondTeamId, formatId, royalty, startDate, eventStatusId);
    }
}
