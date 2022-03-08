import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/**
 * アプリケーション HTTP Service
 */
@Injectable({
    providedIn: 'root',
})
export class AppHttpService {
    /**
     * コンストラクター
     *
     * @param httpClient HttpClient
     */
    constructor(private httpClient: HttpClient) {}

    /**
     * 認証済みかどうかの情報を取得する
     *
     * @returns 認証済み情報
     */
    getIsAuthorized(): Observable<IsAuthorizedResponse> {
        return this.httpClient.get<IsAuthorizedResponse>('/common/app/isAuthorized');
    }
}

/**
 * getIsAuthorized リクエストのレスポンス
 */
export interface IsAuthorizedResponse {
    isAuthorized: boolean;
}
