import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { keepMappingOrder } from 'src/app/enums/keepMappingOrder';
import { UsersPermission, UsersPermissionMapping } from 'src/app/enums/usersPermission';
import { User } from '../users-master-http.service';
import { UsersMasterService } from '../users-master.service';

/**
 * ユーザー情報更新ダイアログ Component
 */
@Component({
    selector: 'app-update-dialog',
    templateUrl: './update-dialog.component.html',
    styleUrls: ['./update-dialog.component.scss'],
})
export class UpdateDialogComponent implements OnInit {
    /**
     * フォームグループ
     */
    fgUpdateForm = new FormGroup({
        fcAccount: new FormControl('', [Validators.required]),
        fcName: new FormControl('', [Validators.required]),
        fcPermission: new FormControl(UsersPermission.User, []),
        fcIsEnabled: new FormControl(true, []),
    });

    /**
     * ユーザー権限マッピング
     */
    usersPermissionMapping = UsersPermissionMapping;

    /**
     * keyvaluePipeの順序を維持するための関数
     */
    keepMappingOrder = keepMappingOrder;

    /**
     * コンストラクター
     *
     * @param matDialogRef MatDialogRef
     * @param data 編集データ
     * @param usersMasterService UsersMasterService
     */
    constructor(
        private matDialogRef: MatDialogRef<UpdateDialogComponent>,
        @Inject(MAT_DIALOG_DATA) private data: User,
        private usersMasterService: UsersMasterService,
    ) {}

    /**
     * 初期化
     */
    ngOnInit(): void {
        this.fgUpdateForm.get('fcAccount')?.setValue(this.data.account);
        this.fgUpdateForm.get('fcName')?.setValue(this.data.name);
        this.fgUpdateForm.get('fcPermission')?.setValue(this.data.permission);
        this.fgUpdateForm.get('fcIsEnabled')?.setValue(this.data.isEnabled);
    }

    /**
     * 更新ボタンクリックイベントハンドラー
     *
     * @param formGroup 更新フォームグループ
     */
    updateFormSubmit(formGroup: FormGroup): void {
        if (formGroup.invalid) {
            return;
        }

        this.usersMasterService.updateUser(
            this.data.id,
            formGroup.get('fcAccount')?.value,
            formGroup.get('fcName')?.value,
            formGroup.get('fcPermission')?.value,
            formGroup.get('fcIsEnabled')?.value,
            () => this.matDialogRef.close(),
        );
    }
}
