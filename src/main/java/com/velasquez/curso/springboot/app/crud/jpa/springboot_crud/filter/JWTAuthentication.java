package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.security.TokenJWTConfig.*;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;


public class JWTAuthentication extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    /**
     * Esta clase JWTAuthentication necesita un AuthenticationManager para delegar la tarea de autenticación.
     *
     * @param authenticationManager es el componente central de Spring Security que se encarga de verificar si las credenciales (nombre de usuario y contraseña) son válidas.
     */
    public JWTAuthentication(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Este método intenta autenticar al usuario. Es lo primero que ocurre cuando un cliente envía una solicitud de login.
     * En pocas palabras, aquí se procesan los datos que envía el cliente (JSON con username y password).
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //El usuario JSON se convertira a objeto JAVA (deserealizar)
        User user = null;
        String username = null;
        String password = null;

        //Los datos JSOn tiene que tener los misimos atributos que la clase USER
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class); // Toma un String y convierte a un objeto
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Se crea un objeto de tipo UsernamePasswordAuthenticationToken, que es la forma en que Spring Security representa las credenciales de un usuario.
         * Este token incluye:
         * El nombre de usuario (username).
         * La contraseña (password).
         */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // Se pasa el token de autenticación (authenticationToken) al AuthenticationManager.
        return authenticationManager.authenticate(authenticationToken);
    }//attemptAuthentication


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();

        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();
        response.addHeader(HEADER_ATHORIZATION, PREFIX_TOKEN + token);
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hola %s Has iniciado sesión con exito!", username));
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType("application/json");
        response.setStatus(200);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticación username o password incorrecto!");
        body.put("error", failed.getMessage()); // Causa del error
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401); // No autorizado
        response.setContentType(CONTENT_TYPE);
    }


}//UsernamePasswordAuthenticationFilter
