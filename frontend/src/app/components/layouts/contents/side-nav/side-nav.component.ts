import { Component, Input } from '@angular/core';
import { SideNavService } from 'src/app/services/side-nav.service';

/**
 * サイドメニューを表示するレイアウトComponent
 */
@Component({
    selector: 'app-side-nav',
    templateUrl: './side-nav.component.html',
    styleUrls: ['./side-nav.component.scss'],
})
export class SideNavComponent {
    /**
     * 認証済み情報
     */
    @Input()
    isAuthorized!: boolean;

    /**
     * コンストラクター
     *
     * @param sideNavService SideNavService
     */
    constructor(public sideNavService: SideNavService) {}
}
