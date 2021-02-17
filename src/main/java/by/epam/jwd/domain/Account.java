package by.epam.jwd.domain;

import java.util.Objects;

public class Account extends Entity {
    private static final long serialVersionUID = 6348133752960448961L;

    private String email;
    private String passwordHash;
    private String salt;
    private int accountDetailsId;
    private int roleId;

    public Account(int id, String email, String passwordHash, String salt, int accountDetailsId, int roleId) {
        super(id);
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.accountDetailsId = accountDetailsId;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getAccountDetailsId() {
        return accountDetailsId;
    }

    public void setAccountDetailsId(int accountDetailsId) {
        this.accountDetailsId = accountDetailsId;
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
        return accountDetailsId == account.accountDetailsId
                && roleId == account.roleId
                && Objects.equals(email, account.email)
                && Objects.equals(passwordHash, account.passwordHash)
                && Objects.equals(salt, account.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, passwordHash, salt, accountDetailsId, roleId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", salt='" + salt + '\'' +
                ", accountDetailsId=" + accountDetailsId +
                ", roleId=" + roleId +
                '}';
    }
}
