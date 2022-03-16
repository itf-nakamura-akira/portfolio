import { Injectable } from '@angular/core';
import { Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, combineLatest, map, Observable } from 'rxjs';
import { UsersPermission } from 'src/app/enums/usersPermission';
import { User, UsersMasterHttpService } from './users-master-http.service';

/**
 * ユーザー設定画面 Service
 */
@Injectable()
export class UsersMasterService {
    /**
     * ユーザーデータ
     */
    private _usersData$ = new BehaviorSubject<User[]>([]);

    /**
     * 検索パラメーター
     */
    private _filteringParameter$ = new BehaviorSubject<SearchParameter>({
        text: '',
        permission: [],
        isEnabled: false,
    });

    /**
     * ユーザーデータ
     */
    get usersData$(): Observable<User[]> {
        // 検索パラメーターでユーザーデータをフィルタリングする
        return combineLatest([this._usersData$, this._filteringParameter$]).pipe(
            map((values) => {
                const usersData = values[0];
                const filteringParameter = values[1];

                return usersData
                    .filter(
                        (data) =>
                            !filteringParameter.text ||
                            data.account.includes(filteringParameter.text) ||
                            data.name.includes(filteringParameter.text),
                    )
                    .filter((data) => filteringParameter.permission.length === 0 || filteringParameter.permission.includes(data.permission))
                    .filter((data) => filteringParameter.isEnabled || data.isEnabled);
            }),
        );
    }

    /**
     * コンストラクター
     *
     * @param router Router
     * @param usersMasterHttpService UsersMasterHttpService
     * @param toastrService ToastrService
     */
    constructor(private router: Router, private usersMasterHttpService: UsersMasterHttpService, private toastrService: ToastrService) {}

    /**
     * ユーザーデータを読み込む
     */
    fetchData(): void {
        this.usersMasterHttpService.getList().subscribe((response) => this._usersData$.next(response.users));
    }

    /**
     * 検索パラメーターを更新する
     *
     * @param name 更新する検索パラメーターのプロパティー名
     * @param value 更新値
     */
    updateFilteringParameter(name: 'text' | 'permission' | 'isEnabled', value: string | UsersPermission[] | boolean): void {
        const parameters = {
            ...this._filteringParameter$.value,
            [name]: value,
        };

        this._filteringParameter$.next(parameters);
        this.updateUrlQueryParams(parameters);
    }

    /**
     * ユーザーデータの登録
     *
     * @param account アカウント
     * @param name 表示名
     * @param password パスワード
     * @param permission ユーザー権限
     * @param isEnabled 有効ステータス
     * @param successCallback 更新成功時コールバック
     */
    registUser(
        account: string,
        name: string,
        password: string,
        permission: UsersPermission,
        isEnabled: boolean,
        successCallback: () => void,
    ): void {
        this.usersMasterHttpService.postUser(account, name, password, permission, isEnabled).subscribe((response) => {
            this.toastrService.success(`${name}(${account})さんのデータを追加しました。`);
            const newList = this._usersData$.value;
            newList.push(response.user);
            this._usersData$.next(newList);
            successCallback();
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
     * @param successCallback 更新成功時コールバック
     */
    updateUser(
        id: number,
        account: string,
        name: string,
        permission: UsersPermission,
        isEnabled: boolean,
        successCallback: () => void,
    ): void {
        this.usersMasterHttpService.putUser(id, account, name, permission, isEnabled).subscribe(() => {
            this.toastrService.success(`${name}(${account})さんのデータを更新しました。`);
            this._usersData$.next(
                this._usersData$.value.map((user) =>
                    user.id === id
                        ? {
                              id,
                              account,
                              name,
                              permission,
                              isEnabled,
                          }
                        : user,
                ),
            );
            successCallback();
        });
    }

    /**
     * ユーザーデータの削除
     *
     * @param id ID
     * @param account アカウント
     * @param name 表示名
     */
    deleteUser(id: number, account: string, name: string): void {
        this.usersMasterHttpService.deleteUser(id).subscribe(() => {
            this.toastrService.success(`${name}(${account})さんのデータを削除しました。`);
            this._usersData$.next(this._usersData$.value.filter((user) => user.id !== id));
        });
    }

    /**
     * URLパラメーターを更新する
     *
     * @param params 検索パラメーター
     */
    private updateUrlQueryParams(params: SearchParameter): void {
        const queryParams: Params = {
            text: params.text,
            permission: params.permission,
            isEnabled: params.isEnabled,
        };

        if (!params.text) {
            delete queryParams['text'];
        }

        if (!params.isEnabled) {
            delete queryParams['isEnabled'];
        }

        this.router.navigate([], { queryParams, replaceUrl: true });
    }
}

/**
 * 検索パラメーター
 */
export interface SearchParameter {
    /**
     * アカウント 表示名
     */
    text: string;

    /**
     * 権限
     */
    permission: UsersPermission[];

    /**
     * 有効ステータス
     */
    isEnabled: boolean;
}
