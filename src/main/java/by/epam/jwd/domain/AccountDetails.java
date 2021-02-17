package by.epam.jwd.domain;


import java.math.BigDecimal;
import java.util.Objects;

public class AccountDetails extends Entity {
    private static final long serialVersionUID = -4757182711383572119L;

    private String nickname;
    private BigDecimal balance;
    private int avatarResourceId;

    public AccountDetails(int id, String nickname, BigDecimal balance, int avatarResourceId) {
        super(id);
        this.nickname = nickname;
        this.balance = balance;
        this.avatarResourceId = avatarResourceId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetails that = (AccountDetails) o;
        return avatarResourceId == that.avatarResourceId
                && Objects.equals(nickname, that.nickname)
                && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, balance, avatarResourceId);
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "nickname='" + nickname + '\'' +
                ", balance=" + balance +
                ", avatarResourceId=" + avatarResourceId +
                '}';
    }
}
