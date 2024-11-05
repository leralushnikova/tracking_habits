package com.lushnikova.aspect;

import org.aspectj.lang.annotation.Pointcut;

/** Класс поинткатов для аспектов*/
public class PointCuts {
    @Pointcut("within(@com.lushnikova.annotations.Loggable *)")
    public void isAnnotateLoggable() {}

    @Pointcut("execution(* * (..))")
    public void isAllExecution() {}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.UserService.save(..))")
    public void saveUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.UserService.findById(..))")
    public void anyUserFindByIdService(){}

    @Pointcut("isAnnotateLoggable() && execution(* update*(..))")
    public void updateUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.UserService.delete(..))")
    public void deleteUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.UserService.findAll())")
    public void findAllUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.HabitService.save(..))")
    public void saveHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.HabitService.findById(..))")
    public void anyHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.HabitService.findAll(..))")
    public void findAllHabitsForUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.HabitService.getHabitsByStatus(..))")
    public void findHabitsByStatusForUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.HabitService.getHabitsByDate(..))")
    public void findHabitsByDatesForUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* updateTitleByIdHabitByIdUser(..))")
    public void updateHabitTitleServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* updateDescriptionByIdHabitByIdUser(..))")
    public void updateHabitDescriptionServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* updateRepeatByIdHabitByIdUser(..))")
    public void updateHabitRepeatServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* updateStatusByIdHabitByIdUser(..))")
    public void updateHabitStatusServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* com.lushnikova.service.HabitService.delete(..))")
    public void deleteHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* getHabitFulfillmentStatistics(..))")
    public void getHabitFulfillmentServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* percentSuccessHabits(..))")
    public void percentSuccessServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* reportHabit(..))")
    public void reportHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* setDoneDates(..))")
    public void setDoneDatesHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* switchOnOrOffPushNotification(..))")
    public void switchOnOrOffPushNotificationServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(* blockByIdUser(..))")
    public void blockUserServiceMethod(){}

}