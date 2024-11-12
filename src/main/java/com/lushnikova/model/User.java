package com.lushnikova.model;

import com.lushnikova.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * Класс Пользователя
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    /** Поле идентификатор*/
    @NotNull
    private Long id;

    /** Поле имя */
    @NotNull
    @Size(min = 3, max = 30, message = "min = 3, max = 30")
    private String name;

    /** Поле почта */
    @NotNull
    @Size(min = 3, max = 30, message = "min = 3, max = 30")
    private String email;

    /** Поле пароль */
    @NotNull
    @Size(min = 3, max = 30, message = "min = 3, max = 30")
    private String password;

    /**
     * Поле блокировка пользователя
     * isActive = true - пользователя активен
     * isActive = false - пользователя заблокирован
     */
    @NotNull
    private Boolean isActive;

    /** Поле роль пользователь или админ */
    @NotNull
    private Role role;
}
