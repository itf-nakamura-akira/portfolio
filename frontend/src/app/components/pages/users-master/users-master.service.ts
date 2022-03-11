import { Injectable } from '@angular/core';
import { Params, Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { UsersPermission } from 'src/app/enums/usersPermission';
import { UsersMasterHttpService } from './users-master-http.service';

/**
 * ユーザー設定画面 Service
 */
@Injectable()
export class UsersMasterService {
    /**
     * 検索パラメーター
     */
    private _filteringParameter$ = new BehaviorSubject<SearchParameter>({
        text: '',
        permission: [],
        isEnabled: false,
    });

    /**
     * 検索パラメーター
     */
    get filteringParameter$(): Observable<SearchParameter> {
        return this._filteringParameter$.pipe(tap((params) => this.updateUrlQueryParams(params)));
    }

    /**
     * コンストラクター
     *
     * @param router Router
     * @param usersMasterHttpService UsersMasterHttpService
     */
    constructor(private router: Router, private usersMasterHttpService: UsersMasterHttpService) {}

    /**
     * 検索パラメーターを更新する
     *
     * @param name 更新する検索パラメーターのプロパティー名
     * @param value 更新値
     */
    updateFilteringParameter(name: 'text' | 'permission' | 'isEnabled', value: string | UsersPermission[] | boolean): void {
        this._filteringParameter$.next({
            ...this._filteringParameter$.value,
            [name]: value,
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
