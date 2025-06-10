package com.senac.pizzademo.dto;

public class PizzaSimplesDTO {
    
    private Long id;
    private String sabor;
    private Long preco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public Long getPreco() {
        return preco;
    }
    public void setPreco(Long preco) {
        this.preco = preco;
    }
}
