package com.lushnikova.homework_2.model;

import com.lushnikova.homework_2.model.enum_for_model.Repeat;
import com.lushnikova.homework_2.model.enum_for_model.Status;
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
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Repeat repeat;
    private Status status;
    private Date createdAt;
    private Integer streak;
    private Time pushTime;
    private Set<Date> doneDates;
}
