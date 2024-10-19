package com.lushnikova.homework_1;

import com.lushnikova.homework_1.controller.AuthenticationController;

/**
 * Класс запуска приложений
 */
public class Main {
    /**
     * Здесь точка старта приложения
     */
    public static void main(String[] args) {
        AuthenticationController authenticationController = new AuthenticationController();
        authenticationController.adminOrUser();
    }

}
