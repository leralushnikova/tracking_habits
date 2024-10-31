package com.lushnikova.dto.request;

import com.lushnikova.model.enums.Repeat;
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
}
