package com.humga.currencyinfo.client;

import com.humga.currencyinfo.dto.GifsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@FeignClient(value = "gif-search", url = "https://api.giphy.com/v1/gifs/search")
public interface GifClient {
    @GetMapping
    GifsDTO searchForImages(
            @RequestParam String api_key,
            @RequestParam String q,
            @RequestParam int limit,
            @RequestParam int offset);

    @GetMapping()
    byte[] getImage(URI uri);
}

