import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

/**
 * HttpClient をインターセプトする Service
 */
@Injectable({
    providedIn: 'root',
})
export class InterceptorService implements HttpInterceptor {
    /**
     * コンストラクター
     */
    constructor() {}

    /**
     * インターセプトメソッド
     *
     * @param req リクエスト
     * @param next HttpHandler
     *
     * @returns HTTPリクエストイベントストリーム
     */
    intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const cloneReq: HttpRequest<unknown> = req.clone({
            url: `${environment.apiURL}${req.url}`,
        });

        return next.handle(cloneReq);
    }
}
