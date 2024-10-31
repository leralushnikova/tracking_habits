package com.lushnikova.homework_3.service.impl;

import com.lushnikova.homework_3.annotations.Loggable;
import com.lushnikova.homework_3.dto.request.HabitRequest;
import com.lushnikova.homework_3.dto.request.UserRequest;
import com.lushnikova.homework_3.dto.response.HabitResponse;
import com.lushnikova.homework_3.dto.response.UserResponse;
import com.lushnikova.homework_3.exception.ModelNotFound;
import com.lushnikova.homework_3.mapper.HabitMapper;
import com.lushnikova.homework_3.mapper.UserMapper;
import com.lushnikova.homework_3.model.ENUM.Role;
import com.lushnikova.homework_3.model.Habit;
import com.lushnikova.homework_3.model.User;
import com.lushnikova.homework_3.model.ENUM.Repeat;
import com.lushnikova.homework_3.model.ENUM.Statistics;
import com.lushnikova.homework_3.model.ENUM.Status;
import com.lushnikova.homework_3.repository.UserRepository;
import com.lushnikova.homework_3.service.UserService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Класс Service по управлению пользователями и их привычек
 */
@Loggable
public class UserServiceImpl implements UserService {
    /** Поле преобразования привычек*/
    private final HabitMapper habitMapper;

    /** Поле преобразования пользователей*/
    private final UserMapper userMapper;

    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userRepository - репозиторий пользователей
     * инициализация поля преобразования привычек
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userMapper = UserMapper.INSTANCE;
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

    /** Функция получения пользователя {@link UserRepository#findById(Long)}
     * @param id - id пользователя
     * @return возвращает объект пользователя
     */
    @Override
    public UserResponse findById(Long id) {
        return userMapper.mapToResponse(userRepository.findById(id));
    }

    /**
     * Процедура обновления имени пользователя {@link UserRepository#updateName(Long, String)}
     * @param id - id пользователя
     * @param name - новое имя пользователя
     */
    @Override
    public void updateName(Long id, String name) {
        userRepository.updateName(id, name);
    }

    /**
     * Процедура обновления почты пользователя {@link UserRepository#updateEmail(Long, String)}
     * @param id - id пользователя
     * @param email - новое имя пользователя
     */
    @Override
    public void updateEmail(Long id, String email) {
        userRepository.updateEmail(id, email);
    }

    /**
     * Процедура обновления почты пользователя {@link UserRepository#updatePassword(Long, String)}
     * @param id - id пользователя
     * @param password - новое имя пользователя
     */
    @Override
    public void updatePassword(Long id, String password) {
        userRepository.updatePassword(id, password);
    }

    /**
     * Процедура обновления роли пользователя {@link User#setRole(Role)}
     * пользователя берем из {@link UserRepository#findById(Long)}
     *
     * @param id   - id пользователя
     * @param role - новая роль
     */
    @Override
    public void updateRole(Long id, Role role) throws ModelNotFound {
        userRepository.updateRole(id, role);
    }

    /**
     * Процедура удаления пользователя {@link UserRepository#delete(Long)}
     * @param id - id пользователя
     */
    @Override
    public void delete(Long id){
        userRepository.delete(id);
    }

    /**
     * Процедура добавления привычки пользователя {@link UserRepository#addHabitByIdUser(Long, Habit)}
     * @param idUser - id пользователя
     * @param habitRequest - привычка
     */
    @Override
    public void addHabitByIdUser(Long idUser, HabitRequest habitRequest) {
        Habit habit = habitMapper.mapToEntity(habitRequest);
        userRepository.addHabitByIdUser(idUser, habit);
    }

    /**
     * Функция получения списка привычек пользователя {@link UserRepository#getHabitsByIdUser(Long)}
     * @param idUser - id пользователя
     * @return возвращает список привычек по idUser
     */
    @Override
    public List<HabitResponse> getHabitsByIdUser(Long idUser) {
        return userRepository.getHabitsByIdUser(idUser).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Функция получения списка привычек пользователя по статусу {@link UserRepository#getHabitsByStatusByIdUser(Long, Status)}
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     */
    @Override
    public List<HabitResponse> getHabitsByStatusByIdUser(Long idUser, Status status) {
        return userRepository.getHabitsByStatusByIdUser(idUser, status).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Функция получения списка привычек пользователя по дате создания {@link UserRepository#getHabitsByLocalDateByIdUser(Long, LocalDate)}
     * @param idUser - id пользователя
     * @param localDate - дата создания
     * @return возвращает список привычек пользователя по дате создания
     */
    @Override
    public List<HabitResponse> getHabitsByLocalDateByIdUser(Long idUser, LocalDate localDate) {
        return userRepository.getHabitsByLocalDateByIdUser(idUser, localDate).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Процедура обновления названия привычки пользователя {@link UserRepository#updateTitleByIdHabitByIdUser(Long, Long, String)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newTitle - новое название привычки
     */
    @Override
    public void updateTitleByIdHabitByIdUser(Long idUser, Long idHabit, String newTitle) {
        userRepository.updateTitleByIdHabitByIdUser(idUser, idHabit, newTitle);
    }

    /**
     * Процедура обновления описания привычки пользователя {@link UserRepository#updateDescriptionByIdHabitByIdUser(Long, Long, String)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newDescription - новое описания привычки
     */
    @Override
    public void updateDescriptionByIdHabitByIdUser(Long idUser, Long idHabit, String newDescription) {
        userRepository.updateDescriptionByIdHabitByIdUser(idUser, idHabit, newDescription);
    }

    /**
     * Процедура обновления частоты выполнения привычки пользователя {@link UserRepository#updateRepeatByIdHabitByIdUser(Long, Long, Repeat)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newRepeat - новая частота выполнения
     */
    @Override
    public void updateRepeatByIdHabitByIdUser(Long idUser, Long idHabit, Repeat newRepeat) {
        userRepository.updateRepeatByIdHabitByIdUser(idUser, idHabit, newRepeat);
    }

    /**
     * Процедура обновления статуса привычки пользователя {@link UserRepository#updateStatusByIdHabitByIdUser(Long, Long, Status)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newStatus - новый статс
     */
    @Override
    public void updateStatusByIdHabitByIdUser(Long idUser, Long idHabit, Status newStatus) {
        userRepository.updateStatusByIdHabitByIdUser(idUser, idHabit, newStatus);
    }

    /**
     * Процедура удаления привычки пользователя {@link UserRepository#deleteHabitByIdUser(Long, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    @Override
    public void deleteHabitByIdUser(Long idUser, Long idHabit) {
        userRepository.deleteHabitByIdUser(idUser, idHabit);
    }

    /**
     * Функция получения генерации статистики выполнения привычки {@link UserRepository#getHabitFulfillmentStatisticsByIdUser(Long, Statistics, Long, LocalDate)}
     * @param idUser - id пользователя
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @param idHabit - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @return возвращает статистики выполнения привычки
     */
    @Override
    public List<String> getHabitFulfillmentStatisticsByIdUser(Long idUser, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return userRepository.getHabitFulfillmentStatisticsByIdUser(idUser, statistics, idHabit, dateFrom);
    }

    /**
     * Функция получения процента успешного выполнения привычек за определенный период {@link UserRepository#percentSuccessHabitsByIdUser(Long, LocalDate, LocalDate)}
     * @param idUser - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     */
    @Override
    public Integer percentSuccessHabitsByIdUser(Long idUser, LocalDate dateFrom, LocalDate dateTo) {
        return userRepository.percentSuccessHabitsByIdUser(idUser, dateFrom, dateTo);
    }

    /**
     * Функция получения отчета для пользователя по прогрессу выполнения привычки {@link UserRepository#reportHabitByIdUser(Long, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @return возвращает отчет по привычке
     */
    @Override
    public String reportHabitByIdUser(Long idUser, Long idHabit) {
        return userRepository.reportHabitByIdUser(idUser, idHabit);
    }

    /**
     * Процедура отметки даты, когда выполнялась привычка {@link UserRepository#setDoneDatesHabitByIdUser(Long, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    @Override
    public void setDoneDatesHabitByIdUser(Long idUser, Long idHabit) {
        userRepository.setDoneDatesHabitByIdUser(idUser, idHabit);
    }

    /**
     * Процедура включения отправки уведомления привычки в указанное время {@link UserRepository#switchOnPushNotificationByIdUser(Long, Long, LocalTime)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param pushTime - время уведомления привычки
     */
    @Override
    public void switchOnPushNotificationByIdUser(Long idUser, Long idHabit, LocalTime pushTime) {
        userRepository.switchOnPushNotificationByIdUser(idUser, idHabit, pushTime);
    }

    /**
     * Процедура выключения уведомления привычки {@link UserRepository#switchOffPushNotificationByIdUser(Long, Long)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    @Override
    public void switchOffPushNotificationByIdUser(Long idUser, Long idHabit) {
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

    /**
          * Процедура блокирования пользователя {@link UserRepository#setIsActiveByIdUser(Long, boolean)}
          * @param idUser - id пользователя
          * @param isActive - блокировка пользователя
          */
    @Override
    public void blockByIdUser(Long idUser, boolean isActive){
        userRepository.setIsActiveByIdUser(idUser, isActive);
    }
}
