package by.epam.jwd.cyberbets.domain.dto;

public record LeagueDto(int id, String leagueName, String leagueIcon, int disciplineId) {
    public LeagueDto(String leagueName, String leagueIcon, int disciplineId){
        this(0, leagueName, leagueIcon, disciplineId);
    }
}
