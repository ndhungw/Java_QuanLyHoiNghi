package ConferenceManagementSystem.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Location {
    private Integer id;
    private String name;
    private String address;
    private Integer capacity;
    private Collection<Conference> conferencesById;

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
    @Column(name = "address", nullable = false, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "capacity", nullable = false)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (id != null ? !id.equals(location.id) : location.id != null) return false;
        if (name != null ? !name.equals(location.name) : location.name != null) return false;
        if (address != null ? !address.equals(location.address) : location.address != null) return false;
        if (capacity != null ? !capacity.equals(location.capacity) : location.capacity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "locationByLocation")
    public Collection<Conference> getConferencesById() {
        return conferencesById;
    }

    public void setConferencesById(Collection<Conference> conferencesById) {
        this.conferencesById = conferencesById;
    }
}
