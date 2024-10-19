package com.lushnikova.homework_2;

import com.lushnikova.homework_2.model.Admin;
import com.lushnikova.homework_2.model.Habit;
import com.lushnikova.homework_2.model.User;
import com.lushnikova.homework_2.model.enum_for_model.Repeat;
import com.lushnikova.homework_2.repository.AdminRepository;
import com.lushnikova.homework_2.repository.HabitRepository;
import com.lushnikova.homework_2.repository.UserRepository;

import java.sql.*;

import static com.lushnikova.homework_2.config.Environment.*;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
//            Statement statement = connection.createStatement();
            UserRepository userRepository = new UserRepository(connection);


            User user = User.builder()
                    .name("James")
                    .email("james@gmail.com")
                    .password("james")
                    .isActive(true)
                    .build();

//            userRepository.save(user);


//            insertRecords(connection);

//            updateRecords(connection);
//            deleteRecords(connection);


/*ResultSet resultSet = statement.executeQuery("select * From demo.users");
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                boolean isActive = resultSet.getBoolean("is_active");
                System.out.println(id + " " + name + " " + email + " " + password + " " + isActive);
            }*/


//            userRepository.updatePassword(3L, "new password");
//            userRepository.delete(1L);
//            System.out.println(userRepository.findAll());

            HabitRepository habitRepository = new HabitRepository(connection);
            Habit habit = Habit.builder()
                    .title("habit_from")
                    .description("habit_from")
                    .repeat(Repeat.DAILY)
                    .build();

//            habitRepository.save(habit, 2L);

//            System.out.println(habitRepository.findById(1L));

//            System.out.println(habitRepository.findAll(1L));

//            System.out.println(habitRepository.getHabitsByStatus(1L, Status.DONE));
//            System.out.println(habitRepository.getHabitsByDate(1L, Date.valueOf("2024-10-18")));

//            habitRepository.updateDescriptionByIdHabit(1L, "new description");
//            habitRepository.updateRepeatByIdHabit(1L, Repeat.WEEKLY);
//            habitRepository.updateStatusByIdHabit(1L, Status.DONE);
//            habitRepository.delete(2L);

//            System.out.println(habitRepository.getHabitFulfillmentStatistics(1L, LocalDate.of(2024,10,8), Statistics.WEEK));
            long id = 1L;
//            habitRepository.setDoneDates(id);
            System.out.println(habitRepository.findById(id));
//            System.out.println(habitRepository.findById(id).getStreak());

//            System.out.println(habitRepository.percentSuccessHabits(1L, LocalDate.of(2024,10,8), LocalDate.now()));
//            habitRepository.reportHabit(1L);
//            habitRepository.switchOnOrOffPushNotification(2L, null);
//            userRepository.setIsActive(1L, false);
//            System.out.println();
//
//            AdminRepository adminRepository = new AdminRepository(connection);
//            Admin admin = Admin.builder()
//                    .id(2L)
//                    .email("admin@lushnikova.com")
//                    .password("admin2")
//                    .build();
//            adminRepository.save(admin);
//            System.out.println(adminRepository.findById(1L));

//            adminRepository.updatePassword(1L, "new password");
//            System.out.println(adminRepository.findAll());
        } catch (SQLException e) {
            System.out.println("Got SQLException: " + e.getMessage());
            System.exit(0);
        }
    }

    //создание пользователя
    private static void insertRecords(Connection connection) throws SQLException {
        String insertDataSql = "insert into demo.users(id, name, email, password, is_active) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
        preparedStatement.setInt(1, 5);
        preparedStatement.setString(2, "lera");
        preparedStatement.setString(3, "lera@gmail.com");
        preparedStatement.setString(4, "password");
        preparedStatement.setBoolean(5, true);
        preparedStatement.executeUpdate();
    }

    //обновление данных у пользователя
    private static void updateRecords(Connection connection) throws SQLException {
        String updateDataSql = "update demo.users set is_active = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateDataSql);
        preparedStatement.setBoolean(1, false);
        preparedStatement.setInt(2, 4);
        preparedStatement.executeUpdate();
    }

    //удаление пользователя
    private static void deleteRecords(Connection connection) throws SQLException {
        String deleteDataSql = "delete from demo.users where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteDataSql);
        preparedStatement.setInt(1, 5);
        preparedStatement.executeUpdate();
    }

    //нахождение пользователя по id
    private static void getRecords(Connection connection) throws SQLException {
        String selectDataSql = "select * from demo.users where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectDataSql);
        preparedStatement.setInt(1, 4);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        final User user = User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .isActive(resultSet.getBoolean("is_active"))
                .build();

        System.out.println(user);
        System.out.println(resultSet.getInt("id") + " " +
                resultSet.getString("name") + " " +
                resultSet.getString("email") + " " +
                resultSet.getString("password") + " " +
                resultSet.getBoolean("is_active"));

//        preparedStatement.executeUpdate();
    }
}

