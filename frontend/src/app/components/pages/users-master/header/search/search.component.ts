import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { takeUntil } from 'rxjs';
import { OnDestroyEmitter } from 'src/app/components/on-destroy-emitter';
import { UsersPermissionMapping } from 'src/app/enums/usersPermission';
import { SearchParameter, UsersMasterService } from '../../users-master.service';

/**
 * ユーザー設定画面の検索フィルター Component
 */
@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss'],
})
export class SearchComponent extends OnDestroyEmitter implements OnInit {
    /**
     * アカウント 表示名 FormControl
     */
    fcText = new FormControl('');

    /**
     * 権限 FormControl
     */
    fcPermission = new FormControl([]);

    /**
     * 有効ステータス FormControl
     */
    fcIsEnabled = new FormControl(false);

    /**
     * ユーザー権限マッピング
     */
    usersPermissionMapping = UsersPermissionMapping;

    /**
     * コンストラクター
     *
     * @param activatedRoute ActivatedRoute
     * @param usersMasterService UsersMasterService
     */
    constructor(private activatedRoute: ActivatedRoute, private usersMasterService: UsersMasterService) {
        super();
    }

    /**
     * 初期化
     */
    ngOnInit(): void {
        // 検索フォームの更新処理
        this.fcText.valueChanges.pipe(takeUntil(this.onDestroy$)).subscribe((value) => {
            this.usersMasterService.updateFilteringParameter('text', value);
        });
        this.fcPermission.valueChanges.pipe(takeUntil(this.onDestroy$)).subscribe((value) => {
            this.usersMasterService.updateFilteringParameter('permission', value);
        });
        this.fcIsEnabled.valueChanges.pipe(takeUntil(this.onDestroy$)).subscribe((value) => {
            this.usersMasterService.updateFilteringParameter('isEnabled', value);
        });

        // URLパラメーターから初期値を取得する
        const queryParams = this.activatedRoute.snapshot.queryParams as SearchParameter;

        this.fcText.setValue(queryParams.text);

        if (Array.isArray(queryParams.permission)) {
            this.fcPermission.setValue(queryParams.permission.map((p) => +p));
        } else {
            this.fcPermission.setValue(!!queryParams.permission ? [+queryParams.permission] : []);
        }

        this.fcIsEnabled.setValue((queryParams.isEnabled as unknown) === 'true');
    }
}
