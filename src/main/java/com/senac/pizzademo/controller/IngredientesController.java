package com.senac.pizzademo.controller;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.pizzademo.model.Ingredientes;
import com.senac.pizzademo.repository.IngredientesRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IngredientesController gerencia os endpoints REST para manipulação dos ingredientes das pizzas.
 * 
 * Permite listar, criar (um ou vários), atualizar (total ou parcialmente) e remover ingredientes.
 * Cada método corresponde a uma operação HTTP e manipula objetos do tipo Ingredientes.
 * 
 * Exemplo de JSON para criar/atualizar um ingrediente:
 * {
 *   "Ingrediente": "Queijo",
 *   "Quantidade": "200g",
 *   "pizza": {
 *     "id": 1
 *   }
 * }
 */
@RestController
@RequestMapping("/ingredientes")
public class IngredientesController {
    private static final Logger logger = LoggerFactory.getLogger(IngredientesController.class);

    private final IngredientesRepository ingredientesRepository;

    public IngredientesController(IngredientesRepository ingredientesRepository) {
        this.ingredientesRepository = ingredientesRepository;
    }

    /**
     * Lista todos os ingredientes cadastrados.
     * 
     * @return Lista de objetos Ingredientes.
     */
    @Operation(summary = "Lista todos os ingredientes", description = "Retorna uma lista com todos os ingredientes cadastrados.")
    @GetMapping
    public List<Ingredientes> getAllIngredientes() {
        logger.info("Listando todos os ingredientes");
        return ingredientesRepository.findAll();
    }

    /**
     * Cria um novo ingrediente.
     * 
     * Recebe um objeto Ingredientes no corpo da requisição e salva no banco de dados.
     * 
     * @param ingredientes Objeto Ingredientes recebido no corpo da requisição.
     * @return O ingrediente criado.
     */
    @Operation(
        summary = "Cria um novo ingrediente",
        description = "Recebe um JSON representando um ingrediente e o salva no banco de dados."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente criado com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Ingredientes.class)
        )
    )
    @PostMapping
    public Ingredientes createIngrediente(@Valid @RequestBody Ingredientes ingredientes) {
        logger.info("Criando ingrediente: {}", ingredientes.getIngrediente());
        return ingredientesRepository.save(ingredientes);
    }

    /**
     * Cria múltiplos ingredientes de uma vez.
     * 
     * Permite cadastrar vários ingredientes em uma única requisição.
     * 
     * @param ingredientesList Lista de objetos Ingredientes.
     * @return Lista dos ingredientes criados.
     */
    @Operation(
        summary = "Cria múltiplos ingredientes",
        description = "Recebe uma lista de ingredientes em formato JSON e salva todos de uma vez."
    )
    @ApiResponse(responseCode = "200", description = "Ingredientes criados com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Ingredientes.class)
        )
    )
    @PostMapping("/batch")
    public List<Ingredientes> createMultiplosIngredientes(@RequestBody List<Ingredientes> ingredientesList) {
        logger.info("Criando múltiplos ingredientes");
        return ingredientesRepository.saveAll(ingredientesList);
    }

    /**
     * Atualiza completamente um ingrediente pelo ID.
     * 
     * Substitui todos os campos do ingrediente identificado pelo ID pelos dados enviados.
     * 
     * @param id ID do ingrediente a ser atualizado.
     * @param ingredientes Dados novos do ingrediente.
     * @return O ingrediente atualizado.
     */
    @Operation(
        summary = "Atualiza completamente um ingrediente",
        description = "Atualiza todos os campos de um ingrediente identificado pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Ingredientes.class)
        )
    )
    @PutMapping("/{id}")
    public Ingredientes updateIngrediente(@PathVariable Long id, @RequestBody Ingredientes ingredientes) {
        logger.info("Atualizando ingrediente com ID {}: {}", id, ingredientes.getIngrediente());
        return ingredientesRepository.findById(id)
            .map(existing -> {
                existing.setIngrediente(ingredientes.getIngrediente());
                existing.setQuantidade(ingredientes.getQuantidade());
                if (ingredientes.getPizza() != null) {
                    existing.setPizza(ingredientes.getPizza());
                }
                return ingredientesRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));
    }

    /**
     * Atualiza parcialmente um ingrediente pelo ID.
     * 
     * Permite atualizar apenas alguns campos do ingrediente identificado pelo ID.
     * 
     * @param id ID do ingrediente a ser atualizado.
     * @param ingredientes Campos a serem atualizados.
     * @return O ingrediente atualizado.
     */
    @Operation(
        summary = "Atualiza parcialmente um ingrediente",
        description = "Atualiza apenas os campos informados de um ingrediente identificado pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Ingredientes.class)
        )
    )
    @PatchMapping("/{id}")
    public Ingredientes updateIngredienteParcial(@PathVariable Long id, @RequestBody Ingredientes ingredientes) {
        logger.info("Atualizando parcialmente ingrediente com ID {}", id);
        return ingredientesRepository.findById(id)
            .map(existing -> {
                if (ingredientes.getIngrediente() != null) {
                    existing.setIngrediente(ingredientes.getIngrediente());
                }
                if (ingredientes.getQuantidade() != null) {
                    existing.setQuantidade(ingredientes.getQuantidade());
                }
                if (ingredientes.getPizza() != null) {
                    existing.setPizza(ingredientes.getPizza());
                }
                return ingredientesRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));
    }

    /**
     * Remove um ingrediente pelo ID.
     * 
     * Exclui o ingrediente identificado pelo ID do banco de dados.
     * 
     * @param id ID do ingrediente a ser removido.
     */
    @Operation(
        summary = "Remove um ingrediente",
        description = "Remove um ingrediente identificado pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente removido com sucesso")
    @DeleteMapping("/{id}")
    public void deleteIngrediente(@PathVariable Long id) {
        logger.info("Removendo ingrediente com ID {}", id);
        ingredientesRepository.deleteById(id);
    }

}
