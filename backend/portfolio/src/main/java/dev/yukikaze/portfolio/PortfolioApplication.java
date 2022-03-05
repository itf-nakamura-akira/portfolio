package dev.yukikaze.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootアプリケーションエントリークラス
 */
@SpringBootApplication
public class PortfolioApplication {
    /**
     * mainメソッド
     *
     * @param args 起動時引数オプション
     */
    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }
}
