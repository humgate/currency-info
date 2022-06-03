package com.humga.currencyinfo.services;

import com.humga.currencyinfo.client.GifClient;
import com.humga.currencyinfo.dto.GifsDTO;
import com.humga.currencyinfo.service.GifService;
import com.humga.currencyinfo.service.RatesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class GifServiceTest {
    @MockBean
    GifClient mockGifClient;
    @MockBean
    RatesService mockRatesService;

    private static GifsDTO risenGifDTO;
    private static GifsDTO fallenGifDTO;

    @Autowired
    GifService gifService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void initTestRates() throws IOException {
        //Init gifs
        risenGifDTO = mapper.readValue(
                "{\"data\": " +
                        "[" +
                        "{\"id\": \"1234\"," +
                        "\"images\": " +
                        "{\"original\": " +
                        "{" +
                        "\"url\": \"http://localhost/risen.gif\"" +
                        "}" +
                        "}" +
                        "}" +
                        "]" +
                        "}",
                GifsDTO.class);

        fallenGifDTO = mapper.readValue(
                "{\"data\": " +
                        "[" +
                        "{\"id\": \"2234\"," +
                        "\"images\": " +
                        "{\"original\": " +
                        "{" +
                        "\"url\": \"http://localhost/fallen.gif\"" +
                        "}" +
                        "}" +
                        "}" +
                        "]" +
                        "}",
                GifsDTO.class);
    }

    @Test
    public void gifServiceGetCurrentStatusPictureRisenTest() throws URISyntaxException {
        //given gifs dto initialized in @BeforeAll and
        byte[] expected = new byte[]{97};

        //when
        when(mockGifClient.searchForImages(anyString(),eq("rich"), eq(1), anyInt())).thenReturn(risenGifDTO);
        when(mockGifClient.getImage(any(URI.class))).thenReturn(expected);
        when(mockRatesService.rateGrown(anyString())).thenReturn(true);

        //then
        byte[] img = gifService.getCurrentStatusPicture("EUR");
        assertEquals(img, expected);
    }

    @Test
    public void gifServiceGetCurrentStatusPictureFallenTest() throws URISyntaxException {
        //given gifs dto initialized in @BeforeAll and
        byte[] expected = new byte[]{98};

        //when
        when(mockGifClient.searchForImages(anyString(),eq("broke"), eq(1), anyInt())).thenReturn(fallenGifDTO);
        when(mockGifClient.getImage(any(URI.class))).thenReturn(expected);
        when(mockRatesService.rateGrown(anyString())).thenReturn(false);

        //then
        byte[] img = gifService.getCurrentStatusPicture("EUR");
        assertEquals(img, expected);
    }
}
