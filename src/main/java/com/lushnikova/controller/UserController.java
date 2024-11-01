package com.lushnikova.controller;

import com.lushnikova.annotations.Loggable;
import com.lushnikova.dto.request.HabitRequest;
import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.HabitResponse;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.middleware.DateMiddleware;
import com.lushnikova.middleware.Middleware;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;
import com.lushnikova.service.HabitService;
import com.lushnikova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.lushnikova.consts.StringConsts.*;
import static com.lushnikova.consts.WebConsts.USERS_PATH;

@Loggable
@RestController
@RequestMapping(USERS_PATH)
public class UserController {

    private final UserService userService;
    private final HabitService habitService;
    private final DateMiddleware dateMiddleware;
    private final Middleware middleware;

    @Autowired
    public UserController(UserService userService, HabitService habitService, DateMiddleware dateMiddleware, Middleware middleware) {
        this.userService = userService;
        this.habitService = habitService;
        this.dateMiddleware = dateMiddleware;
        this.middleware = middleware;
    }

    @GetMapping(value = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable("idUser") Long idUser) {

        return userService.findById(idUser)
                .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(value = "/{idUser}/habits", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HabitResponse> getHabits(@PathVariable("idUser") Long idUser,
                                         @RequestParam(value = "status", required = false) String status,
                                         @RequestParam(value = "date", required = false) String date) {

        if (status != null && date == null) return habitService.getHabitsByStatus(idUser, getStatus(status));
        else if (status == null && date != null) {
            if(dateMiddleware.checkDate(date)) return habitService.getHabitsByDate(idUser, Date.valueOf(date));
        }
        return habitService.findAll(idUser);
    }

    @GetMapping(value = "/{idUser}/habits/percent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPercent(@PathVariable("idUser") Long idUser,
                                              @RequestParam(value = "date_from", required = false) String date_from,
                                              @RequestParam(value = "date_to", required = false) String date_to) {
        if(dateMiddleware.checkDate(date_from) && dateMiddleware.checkDate(date_to)) {
            LocalDate dateFrom = LocalDate.parse(date_from);
            LocalDate dateTo = LocalDate.parse(date_to);
            return habitService.percentSuccessHabits(idUser, dateFrom, dateTo)
                    .map(integer -> new ResponseEntity<>(integer, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/{idUser}/habits/{idHabit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HabitResponse> getHabit(@PathVariable("idUser") Long idUser,
                                                  @PathVariable("idHabit") Long idHabit) {

        return habitService.findById(idHabit, idUser)
                .map(habitResponse -> new ResponseEntity<>(habitResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{idUser}/habits/{idHabit}/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reportHabit(@PathVariable("idUser") Long idUser,
                                                  @PathVariable("idHabit") Long idHabit) {

        return habitService.reportHabit(idHabit, idUser)
                .map(habitResponse -> new ResponseEntity<>(habitResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/habits/{idHabit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getHabitFulfillmentStatistics(@PathVariable("idHabit") Long idHabit,
                                                      @RequestParam(value = "statistics", required = false) String statistics,
                                                      @RequestParam(value = "date", required = false) String date) {

        if (dateMiddleware.checkDate(date)) {
            return habitService.getHabitFulfillmentStatistics(idHabit, LocalDate.parse(date), getStatistics(statistics));
        }
        return new ArrayList<>();
    }



    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        if (middleware.checkPassword(userRequest.getPassword()) && !middleware.checkEmail(userRequest)) {
            return userService.save(userRequest)
                    .map(habitResponse -> new ResponseEntity<>(habitResponse, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{idUser}")
    public void createHabit(@PathVariable("idUser") Long idUser,
                            @RequestBody HabitRequest habitRequest) {
        habitService.save(habitRequest, idUser);
    }



    @PutMapping(value = "/{idUser}")
    public void updateName(@PathVariable("idUser") Long idUser,
                           @RequestBody UserRequest userRequest) {

        if (userRequest.getName() != null) userService.updateName(idUser, userRequest.getName());
        else if (userRequest.getEmail() != null) userService.updateEmail(idUser, userRequest.getEmail());
        else if (userRequest.getPassword() != null) {
            if(middleware.checkPassword(userRequest.getPassword())) userService.updatePassword(idUser, userRequest.getPassword());
        }
    }

    @PutMapping(value = "/{idUser}/habits/{idHabit}")
    public void updateHabit(@PathVariable("idUser") Long idUser,
                            @PathVariable("idHabit") Long idHabit,
                            @RequestBody HabitRequest habitRequest) {

        if (habitRequest.getTitle() != null) habitService.updateTitleByIdHabit(idHabit, habitRequest.getTitle());
        else if (habitRequest.getDescription() != null)
            habitService.updateDescriptionByIdHabit(idHabit, habitRequest.getDescription());
        else if (habitRequest.getRepeat() != null) habitService.updateRepeatByIdHabit(idHabit, habitRequest.getRepeat());
        else if (habitRequest.getStatus() != null) habitService.updateStatusByIdHabit(idHabit, habitRequest.getStatus());
        else if (habitRequest.getPush() != null) {
            if (habitRequest.getPush().equals(NO)) {
                habitService.switchOnOrOffPushNotification(idHabit, null);
            } else {
                if (dateMiddleware.checkTime(habitRequest.getPush())) {
                    LocalTime time = LocalTime.parse(habitRequest.getPush());
                    habitService.switchOnOrOffPushNotification(idHabit, Time.valueOf(time));
                }
            }
        } else if (habitRequest.getDone() != null) {
            if(habitRequest.getDone().equals(YES)) {
                habitService.setDoneDates(idHabit, idUser);
            }
        }
    }


    @DeleteMapping(value = "/{idUser}")
    public void deleteUser(@PathVariable("idUser") Long idUser) {
        userService.delete(idUser);
    }

    @DeleteMapping(value = "/habits/{idHabits}")
    public void deleteHabit(@PathVariable("idHabits") Long idHabits) {
        habitService.delete(idHabits);
    }


    /**
     * Функция получения статуса привычки
     *
     * @param s - ввод
     * @return возвращает статус привычки
     */
    public static Status getStatus(String s) {
        switch (s) {
            case CREATE -> {
                return Status.CREATED;
            }
            case IN_PROGRESS -> {
                return Status.IN_PROGRESS;
            }
            case DONE -> {
                return Status.DONE;
            }
        }
        return null;
    }


    /**
     * Функция получения периода
     *
     * @param s - ввод
     * @return возвращает период
     */
    private Statistics getStatistics(String s) {
        switch (s) {
            case DAY -> {
                return Statistics.DAY;
            }
            case WEEK -> {
                return Statistics.WEEK;
            }
            case MONTH -> {
                return Statistics.MONTH;
            }
        }
        return null;
    }

}
