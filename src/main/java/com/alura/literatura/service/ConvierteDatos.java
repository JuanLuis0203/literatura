package com.alura.literatura.service;

import com.google.gson.Gson;

public class ConvierteDatos {
    private Gson gson = new Gson();

    public <T> T obtenerDatos(String json, Class<T> clase) {
        return gson.fromJson(json, clase);
    }
}