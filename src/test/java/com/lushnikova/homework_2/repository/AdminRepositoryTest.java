package com.lushnikova.homework_2.repository;


import com.lushnikova.homework_2.model.Admin;
import liquibase.Liquibase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;

import static com.lushnikova.homework_2.config.Environment.PASSWORD;
import static com.lushnikova.homework_2.config.Environment.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс тестирования репозитория администраторов")
@Testcontainers
class AdminRepositoryTest {


    private final static DockerImageName postgres = DockerImageName.parse("postgres:13.3");

    static String schema = "CREATE SCHEMA demo";
    static String table = "create demo.table admins(id int primary key, email varchar(30), password varchar(30))";
    static final String SELECT_ADMIN_BY_EMAIL = "SELECT * FROM demo.admins WHERE id = ?";


    private AdminRepository adminRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(postgres.asCompatibleSubstituteFor("postgres"))
            .withExposedPorts(5432)
            .withUsername(USER)
            .withPassword(PASSWORD)
            .withInitScript(schema)
            .withInitScript(table);



    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
        postgreSQLContainer.close();
    }


    @SneakyThrows
    @BeforeEach
    void setUp() {
        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
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

        assertEquals(admin, findByEmail(email));
    }

    @SneakyThrows
    private Admin getAdmin(ResultSet resultSet) {
        Admin admin = new Admin();
        admin.setId(resultSet.getLong("id"));
        admin.setEmail(resultSet.getString("email"));
        admin.setPassword(resultSet.getString("password"));
        return admin;
    }

    /*@SneakyThrows
    public Admin findById(Long id){
        Admin admin = null;
        try(Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ADMIN_BY_ID)){
            preparedStatement.setLong(1, id);


            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            admin = getAdmin(resultSet);
        }
        return admin;
    }*/

    @SneakyThrows
    public Admin findByEmail(String email) {
        Admin admin;
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ADMIN_BY_EMAIL)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            admin = getAdmin(resultSet);
        }

        return admin;
    }


}