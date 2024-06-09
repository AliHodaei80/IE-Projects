package ir.ie.mizdooni.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("Request received.");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null) {
            if (requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtUtil.extractUsername(jwtToken);
                } catch (IllegalArgumentException e) {
                    setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, "Unable to get JWT Token");
                    return;
                } catch (ExpiredJwtException e) {
                    setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, "JWT Token has expired");
                    return;
                } catch (UnsupportedJwtException e) {
                    setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, "JWT Token is unsupported");
                    return;
                } catch (MalformedJwtException e) {
                    setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, "JWT Token is malformed");
                    return;
                } catch (SignatureException e) {
                    setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, "JWT Token signature is invalid");
                    return;
                }
            } else {
                setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, "JWT Token does not begin with Bearer String");
                return;
            }
        } else {
            setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, "JWT Token is missing");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, "Invalid JWT Token");
                return;
            }
        }
        request.setAttribute("JWTUsername", username);
        chain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/api/login") || path.equals("/api/signup") || path.equals("/api/home") || path.equals("/api/logout") || path.matches("/api/Callback.*");
    }

    private void setErrorResponse(int status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", message);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
    }
}
