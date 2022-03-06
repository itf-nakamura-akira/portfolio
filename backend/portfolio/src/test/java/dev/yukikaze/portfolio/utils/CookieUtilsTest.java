package dev.yukikaze.portfolio.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import dev.yukikaze.portfolio.AppProperties;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;

/**
 * CookieUtils のテストコード
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("CookieUtils のテストコード")
public class CookieUtilsTest {
    /**
     * CookieUtils
     */
    @Autowired
    private CookieUtils cookieUtils;

    /**
     * JwtUtils
     */
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * AppProperties
     */
    @Autowired
    private AppProperties appProperties;

    @Test
    @DisplayName("setCookie メソッドのテスト")
    public void setCookie() {
        // Cookieの設定
        var response = new Response();
        response.setCoyoteResponse(new org.apache.coyote.Response());
        this.cookieUtils.setCookie(response, 1L, UsersPermission.Admin);

        // Cookieの確認
        String cookie = response.getHeader("Set-Cookie");
        Pattern pattern = Pattern.compile("jwt=(.*?);");
        Matcher matcher = pattern.matcher(cookie);
        matcher.find();
        String jwtStr = matcher.group().replaceFirst("^jwt=", "").replaceFirst(";$", "");
        Optional<JwtPayload> payload = this.jwtUtils.verifyToken(jwtStr);

        assertTrue(payload.isPresent());
        assertEquals(payload.get().id(), 1L);
        assertEquals(payload.get().permission(), UsersPermission.Admin);

        var tokenExp = new Date(payload.get().exp() * 1000);
        var afterExp = new Date(
                System.currentTimeMillis() + this.appProperties.getJwt().getExp() * 24 * 60 * 60 * 1000);
        var sub = Math.abs(afterExp.getTime() - tokenExp.getTime());

        assertTrue(sub < 5 * 1000); // 差が5秒以下
    }

    @Test
    @DisplayName("delCookie メソッドのテスト")
    public void delCookie() throws ParseException {
        // Cookieの設定
        var response = new Response();
        response.setCoyoteResponse(new org.apache.coyote.Response());
        this.cookieUtils.setCookie(response, 1L, UsersPermission.Admin);

        // Cookieの削除
        this.cookieUtils.delCookie(response);

        // Cookieの確認(有効期限が切れていること)
        String cookie = response.getHeader("Set-Cookie");
        Pattern pattern = Pattern.compile("Expires=(.*?);");
        Matcher matcher = pattern.matcher(cookie);
        matcher.find();
        String expStr = matcher.group().replaceFirst("^Expires=", "").replaceFirst(";$", "");
        DateTimeFormatter expFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)
                .withZone(ZoneId.of("GMT"));
        Date exp = Date.from(ZonedDateTime.of(LocalDateTime.parse(expStr, expFormat), ZoneId.of("GMT"))
                .toInstant());
        Date now = Date.from(ZonedDateTime.of(LocalDateTime.now(ZoneId.of("GMT")), ZoneId.of("GMT"))
                .toInstant());

        assertTrue(exp.getTime() < now.getTime());
    }
}
