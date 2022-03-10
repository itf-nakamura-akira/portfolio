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
     * 認証状況
     */
    isAuthorized$ = this.appService.isAuthorized$;

    /**
     * コンストラクター
     *
     * @param appService AppService
     */
    constructor(private appService: AppService) {}
}
