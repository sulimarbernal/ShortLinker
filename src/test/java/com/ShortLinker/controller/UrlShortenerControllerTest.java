package com.ShortLinker.controller;

import com.ShortLinker.domain.ShortUrl;
import com.ShortLinker.dto.ShortUrlUpdateDTO;
import com.ShortLinker.dto.UrlRequestDTO;
import com.ShortLinker.dto.UrlResponseDTO;
import com.ShortLinker.service.UrlShortenerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UrlShortenerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UrlShortenerService serviceMock;

    @InjectMocks
    private UrlShortenerController controller;

    @BeforeEach
    public void setup() {


        mockMvc = standaloneSetup(controller).addPlaceholderValue("app.short-url-path", "shortUrl").build();
    }

    @Test
    public void testCreateShortUrl_when_validRequest_then_Created() throws Exception {
        UrlRequestDTO requestDTO = new UrlRequestDTO("http://example.com", "true");
        UrlResponseDTO responseDTO = new UrlResponseDTO("123abc", "http://example.com", "", true);
        when(serviceMock.createShortUrl(any(UrlRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/shortUrl/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
    }

    @Test
    public void testCreateShortUrl_when_invalidRequest_then_BadRequest() throws Exception {
        UrlRequestDTO requestDTO = new UrlRequestDTO("no_valid_url", null);

        mockMvc.perform(post("/shortUrl/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testRedirectShortUrl_when_existUrl_then_redirected() throws Exception {
        when(serviceMock.getShortUrl("123abc")).thenReturn(new ShortUrl("123","http://example.com", "123abc", true));

        mockMvc.perform(get("/shortUrl/123abc"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://example.com"));
    }

    @Test
    public void testRedirectShortUrl_when_notExistUrl_then_badRequest() throws Exception {
        when(serviceMock.getShortUrl("nonexistent")).thenReturn(null);

        mockMvc.perform(get("/shortUrl/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void testRedirectShortUrl_when_notActiveUrl_then_badRequest() throws Exception {
        when(serviceMock.getShortUrl("123abc")).thenReturn(new ShortUrl("123","http://example.com", "123abc", false));

        mockMvc.perform(get("/shortUrl/123abc"))
                .andExpect(status().isNotFound())
                .andExpect(redirectedUrl(null));
    }


    @Test
    public void testUpdateShortUrl_when_validRequest_then_updated() throws Exception {
        ShortUrlUpdateDTO updateDTO = new ShortUrlUpdateDTO("http://newexample.com", true);
        ShortUrl updatedShortUrl = new ShortUrl("123", "http://newexample.com", "http://shortUrl.com", true);
        when(serviceMock.updateShortUrl(eq("123"), any(ShortUrlUpdateDTO.class))).thenReturn(updatedShortUrl);

        mockMvc.perform(put("/shortUrl/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longUrl").value("http://newexample.com"));
    }

    @Test
    public void testUpdateShortUrl_when_statusValid_then_updated() throws Exception {
        ShortUrlUpdateDTO updateDTO = new ShortUrlUpdateDTO(null, true);
        ShortUrl updatedShortUrl = new ShortUrl("123", "http://newexample.com", "http://shortUrl.com", true);
        when(serviceMock.updateShortUrl(eq("123"), any(ShortUrlUpdateDTO.class))).thenReturn(updatedShortUrl);

        mockMvc.perform(put("/shortUrl/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void testUpdateShortUrl_when_invalidUrl_then_badRequest() throws Exception {
        ShortUrlUpdateDTO updateDTO = new ShortUrlUpdateDTO("no_valid_url", true);

        mockMvc.perform(put("/shortUrl/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUpdateShortUrl_when_emptyRequest_then_badRequest() throws Exception {
        ShortUrlUpdateDTO updateDTO = new ShortUrlUpdateDTO(null, null);

        mockMvc.perform(put("/shortUrl/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }

}
