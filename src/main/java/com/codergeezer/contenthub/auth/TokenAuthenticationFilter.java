package com.codergeezer.contenthub.auth;

import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.repository.LoginHistoryRepository;
import com.codergeezer.core.base.constant.RequestConstant;
import com.codergeezer.core.base.exception.BaseException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author haidv
 * @version 1.0
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String SECURITY_CODE = "security_code";

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private final TokenProvider tokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    private final LoginHistoryRepository loginHistoryRepository;

    @Autowired
    public TokenAuthenticationFilter(TokenProvider tokenProvider,
                                     CustomUserDetailsService customUserDetailsService,
                                     LoginHistoryRepository loginHistoryRepository) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
        this.loginHistoryRepository = loginHistoryRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt)) {
                Claims claims = tokenProvider.getAllClaimsFromToken(jwt);
                String username = claims.getIssuer();
                if (!loginHistoryRepository.existsByActivatedTrueAndSessionIdAndSecurityCode(
                        claims.getAudience(), request.getHeader(SECURITY_CODE))) {
                    throw new BaseException(ErrorCode.USER_HAS_NOT_GROUP);
                }
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(RequestConstant.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(RequestConstant.BEARER_PREFIX)) {
            return bearerToken.substring(RequestConstant.BEARER_PREFIX.length());
        }
        return null;
    }
}
