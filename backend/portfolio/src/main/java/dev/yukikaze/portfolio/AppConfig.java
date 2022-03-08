package dev.yukikaze.portfolio;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
     * コンストラクター
     *
     * @param appProperties アプリケーション設定
     */
    public AppConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
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
