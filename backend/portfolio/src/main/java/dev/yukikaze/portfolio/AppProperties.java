package dev.yukikaze.portfolio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * application.propertiesのアクセサー
 */
@Component
@ConfigurationProperties("app")
public class AppProperties {
    @Getter
    @Setter
    private Cors cors;

    /**
     * CORS設定クラス
     */
    public static class Cors {
        @Getter
        @Setter
        private String allowedOrigins;
    }

    @Getter
    @Setter
    private Cookie cookie;

    /**
     * Cookie設定クラス
     */
    public static class Cookie {
        @Getter
        private boolean secure;

        @Getter
        private String domain;

        public void setSecure(String secure) {
            this.secure = Boolean.parseBoolean(secure);
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }

    @Getter
    @Setter
    private Jwt jwt;

    /**
     * JWT設定クラス
     */
    public static class Jwt {
        @Getter
        @Setter
        private String secret;

        @Getter
        private long exp;

        public void setExp(String exp) {
            this.exp = Long.parseLong(exp);
        }
    }
}
