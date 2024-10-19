package com.lushnikova.homework_1.controller;


public abstract class Controller {

    public void render(){
        createService();
        enter();
    }

    abstract void createService();

    abstract void enter();

}
