package com.empresa.literalura;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTitle(String title);
    List<Livro> findByLanguagesContaining(String language);
}
