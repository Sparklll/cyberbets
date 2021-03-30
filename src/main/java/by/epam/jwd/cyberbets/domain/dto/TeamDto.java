package by.epam.jwd.cyberbets.domain.dto;

public record TeamDto(String id, String teamName, String teamRating, String teamLogo, String disciplineId, String disciplineLogoPath) {
    public TeamDto(String teamName, String teamRating, String teamLogo, String disciplineId) {
        this(null, teamName, teamRating, teamLogo, disciplineId, null);
    }
}
