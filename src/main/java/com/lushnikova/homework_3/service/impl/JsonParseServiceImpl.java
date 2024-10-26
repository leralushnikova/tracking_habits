package com.lushnikova.homework_3.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lushnikova.homework_3.exception.JsonParseException;
import com.lushnikova.homework_3.service.JsonParseService;

import java.io.IOException;
import java.io.InputStream;

/**
 * Сервис JsonParseServiceImpl по сериализация/десериализация Java-объектов в массив байтов json
 */
public class JsonParseServiceImpl implements JsonParseService {

    /** Поле сериализация и десериализация Java-объектов*/
    private final ObjectMapper objectMapper;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     */
    public JsonParseServiceImpl(){
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Операция сериализации Java-объекта в массив байт json
     * @param object - объект класса
     * @return возвращает массив байтов
     */
    @Override
    public byte[] writeValueAsBytes(Object object) throws JsonParseException {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new JsonParseException();
        }
    }

    /**
     * Операция десериализации Java-объекта во входной поток байтов json
     * @param inputStream - входной поток байтов
     * @param object - класс объекта
     * @return возвращает объект из json
     */
    @Override
    public Object readObject(InputStream inputStream, Class object) {
        try {
            return objectMapper.readValue(inputStream, object);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
