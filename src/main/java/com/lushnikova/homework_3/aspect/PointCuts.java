package com.lushnikova.homework_3.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("within(@com.lushnikova.homework_3.annotations.Loggable *)")
    public void isAnnotateLoggable() {}

    @Pointcut("execution(* * (..))")
    public void isAllExecution() {}

    @Pointcut("isAnnotateLoggable() && execution(public com.lushnikova.homework_3.dto.response.UserResponse save(com.lushnikova.homework_3.dto.request.UserRequest))")
    public void saveUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public com.lushnikova.homework_3.dto.response.UserResponse findById(Long))")
    public void anyUserFindByIdService(){}

    @Pointcut("isAnnotateLoggable() && execution(public void updateName(Long, String))")
    public void updateNameForUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void updateEmail(Long, String))")
    public void updateEmailForUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void updatePassword(Long, String))")
    public void updatePasswordForUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void delete(Long))")
    public void deleteUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void addHabitByIdUser(..))")
    public void addHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void * getHabits*(..))")
    public void habitsForUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void updateTitleByIdHabitByIdUser(..))")
    public void updateHabitTitleServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void updateDescriptionByIdHabitByIdUser(..))")
    public void updateHabitDescriptionServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void updateRepeatByIdHabitByIdUser(..))")
    public void updateHabitRepeatServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void updateStatusByIdHabitByIdUser(..))")
    public void updateHabitStatusServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void deleteHabitByIdUser(..))")
    public void deleteHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public * getHabitFulfillmentStatisticsByIdUser(..))")
    public void getHabitFulfillmentServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public Integer percentSuccessHabitsByIdUser(..))")
    public void percentSuccessServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public String reportHabitByIdUser(..))")
    public void reportHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void setDoneDatesHabitByIdUser(..))")
    public void setDoneDatesHabitServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void switchOnPushNotificationByIdUser(..))")
    public void switchOnPushNotificationServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void switchOffPushNotificationByIdUser(..))")
    public void switchOffPushNotificationServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public * com.lushnikova.homework_3.service.AdminService.findAll())")
    public void findAllUserServiceMethod(){}

    @Pointcut("isAnnotateLoggable() && execution(public void blockByIdUser(..))")
    public void blockUserServiceMethod(){}

}
