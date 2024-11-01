package com.lushnikova.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** Класс Аспектов - аудит действий user is */
@Aspect
@Component
@Order(1)
@Slf4j
public class LoggableAspect {

    /** Измерение времени выполнения Method:ов любых*/
    @Around("PointCuts.isAnnotateLoggable() && PointCuts.isAllExecution()")
    public Object loggingTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis() - startTime;

        log.info("Method: {} is executed {} ms", pjp.getSignature(), endTime);
        return result;
    }


    @AfterReturning(pointcut = "PointCuts.saveUserServiceMethod()")
    public void loggingAfterReturningUserSave(JoinPoint joinPoint){
        log.info("Method: {} user is saved", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.anyUserFindByIdService()")
    public void loggingAfterReturningUserFindById(JoinPoint joinPoint){
        log.info("Method: {} user is found", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.updateUserServiceMethod()")
    public void loggingAfterReturningUserUpdateName(JoinPoint joinPoint){
        log.info("Method: {} user is updated", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.deleteUserServiceMethod()")
    public void loggingAfterReturningUserDelete(JoinPoint joinPoint){
        log.info("Method: {} user is deleted", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.saveHabitServiceMethod()")
    public void loggingAfterReturningAddHabit(JoinPoint joinPoint){
        log.info("Method: {} habit is added", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.anyHabitServiceMethod()")
    public void loggingAfterReturningHabitsForUser(JoinPoint joinPoint){
        log.info("Method: {} habits are found", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitTitleServiceMethod()")
    public void loggingAfterReturningUpdateHabitTitle(JoinPoint joinPoint){
        log.info("Method: {} habit's title is updated", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitDescriptionServiceMethod()")
    public void loggingAfterReturningUpdateHabitDescription(JoinPoint joinPoint){
        log.info("Method: {} habit's description is updated", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitRepeatServiceMethod()")
    public void loggingAfterReturningUpdateHabitRepeat(JoinPoint joinPoint){
        log.info("Method: {} habit's repeat is updated", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitStatusServiceMethod()")
    public void loggingAfterReturningUpdateHabitStatus(JoinPoint joinPoint){
        log.info("Method: {} habit's status is updated", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.deleteHabitServiceMethod()")
    public void loggingAfterReturningDeleteHabit(JoinPoint joinPoint){
        log.info("Method: {} habit is deleted", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.getHabitFulfillmentServiceMethod()")
    public void loggingAfterReturningHabitFulfilment(JoinPoint joinPoint){
        log.info("Method: {} get habit's generation execution statistics", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.percentSuccessServiceMethod()")
    public void loggingAfterReturningPercentSuccess(JoinPoint joinPoint){
        log.info("Method: {} get habits' percent execution for a given period ", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.reportHabitServiceMethod()")
    public void loggingAfterReturningReportHabit(JoinPoint joinPoint){
        log.info("Method: {} get habit's report", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.setDoneDatesHabitServiceMethod()")
    public void loggingAfterReturningSetDoneDatesHabit(JoinPoint joinPoint){
        log.info("Method: {} mark that the habit is completed", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.switchOnOrOffPushNotificationServiceMethod()")
    public void loggingAfterReturningSwitchOffPushNotification(JoinPoint joinPoint){
        log.info("Method: {} habits push notification is on/off", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.findAllUserServiceMethod()")
    public void loggingAfterReturningAllUsers(JoinPoint joinPoint){
        log.info("Method: {} get all users", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "PointCuts.blockUserServiceMethod()")
    public void loggingAfterReturningBlockUser(JoinPoint joinPoint){
        log.info("Method: {} user is blocked/unblocked", joinPoint.getSignature());
    }

}