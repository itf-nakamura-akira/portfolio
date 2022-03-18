import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { User } from '../users-master-http.service';
import { UsersMasterService } from '../users-master.service';

/**
 * パスワードの再設定ダイアログ Component
 */
@Component({
    selector: 'app-reset-password-dialog',
    templateUrl: './reset-password-dialog.component.html',
    styleUrls: ['./reset-password-dialog.component.scss'],
})
export class ResetPasswordDialogComponent {
    /**
     * フォームグループ
     */
    readonly fgResetForm = new FormGroup({
        fcPassword: new FormControl('', [Validators.required]),
    });

    /**
     * パスワードの非表示ステータス
     */
    passwordHide = true;

    /**
     * アカウント
     */
    readonly account = this.data.account;

    /**
     * 表示名
     */
    readonly name = this.data.name;

    /**
     * コンストラクター
     *
     * @param matDialogRef MatDialogRef
     * @param data 編集データ
     * @param usersMasterService UsersMasterService
     */
    constructor(
        private matDialogRef: MatDialogRef<ResetPasswordDialogComponent>,
        @Inject(MAT_DIALOG_DATA) private data: User,
        private usersMasterService: UsersMasterService,
    ) {}

    /**
     * 再設定ボタンクリックイベントハンドラー
     *
     * @param formGroup 更新フォームグループ
     */
    registFormSubmit(formGroup: FormGroup): void {
        if (formGroup.invalid) {
            return;
        }

        // this.usersMasterService.registUser(
        //     formGroup.get('fcPassword')?.value,
        //     () => this.matDialogRef.close(),
        // );
    }
}
