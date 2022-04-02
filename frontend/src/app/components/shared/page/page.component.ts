import { Component, Input } from '@angular/core';

/**
 * mainコンテンツレイアウト用Component
 */
@Component({
    selector: 'app-page',
    templateUrl: './page.component.html',
    styleUrls: ['./page.component.scss'],
})
export class PageComponent {
    /**
     * メインコンテンツの高さを拡張するか
     */
    @Input()
    mainHeightExtension = false;

    /**
     * コンストラクター
     */
    constructor() {}
}
