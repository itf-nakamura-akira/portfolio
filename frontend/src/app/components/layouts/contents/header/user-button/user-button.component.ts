import { Component, OnInit } from '@angular/core';
import { UserButtonService } from './user-button.service';

/**
 * ユーザーメニューを表示するユーザーボタン
 */
@Component({
    selector: 'app-user-button',
    templateUrl: './user-button.component.html',
    styleUrls: ['./user-button.component.scss'],
    providers: [UserButtonService],
})
export class UserButtonComponent implements OnInit {
    /**
     * ログインユーザー情報
     */
    loginUserInfo$ = this.userButtonService.loginUserInfo$;

    /**
     * コンストラクター
     *
     * @param userButtonService UserButtonService
     */
    constructor(private userButtonService: UserButtonService) {}

    /**
     * 初期化
     */
    ngOnInit(): void {
        this.userButtonService.fetchLoginUserInfo();
    }

    /**
     * ログアウトボタンクリックイベントハンドラー
     */
    logout(): void {
        this.userButtonService.logout();
    }
}
