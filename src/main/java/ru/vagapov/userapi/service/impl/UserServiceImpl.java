package ru.vagapov.userapi.service.impl;

import ru.vagapov.userapi.dao.Impl.UserDaoJDBCImpl;
import ru.vagapov.userapi.dao.UserDao;
import ru.vagapov.userapi.entity.UserEntity;
import ru.vagapov.userapi.service.UserService;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;


public class UserServiceImpl implements UserService {
    private final UserDaoJDBCImpl userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = (UserDaoJDBCImpl) userDao;
    }

    public void createUsersTable() {
        Statement preparedStatement;
        String sql = "CREATE TABLE IF NOT EXISTS users (id BIGINT GENERATED ALWAYS AS IDENTITY, FirstName VARCHAR(255), LastName VARCHAR(255), birthDate timestamp, birthPlace VARCHAR(255), age smallint)";
        try {
            Connection connection = userDao.getConnection();
            preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Statement preparedStatement;
        String sql = "DROP TABLE IF EXISTS users";
        try {
            Connection connection = userDao.getConnection();
            preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String firstName,
                         String lastName,
                         LocalDate birthDate,
                         String birthPlace,
                         Byte age) {
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO users(FirstName, LastName, birthDate, birthPlace, age) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = userDao.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, Date.valueOf(birthDate));
            preparedStatement.setString(4, birthPlace);
            preparedStatement.setInt(5, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(Long id) {
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM users WHERE ID = ? ";
        try {
            Connection connection = userDao.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> userList = new ArrayList<>();
        Statement statement;
        String sql = "SELECT ID, FirstName, LastName, birthDate, birthPlace, age FROM users";
        try {
            Connection connection = userDao.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                UserEntity user = new UserEntity();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setBirthDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getDate(4).getTime()), ZoneId.of("UTC")));
                user.setBirthPlace(resultSet.getString(5));
                user.setAge((byte) resultSet.getInt(6));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM users";
        try {
            Connection connection = userDao.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
