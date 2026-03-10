package com.alura.literatura.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record DatosResultados(
        @SerializedName("results") List<DatosLibro> resultados
) {}