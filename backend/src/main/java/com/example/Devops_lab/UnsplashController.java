package com.example.Devops_lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class UnsplashController {

    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/photos/?client_id=t5UCOcguC3JNZQAqAbFZ1d-uktVUUMw6-s6Qnm9E6uQ&page=";
    private int page = 1;

    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public ResponseEntity<List<Map<String, Object>>> hello() {
        List<Map<String, Object>> posts;
        boolean loading = true;

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    UNSPLASH_API_URL + page,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            posts = response.getBody();
            page++;
            System.out.println("Page number is: " + page);
            loading = false;
        } catch (Exception e) {
            System.err.println("Error fetching posts: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        } finally {
            loading = false;
        }

        return ResponseEntity.ok(posts);
    }
}
