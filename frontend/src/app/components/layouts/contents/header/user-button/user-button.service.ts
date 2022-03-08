import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginUserResponse, UserButtonHttpService } from './user-button-http.service';

/**
 * ユーザーボタン Service
 */
@Injectable()
export class UserButtonService {
    /**
     * ログインユーザー情報
     */
    private _loginUserInfo$ = new BehaviorSubject<UserInfo | null>(null);

    /**
     * ログインユーザー情報
     */
    get loginUserInfo$(): Observable<UserInfo | null> {
        return this._loginUserInfo$.asObservable();
    }

    /**
     * コンストラクター
     *
     * @param userButtonHttpService UserButtonHttpService
     */
    constructor(private userButtonHttpService: UserButtonHttpService) {}

    /**
     * ログインユーザー情報を取得する
     */
    fetchLoginUserInfo(): void {
        this.userButtonHttpService.loginUser().subscribe((response) => this._loginUserInfo$.next(response));
    }

    /**
     * ログアウト
     */
    logout(): void {
        this.userButtonHttpService.logout().subscribe(() => this._loginUserInfo$.next(null));
    }
}

/**
 * ログインユーザー情報
 */
export type UserInfo = LoginUserResponse;
