package com.senac.pizzademo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardapioTest {
    @Test
    public void testSetAndGetValor() {
        Cardapio c = new Cardapio();
        c.setValor(25.5f);
        assertEquals(25.5f, c.getValor());
    }

    @Test
    public void testSetAndGetTamanho() {
        Cardapio c = new Cardapio();
        c.setTamanho("G");
        assertEquals("G", c.getTamanho());
    }

    @Test
    public void testSetAndGetPizza() {
        Cardapio c = new Cardapio();
        Pizza p = new Pizza();
        c.setPizza(p);
        assertEquals(p, c.getPizza());
    }
}
