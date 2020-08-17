package ConferenceManagementSystem.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Conference {
    private Integer id;
    private String name;
    private String shortDescription;
    private String detailDescription;
    private String imgUrl;
    private Timestamp time;
    private Integer location;
    private Integer numOfParticipants;
    private Collection<AccountConference> accountConferencesById;
    private Location locationByLocation;

    public Conference() {
    }

    public Conference(Integer id, String name, String shortDescription, String detailDescription, String imgUrl, Timestamp time, Integer location, Integer numOfParticipants) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.detailDescription = detailDescription;
        this.imgUrl = imgUrl;
        this.time = time;
        this.location = location;
        this.numOfParticipants = numOfParticipants;
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
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "shortDescription", nullable = false, length = 100)
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Basic
    @Column(name = "detailDescription", nullable = false, length = 255)
    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    @Basic
    @Column(name = "imgUrl", nullable = false, length = 255)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "location", nullable = false)
    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    @Basic
    @Column(name = "numOfParticipants", nullable = false)
    public Integer getNumOfParticipants() {
        return numOfParticipants;
    }

    public void setNumOfParticipants(Integer numOfParticipants) {
        this.numOfParticipants = numOfParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conference that = (Conference) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortDescription != null ? !shortDescription.equals(that.shortDescription) : that.shortDescription != null)
            return false;
        if (detailDescription != null ? !detailDescription.equals(that.detailDescription) : that.detailDescription != null)
            return false;
        if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (numOfParticipants != null ? !numOfParticipants.equals(that.numOfParticipants) : that.numOfParticipants != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (detailDescription != null ? detailDescription.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (numOfParticipants != null ? numOfParticipants.hashCode() : 0);
        return result;
    }

    // Class Conference có private Collection<AccountConference> accountConferencesById;
    @OneToMany(mappedBy = "conferenceByIdConference")
    public Collection<AccountConference> getAccountConferencesById() {
        return accountConferencesById;
    }

    public void setAccountConferencesById(Collection<AccountConference> accountConferencesById) {
        this.accountConferencesById = accountConferencesById;
    }

    @ManyToOne
    @JoinColumn(name = "location", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Location getLocationByLocation() {
        return locationByLocation;
    }

    public void setLocationByLocation(Location locationByLocation) {
        this.locationByLocation = locationByLocation;
    }
}
