package com.senac.pizzademo.controller;

import com.senac.pizzademo.model.Ingredientes;
import com.senac.pizzademo.repository.IngredientesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientesControllerTest {
    @Mock
    private IngredientesRepository ingredientesRepository;

    @InjectMocks
    private IngredientesController ingredientesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIngredientes() {
        List<Ingredientes> ingredientes = List.of(new Ingredientes());
        when(ingredientesRepository.findAll()).thenReturn(ingredientes);
        List<Ingredientes> result = ingredientesController.getAllIngredientes();
        assertEquals(1, result.size());
        verify(ingredientesRepository, times(1)).findAll();
    }

    @Test
    void testCreateIngrediente() {
        Ingredientes ingrediente = new Ingredientes();
        when(ingredientesRepository.save(ingrediente)).thenReturn(ingrediente);
        Ingredientes result = ingredientesController.createIngrediente(ingrediente);
        assertEquals(ingrediente, result);
        verify(ingredientesRepository, times(1)).save(ingrediente);
    }

    @Test
    void testCreateMultiplosIngredientes() {
        List<Ingredientes> ingredientes = List.of(new Ingredientes(), new Ingredientes());
        when(ingredientesRepository.saveAll(ingredientes)).thenReturn(ingredientes);
        List<Ingredientes> result = ingredientesController.createMultiplosIngredientes(ingredientes);
        assertEquals(2, result.size());
        verify(ingredientesRepository, times(1)).saveAll(ingredientes);
    }

    @Test
    void testUpdateIngrediente_Found() {
        Ingredientes existente = new Ingredientes();
        existente.setIngrediente("Queijo");
        existente.setQuantidade("100g");
        Ingredientes novo = new Ingredientes();
        novo.setIngrediente("Presunto");
        novo.setQuantidade("200g");
        when(ingredientesRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ingredientesRepository.save(any(Ingredientes.class))).thenReturn(novo);
        Ingredientes result = ingredientesController.updateIngrediente(1L, novo);
        assertEquals("Presunto", result.getIngrediente());
        assertEquals("200g", result.getQuantidade());
    }

    @Test
    void testUpdateIngrediente_NotFound() {
        Ingredientes novo = new Ingredientes();
        when(ingredientesRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> ingredientesController.updateIngrediente(1L, novo));
    }

    @Test
    void testUpdateIngredienteParcial_Found() {
        Ingredientes existente = new Ingredientes();
        existente.setIngrediente("Queijo");
        existente.setQuantidade("100g");
        Ingredientes parcial = new Ingredientes();
        parcial.setQuantidade("300g");
        when(ingredientesRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ingredientesRepository.save(any(Ingredientes.class))).thenReturn(existente);
        Ingredientes result = ingredientesController.updateIngredienteParcial(1L, parcial);
        assertEquals("Queijo", result.getIngrediente());
        assertEquals("300g", result.getQuantidade());
    }

    @Test
    void testUpdateIngredienteParcial_NotFound() {
        Ingredientes parcial = new Ingredientes();
        when(ingredientesRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> ingredientesController.updateIngredienteParcial(1L, parcial));
    }

    @Test
    void testDeleteIngrediente() {
        doNothing().when(ingredientesRepository).deleteById(1L);
        ingredientesController.deleteIngrediente(1L);
        verify(ingredientesRepository, times(1)).deleteById(1L);
    }
}
