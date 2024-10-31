package com.lushnikova.model;

import com.lushnikova.model.enums.Role;
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
    private Long id;

    /** Поле имя */
    private String name;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;

    /**
     * Поле блокировка пользователя
     * isActive = true - пользователя активен
     * isActive = false - пользователя заблокирован
     */
    private Boolean isActive;

    /** Поле роль пользователь или админ */
    private Role role;
}
