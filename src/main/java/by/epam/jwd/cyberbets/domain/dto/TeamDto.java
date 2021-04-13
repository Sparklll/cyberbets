package by.epam.jwd.cyberbets.domain.dto;

public record TeamDto(int id, String teamName, int teamRating, String teamLogo, int disciplineId) {
    public TeamDto(String teamName, int teamRating, String teamLogo, int disciplineId) {
        this(0, teamName, teamRating, teamLogo, disciplineId);
    }
}
