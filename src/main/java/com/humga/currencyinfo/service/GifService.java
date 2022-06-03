package com.humga.currencyinfo.service;

import com.humga.currencyinfo.client.GifClient;
import com.humga.currencyinfo.dto.GifsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

@Service
public class GifService {
    private final GifClient gifClient;
    private final RatesService ratesService;

    @Value("${app.config.giphy.querystringgrown}")
    private String rateGrown;
    @Value("${app.config.giphy.querystringfallen}")
    private String rateFallen;
    @Value("${app.config.giphy.maxoffset}")
    private int maxOffset;
    @Value("${app.config.giphy.rendition}")
    private String rendition;
    @Value("${app.config.giphy.urltag}")
    private String downloadUrl;
    @Value("${app.config.giphy.appkey}")
    private String appKey;

    public GifService(GifClient gifClient, RatesService ratesService) {
        this.gifClient = gifClient;
        this.ratesService = ratesService;
    }

    public byte[] getCurrentStatusPicture(String symbol) throws URISyntaxException {
        Random rand = new Random();
        GifsDTO gifsDto;

        if (ratesService.rateGrown(symbol)) {
            gifsDto = gifClient.searchForImages(appKey, rateGrown, 1, rand.nextInt(maxOffset));
        } else {
            gifsDto = gifClient.searchForImages(appKey, rateFallen, 1, rand.nextInt(maxOffset));
        }

        return gifClient.getImage(getImageUri(gifsDto));
    }

    private URI getImageUri(GifsDTO dto) throws URISyntaxException {
        return new URI(dto.getData().get(0).getImages().get(rendition).get(downloadUrl));
    }
}
