package dev.yukikaze.portfolio.resolvers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

import dev.yukikaze.portfolio.Constants;
import dev.yukikaze.portfolio.annotations.JwtCookie;
import dev.yukikaze.portfolio.utils.JwtUtils;

/**
 * JwtCookieアノテーションのResolver
 */
@Component
public class JwtCookieHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private JwtUtils jwtUtils;

    public JwtCookieHandlerMethodArgumentResolver(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
            NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
        Cookie jwtCookie = WebUtils.getCookie(httpServletRequest, Constants.JWT_COOKIE_KEY);

        if (jwtCookie != null) {
            return this.jwtUtils.verifyToken(jwtCookie.getValue())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasMethodAnnotation(JwtCookie.class)
                || parameter.hasParameterAnnotation(JwtCookie.class);
    }
}