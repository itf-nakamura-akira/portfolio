import { Component } from '@angular/core';
import { SideNavService } from 'src/app/services/side-nav.service';

/**
 * サイドメニューを開閉するメニューボタン
 */
@Component({
    selector: 'app-menu-button',
    templateUrl: './menu-button.component.html',
    styleUrls: ['./menu-button.component.scss'],
})
export class MenuButtonComponent {
    /**
     * コンストラクター
     *
     * @param sideNavService SideNavService
     */
    constructor(private sideNavService: SideNavService) {}

    /**
     * メニューボタンクリックイベントハンドラー
     */
    menuButtonClick(): void {
        this.sideNavService.toggle();
    }
}
