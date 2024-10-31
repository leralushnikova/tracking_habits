package com.lushnikova.homework_3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/** Класс Аспектов - аудит действий пользователя */
@Aspect
public class LoggableAspect {

    /** Измерение времени выполнения методов любых*/
    @Around("PointCuts.isAnnotateLoggable() && PointCuts.isAllExecution()")
    public Object loggingTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis() - startTime;

        System.out.println("Метод " + pjp.getSignature() + " выполнялся " + endTime + " мс");
        return result;
    }

    public static LoggableAspect aspectOf(){
        return new LoggableAspect();
    }

    @AfterReturning(pointcut = "PointCuts.saveUserServiceMethod()")
    public void loggingAfterReturningUserSave(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пользователь сохранен");
    }

    @AfterThrowing(pointcut = "PointCuts.saveUserServiceMethod()")
    public void loggingAfterThrowingUserSave(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пользователя не удалось сохранить");
    }
    @AfterReturning(pointcut = "PointCuts.anyUserFindByIdService()")
    public void loggingAfterReturningUserFindById(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пользователь найден");
    }

    @AfterThrowing(pointcut = "PointCuts.anyUserFindByIdService()")
    public void loggingAfterThrowingUserFindById(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пользователя не удалось найти");
    }

    @AfterReturning(pointcut = "PointCuts.updateNameForUserServiceMethod()")
    public void loggingAfterReturningUserUpdateName(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " имя пользователя обновлено");
    }

    @AfterThrowing(pointcut = "PointCuts.updateNameForUserServiceMethod()")
    public void loggingAfterThrowingUserUpdateName(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не удалось обновить имя пользователя");
    }

    @AfterReturning(pointcut = "PointCuts.updateEmailForUserServiceMethod()")
    public void loggingAfterReturningUserUpdateEmail(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " почта пользователя обновлена");
    }

    @AfterThrowing(pointcut = "PointCuts.updateEmailForUserServiceMethod()")
    public void loggingAfterThrowingUserUpdateEmail(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не удалось обновить почту пользователя");
    }

    @AfterReturning(pointcut = "PointCuts.updatePasswordForUserServiceMethod()")
    public void loggingAfterReturningUserUpdatePassword(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пароль пользователя обновлен");
    }

    @AfterThrowing(pointcut = "PointCuts.updatePasswordForUserServiceMethod()")
    public void loggingAfterThrowingUserUpdatePassword(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не удалось обновить пароль пользователя");
    }

    @AfterReturning(pointcut = "PointCuts.deleteUserServiceMethod()")
    public void loggingAfterReturningUserDelete(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пользователь удален");
    }

    @AfterThrowing(pointcut = "PointCuts.deleteUserServiceMethod()")
    public void loggingAfterThrowingUserDelete(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пользователя не удалось удалить");
    }

    @AfterReturning(pointcut = "PointCuts.addHabitServiceMethod()")
    public void loggingAfterReturningAddHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " привычка добавлена");
    }

    @AfterThrowing(pointcut = "PointCuts.addHabitServiceMethod()")
    public void loggingAfterThrowingAddHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " привычку не удалось добавить");
    }

    @AfterReturning(pointcut = "PointCuts.habitsForUserServiceMethod()")
    public void loggingAfterReturningHabitsForUser(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " привычки найдены");
    }

    @AfterThrowing(pointcut = "PointCuts.habitsForUserServiceMethod()")
    public void loggingAfterThrowingHabitsForUser(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " привычки не найдены");
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitTitleServiceMethod()")
    public void loggingAfterReturningUpdateHabitTitle(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " название привычки обновлено");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitTitleServiceMethod()")
    public void loggingAfterThrowingUpdateHabitTitle(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " название привычки не удалось обновить");
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitDescriptionServiceMethod()")
    public void loggingAfterReturningUpdateHabitDescription(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " описание привычки обновлено");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitDescriptionServiceMethod()")
    public void loggingAfterThrowingUpdateHabitDescription(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " описание привычки не удалось обновить");
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitRepeatServiceMethod()")
    public void loggingAfterReturningUpdateHabitRepeat(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " частота повторения привычки обновлено");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitRepeatServiceMethod()")
    public void loggingAfterThrowingUpdateHabitRepeat(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " частоту повторения привычки не удалось обновить");
    }

    @AfterReturning(pointcut = "PointCuts.updateHabitStatusServiceMethod()")
    public void loggingAfterReturningUpdateHabitStatus(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " статус привычки обновлено");
    }

    @AfterThrowing(pointcut = "PointCuts.updateHabitStatusServiceMethod()")
    public void loggingAfterThrowingUpdateHabitStatus(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " статус привычки не удалось обновить");
    }

    @AfterReturning(pointcut = "PointCuts.deleteHabitServiceMethod()")
    public void loggingAfterReturningDeleteHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " привычка удалена");
    }

    @AfterThrowing(pointcut = "PointCuts.deleteHabitServiceMethod()")
    public void loggingAfterThrowingDeleteHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " привычку не удалось удалить");
    }

    @AfterReturning(pointcut = "PointCuts.getHabitFulfillmentServiceMethod()")
    public void loggingAfterReturningHabitFulfilment(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " получена генерация статистики выполнения");
    }

    @AfterThrowing(pointcut = "PointCuts.getHabitFulfillmentServiceMethod()")
    public void loggingAfterThrowingHabitFulfilment(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " генерация статистики выполнения не получена");
    }

    @AfterReturning(pointcut = "PointCuts.percentSuccessServiceMethod()")
    public void loggingAfterReturningPercentSuccess(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " получен процента успешного выполнения привычек за определенный период ");
    }

    @AfterThrowing(pointcut = "PointCuts.percentSuccessServiceMethod()")
    public void loggingAfterThrowingPercentSuccess(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не получен процента успешного выполнения привычек за определенный период ");
    }

    @AfterReturning(pointcut = "PointCuts.reportHabitServiceMethod()")
    public void loggingAfterReturningReportHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " получен отчет для пользователя по прогрессу выполнения привычки");
    }

    @AfterThrowing(pointcut = "PointCuts.reportHabitServiceMethod()")
    public void loggingAfterThrowingReportHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не получен отчет для пользователя по прогрессу выполнения привычки");
    }

    @AfterReturning(pointcut = "PointCuts.setDoneDatesHabitServiceMethod()")
    public void loggingAfterReturningSetDoneDatesHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " отмечена за день привычка");
    }

    @AfterThrowing(pointcut = "PointCuts.setDoneDatesHabitServiceMethod()")
    public void loggingAfterThrowingSetDoneDatesHabit(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " привычку не получилось отметить");
    }

    @AfterReturning(pointcut = "PointCuts.switchOnPushNotificationServiceMethod()")
    public void loggingAfterReturningSwitchOnPushNotification(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " уведомление привычки включена");
    }

    @AfterThrowing(pointcut = "PointCuts.switchOnPushNotificationServiceMethod()")
    public void loggingAfterThrowingSwitchOnPushNotification(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не удалось включить уведомление привычки");
    }

    @AfterReturning(pointcut = "PointCuts.switchOffPushNotificationServiceMethod()")
    public void loggingAfterReturningSwitchOffPushNotification(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " уведомление привычки выключена");
    }

    @AfterThrowing(pointcut = "PointCuts.switchOffPushNotificationServiceMethod()")
    public void loggingAfterThrowingSwitchOffPushNotification(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не удалось выключить уведомление привычки");
    }

    @AfterReturning(pointcut = "PointCuts.findAllUserServiceMethod()")
    public void loggingAfterReturningAllUsers(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " просмотр всех пользователей");
    }

    @AfterThrowing(pointcut = "PointCuts.findAllUserServiceMethod()")
    public void loggingAfterThrowingAllUsers(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " не удалось просмотреть всех пользователей");
    }

    @AfterReturning(pointcut = "PointCuts.blockUserServiceMethod()")
    public void loggingAfterReturningBlockUser(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " блокировка/разблокировка пользователя");
    }

    @AfterThrowing(pointcut = "PointCuts.blockUserServiceMethod()")
    public void loggingAfterThrowingBlockUser(JoinPoint joinPoint){
        System.out.println("Метод " + joinPoint.getSignature() + " пользователя заблокировать/разблокировать не удалось");
    }


}
