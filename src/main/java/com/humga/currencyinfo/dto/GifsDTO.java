package com.humga.currencyinfo.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GifsDTO {
    @Data
    public static class Image {
        String id;
        Map<String, Map <String, String>> images;
    }

   List<Image> data;
}
