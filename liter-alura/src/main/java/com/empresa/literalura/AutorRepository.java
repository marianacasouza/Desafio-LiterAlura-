package com.empresa.literalura;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(Integer year1, Integer year2);

    default List<Autor> findAuthorsAliveInYear(int year) {
        return findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
    }
}

