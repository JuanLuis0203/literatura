package com.alura.literatura.model;

import com.google.gson.annotations.SerializedName;

public record DatosAutor(
        @SerializedName("name") String nombre,
        @SerializedName("birth_year") Integer fechaNacimiento,
        @SerializedName("death_year") Integer fechaFallecimiento
) {}