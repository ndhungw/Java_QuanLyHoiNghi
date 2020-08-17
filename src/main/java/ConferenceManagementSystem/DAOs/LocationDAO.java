package ConferenceManagementSystem.DAOs;

import ConferenceManagementSystem.Entities.Account;
import ConferenceManagementSystem.Entities.Location;
import ConferenceManagementSystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LocationDAO implements DAO<Location>{
    private static LocationDAO instance;

    private LocationDAO () {}

    public static LocationDAO getInstance() {
        if (instance == null) {
            instance = new LocationDAO();
        }
        return instance;
    }

    @Override
    public int getMaxId() {
        return 0;
    }

    public Location getLocationByName(String name) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Location location = null;

        try {
            session.getTransaction().begin();

            String sql = "select l from " + Location.class.getName() + " l where l.name = :name";

            Query<Location> query = session.createQuery(sql);
            query.setParameter("name", name);

            location = query.getSingleResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return location;
    }

    @Override
    public List<Location> getAll() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        List<Location> allLocations = null;

        try {
            session.getTransaction().begin();

            String sql = "select l from " + Location.class.getName() + " l";

            Query<Location> query = session.createQuery(sql);

            allLocations = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return allLocations;
    }

    @Override
    public boolean insert(Location objToInsert) {
        return false;
    }

    @Override
    public Location getById(int id) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Location location = null;

        try {
            session.getTransaction().begin();

            String sql = "select l from " + Location.class.getName() + " l where l.id = :id";

            Query<Location> query = session.createQuery(sql);
            query.setParameter("id", id);

            location = query.getSingleResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return location;
    }

    @Override
    public boolean update(int id, Location objToUpdate) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
