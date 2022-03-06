package dev.yukikaze.portfolio.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.ConnectException;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.exception.ControllerExceptionHandler.AppErrorResponse;

/**
 * ControllerExceptionHandler のテストコード
 */
@DisplayName("ControllerExceptionHandler のテストコード")
public class ControllerExceptionHandlerTest {
    /**
     * ControllerExceptionHandler
     */
    private ControllerExceptionHandler controllerExceptionHandler;

    /**
     * 初期化
     */
    @BeforeEach
    public void beforeAll() {
        this.controllerExceptionHandler = new ControllerExceptionHandler();
    }

    @Test
    @DisplayName("handleResponseStatus メソッドのテスト")
    public void handleResponseStatus() {
        // エラーの生成(status 500)
        var e = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error detail.");
        var connector = new Connector();
        var hsr = new Request(connector);
        var request = new ServletWebRequest(hsr);

        // エラーレスポンスの生成
        ResponseEntity<Object> response = this.controllerExceptionHandler.handleResponseStatus(e, request);

        // エラーレスポンスの確認
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(((AppErrorResponse) response.getBody()).message(), "error detail.");

        // エラーの生成(status 500以外)
        e = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no authorize.");
        connector = new Connector();
        hsr = new Request(connector);
        request = new ServletWebRequest(hsr);

        // エラーレスポンスの生成
        response = this.controllerExceptionHandler.handleResponseStatus(e, request);

        // エラーレスポンスの確認
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
        assertEquals(((AppErrorResponse) response.getBody()).message(), "no authorize.");
    }

    @Test
    @DisplayName("handleOthers メソッドのテスト")
    public void handleOthers() {
        // エラーの生成(Exception)
        var e = new Exception();
        var connector = new Connector();
        var hsr = new Request(connector);
        var request = new ServletWebRequest(hsr);

        // エラーレスポンスの生成
        ResponseEntity<Object> response = this.controllerExceptionHandler.handleOthers(e, request);

        // エラーレスポンスの確認
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(((AppErrorResponse) response.getBody()).message(), "サーバーでエラーが発生しました。");

        // エラーの生成(NullPointerException)
        e = new NullPointerException();
        connector = new Connector();
        hsr = new Request(connector);
        request = new ServletWebRequest(hsr);

        // エラーレスポンスの生成
        response = this.controllerExceptionHandler.handleOthers(e, request);

        // エラーレスポンスの確認
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(((AppErrorResponse) response.getBody()).message(), "サーバーでエラーが発生しました。");

        // エラーの生成(ConnectException)
        e = new ConnectException();
        connector = new Connector();
        hsr = new Request(connector);
        request = new ServletWebRequest(hsr);

        // エラーレスポンスの生成
        response = this.controllerExceptionHandler.handleOthers(e, request);

        // エラーレスポンスの確認
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(((AppErrorResponse) response.getBody()).message(), "サーバーでエラーが発生しました。");
    }
}
