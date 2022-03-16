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

    /**
     * ユーザーデータの登録
     *
     * @param account アカウント
     * @param name 表示名
     * @param password パスワード
     * @param permission ユーザー権限
     * @param isEnabled 有効ステータス
     */
    postUser(
        account: string,
        name: string,
        password: string,
        permission: UsersPermission,
        isEnabled: boolean,
    ): Observable<PostUserResponse> {
        return this.httpClient.post<PostUserResponse>('/masters/usersMaster', {
            account,
            name,
            password,
            permission,
            isEnabled,
        });
    }

    /**
     * ユーザーデータの更新
     *
     * @param id ID
     * @param account アカウント
     * @param name 表示名
     * @param permission ユーザー権限
     * @param isEnabled 有効ステータス
     */
    putUser(id: number, account: string, name: string, permission: UsersPermission, isEnabled: boolean): Observable<void> {
        return this.httpClient.put<void>('/masters/usersMaster', {
            id,
            account,
            name,
            permission,
            isEnabled,
        });
    }

    /**
     * ユーザーデータの削除
     *
     * @param id ID
     */
    deleteUser(id: number): Observable<void> {
        return this.httpClient.delete<void>('/masters/usersMaster', {
            body: { id },
        });
    }
}

/**
 * getList メソッドのレスポンス
 */
interface GetListResponse {
    users: User[];
}

/**
 * postUser メソッドのレスポンス
 */
interface PostUserResponse {
    user: User;
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
