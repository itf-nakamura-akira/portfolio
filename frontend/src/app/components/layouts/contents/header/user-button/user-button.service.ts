import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { AppService } from 'src/app/app.service';
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
     * @param router Router
     * @param appService AppService
     * @param userButtonHttpService UserButtonHttpService
     */
    constructor(private router: Router, private appService: AppService, private userButtonHttpService: UserButtonHttpService) {}

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
        this.userButtonHttpService.logout().subscribe(() => {
            this.appService.setIsAuthorized(false);
            this._loginUserInfo$.next(null);
            this.router.navigateByUrl('login');
        });
    }
}

/**
 * ログインユーザー情報
 */
export type UserInfo = LoginUserResponse;
