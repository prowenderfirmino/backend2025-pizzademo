package com.senac.pizzademo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.pizzademo.model.Cardapio;
import com.senac.pizzademo.repository.CardapioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CardapioController é responsável por gerenciar os endpoints REST relacionados ao cardápio de pizzas.
 */
@RestController
@RequestMapping("/cardapio")
public class CardapioController {
    private static final Logger logger = LoggerFactory.getLogger(CardapioController.class);

    private final CardapioRepository cardapioRepository;

    public CardapioController(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    /**
     * Lista todos os itens do cardápio.
     *
     * Este método retorna todos os registros da tabela de cardápio.
     * Útil para exibir todos os tamanhos, valores e pizzas disponíveis.
     *
     * @return Lista de objetos Cardapio.
     */
    @Operation(summary = "Lista todos os itens do cardápio", description = "Retorna uma lista com todos os itens cadastrados no cardápio.")
    @GetMapping
    public List<Cardapio> getAllCardapios() {
        logger.info("Listando todos os itens do cardápio");
        return cardapioRepository.findAll();
    }

    /**
     * Cria um novo item no cardápio.
     *
     * Recebe um objeto Cardapio no corpo da requisição e salva no banco de dados.
     * Exemplo de uso: cadastrar um novo tamanho ou valor para uma pizza.
     *
     * @param cardapio Objeto Cardapio recebido no corpo da requisição.
     * @return O item criado.
     */
    @Operation(
        summary = "Cria um novo item no cardápio",
        description = "Recebe um JSON representando um item do cardápio e o salva no banco de dados."
    )
    @ApiResponse(responseCode = "200", description = "Item criado com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Cardapio.class)
        )
    )
    @PostMapping
    public Cardapio createCardapio(@Valid @RequestBody Cardapio cardapio) {
        logger.info("Criando item do cardápio: {}", cardapio.getTamanho());
        return cardapioRepository.save(cardapio);
    }

    /**
     * Cria múltiplos itens no cardápio de uma vez.
     *
     * Permite cadastrar vários itens do cardápio em uma única requisição.
     * Útil para importação em lote.
     *
     * @param cardapioList Lista de objetos Cardapio.
     * @return Lista dos itens criados.
     */
    @Operation(
        summary = "Cria múltiplos itens no cardápio",
        description = "Recebe uma lista de itens do cardápio em formato JSON e salva todos de uma vez."
    )
    @ApiResponse(responseCode = "200", description = "Itens criados com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Cardapio.class)
        )
    )
    @PostMapping("/batch")
    public List<Cardapio> createMultiplosCardapios(@RequestBody List<Cardapio> cardapioList) {
        return cardapioRepository.saveAll(cardapioList);
    }

    /**
     * Atualiza completamente um item do cardápio pelo ID.
     *
     * Substitui todos os campos do item identificado pelo ID pelos dados enviados.
     * Útil para corrigir informações de um item já cadastrado.
     *
     * @param id ID do item a ser atualizado.
     * @param cardapio Dados novos do item.
     * @return O item atualizado.
     */
    @Operation(
        summary = "Atualiza completamente um item do cardápio",
        description = "Atualiza todos os campos de um item do cardápio identificado pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Cardapio.class)
        )
    )
    @PutMapping("/{id}")
    public Cardapio updateCardapio(@PathVariable Long id, @RequestBody Cardapio cardapio) {
        return cardapioRepository.findById(id)
            .map(existing -> {
                existing.setValor(cardapio.getValor());
                existing.setTamanho(cardapio.getTamanho());
                if (cardapio.getPizza() != null) {
                    existing.setPizza(cardapio.getPizza());
                }
                return cardapioRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));
    }

    /**
     * Atualiza parcialmente um item do cardápio pelo ID.
     *
     * Permite atualizar apenas alguns campos do item identificado pelo ID.
     * Útil para pequenas correções sem necessidade de enviar todos os dados.
     *
     * @param id ID do item a ser atualizado.
     * @param cardapio Campos a serem atualizados.
     * @return O item atualizado.
     */
    @Operation(
        summary = "Atualiza parcialmente um item do cardápio",
        description = "Atualiza apenas os campos informados de um item do cardápio identificado pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Cardapio.class)
        )
    )
    @PatchMapping("/{id}")
    public Cardapio updateCardapioParcial(@PathVariable Long id, @RequestBody Cardapio cardapio) {
        return cardapioRepository.findById(id)
            .map(existing -> {
                if (cardapio.getValor() != null) {
                    existing.setValor(cardapio.getValor());
                }
                if (cardapio.getTamanho() != null) {
                    existing.setTamanho(cardapio.getTamanho());
                }
                if (cardapio.getPizza() != null) {
                    existing.setPizza(cardapio.getPizza());
                }
                return cardapioRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));
    }

    /**
     * Remove um item do cardápio pelo ID.
     *
     * Exclui o item identificado pelo ID do banco de dados.
     * Útil para remoção de itens obsoletos ou cadastrados incorretamente.
     *
     * @param id ID do item a ser removido.
     */
    @Operation(
        summary = "Remove um item do cardápio",
        description = "Remove um item do cardápio identificado pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Item removido com sucesso")
    @DeleteMapping("/{id}")
    public void deleteCardapio(@PathVariable Long id) {
        cardapioRepository.deleteById(id);
    }

}
