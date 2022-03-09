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
     * コンストラクター
     *
     * @param userButtonService UserButtonService
     */
    constructor(public userButtonService: UserButtonService) {}

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
