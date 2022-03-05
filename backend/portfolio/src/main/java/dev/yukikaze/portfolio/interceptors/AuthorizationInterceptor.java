package dev.yukikaze.portfolio.interceptors;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import dev.yukikaze.portfolio.Constants;
import dev.yukikaze.portfolio.annotations.NonAuth;
import dev.yukikaze.portfolio.annotations.Permission;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.utils.JwtUtils;

/**
 * 認可 Interceptor
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    /**
     * JwtUtils
     */
    private JwtUtils jwtUtils;

    /**
     * コンストラクター
     *
     * @param jwtUtils JwtUtils
     */
    public AuthorizationInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Controllerメソッド前の処理
     *
     * @param request  リクエスト
     * @param response レスポンス
     * @param handler  Controllerメソッド
     * 
     * @return チェック処理の結果
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        // チェックの必要のあるリクエストかチェックする
        if (this.checkIgnoreRequest(request, handler)) {
            return true;
        }

        // JWTトークンから認証情報を確認する
        // ユーザー権限のチェック
        if (!this.validateJwtToken(request, handler)
                || !this.validatePermission(request, handler)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return true;
    }

    /**
     * PreFlightリクエスト・静的リソースへのリクエストかをチェックする
     *
     * @param request リクエスト
     * @param handler Controllerメソッド
     *
     * @return チェック結果
     */
    private boolean checkIgnoreRequest(HttpServletRequest request, Object handler) {
        // PreFlightリクエストは無視する
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        // 静的リソースの場合は認証不要
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        return false;
    }

    /**
     * JWTトークンのチェック
     *
     * @param request リクエスト
     * @param handler Controllerメソッド
     *
     * @return チェック結果
     */
    private boolean validateJwtToken(HttpServletRequest request, Object handler) {
        try {
            // NonAuthアノテーションの確認
            var hm = (HandlerMethod) handler;
            Method method = hm.getMethod();
            NonAuth annotation = AnnotationUtils.findAnnotation(method, NonAuth.class);

            if (annotation != null) {
                return true;
            }

            // Cookieの取得
            Cookie[] cookies = request.getCookies();

            if (cookies == null || cookies.length == 0) {
                throw new Exception();
            }

            // JWTトークンのCookieを取得
            Cookie jwtCookie = Arrays.stream(cookies).filter(v -> v.getName().equals(Constants.JWT_COOKIE_KEY))
                    .findAny().orElseThrow(() -> new Exception());

            // JWTトークンが改変されていないか検証
            JwtPayload jwtPayload = this.jwtUtils.verifyToken(jwtCookie.getValue())
                    .orElseThrow(() -> new Exception());

            // @PrePersistなどController以外から値を取得したいとき用
            RequestAttributes currentAttributes = RequestContextHolder.currentRequestAttributes();
            currentAttributes.setAttribute("requestUserId", jwtPayload.id(),
                    RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return true;
    }

    /**
     * Permissionアノテーションのチェック
     *
     * @param request リクエスト
     * @param handler Controllerメソッド
     *
     * @return チェック結果
     */
    private boolean validatePermission(HttpServletRequest request, Object handler) {
        try {
            // Permissionアノテーションの確認
            var hm = (HandlerMethod) handler;
            Method method = hm.getMethod();
            Class<?> controllerClass = method.getDeclaringClass();
            Permission annotation = AnnotationUtils.findAnnotation(controllerClass, Permission.class);

            if (annotation == null) {
                return true;
            }

            // Cookieの取得
            Cookie[] cookies = request.getCookies();

            if (cookies == null || cookies.length == 0) {
                throw new Exception();
            }

            // JWTトークンのCookieを取得
            Cookie jwtCookie = Arrays.stream(cookies).filter(v -> v.getName().equals(Constants.JWT_COOKIE_KEY))
                    .findAny().orElseThrow(() -> new Exception());

            // JWTトークンが改変されていないか検証
            JwtPayload jwtPayload = this.jwtUtils.verifyToken(jwtCookie.getValue())
                    .orElseThrow(() -> new Exception());

            // アノテーションで指定された権限を所持しているか検証
            UsersPermission cookiePermission = jwtPayload.permission();
            UsersPermission[] annotationPermission = annotation.value();

            return Arrays.asList(annotationPermission).contains(cookiePermission);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
