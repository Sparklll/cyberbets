package by.epam.jwd.cyberbets.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record EventDto(int disciplineId, int leagueId, int firstTeamId, int secondTeamId,
                       int formatId, BigDecimal royalty, Instant startDate, int eventStatusId) {
}
