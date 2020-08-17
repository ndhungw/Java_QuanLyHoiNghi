package ConferenceManagementSystem.Utils;

import ConferenceManagementSystem.Entities.Account;

public final class Session {
    private Account account;

    // singleton
    private static Session instance;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
            return instance;
        }
        return instance;
    }

    private Session() {};
    // --

    public Session(Account account) {
        this.account = account;
    }

    public void cleanSession() {
        account=null;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}