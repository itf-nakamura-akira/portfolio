package dev.yukikaze.portfolio.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RestAPIのエラーハンドリング
 */
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 明示的に指定されているエラーをハンドリングする
     */
    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = ex.getStatus();

        return this.handleExceptionInternal(ex, headers, status, ex.getReason(), request);
    }

    /**
     * 明示されていない予期せぬエラーをハンドリングする
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleOthers(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return this.handleExceptionInternal(ex, headers, status, "サーバーでエラーが発生しました。", request);
    }

    /**
     * HttpMessageが正常に読み込めなかったときのエラーをハンドリングする
     */
    protected org.springframework.http.ResponseEntity<java.lang.Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, "サーバーでエラーが発生しました。",
                request);
    }

    /**
     * 全てのハンドラーから呼び出される共通処理メソッド
     */
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status,
            String message, WebRequest request) {
        var body = new AppErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * エラー時の共通Response body
     */
    record AppErrorResponse(int status, String message) {
    }
}
