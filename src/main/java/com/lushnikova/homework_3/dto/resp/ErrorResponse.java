package com.lushnikova.homework_3.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс Response обработки ошибок
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    /** Поле название ошибки */
    private String error;

    /** Поле статус код */
    private Integer status;

    /** Поле время и дата возникновения ошибки */
    private String time;

    /** Поле уникальный идентификатор ошибки*/
    private UUID errorId;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param error - название ошибки
     * @param status - статус код
     */
    public ErrorResponse(String error, Integer status) {
        this.error = error;
        this.status = status;
        this.time = LocalDateTime.now().toString();
        this.errorId = UUID.randomUUID();
    }
}