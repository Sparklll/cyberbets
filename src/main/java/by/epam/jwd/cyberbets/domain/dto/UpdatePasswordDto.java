package by.epam.jwd.cyberbets.domain.dto;

public record UpdatePasswordDto(String currentPassword, String newPassword, String repeatedNewPassword) {
}
