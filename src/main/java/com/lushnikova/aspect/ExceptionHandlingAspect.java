package com.lushnikova.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** Класс Аспектов - аудит действий пользователя при возникновении ошибок*/
@Aspect
@Component
@Order(2)
@Slf4j
public class ExceptionHandlingAspect {

    @AfterThrowing(pointcut = "PointCuts.saveUserServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingUserSave(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("User isn't saved");
    }

    @AfterThrowing(pointcut = "PointCuts.anyUserFindByIdService()", throwing = "exc")
    public void loggingAfterThrowingUserFindById(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("User isn't found");
    }

    @AfterThrowing(pointcut = "PointCuts.updateUserServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingUserUpdateName(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("User isn't updated");
    }

    @AfterThrowing(pointcut = "PointCuts.deleteUserServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingUserDelete(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("User isn't deleted");
    }

    @AfterThrowing(pointcut = "PointCuts.saveHabitServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingAddHabit(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habit isn't added");
    }

    @AfterThrowing(pointcut = "PointCuts.anyHabitServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingHabitsForUser(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habits aren't found");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitTitleServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingUpdateHabitTitle(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habit's title isn't updated");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitDescriptionServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingUpdateHabitDescription(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habit's description isn't updated");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitRepeatServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingUpdateHabitRepeat(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habit's repeat isn't updated");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitStatusServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingUpdateHabitStatus(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habit's status isn't updated");
    }

    @AfterThrowing(pointcut = "PointCuts.deleteHabitServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingDeleteHabit(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habit isn't deleted");
    }

    @AfterThrowing(pointcut = "PointCuts.getHabitFulfillmentServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingHabitFulfilment(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Don't get habit's generation execution statistics");
    }

    @AfterThrowing(pointcut = "PointCuts.percentSuccessServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingPercentSuccess(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Don't get habits' percent execution for a given period ");
    }

    @AfterThrowing(pointcut = "PointCuts.reportHabitServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingReportHabit(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Don't get habit's report");
    }

    @AfterThrowing(pointcut = "PointCuts.setDoneDatesHabitServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingSetDoneDatesHabit(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Don't mark that the habit isn't completed");
    }

    @AfterThrowing(pointcut = "PointCuts.switchOnOrOffPushNotificationServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingSwitchOnPushNotification(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Habits push notification isn't on/off");
    }

    @AfterThrowing(pointcut = "PointCuts.findAllUserServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingAllUsers(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("Don't get all users");
    }

    @AfterThrowing(pointcut = "PointCuts.blockUserServiceMethod()", throwing = "exc")
    public void loggingAfterThrowingBlockUser(JoinPoint joinPoint, Exception exc){
        logError(joinPoint, exc);
        log.error("User isn't blocked/unblocked");
    }

    private void logError(JoinPoint joinPoint, Exception exc){
        log.error("Method: {} of Class: {} gave exception {}", joinPoint.getSignature().getName(), joinPoint.getSourceLocation().getWithinType().getName(), exc);
    }
}
