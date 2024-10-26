package com.lushnikova.homework_1.consts;

/**
 * Класс констант для выбора режимов
 */
public class ModesConsts {

    public static final String ADMIN_OR_USER = """
            Вы хотите войти как:
            1 - администратор
            2 - пользователь
            exit - выход
            """;


    public static final String MODES_FOR_USER_ADMIN = """
            Выберите:
            1 - просмотреть список пользователей и их привычки
            2 - заблокировать пользователя
            3 - удалить пользователя
            exit - выход
            """;


    public static final String BLOCK_USER = """
            Выберите:
            1 - заблокировать пользователя
            2 - разблокировать пользователя
            """;

    public static final String ENTER_USER = "Вы хотите войти(in), зарегистрироваться(up) или выйти(exit)?[in/up/exit]";


    public static final String MODES_FOR_USER = """
            Выберите режим:
            1 - Редактировать данные пользователя
            2 - Просмотр данных пользователя
            3 - Управления режимами привычек
            4 - Просмотр привычек
            5 - Статистика привычек
            6 - Процент успешного выполнения привычек
            7 - Отчет по привычке
            exit - выход
            """;

    public static final String CRUD_HABITS = """
            Выберите:
            1 - создать
            2 - удалить
            3 - редактировать привычку
            4 - отметить выполненную привычку
            5 - включить/выключить уведомление привычки
            exit - выход из режима управления привычек
            """;

    public static final String UPDATE_HABIT = """
            Отредактировать:
            1 - название привычки
            2 - описание привычки
            3 - повторение привычки
            4 - статус привычки
            exit - выход из редактирования привычки
            """;

    public static final String READ_HABITS = """
            Выберите список привычек:
            1 - полный список
            2 - отфильтрованный по дате создания
            3 - отфильтрованный по статусу
            exit - выход из списка привычек
            """;

    public static final String SWITCH_ON_OR_OFF_NOTIFICATION = """
            Вы хотите включить или выключить привычку? [on/off]
            """;

    public static final String GET_ERROR_HABITS = """
            У вас еще не привычек!
            ----------------------------------------------
            """;

    public static final String GET_ERROR_HABIT = """
            Такая привычка не найдена!
            ----------------------------------------------
            """;

    public static final String REPEAT_HABIT = """
            Как часто ее нужно выполнять:
             d - ежедневно
             w - еженедельно
            """;

    public static final String STATUS_HABIT = """
            Выберите статус привычки:
             1 - создана
             2 - в процессе
             3 - готово
            """;
    public static final String STATISTICS_HABIT = """
            Выберите за какой период вы хотите получить привычку:
            1 - день
            2 - неделя
            3 - месяц
            """;

    public static final String MESSAGE_PASSWORD = """
            Пароль должен содержать 8 символов латинского алфавита,
            минимум одну заглавную букву,
            одну маленькую букву либо спец. символ, либо цифру.
            """;

    public static final String EDIT_USER = """
            Вы хотите изменить:
            1 - имя
            2 - email
            3 - пароль
            4 - удалить профиль
            exit - выход из режима редактирования пользователя
            """;

    public static final String RECOVER_PASSWORD = """
            Пароль введен не верно!
            Хотите восстановить пароль(y) или попробовать еще попытку(n)? [y/n]
            """;

    public static final String WRONG_INPUT = """
            Неправильный ввод!
            ----------------------------------------------
            """;





}
