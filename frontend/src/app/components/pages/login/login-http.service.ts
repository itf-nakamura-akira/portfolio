import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/**
 * ログイン画面 HTTP Service
 */
@Injectable({
    providedIn: 'root',
})
export class LoginHttpService {
    /**
     * コンストラクター
     *
     * @param httpClient HttpClient
     */
    constructor(private httpClient: HttpClient) {}

    /**
     * ログイン
     *
     * @param account アカウント
     * @param password パスワード
     */
    login(account: string, password: string): Observable<void> {
        const body = { account, password };

        return this.httpClient.post<void>('/login', body);
    }
}
