import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { map, Observable, tap } from 'rxjs';
import { AppService } from '../app.service';

/**
 * 認証情報を元にルーティングを許可する Guard
 */
@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {
    /**
     * 認証済み情報
     */
    private _isAuthorized: boolean | undefined = undefined;

    /**
     * コンストラクター
     *
     * @param router Router
     * @param appService AppService
     */
    constructor(private router: Router, private appService: AppService) {
        this.appService.isAuthorized$.subscribe((isAuthorized) => (this._isAuthorized = isAuthorized));
    }

    /**
     * ルーティングの許可チェック
     *
     * @param route ActivatedRouteSnapshot
     * @param state RouterStateSnapshot
     *
     * @returns 認証結果
     */
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
        if (this._isAuthorized !== undefined) {
            return this.alreadyChecked(state);
        } else {
            return this.notCheckedYet(state);
        }
    }

    /**
     * 認証情報が取得できている時の処理
     *
     * @param state RouterStateSnapshot
     *
     * @returns チェック結果
     */
    private alreadyChecked(state: RouterStateSnapshot): boolean {
        if (state.url === '/login') {
            return !this._isAuthorized;
        } else {
            if (this._isAuthorized) {
                return true;
            } else {
                this.router.navigateByUrl('login');

                return false;
            }
        }
    }

    /**
     * アプリ起動直後で認証情報が取得できていない時の処理
     *
     * @param state RouterStateSnapshot
     *
     * @returns チェック結果
     */
    private notCheckedYet(state: RouterStateSnapshot): Observable<boolean> {
        setTimeout(() => this.appService.fetchIsAuthorized(), 0);

        if (state.url === '/login') {
            return this.appService.isAuthorized$.pipe(map((isAuthorized) => !isAuthorized));
        } else {
            return this.appService.isAuthorized$.pipe(
                tap((isAuthorized) => {
                    if (!isAuthorized) {
                        this.router.navigateByUrl('login');
                    }
                }),
            );
        }
    }
}
