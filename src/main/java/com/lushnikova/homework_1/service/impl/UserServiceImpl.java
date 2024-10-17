package com.lushnikova.homework_1.service.impl;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.mapper_mapstruct.HabitMapper;
import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.model.Habit;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.model.enums.Repeat;
import com.lushnikova.homework_1.model.enums.Statistics;
import com.lushnikova.homework_1.model.enums.Status;
import com.lushnikova.homework_1.service.UserService;
import com.lushnikova.homework_1.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Класс Service по управлению пользователями и их привычек
 */
public class UserServiceImpl implements UserService {
    /** Поле преобразования привычек*/
    private final HabitMapper habitMapper;

    /** Поле преобразования пользователей*/
    private final UserMapper userMapper;

    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userMapper - инструмент преобразования пользователей
     * @param userRepository - репозиторий пользователей
     * инициализация поля преобразования привычек
     */
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.habitMapper = HabitMapper.INSTANCE;
    }

    /** Процедура сохранения пользователя {@link UserRepository#save(User)}
     * @param userRequest - пользователя
     * @return возвращает объект пользователя
     */
    @Override
    public UserResponse save(UserRequest userRequest) {
        User user = userMapper.mapToEntity(userRequest);
        return userMapper.mapToResponse(userRepository.save(user));
    }

    /** Функция получения пользователя {@link UserRepository#findById(UUID)}
     * @param id - id пользователя
     * @return возвращает объект пользователя
     */
    @Override
    public UserResponse findById(UUID id) {
        return userMapper.mapToResponse(userRepository.findById(id));
    }

    /**
     * Процедура обновления имени пользователя {@link UserRepository#updateName(UUID, String)}
     * @param id - id пользователя
     * @param name - новое имя пользователя
     */
    @Override
    public void updateName(UUID id, String name) {
        userRepository.updateName(id, name);
    }

    /**
     * Процедура обновления почты пользователя {@link UserRepository#updateEmail(UUID, String)}
     * @param id - id пользователя
     * @param email - новое имя пользователя
     */
    @Override
    public void updateEmail(UUID id, String email) {
        userRepository.updateEmail(id, email);
    }

    /**
     * Процедура обновления почты пользователя {@link UserRepository#updatePassword(UUID, String)}
     * @param id - id пользователя
     * @param password - новое имя пользователя
     */
    @Override
    public void updatePassword(UUID id, String password) {
        userRepository.updatePassword(id, password);
    }

    /**
     * Процедура удаления пользователя {@link UserRepository#delete(UUID)}
     * @param id - id пользователя
     */
    @Override
    public void delete(UUID id){
        userRepository.delete(id);
    }

    /**
     * Процедура добавления привычки пользователя {@link UserRepository#addHabitByIdUser(UUID, Habit)}
     * @param idUser - id пользователя
     * @param habitRequest - привычка
     */
    @Override
    public void addHabitByIdUser(UUID idUser, HabitRequest habitRequest) {
        Habit habit = habitMapper.mapToEntity(habitRequest);
        userRepository.addHabitByIdUser(idUser, habit);
    }

    /**
     * Функция получения списка привычек пользователя {@link UserRepository#getHabitsByIdUser(UUID)}
     * @param idUser - id пользователя
     * @return возвращает список привычек по idUser
     */
    @Override
    public List<HabitResponse> getHabitsByIdUser(UUID idUser) {
        return userRepository.getHabitsByIdUser(idUser).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Функция получения списка привычек пользователя по статусу {@link UserRepository#getHabitsByStatusByIdUser(UUID, Status)}
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     */
    @Override
    public List<HabitResponse> getHabitsByStatusByIdUser(UUID idUser, Status status) {
        return userRepository.getHabitsByStatusByIdUser(idUser, status).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Функция получения списка привычек пользователя по дате создания {@link UserRepository#getHabitsByLocalDateByIdUser(UUID, LocalDate)}
     * @param idUser - id пользователя
     * @param localDate - дата создания
     * @return возвращает список привычек пользователя по дате создания
     */
    @Override
    public List<HabitResponse> getHabitsByLocalDateByIdUser(UUID idUser, LocalDate localDate) {
        return userRepository.getHabitsByLocalDateByIdUser(idUser, localDate).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Процедура обновления названия привычки пользователя {@link UserRepository#updateTitleByIdHabitByIdUser(UUID, Long, String)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newTitle - новое название привычки
     */
    @Override
    public void updateTitleByIdHabitByIdUser(UUID idUser, Long idHabit, String newTitle) {
        userRepository.updateTitleByIdHabitByIdUser(idUser, idHabit, newTitle);
    }

    /**
     * Процедура обновления описания привычки пользователя {@link UserRepository#updateDescriptionByIdHabitByIdUser(UUID, Long, String)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newDescription - новое описания привычки
     */
    @Override
    public void updateDescriptionByIdHabitByIdUser(UUID idUser, Long idHabit, String newDescription) {
        userRepository.updateDescriptionByIdHabitByIdUser(idUser, idHabit, newDescription);
    }

    /**
     * Процедура обновления частоты выполнения привычки пользователя {@link UserRepository#updateRepeatByIdHabitByIdUser(UUID, Long, Repeat)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newRepeat - новая частота выполнения
     */
    @Override
    public void updateRepeatByIdHabitByIdUser(UUID idUser, Long idHabit, Repeat newRepeat) {
        userRepository.updateRepeatByIdHabitByIdUser(idUser, idHabit, newRepeat);
    }

    /**
     * Процедура обновления статуса привычки пользователя {@link UserRepository#updateStatusByIdHabitByIdUser(UUID, Long, Status)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newStatus - новый статс
     */
    @Override
    public void updateStatusByIdHabitByIdUser(UUID idUser, Long idHabit, Status newStatus) {
        userRepository.updateStatusByIdHabitByIdUser(idUser, idHabit, newStatus);
    }

    /**
     * Процедура удаления привычки пользователя {@link UserRepository#deleteHabitByIdUser(UUID, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    @Override
    public void deleteHabitByIdUser(UUID idUser, Long idHabit) {
        userRepository.deleteHabitByIdUser(idUser, idHabit);
    }

    /**
     * Функция получения генерации статистики выполнения привычки {@link UserRepository#getHabitFulfillmentStatisticsByIdUser(UUID, Statistics, Long, LocalDate)}
     * @param idUser - id пользователя
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @param idHabit - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @return возвращает статистики выполнения привычки
     */
    @Override
    public List<String> getHabitFulfillmentStatisticsByIdUser(UUID idUser, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return userRepository.getHabitFulfillmentStatisticsByIdUser(idUser, statistics, idHabit, dateFrom);
    }

    /**
     * Функция получения процента успешного выполнения привычек за определенный период {@link UserRepository#percentSuccessHabitsByIdUser(UUID, LocalDate, LocalDate)}
     * @param idUser - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     */
    @Override
    public int percentSuccessHabitsByIdUser(UUID idUser, LocalDate dateFrom, LocalDate dateTo) {
        return userRepository.percentSuccessHabitsByIdUser(idUser, dateFrom, dateTo);
    }

    /**
     * Процедура формирование отчета для пользователя по прогрессу выполнения привычки {@link UserRepository#reportHabitByIdUser(UUID, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    @Override
    public void reportHabitByIdUser(UUID idUser, Long idHabit) {
        userRepository.reportHabitByIdUser(idUser, idHabit);
    }

    /**
     * Процедура отметки даты, когда выполнялась привычка {@link UserRepository#setDoneDatesHabitByIdUser(UUID, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    @Override
    public void setDoneDatesHabitByIdUser(UUID idUser, Long idHabit) {
        userRepository.setDoneDatesHabitByIdUser(idUser, idHabit);
    }

    /**
     * Процедура определения значения поля {@link UserRepository#setIsActiveByIdUser(UUID, boolean)}
     * @param idUser - id пользователя
     * @param isActive - блокировка
     */
    public void setIsActiveByIdUser(UUID idUser, boolean isActive) {
        userRepository.setIsActiveByIdUser(idUser, isActive);
    }

    /**
     * Процедура включения отправки уведомления привычки в указанное время {@link UserRepository#switchOnPushNotificationByIdUser(UUID, Long, LocalTime)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param pushTime - время уведомления привычки
     */
    @Override
    public void switchOnPushNotificationByIdUser(UUID idUser, Long idHabit, LocalTime pushTime) {
        userRepository.switchOnPushNotificationByIdUser(idUser, idHabit, pushTime);
    }

    /**
     * Процедура выключения уведомления привычки {@link UserRepository#switchOffPushNotificationByIdUser(UUID, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    @Override
    public void switchOffPushNotificationByIdUser(UUID idUser, Long idHabit) {
        userRepository.switchOffPushNotificationByIdUser(idUser, idHabit);
    }

    /**
     * Функция получения списка администраторов {@link UserRepository#findAll()}
     * @return возвращает копию списка администраторов
     */
    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToResponse).toList();
    }
}
