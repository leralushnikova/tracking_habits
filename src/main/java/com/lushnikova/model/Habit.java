package com.lushnikova.model;

import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

/**
 * Класс Привычки
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Habit {
    /** Поле идентификатор*/
    @NotNull
    private Long id;

    /** Поле идентификатор пользователя*/
    @NotNull
    private Long userId;

    /** Поле название */
    @NotNull
    @Size(min = 2, max = 500, message = "min = 2, max = 50")
    private String title;

    /** Поле описание */
    private String description;

    /** Поле частота повторения */
    private Repeat repeat;

    /** Поле статус */
    private Status status;

    /** Поле дата создания */
    private Date createdAt;

    /** Поле текущая серии выполнений(сколько дней подряд выполнялась привычки)*/
    private Integer streak;

    /** Поле временя отправки уведомления */
    private Time pushTime;

    /** Поле история выполнения каждой привычки */
    private Set<Date> doneDates;
}
