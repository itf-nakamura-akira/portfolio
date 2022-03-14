import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsersPermission } from 'src/app/enums/usersPermission';

/**
 * ユーザー設定画面 HTTP Service
 */
@Injectable({
    providedIn: 'root',
})
export class UsersMasterHttpService {
    /**
     * コンストラクター
     *
     * @param httpClient HttpClient
     */
    constructor(private httpClient: HttpClient) {}

    /**
     * ユーザーデータ一覧の取得
     *
     * @returns ユーザーデータ一覧
     */
    getList(): Observable<GetListResponse> {
        return this.httpClient.get<GetListResponse>('/masters/usersMaster/list');
    }
}

/**
 * getList メソッドのレスポンス
 */
interface GetListResponse {
    users: User[];
}

/**
 * ユーザーデータ
 */
export interface User {
    /**
     * ID
     */
    id: number;

    /**
     * アカウント
     */
    account: string;

    /**
     * 表示名
     */
    name: string;

    /**
     * ユーザー権限
     */
    permission: UsersPermission;

    /**
     * 有効ステータス
     */
    isEnabled: boolean;
}