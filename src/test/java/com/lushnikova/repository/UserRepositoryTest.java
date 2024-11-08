package com.lushnikova.repository;

import com.lushnikova.config.TestConfig;
import com.lushnikova.model.User;
import com.lushnikova.model.enums.Role;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class UserRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:13.3")
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.liquibase.contexts", () -> "!prod");
    }

    @Autowired
    UserRepository userRepository;

    @SneakyThrows
    @Test
    @DisplayName("Сохранение пользователя в репозиторий")
    void shouldSaveUser() {
        String name = "user";
        User user = User.builder()
                .email("lera@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
        user.setName(name);

        userRepository.save(user);

        assertNotNull(userRepository.findAll().stream().filter(el -> el.getName().equals(name)));
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