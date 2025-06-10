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


public class Ingredientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "O nome do ingrediente é obrigatório.")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "O nome do ingrediente deve ter entre 2 e 50 caracteres.")
    private String ingrediente;

    @jakarta.validation.constraints.NotBlank(message = "A quantidade é obrigatória.")
    private String quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizza_id")
    @JsonBackReference
    private Pizza pizza;

    public Long getId() {
        return this.id;
    }

    public String getIngrediente() {
        return this.ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }


    public Ingredientes(String ingrediente, String quantidade, Pizza pizza) {
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
        this.pizza = pizza;
    }
    
    public Ingredientes() {
    }

}
