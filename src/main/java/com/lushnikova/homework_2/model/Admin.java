package com.lushnikova.homework_2.model;

import lombok.*;


/**
 * Класс Администратора
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Admin {
    /** Поле идентификатор*/
    private Long id;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;
}


