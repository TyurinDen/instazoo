package vk.dentttt.instazoo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import vk.dentttt.instazoo.entities.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    @Value("${jwt.token.expired}")
    private long validityInMs;

    @Value("${jwt.token.header}")
    private String authorizationHeader;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    public String generateToken(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
        claims.put("roles", jwtUser.getAuthorities());

        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityInMs);

        return tokenPrefix + Jwts.builder()
                                 .setClaims(claims)
                                 .setIssuedAt(now)
                                 .setExpiration(expiresAt)
                                 .signWith(SignatureAlgorithm.HS512, tokenSecret)
                                 .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                   .setSigningKey(tokenSecret)
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String token = req.getHeader(authorizationHeader);
        if (token != null && token.startsWith("Bearer_")) {
            return token.substring(7);
        }
        return null;
    }

    private List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> result.add(role.getName()));
        return result;
    }

}
