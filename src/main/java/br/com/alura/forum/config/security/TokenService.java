package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authenticate) {
        Usuario logado = (Usuario) authenticate.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API do Fórum da Alura") // quem é a aplicacao que tá fazendo a geração do token
                .setSubject(logado.getId().toString()) // quem é o usuario autenticado que o token pertence, passaremos o id, pois ele é unico
                .setIssuedAt(hoje) // data de geração do token
                .setExpiration(dataExpiracao) // o token tem um periodo de expiração, para nao ficar infinito (risco de seguranca)
                .signWith(SignatureAlgorithm.HS256, secret) // o token tem que ser criptografado, temos que dizer qual é o algoritmo e a senha da aplicacao, pra gerar a criptografia
                .compact(); // compactar e transformar em uma string
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
