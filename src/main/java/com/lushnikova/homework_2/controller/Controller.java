package com.lushnikova.homework_2.controller;

/**
 * Абстрактный класс Controller
 */
public abstract class Controller {

    /**
     * Процедура последовательного выполнения методов
     */
    public void render(){
        createService();
        enter();
    }

    /**
     * Процедура создания сервиса
     */
    abstract void createService();

    /**
     * Авторизация
     */
    abstract void enter();

}
