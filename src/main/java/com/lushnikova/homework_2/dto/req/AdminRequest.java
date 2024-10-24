package com.lushnikova.homework_2.dto.req;

import lombok.*;


/**
 * Класс AdminRequest является объект request для класса {@see Admin}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminRequest {
    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;
}
