package com.lushnikova.dto.request;

import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;
import lombok.*;

/**
 * Класс HabitRequest является объект request для класса {@see Habit}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HabitRequest {
    /** Поле название */
    private String title;

    /** Поле описание */
    private String description;

    /** Поле частота повторения */
    private Repeat repeat;

    /** Поле статус */
    private Status status;

    /** Поле временя отправки уведомления */
    private String push;

    /** Поле отметка даты выполнения привычки */
    private String done;
}
