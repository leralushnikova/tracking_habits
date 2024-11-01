package com.lushnikova.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
@Slf4j
public class ExceptionHandlingAspect {


    @AfterThrowing(pointcut = "PointCuts.saveUserServiceMethod()")
    public void loggingAfterThrowingUserSave(JoinPoint joinPoint){
        log.error("Method: {} user isn't saved", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.anyUserFindByIdService()")
    public void loggingAfterThrowingUserFindById(JoinPoint joinPoint){
        log.error("Method: {} user isn't found", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.updateUserServiceMethod()")
    public void loggingAfterThrowingUserUpdateName(JoinPoint joinPoint){
        log.error("Method: {} user isn't updated", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.deleteUserServiceMethod()")
    public void loggingAfterThrowingUserDelete(JoinPoint joinPoint){
        log.error("Method: {} user isn't deleted", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.saveHabitServiceMethod()")
    public void loggingAfterThrowingAddHabit(JoinPoint joinPoint){
        log.error("Method: {} habit isn't added", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.anyHabitServiceMethod()")
    public void loggingAfterThrowingHabitsForUser(JoinPoint joinPoint){
        log.error("Method: {} habits aren't found", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitTitleServiceMethod()")
    public void loggingAfterThrowingUpdateHabitTitle(JoinPoint joinPoint){
        log.error("Method: {} habit's title isn't updated", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitDescriptionServiceMethod()")
    public void loggingAfterThrowingUpdateHabitDescription(JoinPoint joinPoint){
        log.error("Method: {} habit's description isn't updated", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitRepeatServiceMethod()")
    public void loggingAfterThrowingUpdateHabitRepeat(JoinPoint joinPoint){
        log.error("Method: {} habit's repeat isn't updated", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitStatusServiceMethod()")
    public void loggingAfterThrowingUpdateHabitStatus(JoinPoint joinPoint){
        log.error("Method: {} habit's status isn't updated", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.deleteHabitServiceMethod()")
    public void loggingAfterThrowingDeleteHabit(JoinPoint joinPoint){
        log.error("Method: {} habit isn't deleted", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.getHabitFulfillmentServiceMethod()")
    public void loggingAfterThrowingHabitFulfilment(JoinPoint joinPoint){
        log.error("Method: {} don't get habit's generation execution statistics", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.percentSuccessServiceMethod()")
    public void loggingAfterThrowingPercentSuccess(JoinPoint joinPoint){
        log.error("Method: {} don't get habits' percent execution for a given period ", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.reportHabitServiceMethod()")
    public void loggingAfterThrowingReportHabit(JoinPoint joinPoint){
        log.error("Method: {} don't get habit's report", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.setDoneDatesHabitServiceMethod()")
    public void loggingAfterThrowingSetDoneDatesHabit(JoinPoint joinPoint){
        log.error("Method: {} don't mark that the habit isn't completed", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.switchOnOrOffPushNotificationServiceMethod()")
    public void loggingAfterThrowingSwitchOnPushNotification(JoinPoint joinPoint){
        log.error("Method: {} habits push notification isn't on/off", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.findAllUserServiceMethod()")
    public void loggingAfterThrowingAllUsers(JoinPoint joinPoint){
        log.error("Method: {} don't get all users", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "PointCuts.blockUserServiceMethod()")
    public void loggingAfterThrowingBlockUser(JoinPoint joinPoint){
        log.error("Method: {} user isn't blocked/unblocked", joinPoint.getSignature());
    }
}
