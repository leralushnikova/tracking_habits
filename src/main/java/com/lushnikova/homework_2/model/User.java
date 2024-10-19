package com.lushnikova.homework_2.model;

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
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
}
