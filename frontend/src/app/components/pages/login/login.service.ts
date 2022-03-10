import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { AppService } from 'src/app/app.service';
import { LoginHttpService } from './login-http.service';

/**
 * ログイン画面 Service
 */
@Injectable()
export class LoginService {
    /**
     * エラー状態
     */
    private _errorMessage$ = new Subject<string>();

    /**
     * エラー状態
     */
    get errorMessage$(): Observable<string> {
        return this._errorMessage$.asObservable();
    }

    /**
     * コンストラクター
     *
     * @param loginHttpService LoginHttpService
     */
    constructor(private router: Router, private appService: AppService, private loginHttpService: LoginHttpService) {}

    /**
     * ログイン
     *
     * @param account アカウント
     * @param password パスワード
     */
    login(account: string, password: string): void {
        this.loginHttpService.login(account, password).subscribe(
            () => {
                this.appService.setIsAuthorized(true);
                this.router.navigateByUrl('dashboard');
            },
            () => {
                this._errorMessage$.next('ログインに失敗しました。アカウントもしくはパスワードが間違っています。');
            },
        );
    }
}
