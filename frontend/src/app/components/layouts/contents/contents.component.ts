import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { tap } from 'rxjs';
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
     * 認証済み情報
     */
    isAuthorized$ = this.appService.isAuthorized$.pipe(
        tap((isAuthorized) => {
            if (!isAuthorized) {
                this.router.navigateByUrl('login');
            }
        }),
    );

    /**
     * コンストラクター
     *
     * @param appService AppService
     */
    constructor(private router: Router, private appService: AppService) {}
}
