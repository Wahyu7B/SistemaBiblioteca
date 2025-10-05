package com.example.acceso.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SessionInterceptor.class);
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    public static final String[] PUBLIC = {
            "/",                 
            "/login", "/logout",
            "/css/**", "/js/**", "/images/**", "/img/**", "/assets/**", "/webjars/**",
            "/favicon.ico", "/error"
    };

    private boolean isPublic(String uri) {
        for (String p : PUBLIC) if (MATCHER.match(p, uri)) return true;
        return false;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest req,
                             @NonNull HttpServletResponse res,
                             @NonNull Object handler) throws Exception {

        String ctx = req.getContextPath() == null ? "" : req.getContextPath();
        String uri = req.getRequestURI();
        if (!ctx.isEmpty() && uri.startsWith(ctx)) uri = uri.substring(ctx.length());

        if ("/".equals(uri) || uri.isEmpty()) {
            log.debug("SessionInterceptor: allow ROOT '/'");
            return true;
        }

        if (isPublic(uri)) {
            log.debug("SessionInterceptor: allow PUBLIC uri={}", uri);
            return true;
        }

        HttpSession session = req.getSession(false);
        Object user = (session != null ? session.getAttribute("usuarioLogueado") : null);

        boolean hasUser = (user != null);
        log.debug("SessionInterceptor: uri={} secured=true userPresent={}", uri, hasUser);

        if (!hasUser) {
            res.sendRedirect(ctx + "/login");
            return false;
        }
        return true;
    }
}
