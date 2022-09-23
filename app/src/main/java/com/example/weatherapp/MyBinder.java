package com.example.weatherapp;

import android.os.Binder;

public class MyBinder extends Binder {
    private ApiService service;

    public MyBinder (ApiService service) {
        this.service = service;
    }

    ApiService getService() {
        return service;
    }
}
