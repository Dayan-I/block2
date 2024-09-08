package ru.vagapov.userapi.dao.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;


import org.hibernate.query.Query;
import ru.vagapov.userapi.dao.UserDao;
import ru.vagapov.userapi.entity.UserEntity;
import ru.vagapov.userapi.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.vagapov.userapi.util.HibernateUtil.getSessionFactory;

public class UserHibernateDaoImpl implements UserDao {
    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS " +
                "users (id BIGINT GENERATED ALWAYS AS IDENTITY, " +
                "firstName VARCHAR(255), lastName VARCHAR(255), " +
                "birthDate TIMESTAMP," +
                " birthPlace VARCHAR(255), " +
                "age SMALLINT)";

        Query query = session.createQuery(sql);

        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS users";

        Query query = session.createQuery(sql);

        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String firstName, String lastName, LocalDate birthDate, String birthPlace, Byte age) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();


            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(Long id) {

    }

    @Override
    public List<UserEntity> getAllUsers() {
        return List.of();
    }

    @Override
    public void cleanUsersTable() {

    }
}
