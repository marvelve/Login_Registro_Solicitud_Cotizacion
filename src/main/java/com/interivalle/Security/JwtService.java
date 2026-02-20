package com.interivalle.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expMs;

    private Key getKey() {
        // Usar UTF-8 evita problemas de encoding
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String correo, Integer idRol) {

        return Jwts.builder()
                .setSubject(correo)
                .claim("idRol", idRol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerCorreo(String token) {
        return getClaims(token).getSubject();
    }

    public Integer extraerRol(String token) {
        return getClaims(token).get("idRol", Integer.class);
    }

    public boolean esTokenValido(String token) {

        try {
            getClaims(token);
            return true;

        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token no soportado");
        } catch (MalformedJwtException e) {
            System.out.println("Token mal formado");
        } catch (SecurityException e) {
            System.out.println("Firma inválida");
        } catch (IllegalArgumentException e) {
            System.out.println("Token vacío");
        }

        return false;
    }

    private Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
