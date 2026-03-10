Este proyecto es una aplicación de consola desarrollada en Java 17 con Spring Boot 3 que gestiona un catálogo de libros consumiendo la API de Gutendex.
La aplicación permite buscar libros por título y persistirlos en una base de datos PostgreSQL, relacionándolos con sus respectivos autores mediante 
una asociación @ManyToOne. Cuenta con funcionalidades para listar libros y autores registrados, filtrar autores vivos en un año determinado y consultar libros por idioma
(es, en, fr, pt), integrando validaciones para evitar registros duplicados y asegurar la integridad de la información.
