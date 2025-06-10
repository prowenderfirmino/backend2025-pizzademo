package com.senac.pizzademo.controller;

import com.senac.pizzademo.model.Pizza;
import com.senac.pizzademo.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaControllerTest {
    @Mock
    private PizzaRepository pizzaRepository;

    @InjectMocks
    private PizzaController pizzaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPizzas() {
        List<Pizza> pizzas = List.of(new Pizza());
        when(pizzaRepository.findAll()).thenReturn(pizzas);
        List<Pizza> result = pizzaController.getAllPizzas();
        assertEquals(1, result.size());
        verify(pizzaRepository, times(1)).findAll();
    }

    @Test
    void testCreatePizza() {
        Pizza pizza = new Pizza();
        when(pizzaRepository.save(pizza)).thenReturn(pizza);
        Pizza result = pizzaController.createPizza(pizza);
        assertEquals(pizza, result);
        verify(pizzaRepository, times(1)).save(pizza);
    }

    @Test
    void testUpdateParcial_PizzaFound() {
        Pizza pizza = new Pizza();
        pizza.setSabor("Mussarela");
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);
        Map<String, Object> updates = new HashMap<>();
        updates.put("sabor", "Calabresa");
        ResponseEntity<Pizza> response = pizzaController.updateParcial(1L, updates);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Pizza responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Calabresa", responseBody.getSabor());
    }

    @Test
    void testUpdateParcial_PizzaNotFound() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());
        Map<String, Object> updates = new HashMap<>();
        ResponseEntity<Pizza> response = pizzaController.updateParcial(1L, updates);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testInserirMultiplas() {
        List<Pizza> pizzas = List.of(new Pizza(), new Pizza());
        when(pizzaRepository.saveAll(pizzas)).thenReturn(pizzas);
        ResponseEntity<List<Pizza>> response = pizzaController.inserirMultiplas(pizzas);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<Pizza> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
    }

    @Test
    void testDeletePizza_PizzaFound() {
        when(pizzaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pizzaRepository).deleteById(1L);
        ResponseEntity<Void> response = pizzaController.deletePizza(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pizzaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePizza_PizzaNotFound() {
        when(pizzaRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Void> response = pizzaController.deletePizza(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdatePizza_PizzaFound() {
        Pizza pizza = new Pizza();
        pizza.setSabor("Mussarela");
        Pizza updated = new Pizza();
        updated.setSabor("Calabresa");
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(updated);
        ResponseEntity<Pizza> response = pizzaController.updatePizza(1L, updated);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Pizza responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Calabresa", responseBody.getSabor());
    }

    @Test
    void testUpdatePizza_PizzaNotFound() {
        Pizza updated = new Pizza();
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Pizza> response = pizzaController.updatePizza(1L, updated);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
