package by.epam.jwd.cyberbets.domain.dto;

public record TeamDto(int id, String teamName, int teamRating, String teamLogo, String disciplineId) {
    public TeamDto(String teamName, int teamRating, String teamLogo, String disciplineId) {
        this(0, teamName, teamRating, teamLogo, disciplineId);
    }
}
