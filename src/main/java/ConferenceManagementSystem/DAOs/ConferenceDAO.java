package ConferenceManagementSystem.DAOs;

import ConferenceManagementSystem.Entities.Conference;
import ConferenceManagementSystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDAO implements DAO<Conference> {
    private static ConferenceDAO instance;
    private ConferenceDAO() {}
    public static ConferenceDAO getInstance() {
        if (instance == null) {
            instance = new ConferenceDAO();
        }
        return instance;
    }

//    public List<String> getAllLocation() {
//        SessionFactory factory = HibernateUtils.getSessionFactory();
//        Session session = factory.getCurrentSession();
//
//        List<String> list = new ArrayList<>();
//
//        try {
//            session.getTransaction().begin();
//
//            String sql = "Select c.location" +
//                    " from " + Conference.class.getName() + " c";
//
//            // Tạo đối tượng Query.
//            Query<String> query = session.createQuery(sql);
//
//            // Thực hiện truy vấn.
//            list = query.getResultList();
//
//            // Commit dữ liệu
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.getTransaction().rollback();
//        }
//
//        return list;
//    }

    public Conference getConferenceByName (String conferenceName) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Conference conference = new Conference();

        try {
            session.getTransaction().begin();

            String sql = "Select c" +
                    " from " + Conference.class.getName() + " c" +
                    " where c.name=:conferenceName";

            // Tạo đối tượng Query.
            Query<Conference> query = session.createQuery(sql);
            query.setParameter("conferenceName", conferenceName);

            // Thực hiện truy vấn.
            conference = query.getSingleResult();

            // Commit dữ liệu
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return conference;
    }

    @Override
    public List<Conference> getAll() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            String sql = "Select c from " + Conference.class.getName() + " c";

            // Tạo đối tượng Query.
            Query<Conference> query = session.createQuery(sql);

            // Thực hiện truy vấn.
            List<Conference> allConferences = query.getResultList();

            // Commit dữ liệu
            session.flush();
            session.getTransaction().commit();

            return allConferences;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return null;
    }

    @Override
    public boolean insert(Conference objToInsert) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            session.save(objToInsert);

            session.flush();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return false;
    }

    @Override
    public Conference getById(int id) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session1 = factory.getCurrentSession();

        Conference conference = new Conference();

        try {
            session1.getTransaction().begin();

            Query<Conference> query = session1.createQuery("SELECT c" +
                            " FROM " + Conference.class.getName() + " c" +
                            " WHERE c.id = :id"
            );
            query.setParameter("id", id);

            conference = query.getSingleResult();

            session1.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session1.getTransaction().rollback();
        }
        return conference;
    }

    public Conference getById(Session session, int id) {
        String sql = "SELECT c" +
                " FROM " + Conference.class.getName() + " c" +
                " WHERE c.id = :id";
        Query<Conference> query = session.createQuery(sql);
        query.setParameter("id", id);
        Conference conference = query.getSingleResult();

        return conference;
    }

    @Override
    public boolean update(int id, Conference objToUpdate) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session1 = factory.getCurrentSession();

        Conference conference = null;
        
        try {
            session1.getTransaction().begin();

            // Đây là đối tượng có trạng thái Persistent.
            conference = this.getById(session1, id);

            // session1 đã bị đóng lại sau commit được gọi.
            session1.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session1.getTransaction().rollback();
        }

        // Mở 1 session khác
        Session session2 = factory.getCurrentSession();

        try {
            session2.getTransaction().begin();

            // Kiểm tra trạng thái của conference:
            // ==> false
            System.out.println("- conference Persistent? " + session2.contains(conference));

            // set dữ liệu mới
            conference.setName(objToUpdate.getName());
            conference.setShortDescription(objToUpdate.getShortDescription());
            conference.setDetailDescription(objToUpdate.getDetailDescription());
            conference.setLocation(objToUpdate.getLocation());
            conference.setTime(objToUpdate.getTime());
            conference.setImgUrl(objToUpdate.getImgUrl());
            conference.setNumOfParticipants(objToUpdate.getNumOfParticipants());

            // update(..) chỉ áp dụng cho đối tượng Detached.
            // (Không dùng được với đối tượng Transient).
            // Sử dụng update(emp) để đưa emp trở lại trạng thái Persistent.
            session2.update(conference);

            // Chủ động đẩy dữ liệu xuống DB.
            // Câu lệnh update sẽ được gọi.
            session2.flush();

            // session2 đã bị đóng lại sau commit được gọi.
            session2.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session2.getTransaction().rollback();
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public int getMaxId() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Number value = null;

        try {
            session.getTransaction().begin();

            String sql = "Select max(c.id) from " + Conference.class.getName() + " c";
            Query<Number> query = session.createQuery(sql);
            value = query.getSingleResult();

            if (value == null) {
                value = (Number)(0);
            }

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return value.intValue();
    }

    public boolean checkIfConferenceOccured (int idConference) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        boolean hasOccurred = true;

        try {
            session.getTransaction().begin();

            StoredProcedureQuery sp_checkIfConferenceOccurred = session.createStoredProcedureQuery("sp_checkIfConferenceOccurred");
            sp_checkIfConferenceOccurred.registerStoredProcedureParameter("idConference", Integer.class, ParameterMode.IN);
            sp_checkIfConferenceOccurred.registerStoredProcedureParameter("count", Integer.class, ParameterMode.OUT);

            sp_checkIfConferenceOccurred.setParameter("idConference", idConference);
            sp_checkIfConferenceOccurred.execute();

            int count = (Integer) sp_checkIfConferenceOccurred.getOutputParameterValue("count");
            System.out.println("Result: " + count);

            if (count == 0) {
                hasOccurred = false; // not yet
            }

            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return hasOccurred;
    }
}
