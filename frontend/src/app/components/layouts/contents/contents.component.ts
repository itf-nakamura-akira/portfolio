import { Component } from '@angular/core';
import { AppService } from 'src/app/app.service';

/**
 * コンテンツを配置するレイアウトComponent
 */
@Component({
    selector: 'app-contents',
    templateUrl: './contents.component.html',
    styleUrls: ['./contents.component.scss'],
})
export class ContentsComponent {
    /**
     * コンストラクター
     *
     * @param appService AppService
     */
    constructor(public appService: AppService) {}
}
