package com.lushnikova.homework_2.dto.resp;

import lombok.*;


/**
 * Класс AdminResponse является объект response для класса {@see Admin}
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {

    /** Поле уникальный идентификатор */
    @Getter
    private Long id;


    /** Поле почта */
    @Getter
    private String email;


    /** Поле пароль */
    private String password;

    @Override
    public String toString() {
        return "AdminResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
