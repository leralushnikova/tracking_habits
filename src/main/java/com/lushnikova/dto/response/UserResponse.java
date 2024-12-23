package com.lushnikova.dto.response;


import com.lushnikova.model.enums.Role;
import lombok.*;

/**
 * Класс UserResponse является объект response для класса {@see User}
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    /** Поле идентификатор*/
    @Getter
    private Long id;

    /** Поле имя */
    @Getter
    private String name;

    /** Поле почта */
    @Getter
    private String email;

    /** Поле пароль */
    private String password;

    /** Поле блокировка пользователя */
    @Getter
    private Boolean isActive;

    /** Поле роль пользователь или админ */
    @Getter
    private Role role;

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                '}';
    }
}
