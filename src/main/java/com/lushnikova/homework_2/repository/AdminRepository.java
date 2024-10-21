package com.lushnikova.homework_2.repository;

import com.lushnikova.homework_2.model.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.lushnikova.homework_2.config.SQL_Query.*;


/**
 * Класс Repository для администраторов
 */
public class AdminRepository {


    /**
     * Поле соединение с б/д
     */
    private final Connection connection;


    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param connection - соединение с б/д
     */
    public AdminRepository(Connection connection) {
        this.connection = connection;
    }


    /**
     * Процедура сохранения администратора
     *
     * @param admin - объект администратора
     */
    public void save(Admin admin) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ADMINS);
        preparedStatement.setLong(1, admin.getId());
        preparedStatement.setString(2, admin.getEmail());
        preparedStatement.setString(3, admin.getPassword());
        preparedStatement.executeUpdate();
    }


    /**
     * Функция получения администратора
     *
     * @param id - id администратора
     * @return возвращает объект администратора
     */
    public Admin findById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ADMIN_BY_ID);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getAdmin(resultSet);
    }

    /**
     * Функция получения администратора
     *
     * @param resultSet - результат получения запроса из б/д
     * @return возвращение объекта администратора
     * @throws SQLException
     */
    private Admin getAdmin(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setId(resultSet.getLong("id"));
        admin.setEmail(resultSet.getString("email"));
        admin.setPassword(resultSet.getString("password"));
        return admin;
    }

    /**
     * Процедура обновления пароля администратора
     *
     * @param id          - объект id администратора
     * @param newPassword - пароль администратора
     */
    public void updatePassword(Long id, String newPassword) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADMIN_PASSWORD);
        preparedStatement.setString(1, newPassword);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
    }


    /**
     * Функция получения списка администраторов
     *
     * @return возвращает копию списка администраторов
     */
    public synchronized List<Admin> findAll() throws SQLException {
        List<Admin> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_ADMINS);
        while (resultSet.next()) {
            list.add(getAdmin(resultSet));
        }
        return list;
    }
}
