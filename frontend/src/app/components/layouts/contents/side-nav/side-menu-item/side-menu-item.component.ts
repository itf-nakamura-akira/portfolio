import { Component, Input } from '@angular/core';

/**
 * サイドメニューアイテムComponent
 */
@Component({
    selector: 'app-side-menu-item',
    templateUrl: './side-menu-item.component.html',
    styleUrls: ['./side-menu-item.component.scss'],
})
export class SideMenuItemComponent {
    /**
     * Material アイコン
     */
    @Input()
    icon = '';

    /**
     * メニュー名
     */
    @Input()
    label = '';

    /**
     * ルーティング
     */
    @Input()
    route = '';

    /**
     * コンストラクター
     */
    constructor() {}
}
