import { Component, OnInit } from '@angular/core';
import { OnDestroyEmitter } from '../../on-destroy-emitter';
import { UsersMasterService } from './users-master.service';

/**
 * ユーザー設定ページ
 */
@Component({
    selector: 'app-users-master',
    templateUrl: './users-master.component.html',
    styleUrls: ['./users-master.component.scss'],
    providers: [UsersMasterService],
})
export class UsersMasterComponent extends OnDestroyEmitter implements OnInit {
    /**
     * ユーザーデータ
     */
    usersData$ = this.usersMasterService.usersData$;

    /**
     * コンストラクター
     *
     * @param usersMasterService UsersMasterService
     */
    constructor(private usersMasterService: UsersMasterService) {
        super();
    }

    /**
     * 初期化
     */
    ngOnInit(): void {
        this.usersMasterService.fetchData();
    }
}
