package com.senac.pizzademo.controller;

import com.senac.pizzademo.model.Cardapio;
import com.senac.pizzademo.repository.CardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CardapioControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CardapioRepository cardapioRepository;

    @InjectMocks
    private CardapioController cardapioController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardapioController).build();
    }

    @Test
    public void testGetAllCardapio() throws Exception {
        Cardapio c1 = new Cardapio();
        c1.setValor(30f);
        c1.setTamanho("M");
        Cardapio c2 = new Cardapio();
        c2.setValor(40f);
        c2.setTamanho("G");
        when(cardapioRepository.findAll()).thenReturn(Arrays.asList(c1, c2));
        mockMvc.perform(get("/cardapio"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCardapioById() throws Exception {
        Cardapio c = new Cardapio();
        c.setValor(30f);
        c.setTamanho("M");
        when(cardapioRepository.findById(1L)).thenReturn(Optional.of(c));
        mockMvc.perform(get("/cardapio/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCardapio() throws Exception {
        Cardapio c = new Cardapio();
        c.setValor(30f);
        c.setTamanho("M");
        when(cardapioRepository.save(any(Cardapio.class))).thenReturn(c);
        String json = "{\"valor\":30,\"tamanho\":\"M\"}";
        mockMvc.perform(post("/cardapio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCardapio() throws Exception {
        Cardapio c = new Cardapio();
        c.setValor(30f);
        c.setTamanho("M");
        when(cardapioRepository.findById(1L)).thenReturn(Optional.of(c));
        when(cardapioRepository.save(any(Cardapio.class))).thenReturn(c);
        String json = "{\"valor\":35,\"tamanho\":\"G\"}";
        mockMvc.perform(put("/cardapio/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCardapio() throws Exception {
        Cardapio c = new Cardapio();
        when(cardapioRepository.findById(1L)).thenReturn(Optional.of(c));
        doNothing().when(cardapioRepository).deleteById(1L);
        mockMvc.perform(delete("/cardapio/1"))
                .andExpect(status().isOk());
    }
}
