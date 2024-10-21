package com.lushnikova.homework_2.repository;


import com.lushnikova.homework_2.model.Admin;
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
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;

import static com.lushnikova.homework_2.config.Environment.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Класс тестирования репозитория администраторов")
@Testcontainers
class AdminRepositoryTest {


    private final static DockerImageName postgres = DockerImageName.parse("postgres:13.3");


    private AdminRepository adminRepository;

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
        adminRepository = new AdminRepository(connection);
    }


    @SneakyThrows
    @Test
    @DisplayName("Сохранение администратора в репозиторий")
    void shouldSaveAdmin() {

        String email = "admin@lushnikova.com";
        Admin admin = Admin.builder()
                .email(email)
                .password("password")
                .build();

        adminRepository.save(admin);

        assertNotNull(adminRepository.findById(2L));
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение пароля администратора")
    void shouldUpdatePasswordAdmin() {
        String password = "password";
        long id = 1l;
        adminRepository.updatePassword(id, password);

        assertNotNull(adminRepository.findById(id).getPassword(), password);
    }

}