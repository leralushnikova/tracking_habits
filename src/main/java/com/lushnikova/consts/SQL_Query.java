package com.lushnikova.consts;

/**
 * Класс SQL запросов
 */
public class SQL_Query {

    public static final String INSERT_INTO_USERS = "INSERT INTO demo.users(name, email, password, is_active, role) VALUES (?, ?, ?, ?, ?)";

    public static final String INSERT_INTO_HABITS = "INSERT INTO demo.habits(user_id, title, description, repeat, status, create_at) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SELECT_USER_BY_ID = "SELECT * FROM demo.users WHERE id = ?";

    public static final String SELECT_HABIT_BY_ID = "SELECT * FROM demo.habits WHERE id = ? and user_id = ?";

    public static final String UPDATE_USER_NAME = "UPDATE demo.users SET name = ? WHERE id = ?";

    public static final String UPDATE_USER_EMAIL = "UPDATE demo.users SET email = ? WHERE id = ?";

    public static final String UPDATE_USER_PASSWORD = "UPDATE demo.users SET password = ? WHERE id = ?";

    public static final String UPDATE_USER_IS_ACTIVE = "UPDATE demo.users SET is_active = ? WHERE id = ? and role = 'USER'";

    public static final String UPDATE_USER_ROLE = "UPDATE demo.users SET role = ? WHERE id = ?";

    public static final String DELETE_USER = "DELETE FROM demo.users WHERE id = ?";

    public static final String SELECT_USERS = "SELECT * FROM demo.users";

    public static final String SELECT_HABITS_BY_ID_USER = """
            select * from demo.habits
            where user_id = ?;
            """;

    public static final String SELECT_HABITS_BY_STATUS_BY_ID_USER = """
            select * from demo.habits
            where user_id = ?
            and status = ?;
            """;

    public static final String SELECT_HABITS_BY_DATE_BY_ID_USER = """
            select * from demo.habits
            where user_id = ?
            and create_at = ?;
            """;

    public static final String UPDATE_HABIT_TITLE = "UPDATE demo.habits SET title = ? WHERE id = ?";

    public static final String UPDATE_HABIT_DESCRIPTION= "UPDATE demo.habits SET description = ? WHERE id = ?";

    public static final String UPDATE_HABIT_REPEAT= "UPDATE demo.habits SET repeat = ? WHERE id = ?";

    public static final String UPDATE_HABIT_STATUS= "UPDATE demo.habits SET status = ? WHERE id = ?";

    public static final String DELETE_HABITS_BY_ID_USER = """
            delete from demo.habits
            where user_id = ?;
            """;

    public static final String DELETE_HABIT = "DELETE FROM demo.habits WHERE id = ?";

    public static final String SELECT_DONE_DATES_BY_ID_HABIT = """
            select done_date from demo.habits_donedates
            where habit_id = ?;
            """;

    public static final String INSERT_INTO_HABIT_DANEDATES = "insert into demo.habits_donedates values (?, ?);";

    public static final String UPDATE_HABIT_STREAK= "UPDATE demo.habits SET streak = ? WHERE id = ?";

    public static final String UPDATE_HABIT_PUSH_TIME = "UPDATE demo.habits SET push_time = ? WHERE id = ?";
}
