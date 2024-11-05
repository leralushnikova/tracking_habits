package com.lushnikova.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** Класс Аспектов - аудит действий пользователя */
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


    @AfterReturning(pointcut = "PointCuts.saveUserServiceMethod()", returning = "result")
    public void loggingAfterReturningUserSave(JoinPoint joinPoint, Object result){
        logInfo(joinPoint);
        log.info("User is saved {}", result);
    }

    @AfterReturning(pointcut = "PointCuts.anyUserFindByIdService()", returning = "result")
    public void loggingAfterReturningUserFindById(JoinPoint joinPoint, Object result){
        logInfo(joinPoint);
        log.info("User is found {}", result);
    }

    @AfterReturning(pointcut = "PointCuts.updateUserServiceMethod()")
    public void loggingAfterReturningUserUpdateName(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("User is updated with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.deleteUserServiceMethod()")
    public void loggingAfterReturningUserDelete(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("User is deleted with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.saveHabitServiceMethod()", returning = "result")
    public void loggingAfterReturningAddHabit(JoinPoint joinPoint, Object result){
        logInfo(joinPoint);
        log.info("Habit is added {}", result);
    }

    @AfterReturning(pointcut = "PointCuts.anyHabitServiceMethod()", returning = "result")
    public void loggingAfterReturningHabitsForUser(JoinPoint joinPoint, Object result){
        logInfo(joinPoint);
        log.info("Habits are found {}", result);
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitTitleServiceMethod()")
    public void loggingAfterReturningUpdateHabitTitle(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Habit's title is updated with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitDescriptionServiceMethod()")
    public void loggingAfterReturningUpdateHabitDescription(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Habit's description is updated with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitRepeatServiceMethod()")
    public void loggingAfterReturningUpdateHabitRepeat(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Habit's repeat is updated with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitStatusServiceMethod()")
    public void loggingAfterReturningUpdateHabitStatus(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Habit's status is updated with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.deleteHabitServiceMethod()")
    public void loggingAfterReturningDeleteHabit(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Habit is deleted with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.getHabitFulfillmentServiceMethod()")
    public void loggingAfterReturningHabitFulfilment(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Get habit's generation execution statistics with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.percentSuccessServiceMethod()")
    public void loggingAfterReturningPercentSuccess(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Get habits' percent execution for a given period with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.reportHabitServiceMethod()", returning = "result")
    public void loggingAfterReturningReportHabit(JoinPoint joinPoint, Object result){
        logInfo(joinPoint);
        log.info("Get habit's report {}", result);
    }

    @AfterReturning(pointcut = "PointCuts.setDoneDatesHabitServiceMethod()")
    public void loggingAfterReturningSetDoneDatesHabit(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Mark that the habit is completed with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.switchOnOrOffPushNotificationServiceMethod()")
    public void loggingAfterReturningSwitchOffPushNotification(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("Habits push notification is on/off with arguments {}", joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "PointCuts.findAllUserServiceMethod()", returning = "result")
    public void loggingAfterReturningAllUsers(JoinPoint joinPoint, Object result){
        logInfo(joinPoint);
        log.info("Get all users {}", result);
    }

    @AfterReturning(pointcut = "PointCuts.blockUserServiceMethod()")
    public void loggingAfterReturningBlockUser(JoinPoint joinPoint){
        logInfo(joinPoint);
        log.info("User is blocked/unblocked with arguments {}", joinPoint.getArgs());
    }

    private void logInfo(JoinPoint joinPoint){
        log.info("Method: {} of Class: {}", joinPoint.getSignature().getName(), joinPoint.getSourceLocation().getWithinType().getName());
    }

}