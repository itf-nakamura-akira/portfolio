import { Component, ViewContainerRef } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { RegistDialogComponent } from '../../regist-dialog/regist-dialog.component';

/**
 * ユーザー新規登録ボタン Component
 */
@Component({
    selector: 'app-regist-button',
    templateUrl: './regist-button.component.html',
    styleUrls: ['./regist-button.component.scss'],
})
export class RegistButtonComponent {
    /**
     * コンストラクター
     *
     * @param viewContainerRef ViewContainerRef
     * @param matDialog MatDialog
     */
    constructor(private viewContainerRef: ViewContainerRef, private matDialog: MatDialog) {}

    /**
     * 新規登録ボタンクリックイベントハンドラー
     */
    buttonClick(): void {
        const dialogConfig: MatDialogConfig = {
            width: '400px',
            viewContainerRef: this.viewContainerRef,
        };
        this.matDialog.open(RegistDialogComponent, dialogConfig);
    }
}
