package com.lushnikova.homework_2.controller;

/**
 * Абстрактный класс Controller
 */
public abstract class Controller {

    /**
     * Процедура последовательного выполнения методов
     */
    public void render(){
        enter();
    }

    /**
     * Авторизация
     */
    abstract void enter();

}
