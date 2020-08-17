package ConferenceManagementSystem.DAOs;

import java.util.List;

public interface DAO<T> {
    public int getMaxId();

    public List<T> getAll();

    public boolean insert(T objToInsert); // C

    public T getById(int id); // R

    public boolean update(int id, T objToUpdate); // U

    public boolean delete(int id); // R
}
