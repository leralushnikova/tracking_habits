package com.lushnikova.dto.response;

import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;
import lombok.*;

import java.sql.Time;
import java.util.Set;

/**
 * Класс HabitResponse является объект response для класса {@see Habit}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HabitResponse {
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
    private String createdAt;

    /** Поле текущая серии выполнений(сколько дней подряд выполнялась привычки)*/
    private Integer streak;

    /** Поле временя отправки уведомления */
    private Time pushTime;

    /** Поле история выполнения каждой привычки */
    private Set<String> doneDates;
}
