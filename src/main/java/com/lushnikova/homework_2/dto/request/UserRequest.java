package com.lushnikova.homework_2.dto.request;

import com.lushnikova.homework_2.model.ENUM.Role;
import lombok.*;


/**
 * Класс UserRequest является объект request для класса {@see User}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserRequest {
    /** Поле имя */
    private String name;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;

    /** Поле роль пользователь или админ */
    private Role role;
}
