package dev.yukikaze.portfolio;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.yukikaze.portfolio.resolvers.JwtCookieHandlerMethodArgumentResolver;

/**
 * アプリケーションコンフィグ
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {
    /**
     * アプリケーション設定
     */
    private final AppProperties appProperties;

    /**
     * JwtCookieアノテーション Resolver
     */
    private JwtCookieHandlerMethodArgumentResolver jwtCookieHandlerMethodArgumentResolver;

    /**
     * コンストラクター
     *
     * @param appProperties                          アプリケーション設定
     * @param jwtCookieHandlerMethodArgumentResolver JwtCookieアノテーション Resolver
     */
    public AppConfig(AppProperties appProperties,
            JwtCookieHandlerMethodArgumentResolver jwtCookieHandlerMethodArgumentResolver) {
        this.appProperties = appProperties;
        this.jwtCookieHandlerMethodArgumentResolver = jwtCookieHandlerMethodArgumentResolver;
    }

    /**
     * Resolverの登録
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(this.jwtCookieHandlerMethodArgumentResolver);
    }

    /**
     * CORSの設定
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigins = this.appProperties.getCors().getAllowedOrigins();

        registry.addMapping("/**").allowedOrigins(allowedOrigins)
                .allowedMethods(HttpMethod.OPTIONS.name(), HttpMethod.HEAD.name(),
                        HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name())
                .allowedHeaders("*").allowCredentials(true).maxAge(0);
    }

    // /**
    // * ロギングフィルターの設定
    // */
    // @Bean
    // public RequestLoggingFilter requestLoggingFilter() {
    // return new RequestLoggingFilter();
    // }
}
