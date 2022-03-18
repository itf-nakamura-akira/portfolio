import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UsersPermission, UsersPermissionMapping } from 'src/app/enums/usersPermission';
import { UsersMasterService } from '../users-master.service';

/**
 * ユーザー情報新規登録ダイアログ Component
 */
@Component({
    selector: 'app-regist-dialog',
    templateUrl: './regist-dialog.component.html',
    styleUrls: ['./regist-dialog.component.scss'],
})
export class RegistDialogComponent {
    /**
     * フォームグループ
     */
    fgRegistForm = new FormGroup({
        fcAccount: new FormControl('', [Validators.required]),
        fcName: new FormControl('', [Validators.required]),
        fcPassword: new FormControl('', [Validators.required]),
        fcPermission: new FormControl(UsersPermission.User, []),
        fcIsEnabled: new FormControl(true, []),
    });

    /**
     * ユーザー権限マッピング
     */
    usersPermissionMapping = UsersPermissionMapping;

    /**
     * パスワードの非表示ステータス
     */
    passwordHide = true;

    /**
     * コンストラクター
     *
     * @param matDialogRef MatDialogRef
     * @param usersMasterService UsersMasterService
     */
    constructor(private matDialogRef: MatDialogRef<RegistDialogComponent>, private usersMasterService: UsersMasterService) {}

    /**
     * 登録ボタンクリックイベントハンドラー
     *
     * @param formGroup 更新フォームグループ
     */
    registFormSubmit(formGroup: FormGroup): void {
        if (formGroup.invalid) {
            return;
        }

        this.usersMasterService.registUser(
            formGroup.get('fcAccount')?.value,
            formGroup.get('fcName')?.value,
            formGroup.get('fcPassword')?.value,
            formGroup.get('fcPermission')?.value,
            formGroup.get('fcIsEnabled')?.value,
            () => this.matDialogRef.close(),
        );
    }
}
