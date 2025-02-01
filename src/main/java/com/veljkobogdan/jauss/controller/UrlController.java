package com.veljkobogdan.jauss.controller;

import com.veljkobogdan.jauss.document.Url;
import com.veljkobogdan.jauss.repository.UrlRepository;
import com.veljkobogdan.jauss.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api")
public class UrlController {
    private final UrlRepository urlRepository;
    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlRepository urlRepository, UrlShortenerService urlShortenerService) {
        this.urlRepository = urlRepository;
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<Url> create(@RequestBody String url) throws Exception {
        Url savedUrl = urlShortenerService.shorten(url);

        return ResponseEntity.ok(savedUrl);
    }

    @GetMapping
    public ResponseEntity<List<Url>> getAll() {
        List<Url> urls = urlRepository.findAll();

        return ResponseEntity.ok(urls);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Url> update(@PathVariable String id, @RequestBody Url url) {
        Optional<Url> optionalUrl = urlRepository.findById(id);
        if (optionalUrl.isPresent()) {
            Url updatedUrl = optionalUrl.get();
            updatedUrl.setHash(url.getHash());
            updatedUrl.setLongUrl(url.getLongUrl());
            return ResponseEntity.ok(updatedUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            urlRepository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't delete Url");
        }
    }
}
