import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsersPermission } from 'src/app/enums/usersPermission';

/**
 * ユーザーボタン HTTP Service
 */
@Injectable({
    providedIn: 'root',
})
export class UserButtonHttpService {
    /**
     * コンストラクター
     *
     * @param httpClient HttpClient
     */
    constructor(private httpClient: HttpClient) {}

    /**
     * ログインユーザー情報を取得
     *
     * @returns ログインユーザー情報
     */
    loginUser(): Observable<LoginUserResponse> {
        return this.httpClient.get<LoginUserResponse>('/common/users/loginUser');
    }

    /**
     * サインアウト処理
     */
    logout(): Observable<void> {
        return this.httpClient.post<void>('/common/users/logout', {});
    }
}

/**
 * loginUser リクエストのレスポンス
 */
export interface LoginUserResponse {
    account: string;

    name: string;

    permission: UsersPermission;
}
