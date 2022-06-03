package com.humga.currencyinfo.services;

import com.humga.currencyinfo.service.GifService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GifService gifServiceMock;

    @Test
    void basicControllerTest() throws Exception {
        //given
        byte[] expected = new byte[]{97};

        //when
        when(gifServiceMock.getCurrentStatusPicture(anyString())).thenReturn(expected);

        //then
        mockMvc.perform(get("/picture?symbol=EUR").contentType(MediaType.IMAGE_GIF_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(expected));
    }

    @Test
    void invalidRequestParameterTest() throws Exception {
        mockMvc.perform(get("/picture?ymbo=EUR").contentType(MediaType.IMAGE_GIF_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string("400 BAD_REQUEST. Required request parameter 'symbol' for method" +
                                " parameter type String is not present."));
    }

    @Test
    void invalidPathTest() throws Exception {
        mockMvc.perform(get("/pic?symbol=EUR").contentType(MediaType.IMAGE_GIF_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string("404 NOT_FOUND. No handler found for GET /pic."));
    }
}
