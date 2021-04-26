package by.epam.jwd.cyberbets.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Account extends Entity {
    private static final long serialVersionUID = 6348133752960448961L;

    private String email;
    private String passwordHash;
    private BigDecimal balance;
    private Role role;
    private Resource avatarResource;
    private Instant registrationDate;

    public Account(int id, String email, String passwordHash, BigDecimal balance, Role role, Resource avatarResource) {
        super(id);
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
        this.role = role;
        this.avatarResource = avatarResource;
    }

    public Account(int id, String email, String passwordHash, BigDecimal balance, Role role, Resource avatarResource,
                   Instant registrationDate) {
        super(id);
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
        this.role = role;
        this.avatarResource = avatarResource;
        this.registrationDate = registrationDate;
    }

    public Account(String email, String passwordHash, BigDecimal balance, Role role, Resource avatarResource) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
        this.role = role;
        this.avatarResource = avatarResource;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Resource getAvatarResource() {
        return avatarResource;
    }

    public void setAvatarResource(Resource avatarResource) {
        this.avatarResource = avatarResource;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(email, account.email)
                && Objects.equals(passwordHash, account.passwordHash)
                && Objects.equals(balance, account.balance)
                && role == account.role
                && Objects.equals(avatarResource, account.avatarResource)
                && Objects.equals(registrationDate, account.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, passwordHash, balance, role, avatarResource, registrationDate);
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                ", avatarResource=" + avatarResource +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
