package com.lushnikova.repository;

import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;
import com.lushnikova.model.Habit;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.lushnikova.consts.SQL_Query.*;


/**
 * Класс Repository для привычек
 */
@Repository
public class HabitRepository {

    /**
     * Поле соединение бд
     */
    private final Connection connection;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param source - соединение б/д
     */
    @SneakyThrows
    @Autowired
    public HabitRepository(DataSource source) {
        this.connection = source.getConnection();
    }

    /**
     * Процедура сохранения привычки
     * @param habit - объект привычки
     * @param idUser - id пользователя
     * @throws SQLException
     */
    public void save(Habit habit, Long idUser) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_HABITS)){
            preparedStatement.setLong(1, idUser);
            preparedStatement.setString(2, habit.getTitle());
            preparedStatement.setString(3, habit.getDescription());
            preparedStatement.setString(4, habit.getRepeat().name());
            preparedStatement.setString(5, Status.CREATED.name());
            preparedStatement.setDate(6, Date.valueOf(LocalDate.now().toString()));
            preparedStatement.executeUpdate();
        }
    }


    /**
     * Функция получения привычки
     * @param idHabit - id привычки
     * @param idUser- id пользователя
     * @return возвращает объект привычки
     * @throws SQLException
     */
    public Habit findById(Long idHabit, Long idUser) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HABIT_BY_ID)){
            preparedStatement.setLong(1, idHabit);
            preparedStatement.setLong(2, idUser);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return getHabit(resultSet);
            }
        }
    }

    /**
     * Функция получения привычки
     * @param resultSet - результат получения запроса из б/д
     * @return возвращение объекта привычки
     * @throws SQLException
     */
    private Habit getHabit(ResultSet resultSet) throws SQLException {
        Habit habit = new Habit();
        long id = resultSet.getLong("id");
        habit.setId(id);
        habit.setUserId(resultSet.getLong("user_id"));
        habit.setTitle(resultSet.getString("title"));
        habit.setDescription(resultSet.getString("description"));

        String repeatString = resultSet.getString("repeat");
        Repeat repeat = null;
        switch (repeatString) {
            case "DAILY" -> repeat = Repeat.DAILY;
            case "WEEKLY" -> repeat = Repeat.WEEKLY;
        }

        habit.setRepeat(repeat);

        String statusString = resultSet.getString("status");
        Status status = null;
        switch (statusString) {
            case "CREATED" -> status = Status.CREATED;
            case "IN_PROGRESS" -> status = Status.IN_PROGRESS;
            case "DONE" -> status = Status.DONE;
        }
        habit.setStatus(status);

        habit.setCreatedAt(resultSet.getDate("create_at"));
        habit.setStreak(resultSet.getInt("streak"));
        habit.setPushTime(resultSet.getTime("push_time"));
        habit.setDoneDates(listDoneDates(id));
        return habit;
    }

    /**
     * Функция получения списка привычек пользователя
     * @param idUser - id пользователя
     * @return возвращает список привычек пользователя
     */
    public List<Habit> findAll(Long idUser) throws SQLException {

        List<Habit> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HABITS_BY_ID_USER)){
            preparedStatement.setLong(1, idUser);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {

                    list.add(getHabit(resultSet));
                }
                return list;
            }
        }
    }

    /**
     * Функция получения списка привычек пользователя по статусу
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     * @throws SQLException
     */
    public List<Habit> getHabitsByStatus(Long idUser, Status status) throws SQLException {

        List<Habit> list = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HABITS_BY_STATUS_BY_ID_USER)){
            preparedStatement.setLong(1, idUser);
            preparedStatement.setString(2, status.name());

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {

                    list.add(getHabit(resultSet));
                }
                return list;
            }
        }
    }

    /**
     * Функция получения списка привычек пользователя по дате создания
     *
     * @param idUser - id пользователя
     * @param date   - дата создания
     * @return возвращает список привычек пользователя по дате создания
     * @throws SQLException
     */
    public List<Habit> getHabitsByDate(Long idUser, Date date) throws SQLException {

        List<Habit> list = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HABITS_BY_DATE_BY_ID_USER)){
            preparedStatement.setLong(1, idUser);
            preparedStatement.setDate(2, date);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    list.add(getHabit(resultSet));
                }
                return list;
            }
        }
    }


    /**
     * Процедура обновления названия привычки
     * @param id       - id привычки
     * @param newTitle - новое название привычки
     * @throws SQLException
     */
    public void updateTitleByIdHabit(Long id, String newTitle) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HABIT_TITLE)){
            preparedStatement.setString(1, newTitle);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Процедура обновления описания привычки
     * @param id             - id привычки
     * @param newDescription - новое описания привычки
     * @throws SQLException
     */
    public void updateDescriptionByIdHabit(Long id, String newDescription) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HABIT_DESCRIPTION)){
            preparedStatement.setString(1, newDescription);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Процедура обновления частоты выполнения привычки
     * @param id        - id привычки
     * @param newRepeat - новая частота выполнения
     * @throws SQLException
     */
    public void updateRepeatByIdHabit(Long id, Repeat newRepeat) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HABIT_REPEAT)){
            preparedStatement.setString(1, newRepeat.name());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Процедура обновления статуса привычки
     * @param id        - id привычки
     * @param newStatus - новый статс
     * @throws SQLException
     */
    public void updateStatusByIdHabit(Long id, Status newStatus) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HABIT_STATUS)){
            preparedStatement.setString(1, newStatus.name());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Процедура удаления привычки
     * @param id - id привычки
     * @throws SQLException
     */
    public void delete(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HABIT)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Функция получения генерации статистики выполнения привычки
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @param id         - id привычки
     * @param dateFrom   - дата, с какого числа нужно посчитать статистику
     * @return возвращает статистики выполнения привычки
     * @throws SQLException
     */
    public List<String> getHabitFulfillmentStatistics(Long id, LocalDate dateFrom, Statistics statistics) throws SQLException {
        int days = 0;
        switch (statistics) {
            case DAY -> days = 1;
            case WEEK -> days = 7;
            case MONTH -> days = 31;
        }

        List<String> list = new ArrayList<>();
        LocalDate dateTo = dateFrom.plusDays(days);

        while (!dateTo.isEqual(dateFrom)) {
            StringBuilder sb = new StringBuilder();
            sb.append(dateFrom).append("\t");

            Set<Date> listDates = listDoneDates(id);
            boolean flag = false;
            for (Date date : listDates) {
                if (dateFrom.toString().equals(date.toString())) {
                    flag = true;
                    break;
                }
            }
            if (flag) sb.append(" + ");
            else sb.append(" - ");
            list.add(sb.toString());
            dateFrom = dateFrom.plusDays(1);
        }


        return list;
    }

    /**
     * Функция получения списка дат выполнены задач
     * @param id - id пользователя
     * @return возвращает даты выполнения привычки
     * @throws SQLException
     */
    private Set<Date> listDoneDates(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DONE_DATES_BY_ID_HABIT)){
            preparedStatement.setLong(1, id);

            Set<Date> date = new TreeSet<>();

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    date.add(resultSet.getDate("done_date"));
                }
                return date;
            }
        }
    }

    /**
     * Процедура отметки даты, когда выполнялась привычка
     * и подсчета текущей серии выполнения
     * @param idHabit - id привычки
     * @throws SQLException
     */
    public void setDoneDates(Long idHabit, Long idUser) throws SQLException {
        LocalDate date = LocalDate.now();

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_HABIT_DANEDATES);
            PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_HABIT_STREAK)){

            preparedStatement.setLong(1, idHabit);
            preparedStatement.setDate(2, Date.valueOf(date.toString()));
            preparedStatement.executeUpdate();

            Set<Date> dates = listDoneDates(idHabit);
            int streak = findById(idHabit, idUser).getStreak();

            if (dates.toString().contains(date.minusDays(1).toString())) {
                streak++;
            } else streak = 1;

            preparedStatement1.setInt(1, streak);
            preparedStatement1.setLong(2, idHabit);

            preparedStatement1.executeUpdate();
        }
    }

    /**
     * Функция получения процента успешного выполнения привычек за определенный период
     * @param idUser   - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo   - по какое число
     * @return возвращает процент успешного выполнения привычек
     * @throws SQLException
     */
    public int percentSuccessHabits(Long idUser, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        long resultDays = ChronoUnit.DAYS.between(dateFrom, dateTo);

        List<Habit> habits = findAll(idUser);

        int allHabits = habits.size();
        int sumHabit = 0;

        while (!dateTo.isEqual(dateFrom)) { //последний день не включается

            for (Habit habit : habits) {
                Set<Date> listDates = listDoneDates(habit.getId());
                habit.setDoneDates(listDates);
                for (Date date : listDates) {
                    if (date.toString().equals(dateFrom.toString())) {
                        sumHabit++;
                        break;
                    }
                }
            }

            dateFrom = dateFrom.plusDays(1);
        }

        return (int) (Math.round((double) (sumHabit * 100) / (double) (allHabits * resultDays)));
    }

    /**
     * Функция получения отчета для пользователя по прогрессу выполнения привычки
     * @param idHabit - id привычки
     * @param idUser - id пользователя
     * @return возвращает отчет по привычке
     * @throws SQLException
     */
    public String reportHabit(Long idHabit, Long idUser) throws SQLException {
        StringBuilder builder = new StringBuilder();

        Habit habit = findById(idHabit, idUser);

        builder.append("User's id: ").append(habit.getId()).append("\n");
        builder.append("Title: ").append(habit.getTitle()).append("\n");
        builder.append("Description: ").append(habit.getDescription()).append("\n");
        builder.append("Status: ").append(habit.getStatus()).append("\n");
        builder.append("Repeat: ").append(habit.getRepeat()).append("\n");

        StringBuilder stringBuilder = new StringBuilder("Notification : ");
        if (habit.getPushTime() == null) stringBuilder.append("off");
        else stringBuilder.append("on").append(" at ").append(habit.getPushTime());

        builder.append(stringBuilder).append("\n");
        builder.append("Streak: ").append(habit.getStreak()).append("\n");
        builder.append("Done dates: ").append(habit.getDoneDates()).append("\n");

        return builder.toString();
    }

    /**
     * Процедура включения/выключения отправки уведомления привычки в указанное время
     * @param id       - id привычки
     * @param pushTime - время уведомления привычки
     * @throws SQLException
     */
    public void switchOnOrOffPushNotification(Long id, Time pushTime) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HABIT_PUSH_TIME)){
            preparedStatement.setTime(1, pushTime);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

}

