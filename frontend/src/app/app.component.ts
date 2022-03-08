import { Component, OnInit } from '@angular/core';
import { AppService } from './app.service';

/**
 * Angularアプリケーション
 */
@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
    /**
     * コンストラクター
     *
     * @param appService AppService
     */
    constructor(private appService: AppService) {}

    /**
     * 初期化
     */
    ngOnInit(): void {
        this.appService.updateIsAuthorized();
    }
}
