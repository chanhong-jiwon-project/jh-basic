package com.jhs.crud.auth.util;

import com.jhs.crud.enm.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    String HEADER_STRING = "Authorization";
    String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String refreshToken = jwtTokenUtil.resolveRefreshToken(req);
        String accessToken = jwtTokenUtil.resolveAccessToken(req);
        String email = null;

        try {
            if (accessToken != null) {
                jwtTokenUtil.validateToken(accessToken);
                email = jwtTokenUtil.getUsernameFromToken(accessToken);
            } else if (refreshToken != null) {
                jwtTokenUtil.validateRefresh(refreshToken);
            }
        } catch (EmptyTokenException e) {
            req.setAttribute("exception", ErrorCode.TOKEN_NULL);
        } catch (SecurityException | MalformedJwtException e) {
            req.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN);
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            req.setAttribute("exception", ErrorCode.EXPIRED_TOKEN);
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            req.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            req.setAttribute("exception", ErrorCode.WRONG_TOKEN);
        } catch (NullPointerException e){
            throw new NullPointerException("JWT토큰이 존재하지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtTokenUtil.validateToken(accessToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + email + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}
