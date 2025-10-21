package br.com.jrsr.financeapi.infrastructure.components;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Date;

@Component
@RequestScope
// Por padrão, beans @Component são singleton, então dados armazenados neles poderiam ser compartilhados entre requisições.
// Usando @RequestScope, cada requisição recebe uma instância própria do UserContext, garantindo que claimsCache pertença apenas àquela requisição.
public class UserContext {

    private final HttpServletRequest request;
    private Claims claimsCache;

    public UserContext(HttpServletRequest request) {
        this.request = request;
    }

    private Claims getClaims() {
        if (claimsCache == null) {
            claimsCache = (Claims) request.getAttribute("claims");
        }
        return claimsCache;
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
