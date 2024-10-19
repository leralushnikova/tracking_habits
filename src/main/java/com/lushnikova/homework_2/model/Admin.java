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
    private Long id;
    private String email;
    private String password;
}


