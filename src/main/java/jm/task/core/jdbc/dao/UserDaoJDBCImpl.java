package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    
    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            String sql = "CREATE TABLE IF NOT EXISTS `mydbusers`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(20) NOT NULL,\n" +
                    "  `lastName` VARCHAR(20) NOT NULL,\n" +
                    "  `age` TINYINT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }

    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            String sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getConnection();
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getConnection();
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = Util.getConnection();
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong(1));
                users.add(user);
            }
            resultSet.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            String sql = "DELETE FROM users";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
