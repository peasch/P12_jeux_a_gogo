package com.peasch.jeuxagogo.controller.security.config;


import com.peasch.jeuxagogo.controller.security.service.CustomUserDetailsService;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import io.jsonwebtoken.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:spongeBob}")
    private String secretKey ="spongeBob";

    private long validityInMilliseconds =24*60*60*1000;
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    @Autowired
    private CustomUserDetailsService userDetailsService;




    public String createToken(String username, Set<RoleDto> set) { // Nous créons le Token : nous retrouvons les Claims dont nous avons parlé dans le cou
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", set);
        long expMillis = nowMillis + validityInMilliseconds;
        Date validity = new Date(expMillis);
        return Jwts.builder()//
                .setClaims(claims)// On y ajoute les informations qu'on veut nous même, les Claims.
                .setIssuedAt(now)// Date de création
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsername(String token) { // On récupère le Claims userName.
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) { // Nous récupérons l'authentification courante de l'utilsiateur courant.
        UserDetails userDetails =  userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req) { // Méthode qui va extraire de la requête Http le token.
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public boolean validateToken(String token) { // Voici la méthode qui va nous permettre de valider ou non un Token de part sa date d'expiration ou
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date(System.currentTimeMillis()))) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token");
        }
    }
}
