import { Component } from '@angular/core';
import { ProgressBarService } from 'src/app/services/progress-bar.service';

/**
 * Http Clientの通信中に表示するプログレスバー Component
 */
@Component({
    selector: 'app-progress-bar',
    templateUrl: './progress-bar.component.html',
    styleUrls: ['./progress-bar.component.scss'],
})
export class ProgressBarComponent {
    /**
     * ローディング状態
     */
    state$ = this.progressBarService.state$;

    /**
     * コンストラクター
     *
     * @param progressBarService ProgressBarService
     */
    constructor(private progressBarService: ProgressBarService) {}
}
