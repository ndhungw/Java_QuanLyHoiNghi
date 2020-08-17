package ConferenceManagementSystem.DAOs;

import ConferenceManagementSystem.Entities.Account;
import ConferenceManagementSystem.Entities.AccountConference;
import ConferenceManagementSystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class AccountConferenceDAO implements DAO<AccountConference> {
    // singleton
    private static AccountConferenceDAO instance;
    private AccountConferenceDAO() {}

    public static AccountConferenceDAO getInstance() {
        if (instance==null) {
            instance = new AccountConferenceDAO();
        }
        return instance;
    }
    // --

    public List<String> getSubscriberList(int idConference) {
        List<String> allSubscribersName = null;

        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

//            StoredProcedureQuery sp_getSubscribers = session.createStoredProcedureQuery("sp_getSubscribers");
//            sp_getSubscribers.registerStoredProcedureParameter("idConference", Integer.class, ParameterMode.IN);
//
//            sp_getSubscribers.setParameter("idConference", idConference);
//
//            allSubscribers = (List<Account>) sp_getSubscribers.getResultList();

            String sql = "Select a.displayName" +
                    " from " + Account.class.getName() + " a" +
                    " join " + AccountConference.class.getName() + " ac" +
                    " on a.id = ac.idAccount" +
                    " where ac.idConference = :idConference";

            // Tạo đối tượng Query.
            Query<String> query = session.createQuery(sql);
            query.setParameter("idConference", idConference);

            // Thực hiện truy vấn.
            allSubscribersName = query.getResultList();

            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return allSubscribersName;
    }

    public int getNumberOfSubscriber(int idConference) {
        int numOfSubscribers = 0;

        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            StoredProcedureQuery sp_getNumberOfSubscribers = session.createStoredProcedureQuery("sp_getNumberOfSubscribers");
            sp_getNumberOfSubscribers.registerStoredProcedureParameter("idConference", Integer.class, ParameterMode.IN);
            sp_getNumberOfSubscribers.registerStoredProcedureParameter("count", Integer.class, ParameterMode.OUT);

            sp_getNumberOfSubscribers.setParameter("idConference", idConference);
            sp_getNumberOfSubscribers.execute();

            numOfSubscribers = (Integer) sp_getNumberOfSubscribers.getOutputParameterValue("count");
            System.out.println("NumberOfSubscribers: " + numOfSubscribers);

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return numOfSubscribers;
    }

    public int checkIfSubscribed(int idAccount, int idConference) {
        int subscribedTimes = 0;

        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            StoredProcedureQuery sp_checkIfSubscribed = session.createStoredProcedureQuery("sp_checkIfSubscribed");
            sp_checkIfSubscribed.registerStoredProcedureParameter("idAccount", Integer.class, ParameterMode.IN);
            sp_checkIfSubscribed.registerStoredProcedureParameter("idConference", Integer.class, ParameterMode.IN);
            sp_checkIfSubscribed.registerStoredProcedureParameter("count", Integer.class, ParameterMode.OUT);

            sp_checkIfSubscribed.setParameter("idAccount", idAccount);
            sp_checkIfSubscribed.setParameter("idConference", idConference);
            sp_checkIfSubscribed.execute();

            subscribedTimes = (Integer) sp_checkIfSubscribed.getOutputParameterValue("count");
            System.out.println("NumberOfSubscribers: " + subscribedTimes);

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return subscribedTimes;
    }

    public boolean deleteRequestOfBlockedUserByUserId(int idAccount) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            StoredProcedureQuery sp_DeleteRequestOfBlockedUser = session.createStoredProcedureQuery("sp_DeleteRequestOfBlockedUser");
            sp_DeleteRequestOfBlockedUser.registerStoredProcedureParameter("idAccount", Integer.class, ParameterMode.IN);

            sp_DeleteRequestOfBlockedUser.setParameter("idAccount", idAccount);
            sp_DeleteRequestOfBlockedUser.execute();

            session.flush();
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return false;
    }

    public List<AccountConference> getAll(int status) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        List<AccountConference> allRequest = null;

        try {
            session.getTransaction().begin();

            String sql = "Select ac from " + AccountConference.class.getName() + " ac where ac.status = :status";

            // Tạo đối tượng Query.
            Query<AccountConference> query = session.createQuery(sql);
            query.setParameter("status",status);

            // Thực hiện truy vấn.
            allRequest = query.getResultList();

            // Commit dữ liệu
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return allRequest;
    }

    @Override
    public List<AccountConference> getAll() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        List<AccountConference> allRequest = null;

        try {
            session.getTransaction().begin();

            String sql = "Select ac from " + AccountConference.class.getName() + " ac";

            // Tạo đối tượng Query.
            Query<AccountConference> query = session.createQuery(sql);

            // Thực hiện truy vấn.
            allRequest = query.getResultList();

            // Commit dữ liệu
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return allRequest;
    }

    @Override
    public boolean insert(AccountConference objToInsert) {
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
    public AccountConference getById(int id) {
        return null;
    }

    public AccountConference getById(Session session, int id) {
        String sql = "SELECT ac" +
                " FROM " + AccountConference.class.getName() + " ac" +
                " WHERE ac.id = :id";
        Query<AccountConference> query = session.createQuery(sql);
        query.setParameter("id", id);
        AccountConference accountConference = query.getSingleResult();

        return accountConference;
    }

    @Override
    public boolean update(int id, AccountConference objToUpdate) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session1 = factory.getCurrentSession();

        AccountConference accountConference = null;

        try {
            session1.getTransaction().begin();

            // Đây là đối tượng có trạng thái Persistent.
            accountConference = this.getById(session1, id);

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
            System.out.println("- accountConference Persistent? " + session2.contains(accountConference));

            // set dữ liệu mới
            accountConference.setIdAccount(objToUpdate.getIdAccount());
            accountConference.setIdConference(objToUpdate.getIdConference());
            accountConference.setStatus(objToUpdate.getStatus());

            // update(..) chỉ áp dụng cho đối tượng Detached.
            // (Không dùng được với đối tượng Transient).
            // Sử dụng update(emp) để đưa emp trở lại trạng thái Persistent.
            session2.update(accountConference);

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
        return 0;
    }
}
