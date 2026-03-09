package com.alura.literatura.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record DatosLibro(
        @SerializedName("title") String titulo,
        @SerializedName("authors") List<DatosAutor> autor,
        @SerializedName("languages") List<String> idiomas,
        @SerializedName("download_count") Double descargas
) {}

