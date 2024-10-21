package com.lushnikova.homework_2.repository;

import com.lushnikova.homework_2.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.lushnikova.homework_2.config.SQL_Query.*;

/**
 * Класс Repository для пользователей
 */
public class UserRepository {

    /**
     * Поле соединение с бд
     */

    private final Connection connection;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param connection - соединение с б/д
     */
    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Процедура сохранения пользователя
     *
     * @param user - объект пользователя
     * @return возвращает объект пользователя
     */
    public User save(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setBoolean(4, true);
        preparedStatement.executeUpdate();
        return user;
    }

    /**
     * Функция получения пользователя
     *
     * @param id - id пользователя
     * @return возвращает объект пользователя
     */
    public User findById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return getUser(resultSet);
    }

    /**
     * Функция получения пользователя
     *
     * @param resultSet - результат получения запроса из б/д
     * @return возвращение объекта пользователя
     * @throws SQLException
     */
    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setIsActive(resultSet.getBoolean("is_active"));
        return user;
    }

    /**
     * Процедура обновления имени пользователя
     *
     * @param id   - id пользователя
     * @param name - новое имя пользователя
     */
    public void updateName(Long id, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_NAME);
        preparedStatement.setString(1, name);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
    }

    /**
     * Процедура обновления почты пользователя
     *
     * @param id    - id пользователя
     * @param email - новое имя пользователя
     */
    public void updateEmail(Long id, String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_EMAIL);
        preparedStatement.setString(1, email);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
    }

    /**
     * Процедура обновления пароля пользователя
     *
     * @param id       - id пользователя
     * @param password - новый пароль пользователя
     */
    public void updatePassword(Long id, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD);
        preparedStatement.setString(1, password);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
    }

    /**
     * Процедура удаления пользователя
     *
     * @param id - id пользователя
     */
    public void delete(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HABITS_BY_ID_USER);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();

        PreparedStatement preparedStatement1 = connection.prepareStatement(DELETE_USER);
        preparedStatement1.setLong(1, id);
        preparedStatement1.executeUpdate();
    }

    /**
     * Процедура определения значения поля
     *
     * @param id       - id пользователя
     * @param isActive - блокировка
     */
    public void setIsActive(Long id, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_IS_ACTIVE);
        preparedStatement.setBoolean(1, isActive);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
    }


    /**
     * Функция получения списка администраторов
     *
     * @return возвращает список администраторов
     */
    public List<User> findAll() throws SQLException {
        List<User> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_USERS);
        while (resultSet.next()) {
            list.add(getUser(resultSet));
        }
        return list;
    }

}
