package com.senac.pizzademo.controller;
import java.util.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.pizzademo.model.Cardapio;
import com.senac.pizzademo.model.Ingredientes;
import com.senac.pizzademo.model.Pizza;
import com.senac.pizzademo.repository.PizzaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PizzaController gerencia os endpoints REST para manipulação das pizzas.
 * 
 * Permite listar, criar (uma ou várias), atualizar (total ou parcialmente) e remover pizzas.
 * Cada método corresponde a uma operação HTTP e manipula objetos do tipo Pizza.
 * 
 * Exemplo de JSON para criar/atualizar uma pizza:
 * {
 *   "sabor": "Calabresa",
 *   "ingredientes": [
 *     { "id": 1, "Ingrediente": "Calabresa", "Quantidade": "100g" }
 *   ],
 *   "cardapio": [
 *     { "id": 1, "valor": 49.90, "tamanho": "Grande" }
 *   ]
 * }
 */
@RestController
@RequestMapping("/pizza")
public class PizzaController {
    private static final Logger logger = LoggerFactory.getLogger(PizzaController.class);

    private final PizzaRepository pizzaRepository;

    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    /**
     * Lista todas as pizzas cadastradas.
     *
     * @return Lista de objetos Pizza.
     */
    @Operation(summary = "Lista todas as pizzas", description = "Retorna uma lista com todas as pizzas cadastradas.")
    @GetMapping
    public List<Pizza> getAllPizzas() {
        logger.info("Listando todas as pizzas");
        return pizzaRepository.findAll();
    }

    /**
     * Cria uma nova pizza.
     *
     * Recebe um objeto Pizza no corpo da requisição e salva no banco de dados.
     *
     * @param pizza Objeto Pizza recebido no corpo da requisição.
     * @return A pizza criada.
     */
    @Operation(
        summary = "Cria uma nova pizza",
        description = "Recebe um JSON representando uma pizza e a salva no banco de dados."
    )
    @ApiResponse(responseCode = "200", description = "Pizza criada com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pizza.class)
        )
    )
    @PostMapping
    public Pizza createPizza(@Valid @RequestBody Pizza pizza) {
        logger.info("Criando pizza: {}", pizza.getSabor());
        return pizzaRepository.save(pizza);
    }

    /**
     * Atualiza parcialmente uma pizza pelo ID.
     *
     * Permite atualizar apenas alguns campos da pizza identificada pelo ID.
     *
     * @param id ID da pizza a ser atualizada.
     * @param updates Campos a serem atualizados.
     * @return A pizza atualizada, se encontrada.
     */
    @Operation(
        summary = "Atualiza parcialmente uma pizza",
        description = "Atualiza apenas os campos informados de uma pizza identificada pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Pizza atualizada com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pizza.class)
        )
    )
    @SuppressWarnings("unchecked")
    @PatchMapping("/{id}")
    public ResponseEntity<Pizza> updateParcial(@PathVariable Long id, 
    @RequestBody Map<String, Object> updates)
    {
        return pizzaRepository.findById(id)
            .map(pizza -> {
                updates.forEach((campo,valor)->{
                    switch(campo){
                        case "sabor" -> pizza.setSabor((String)valor);
                        case "ingredientes" -> pizza.setIngredientes((Set<Ingredientes>) valor);
                        case "cardapio" -> pizza.setCardapio((Set<Cardapio>) valor);
                        default -> {
                            // Opcional - tratar campos desconhecidos
                        }
                    }   
                });
               
                return ResponseEntity.ok(pizzaRepository.save(pizza));
            }).orElse(ResponseEntity.notFound().build());
        
    }

    /**
     * Cria múltiplas pizzas de uma vez.
     *
     * Permite cadastrar várias pizzas em uma única requisição.
     *
     * @param pizzas Lista de objetos Pizza.
     * @return Lista das pizzas criadas.
     */
    @Operation(
        summary = "Cria múltiplas pizzas",
        description = "Recebe uma lista de pizzas em formato JSON e salva todas de uma vez."
    )
    @ApiResponse(responseCode = "201", description = "Pizzas criadas com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pizza.class)
        )
    )
    @PostMapping("/batch")
    public ResponseEntity<List<Pizza>> inserirMultiplas
        (@RequestBody List<Pizza> pizzas){
        return new ResponseEntity<>(
            pizzaRepository.saveAll(pizzas), HttpStatus.CREATED
        );
    }

    /**
     * Remove uma pizza pelo ID.
     *
     * Exclui a pizza identificada pelo ID do banco de dados.
     *
     * @param id ID da pizza a ser removida.
     * @return ResponseEntity sem conteúdo se removido, ou not found se não existir.
     */
    @Operation(
        summary = "Remove uma pizza",
        description = "Remove uma pizza identificada pelo ID."
    )
    @ApiResponse(responseCode = "204", description = "Pizza removida com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id)
    {
        if (!pizzaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        pizzaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza completamente uma pizza pelo ID.
     *
     * Substitui todos os campos da pizza identificada pelo ID pelos dados enviados.
     *
     * @param id ID da pizza a ser atualizada.
     * @param pizza Dados novos da pizza.
     * @return A pizza atualizada, se encontrada.
     */
    @Operation(
        summary = "Atualiza completamente uma pizza",
        description = "Atualiza todos os campos de uma pizza identificada pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Pizza atualizada com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pizza.class)
        )
    )
    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id, 
    @RequestBody Pizza pizza)
    {
        return pizzaRepository.findById(id)
            .map(p -> {
                p.setSabor(pizza.getSabor());
                p.setIngredientes(pizza.getIngredientes());
                p.setCardapio(pizza.getCardapio());
                return ResponseEntity.ok(pizzaRepository.save(p));
            }).orElse(ResponseEntity.notFound().build());
    
    }

}
