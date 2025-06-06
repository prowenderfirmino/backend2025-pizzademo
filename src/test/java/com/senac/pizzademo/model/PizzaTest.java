package com.senac.pizzademo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class PizzaTest {
    @Test
    public void testSetAndGetSabor() {
        Pizza p = new Pizza();
        p.setSabor("Calabresa");
        assertEquals("Calabresa", p.getSabor());
    }

    @Test
    public void testSetAndGetIngredientes() {
        Pizza p = new Pizza();
        Set<Ingredientes> ingredientes = new HashSet<>();
        Ingredientes i = new Ingredientes();
        i.setIngrediente("Queijo");
        ingredientes.add(i);
        p.setIngredientes(ingredientes);
        assertEquals(ingredientes, p.getIngredientes());
    }

    @Test
    public void testSetAndGetCardapio() {
        Pizza p = new Pizza();
        Set<Cardapio> cardapio = new HashSet<>();
        Cardapio c = new Cardapio();
        cardapio.add(c);
        p.setCardapio(cardapio);
        assertEquals(cardapio, p.getCardapio());
    }
}
