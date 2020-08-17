package ConferenceManagementSystem.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Account {
    private Integer id;
    private String displayName;
    private String username;
    private String password;
    private Integer type;
    private Integer blocked;
    private Collection<AccountConference> accountConferencesById;

    public Account() {
    }

    public Account(Integer id, String displayName, String username, String hashedPassword, Integer type, Integer blocked) {
        this.id = id;
        this.displayName = displayName;
        this.username = username;
        this.password = hashedPassword;
        this.type = type;
        this.blocked = blocked;
    }

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "displayName", nullable = false, length = 100)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "blocked", nullable = false)
    public Integer getBlocked() {
        return blocked;
    }

    public void setBlocked(Integer blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (displayName != null ? !displayName.equals(account.displayName) : account.displayName != null) return false;
        if (username != null ? !username.equals(account.username) : account.username != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (type != null ? !type.equals(account.type) : account.type != null) return false;
        if (blocked != null ? !blocked.equals(account.blocked) : account.blocked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "accountByIdAccount")
    public Collection<AccountConference> getAccountConferencesById() {
        return accountConferencesById;
    }

    public void setAccountConferencesById(Collection<AccountConference> accountConferencesById) {
        this.accountConferencesById = accountConferencesById;
    }
}
