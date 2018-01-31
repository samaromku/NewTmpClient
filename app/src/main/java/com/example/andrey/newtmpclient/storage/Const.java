package com.example.andrey.newtmpclient.storage;

/**
 * Created by savchenko on 19.01.18.
 */

public interface Const {
    String SUCCESS_LOGOUT = "success_logout";

    String UPLOAD_FILE = "Загрузка";
    String PLEASE_WAIT = "Пожалуйста подождите";
    String CREATING_TASK = "Создание задания";

    String ERROR_DATA = "Ошибка получения данных";
    String NOT_AUTH = "Вы не авторизированы";
    String AUTH = "Авторизация";
    String ADDRESSES = "Адреса";
    String WRONG_ADDRESS = "Неправильный адрес";
    String TASK_NUMBER = "taskNumber";

    String FILL_FIELD = "Вы должны заполнить это поле";

    int DAY = 1;
    int WEEK = 7;
    int MONTH = 30;
    int ALL_TIME = -1;

    String DATE_FORMAT_FROM_SERVER = "dd-MM-yy HH:mm";
    String BASE_CREATED_DATE = "yy/MM/dd HH:mm";
}
