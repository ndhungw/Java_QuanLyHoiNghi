package ConferenceManagementSystem.Entities;

import javax.persistence.*;

@Entity
@Table(name = "account_conference", schema = "conferencemanagementsystem", catalog = "")
public class AccountConference {
    private Integer id;
    private Integer idAccount;
    private Integer idConference;
    private Integer status;
    private Account accountByIdAccount;
    private Conference conferenceByIdConference;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_account", nullable = false)
    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    @Basic
    @Column(name = "id_conference", nullable = false)
    public Integer getIdConference() {
        return idConference;
    }

    public void setIdConference(Integer idConference) {
        this.idConference = idConference;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountConference that = (AccountConference) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idAccount != null ? !idAccount.equals(that.idAccount) : that.idAccount != null) return false;
        if (idConference != null ? !idConference.equals(that.idConference) : that.idConference != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idAccount != null ? idAccount.hashCode() : 0);
        result = 31 * result + (idConference != null ? idConference.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Account getAccountByIdAccount() {
        return accountByIdAccount;
    }

    public void setAccountByIdAccount(Account accountByIdAccount) {
        this.accountByIdAccount = accountByIdAccount;
    }

    // Class AccountConference này có private Conference conferenceByIdConference;
    @ManyToOne
    @JoinColumn(name = "id_conference", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Conference getConferenceByIdConference() {
        return conferenceByIdConference;
    }
    public void setConferenceByIdConference(Conference conferenceByIdConference) {
        this.conferenceByIdConference = conferenceByIdConference;
    }
}
