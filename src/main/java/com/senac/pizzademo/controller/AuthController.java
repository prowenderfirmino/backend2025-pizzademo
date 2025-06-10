package com.senac.pizzademo.controller;

import com.senac.pizzademo.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller responsável pela autenticação de usuários.
 * 
 * Fornece um endpoint para login, que retorna um token JWT caso as credenciais estejam corretas.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * Realiza a autenticação do usuário e retorna um token JWT caso as credenciais estejam corretas.
     *
     * @param user Mapa contendo as chaves "username" e "password".
     * @return Mapa contendo o token JWT.
     * @throws ResponseStatusException Se as credenciais estiverem incorretas, retorna HTTP 401.
     */
    @Operation(
        summary = "Realiza login e retorna um token JWT",
        description = "Recebe um JSON com username e password. Se as credenciais estiverem corretas, retorna um token JWT para ser usado nos endpoints protegidos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(example = "{\"token\": \"eyJhbGciOiJIUzUxMiJ9...\"}")
            )
        ),
        @ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos",
            content = @Content(mediaType = "application/json",
                schema = @Schema(example = "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Usuário ou senha inválidos.\",\"path\":\"/auth/login\"}")
            )
        )
    })
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "JSON com username e password",
        required = true,
        content = @Content(
            schema = @Schema(example = "{\"username\": \"admin\", \"password\": \"senha123\"}")
        )
    )
    public Map<String, String> login(
        @RequestBody Map<String, String> user
    ) {
        // Extrai o nome de usuário e senha do corpo da requisição
        String username = user.get("username");
        String password = user.get("password");

        // Verifica se o usuário e senha estão corretos (hardcoded para exemplo)
        if ("admin".equals(username) && "senha123".equals(password)) {
            // Gera o token JWT usando o utilitário JwtUtil
            String token = JwtUtil.generateToken(username);

            // Cria um mapa de resposta contendo o token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            // Retorna o token para o cliente
            return response;
        } else {
            // Se as credenciais estiverem erradas, lança exceção com status 401 (não autorizado)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos.");
        }
    }
}
