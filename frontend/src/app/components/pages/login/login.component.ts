import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';
import { LoginService } from './login.service';

/**
 * ログイン画面 Component
 */
@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    providers: [LoginService],
})
export class LoginComponent implements OnInit {
    /**
     * コンストラクター
     *
     * @param appService AppService
     * @param loginService LoginService
     */
    constructor(private appService: AppService, private loginService: LoginService) {}

    /**
     * 初期化
     */
    ngOnInit(): void {
        this.appService.setIsAuthorized(false);
    }

    /**
     * ログインボタンクリックイベントハンドラー
     */
    loginButtonClick(): void {
        this.loginService.login('admin', 'admin');
    }
}
