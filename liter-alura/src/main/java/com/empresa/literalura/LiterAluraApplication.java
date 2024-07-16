package com.empresa.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("Escolha o número de sua opção:");
            System.out.println("1-Busca livro pelo título");
            System.out.println("2-Listar livros registrados");
            System.out.println("3-Listar autores registrados");
            System.out.println("4-Listar autores vivos em um determinado ano");
            System.out.println("5-Listar livros em um determinado idioma");
            System.out.println("6-Sair");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.println("Digite o título do livro:");
                    String title = scanner.nextLine();
                    livroService.buscarELivrosSalvar(title);
                    break;
                case 2:
                    System.out.println("Livros registrados:");
                    livroRepository.findAll().forEach(livro -> System.out.println(livro.getTitle()));
                    break;
                case 3:
                    System.out.println("Autores registrados:");
                    autorRepository.findAll().forEach(autor -> System.out.println(autor.getName()));
                    break;
                case 4:
                    System.out.println("Digite o ano:");
                    int year = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    autorRepository.findAuthorsAliveInYear(year)
                            .forEach(autor -> System.out.println(autor.getName()));
                    break;
                case 5:
                    System.out.println("Digite o idioma:");
                    String language = scanner.nextLine();
                    livroRepository.findByLanguagesContaining(language)
                            .forEach(livro -> System.out.println(livro.getTitle()));
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
