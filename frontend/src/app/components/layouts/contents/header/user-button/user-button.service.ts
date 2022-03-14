import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppService, UserInfo } from 'src/app/app.service';
import { UserButtonHttpService } from './user-button-http.service';

/**
 * ユーザーボタン Service
 */
@Injectable()
export class UserButtonService {
    /**
     * ログインユーザー情報
     */
    get loginUserInfo$(): Observable<UserInfo | null> {
        return this.appService.loginUserInfo$;
    }

    /**
     * コンストラクター
     *
     * @param appService AppService
     * @param userButtonHttpService UserButtonHttpService
     */
    constructor(private appService: AppService, private userButtonHttpService: UserButtonHttpService) {}

    /**
     * ログインユーザー情報を取得する
     */
    fetchLoginUserInfo(): void {
        this.userButtonHttpService.loginUser().subscribe((response) => this.appService.setLoginUserInfo(response));
    }

    /**
     * ログアウト
     */
    logout(): void {
        this.appService.logout();
    }
}
