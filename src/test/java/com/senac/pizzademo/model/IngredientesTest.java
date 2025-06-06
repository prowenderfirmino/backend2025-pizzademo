package com.senac.pizzademo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientesTest {
    @Test
    public void testSetAndGetIngrediente() {
        Ingredientes i = new Ingredientes();
        i.setIngrediente("Tomate");
        assertEquals("Tomate", i.getIngrediente());
    }

    @Test
    public void testSetAndGetQuantidade() {
        Ingredientes i = new Ingredientes();
        i.setQuantidade("100g");
        assertEquals("100g", i.getQuantidade());
    }

    @Test
    public void testSetAndGetPizza() {
        Ingredientes i = new Ingredientes();
        Pizza p = new Pizza();
        i.setPizza(p);
        assertEquals(p, i.getPizza());
    }
}
