import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { AppHttpService } from './app-http.service';
import { LoginUserResponse } from './components/layouts/contents/header/user-button/user-button-http.service';

/**
 * アプリケーション Service
 */
@Injectable({
    providedIn: 'root',
})
export class AppService {
    /**
     * 認証済み情報
     */
    private _isAuthorized$ = new Subject<boolean>();

    /**
     * ログインユーザー情報
     */
    private _loginUserInfo$ = new BehaviorSubject<UserInfo | null>(null);

    /**
     * 認証済み情報
     */
    get isAuthorized$(): Observable<boolean> {
        return this._isAuthorized$.asObservable();
    }

    /**
     * ログインユーザー情報
     */
    get loginUserInfo$(): Observable<UserInfo | null> {
        return this._loginUserInfo$.asObservable();
    }

    /**
     * コンストラクター
     *
     * @param router Router
     * @param appHttpService AppHttpService
     */
    constructor(private router: Router, private appHttpService: AppHttpService) {}

    /**
     * APIを経由してローカルの認証済み情報を更新する
     */
    fetchIsAuthorized(): void {
        this.appHttpService.getIsAuthorized().subscribe((response) => this._isAuthorized$.next(response.isAuthorized));
    }

    /**
     * ローカルの認証済み情報を更新する
     *
     * @param 更新値
     */
    setIsAuthorized(isAuthorized: boolean): void {
        this._isAuthorized$.next(isAuthorized);
    }

    /**
     * ログアウト
     */
    logout(): void {
        this.appHttpService.logout().subscribe(() => {
            this.setIsAuthorized(false);
            this._loginUserInfo$.next(null);
            this.router.navigateByUrl('login');
        });
    }

    /**
     * ログインユーザー情報をセットする
     *
     * @param data ユーザー情報
     */
    setLoginUserInfo(data: UserInfo): void {
        this._loginUserInfo$.next(data);
    }
}

/**
 * ログインユーザー情報
 */
export type UserInfo = LoginUserResponse;
