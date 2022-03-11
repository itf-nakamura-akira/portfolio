import { Component, Input } from '@angular/core';
import { SideNavService } from 'src/app/services/side-nav.service';

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
     * 開閉状況
     */
    isOpen$ = this.sideNavService.isOpen$;

    /**
     * コンストラクター
     *
     * @param sideNavService SideNavService
     */
    constructor(private sideNavService: SideNavService) {}
}
