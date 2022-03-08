import { Component } from '@angular/core';
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
export class UserButtonComponent {
    /**
     * コンストラクター
     *
     * @param userButtonService UserButtonService
     */
    constructor(private userButtonService: UserButtonService) {}

    /**
     * ログアウトボタンクリックイベントハンドラー
     */
    logout(): void {
        this.userButtonService.logout();
    }
}
