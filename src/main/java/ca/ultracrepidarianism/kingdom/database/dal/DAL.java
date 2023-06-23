package ca.ultracrepidarianism.kingdom.database.dal;

public interface DAL {
    <T> void insert(T obj);
    <T> void update(T obj);
    <T> void delete(T obj);
    <T> void get(T obj);


}
