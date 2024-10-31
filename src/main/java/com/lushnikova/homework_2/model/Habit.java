package com.lushnikova.homework_2.model;

import com.lushnikova.homework_2.model.ENUM.Repeat;
import com.lushnikova.homework_2.model.ENUM.Status;
import lombok.*;

import java.sql.Time;
import java.sql.Date;
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
    private Long id;

    /** Поле идентификатор пользователя*/
    private Long userId;

    /** Поле название */
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
