package com.veljkobogdan.jauss.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veljkobogdan.jauss.document.Url;
import com.veljkobogdan.jauss.repository.UrlRepository;
import com.veljkobogdan.jauss.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebMvc
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@WebMvcTest(UrlController.class)
public class UrlControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    private UrlShortenerService urlShortenerService;
    @InjectMocks
    UrlController urlController;
    @MockitoBean
    private UrlRepository urlRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateUrl() throws Exception {
        String longUrl = "https://example.com";
        Url savedUrl = new Url("1", longUrl, "abc123");
        when(urlShortenerService.shorten(longUrl)).thenReturn(savedUrl);

        mockMvc.perform(post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(longUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.longUrl").value(longUrl))
                .andExpect(jsonPath("$.hash").value("abc123"));

        verify(urlShortenerService, times(1)).shorten(longUrl);
    }
}
