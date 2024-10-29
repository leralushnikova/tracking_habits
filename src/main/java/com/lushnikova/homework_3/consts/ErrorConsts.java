package com.lushnikova.homework_3.consts;

import lombok.experimental.UtilityClass;

import static com.lushnikova.homework_3.consts.StringConsts.*;

/**
 * Утилитный класс для указания ошибок
 */
@UtilityClass
public class ErrorConsts {
        public static final String ADMIN_EXISTS = "Admin already exists!";
        public static final String USER_EXISTS = "User already exists!";
        public static final String WRONG_REQUEST = "Wrong request!";
        public static final String WRONG_PASSWORD = "Wrong password!";
        public static final String WRONG_DATE = "Wrong date! Need to type yyyy-MM-dd";
        public static final String WRONG_TIME = "Wrong time! Need to type HH:mm";
        public static final String WRONG_STATISTICS = "Wrong to type statistics need to choice " + DAY + "/ " + WEEK + "/ " + MONTH + "!";
        public static final String WRONG_STATUS = "Wrong to type status need to choice " + CREATE + "/ " + IN_PROGRESS + "/ " + DONE + "!";
        public static final String WRONG_REPEAT= "Wrong to type repeat need to choice " + DAILY + "/ " + WEEKLY + "!";
}
