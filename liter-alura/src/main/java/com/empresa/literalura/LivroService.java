package com.empresa.literalura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void buscarELivrosSalvar(String search) {
        String url = "https://gutendex.com/books/?search=" + search;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode results = root.path("results");

            for (JsonNode result : results) {
                String title = result.path("title").asText();
                JsonNode authorNode = result.path("authors").get(0);
                String authorName = authorNode.path("name").asText();
                int birthYear = authorNode.path("birth_year").asInt();
                int deathYear = authorNode.path("death_year").asInt();
                List<String> languages = Arrays.asList(mapper.convertValue(result.path("languages"), String[].class));

                Autor autor = new Autor();
                autor.setName(authorName);
                autor.setBirthYear(birthYear);
                autor.setDeathYear(deathYear);

                autorRepository.save(autor);

                Livro livro = new Livro();
                livro.setTitle(title);
                livro.setLanguages(languages);
                livro.setAutor(autor);

                livroRepository.save(livro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

