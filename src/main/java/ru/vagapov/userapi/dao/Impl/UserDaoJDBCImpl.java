package ru.vagapov.userapi.dao.Impl;

import ru.vagapov.userapi.dao.UserDao;
import ru.vagapov.userapi.entity.UserEntity;
import ru.vagapov.userapi.util.ConnectionUtil;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final ConnectionUtil connectionUtil = new ConnectionUtil();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement preparedStatement;
        Connection connection = connectionUtil.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS users (id BIGINT GENERATED ALWAYS AS IDENTITY, firstName VARCHAR(255), lastName VARCHAR(255), birthDate timestamp, birthPlace VARCHAR(255), age smallint)";
        try {
            preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dropUsersTable() {
        Statement preparedStatement;
        Connection connection = connectionUtil.getConnection();
        String sql = "DROP TABLE IF EXISTS users";
        try {
            preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveUser(String firstName,
                         String lastName,
                         LocalDate birthDate,
                         String birthPlace,
                         Byte age) {
        PreparedStatement preparedStatement;
        Connection connection = connectionUtil.getConnection();
        String sql = "INSERT INTO users(firstName, lastName, birthDate, birthPlace, age) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, Date.valueOf(birthDate));
            preparedStatement.setString(4, birthPlace);
            preparedStatement.setInt(5, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void removeUserById(Long id) {
        PreparedStatement preparedStatement;
        Connection connection = connectionUtil.getConnection();
        String sql = "DELETE FROM users WHERE id = ? ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public List<UserEntity> getAllUsers() {
        List<UserEntity> userList = new ArrayList<>();
        Statement statement;
        Connection connection = connectionUtil.getConnection();
        String sql = "SELECT id, firstName, lastName, birthDate, birthPlace, age FROM users";
        try {
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
        } finally {
            try {
                connectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        PreparedStatement preparedStatement;
        Connection connection = connectionUtil.getConnection();
        String sql = "DELETE FROM users";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connectionUtil.closeConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

