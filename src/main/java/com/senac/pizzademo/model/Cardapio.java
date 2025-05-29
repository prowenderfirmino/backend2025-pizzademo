package com.senac.pizzademo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity

public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @jakarta.validation.constraints.NotNull(message = "O valor é obrigatório.")
    private Float valor;

    @jakarta.validation.constraints.NotBlank(message = "O tamanho é obrigatório.")
    private String tamanho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizza_id")
    @JsonBackReference
    private Pizza pizza;

    public Long getId() {
        return this.id;
    }

    public Float getValor() {
        return this.valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getTamanho() {
        return this.tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }


    public Cardapio(Float valor, String tamanho, Pizza pizza) {        
        this.valor = valor;
        this.tamanho = tamanho;
        this.pizza = pizza;
    }

    public Cardapio(){

    }

}
