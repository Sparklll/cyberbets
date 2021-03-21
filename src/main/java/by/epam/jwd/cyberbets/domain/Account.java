package by.epam.jwd.cyberbets.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Account extends Entity {
    private static final long serialVersionUID = 6348133752960448961L;

    private String email;
    private String passwordHash;
    private BigDecimal balance;
    private int roleId;
    private int avatarResourceId;

    public Account(int id, String email, String passwordHash, BigDecimal balance, int roleId, int avatarResourceId) {
        super(id);
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
        this.avatarResourceId = avatarResourceId;
        this.roleId = roleId;
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

    public int getAvatarResourceId() {
        return avatarResourceId;
    }

    public void setAvatarResourceId(int avatarResourceId) {
        this.avatarResourceId = avatarResourceId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return roleId == account.roleId
                && avatarResourceId == account.avatarResourceId
                && Objects.equals(email, account.email)
                && Objects.equals(passwordHash, account.passwordHash)
                && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, passwordHash, balance, roleId, avatarResourceId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", balance=" + balance +
                ", roleId=" + roleId +
                ", avatarResourceId=" + avatarResourceId +
                '}';
    }
}
