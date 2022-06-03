package com.humga.currencyinfo.controller;

import com.humga.currencyinfo.service.GifService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@CrossOrigin
@RequestMapping
public class ApplicationController {
    private final GifService gifService;
    public ApplicationController(GifService gifService) {
        this.gifService = gifService;
    }

    @GetMapping(value="/picture", produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getPicture(@RequestParam String symbol) throws URISyntaxException {
        return gifService.getCurrentStatusPicture(symbol);
    }
}
