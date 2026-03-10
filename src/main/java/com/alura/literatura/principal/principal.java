package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;

    public principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n=== LITER ALURA ===
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    Elija una opción: """;
            System.out.println(menu);

            try {
                opcion = Integer.parseInt(teclado.nextLine());

                switch (opcion) {
                    case 1 -> buscarLibroWeb();
                    case 2 -> repositorio.findAll().forEach(System.out::println);
                    case 3 -> autorRepositorio.findAll().forEach(System.out::println);
                    case 4 -> listarAutoresVivos();
                    case 5 -> listarLibrosPorIdioma();
                    case 0 -> System.out.println("Cerrando la aplicación...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }

    private void buscarLibroWeb() {
        System.out.println("Ingrese el nombre del libro:");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream().findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibro datosLibro = libroBuscado.get();
            DatosAutor datosAutor = datosLibro.autor().get(0);

            Autor autor = autorRepositorio.findByNombreIgnoreCase(datosAutor.nombre());
            if (autor == null) {
                autor = new Autor(datosAutor);
                autorRepositorio.save(autor);
            }

            try {
                Libro libro = new Libro(datosLibro, autor);
                repositorio.save(libro);
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("Error: El libro ya está registrado en la base de datos.");
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año para validar autores vivos:");
        try {
            var anio = Integer.parseInt(teclado.nextLine());
            List<Autor> autoresVivos = autorRepositorio.buscarAutoresVivosEnAnio(anio);
            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Año no válido.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (es, en, fr, pt):");
        var idioma = teclado.nextLine();
        List<Libro> libros = repositorio.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            libros.forEach(System.out::println);
        }
    }
}