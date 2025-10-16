package br.com.jrsr.financeapi.infrastructure.components;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserContext {

    private final HttpServletRequest request;

    public UserContext(HttpServletRequest request) {
        this.request = request;
    }

    private Claims getClaims() {
        return (Claims) request.getAttribute("claims");
    }

    public String getUserEmail() {
        Claims claims = getClaims();
        return claims != null ? claims.getSubject() : null;
    }

    public Date getIssuedAt() {
        Claims claims = getClaims();
        return claims != null ? claims.getIssuedAt() : null;
    }

    public Date getExpiration() {
        Claims claims = getClaims();
        return claims != null ? claims.getExpiration() : null;
    }
}
