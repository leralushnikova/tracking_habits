package com.lushnikova.homework_2.repository;

import com.lushnikova.homework_2.model.Habit;
import com.lushnikova.homework_2.model.enum_for_model.Repeat;
import com.lushnikova.homework_2.model.enum_for_model.Status;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Time;
import java.time.LocalDate;

import static com.lushnikova.homework_2.config.Environment.*;
import static org.junit.jupiter.api.Assertions.*;

class HabitRepositoryTest {
    private final static DockerImageName postgres = DockerImageName.parse("postgres:13.3");


    private HabitRepository habitRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(postgres.asCompatibleSubstituteFor("postgres"))
            .withExposedPorts(5432)
            .withUsername(getUSER())
            .withPassword(getPassword());


    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }


    @SneakyThrows
    @BeforeEach
    void setUp() {
        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("db/changelog/db.changelog-test.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
        habitRepository = new HabitRepository(connection);
    }

    @SneakyThrows
    @Test
    @DisplayName("Добавление привычки пользователю")
    void shouldSaveHabit() {
        String title = "new habit";
        Habit habit = Habit.builder()
                .title(title)
                .description("new habit")
                .repeat(Repeat.DAILY)
                .build();

        habitRepository.save(habit, 2L);

        assertNotNull(habitRepository.findAll(2L).stream().filter(el -> el.equals(title)));
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение названия привычки")
    void shouldUpdateTitleByIdHabit() {
        String title = "new habit";

        long id = 2L;
        habitRepository.updateTitleByIdHabit(id, title);

        assertEquals(habitRepository.findById(id).getTitle(), title);
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение описания привычки")
    void shouldUpdateDescriptionByIdHabit() {
        String newDescription = "new description";

        long id = 2L;
        habitRepository.updateDescriptionByIdHabit(id, newDescription);

        assertEquals(habitRepository.findById(id).getDescription(), newDescription);
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение частоты повторения привычки")
    void shouldUpdateRepeatByIdHabit() {
        Repeat repeat = Repeat.DAILY;

        long id = 2L;
        habitRepository.updateRepeatByIdHabit(id, repeat);

        assertEquals(habitRepository.findById(id).getRepeat(), repeat);
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение статуса привычки")
    void shouldUpdateStatusByIdHabit() {
        Status status = Status.DONE;

        long id = 2L;
        habitRepository.updateStatusByIdHabit(id, status);

        assertEquals(habitRepository.findById(id).getStatus(), status);
    }

    @SneakyThrows
    @Test
    @DisplayName("Удаление привычки")
    void shouldDelete() {

        long id = 2L;
        Habit habit = habitRepository.findById(id);

        habitRepository.delete(id);

        assertFalse(habitRepository.findAll(habit.getUserId()).contains(habit));
    }

    @SneakyThrows
    @Test
    @DisplayName("Отметка, что привычка выполнения")
    void shouldSetDoneDates() {
        long id = 2L;

        Date date = Date.valueOf(LocalDate.now());
        habitRepository.setDoneDates(id);

        Habit habit = habitRepository.findById(id);

        assertTrue(habit.getDoneDates().contains(date));
    }

    @SneakyThrows
    @Test
    @DisplayName("Выключение уведомления привычки")
    void shouldSwitchOnOrOffPushNotification() {

        Time time = Time.valueOf("11:10:00");

        long id = 2L;
        habitRepository.switchOnOrOffPushNotification(id, time);

        Habit habit = habitRepository.findById(id);

        assertEquals(habit.getPushTime(), time);
    }
}