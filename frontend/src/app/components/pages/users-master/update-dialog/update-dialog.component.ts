import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UsersPermission, UsersPermissionMapping } from 'src/app/enums/usersPermission';
import { User } from '../users-master-http.service';

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
     * フォームループ
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
     * コンストラクター
     *
     * @param matDialogRef MatDialogRef
     * @param data 編集データ
     */
    constructor(private matDialogRef: MatDialogRef<UpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) private data: User) {}

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
        this.matDialogRef.close();
    }
}
