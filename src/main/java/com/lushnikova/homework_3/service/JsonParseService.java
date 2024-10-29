package com.lushnikova.homework_3.service;

import com.lushnikova.homework_3.exception.JsonParseException;

import java.io.InputStream;

/**
 * Интерфейс JsonParseService по сериализация/десериализация объектов в массив байтов json
 */
public interface JsonParseService {

    /**
     * Операция сериализации объекта в массив байт json
     * @param object - объект класса
     * @return возвращает массив байтов
     */
    byte[] writeValueAsBytes(Object object) throws JsonParseException;

    /**
     * Операция десериализации объекта во входной поток байтов json
     * @param inputStream - входной поток байтов
     * @param object - класс объекта
     * @return возвращает объект из json
     */
    Object readObject(InputStream inputStream, Class object);

    /**
     * Операция сериализации Java-объекта в строку
     * @param object - объект класса
     * @return возвращает строку
     */
    String writeToJson(Object object) throws JsonParseException;


}
