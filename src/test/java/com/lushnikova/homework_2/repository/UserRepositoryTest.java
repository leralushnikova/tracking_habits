package com.lushnikova.homework_2.repository;

import com.lushnikova.homework_2.model.User;
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
import java.sql.DriverManager;

import static com.lushnikova.homework_2.config.Environment.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final static DockerImageName postgres = DockerImageName.parse("postgres:13.3");


    private UserRepository userRepository;

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
        userRepository = new UserRepository(connection);
    }

    @SneakyThrows
    @Test
    @DisplayName("Сохранение пользователя в репозиторий")
    void shouldSaveUser() {
        User user = User.builder()
                .name("lera")
                .email("lera@gmail.com")
                .password("123")
                .build();

        int size = userRepository.findAll().size();

        userRepository.save(user);

        assertNotNull(userRepository.findById((long) size));
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение имени пользователя")
    void shouldUpdateNameUser() {

        String newName = "new_name";

        long id = 2L;
        userRepository.updateName(id, newName);


        assertEquals(userRepository.findById(id).getName(), newName);
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение пароля пользователя")
    void shouldUpdatePasswordUser() {
        String newPassword = "new_password";

        long id = 2L;
        userRepository.updatePassword(id, newPassword);

        assertEquals(userRepository.findById(id).getPassword(), newPassword);
    }

    @SneakyThrows
    @Test
    @DisplayName("Удаление пользователя")
    void shouldDeleteUser() {

        long id = 2L;
        User user = userRepository.findById(id);

        userRepository.delete(id);

        assertFalse(userRepository.findAll().contains(user));
    }

    @SneakyThrows
    @Test
    @DisplayName("Блокировка пользователя>")
    void shouldBlockUser() {

        boolean block = false;
        long id = 2L;

        userRepository.setIsActive(id, block);

        assertEquals(userRepository.findById(id).getIsActive(), block);
    }

}