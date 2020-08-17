package ConferenceManagementSystem.DAOs;

import ConferenceManagementSystem.Entities.Account;
import ConferenceManagementSystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.util.List;

public class AccountDAO implements DAO<Account> {
    private static AccountDAO instance;

    private AccountDAO() {
    }

    public static AccountDAO getInstance() {
        if (instance == null) {
            instance = new AccountDAO();
        }
        return instance;
    }

    public int getMaxId() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Number value = null;

        try {
            session.getTransaction().begin();

            String sql = "Select max(acc.id) from " + Account.class.getName() + " acc";
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

//    public static void printSQLException(SQLException ex) {
//        for (Throwable e : ex) {
//            if (e instanceof SQLException) {
//                e.printStackTrace(System.err);
//                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//                System.err.println("Message: " + e.getMessage());
//                Throwable t = ex.getCause();
//                while (t != null) {
//                    System.out.println("Cause: " + t);
//                    t = t.getCause();
//                }
//            }
//        }
//    }

    public List<Account> getAll(int type, int isBlocked) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        List<Account> allUsers = null;

        try {
            session.getTransaction().begin();

            String sql;
            Query<Account> query = null;

            if (type == -1) {
                if (isBlocked == -1) {
                    // all types, all status
                    sql= "select a" +
                            " from " + Account.class.getName() + " a";
                    query = session.createQuery(sql);
                } else {
                    // all types, 1 status (blocked or not)
                    sql= "select a" +
                            " from " + Account.class.getName() + " a" +
                            " where a.blocked = :isBlocked";
                    query = session.createQuery(sql);
                    query.setParameter("isBlocked",isBlocked);
                }
            } else {
                // 1 type, all status
                if (isBlocked == -1) {
                    sql = "select a" +
                            " from " + Account.class.getName() + " a" +
                            " where  a.type = :type";
                    query = session.createQuery(sql);
                    query.setParameter("type", type);
                } else {
                    // 1 type, 1 status
                    sql= "select a" +
                            " from " + Account.class.getName() + " a" +
                            " where  a.type = :type and  a.blocked = :isBlocked";
                    query = session.createQuery(sql);
                    query.setParameter("type",type);
                    query.setParameter("isBlocked",isBlocked);
                }
            }

            // Thực hiện truy vấn.
            allUsers = query.getResultList();

            // Commit dữ liệu
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return allUsers;
    }

    @Override
    public List<Account> getAll() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        List<Account> allAccounts = null;

        try {
            session.getTransaction().begin();

            String sql = "Select a from " + Account.class.getName() + " a";

            // Tạo đối tượng Query.
            Query<Account> query = session.createQuery(sql);

            // Thực hiện truy vấn.
            allAccounts = query.getResultList();

            // Commit dữ liệu
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return allAccounts;
    }

    @Override
    public boolean insert(Account accountToInsert) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            Serializable id = session.save(accountToInsert);
            // Lúc này đối tượng đã có trạng thái Persistent, được quản lý trong Session

            // Chủ động đẩy dữ liệu xuống DB, gọi flush()
            // Nếu không gọi flush() dữ liệu sẽ được đẩy xuống tại lệnh commit()
            // Lúc này mới có insert
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
    public Account getById(int id) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();
        Account account = null;
        try {
            session.getTransaction().begin();
            String sql = "select a from " + Account.class.getName() + " a where a.id = :id";
            Query<Account> query = session.createQuery(sql);
            query.setParameter("id", id);
            account = query.getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return account;
    }

    @Override
    public boolean update(int id, Account objToUpdate) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session1 = factory.getCurrentSession();
        Account account = null;
        try {
            session1.getTransaction().begin();
            // Đây là đối tượng có trạng thái Persistent.
            account = this.getById(session1, id);
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
            System.out.println("- account Persistent? " + session2.contains(account));
            // set dữ liệu mới
            account.setDisplayName(objToUpdate.getDisplayName());
            account.setUsername(objToUpdate.getUsername());
            account.setPassword(objToUpdate.getPassword());
            account.setType(objToUpdate.getType());
            account.setBlocked(objToUpdate.getBlocked());
            // update(..) chỉ áp dụng cho đối tượng Detached.
            // (Không dùng được với đối tượng Transient).
            // Sử dụng update(emp) để đưa emp trở lại trạng thái Persistent.
            session2.update(account);
            // Chủ động đẩy dữ liệu xuống DB. Câu lệnh update sẽ được gọi.
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

    public Account getById(Session session, int id) {
        String sql = "SELECT a" +
                " FROM " + Account.class.getName() + " a" +
                " WHERE a.id = :id";
        Query<Account> query = session.createQuery(sql);
        query.setParameter("id", id);
        Account account = query.getSingleResult();
        return account;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    // get hashed password saved in DB
    public String getHashedPassword (String username) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        String hashedPassword = null;

        try {
            session.getTransaction().begin();

            StoredProcedureQuery sp_getHashedPassword = session.createStoredProcedureQuery("getHashedPassword");
            sp_getHashedPassword.registerStoredProcedureParameter("username",String.class, ParameterMode.IN);
            sp_getHashedPassword.registerStoredProcedureParameter("hashedPassword", String.class, ParameterMode.OUT);

            sp_getHashedPassword.setParameter("username", username);
            sp_getHashedPassword.execute();

            hashedPassword = (String) sp_getHashedPassword.getOutputParameterValue("hashedPassword");
            System.out.println("Hashed password: " + hashedPassword);

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return hashedPassword;
    }

    public Account getAccountForSession(String username, String password) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Account account = null;

        try {
            session.getTransaction().begin();

            String sql = "SELECT acc " +
                    "FROM " + Account.class.getName() + " acc  " +
                    "WHERE acc.username = :usernameToCheck AND acc.password = :passwordToCheck";

            Query<Account> query = session.createQuery(sql);
            query.setParameter("usernameToCheck", username);
            query.setParameter("passwordToCheck", password);

            account = query.getSingleResult();

//            StoredProcedureQuery sp_validateAccount = session.createStoredProcedureQuery("validateAccount", Account.class);
//            sp_validateAccount.registerStoredProcedureParameter("username",String.class, ParameterMode.IN);
//            sp_validateAccount.registerStoredProcedureParameter("password",String.class, ParameterMode.IN);
//            sp_validateAccount.setParameter("username",username);
//            // hash password here then register para below
//            // ...
//            sp_validateAccount.setParameter("password",password);


//            if (!sp_getHashedPassword.getResultList().isEmpty()) {
//                // get account for session
//                StoredProcedureQuery sp_validateAccount = session.createStoredProcedureQuery("getAccount", Account.class);
//                sp_validateAccount.registerStoredProcedureParameter("username", String.class, ParameterMode.IN);
//                sp_validateAccount.registerStoredProcedureParameter("password", String.class, ParameterMode.IN);
//                sp_validateAccount.setParameter("username", username);
//                sp_validateAccount.setParameter("password",hashedPassword); // hashedPassword instead of password
//
//                account = (Account) sp_validateAccount.getSingleResult();
//                System.out.println(account.toString()); // log
//            } else {
//                // No existing account has that username (account = null)
//            }

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return account;
    }
}
