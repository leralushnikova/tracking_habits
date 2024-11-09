package com.lushnikova.repository;

import com.lushnikova.model.enums.Role;
import com.lushnikova.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.lushnikova.consts.SQL_Query.*;

/**
 * Класс Repository для пользователей
 */
@Repository
public class UserRepository {

    /**
     * Поле соединение с бд
     */
    private final Connection connection;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param source - соединение с б/д
     */
    @SneakyThrows
    @Autowired
    public UserRepository(DataSource source) {
        this.connection = source.getConnection();
    }

    /**
     * Процедура сохранения пользователя
     *
     * @param user - объект пользователя
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    public User save(User user) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS)){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBoolean(4, true);
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.executeUpdate();
            return user;
        }
    }

    /**
     * Функция получения пользователя
     *
     * @param id - id пользователя
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    public User findById(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)){
            preparedStatement.setLong(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return getUser(resultSet);
            }
        }
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

        String roleString = resultSet.getString("role");
        Role role = null;
        switch (roleString) {
            case "USER" -> role = Role.USER;
            case "ADMIN" -> role = Role.ADMIN;
        }
        user.setRole(role);
        return user;
    }

    /**
     * Процедура обновления имени пользователя
     *
     * @param id   - id пользователя
     * @param name - новое имя пользователя
     * @throws SQLException
     */
    public void updateName(Long id, String name) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_NAME)){
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Процедура обновления почты пользователя
     *
     * @param id    - id пользователя
     * @param email - новое имя пользователя
     * @throws SQLException
     */
    public void updateEmail(Long id, String email) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_EMAIL)){
            preparedStatement.setString(1, email);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Процедура обновления пароля пользователя
     *
     * @param id       - id пользователя
     * @param password - новый пароль пользователя
     * @throws SQLException
     */
    public void updatePassword(Long id, String password) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD)){
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Процедура удаления пользователя
     *
     * @param id - id пользователя
     * @throws SQLException
     */
    public void delete(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HABITS_BY_ID_USER);
            PreparedStatement preparedStatement1 = connection.prepareStatement(DELETE_USER)){

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            preparedStatement1.setLong(1, id);
            preparedStatement1.executeUpdate();
        }
    }

    /**
     * Процедура блокировки/разблокировки пользователя
     *
     * @param id       - id пользователя
     * @param isActive - блокировка
     * @throws SQLException
     */
    public void setIsActive(Long id, boolean isActive) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_IS_ACTIVE)){
            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }


    /**
     * Функция получения списка администраторов
     *
     * @return возвращает список администраторов
     * @throws SQLException
     */
    public List<User> findAll() throws SQLException {
        List<User> list = new ArrayList<>();

        try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_USERS)){
            while (resultSet.next()) {
                list.add(getUser(resultSet));
            }
            return list;
        }
    }

}
