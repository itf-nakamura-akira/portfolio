import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

/**
 * 共通メッセージダイアログ Component
 */
@Component({
    selector: 'app-message-dialog',
    templateUrl: './message-dialog.component.html',
    styleUrls: ['./message-dialog.component.scss'],
})
export class MessageDialogComponent {
    /**
     * タイトル
     */
    title: string = this.data.title;

    /**
     * メッセージ
     */
    message: string = this.data.message;

    /**
     * アクションボタン
     */
    actions: DialogButtons[] = this.data.buttons;

    /**
     * 初期フォーカス
     */
    initialFocus = this.data.initialFocus;

    /**
     * ダイアログボタン
     */
    readonly DialogButtons = DialogButtons;

    /**
     * コンストラクター
     */
    constructor(
        private matDialogRef: MatDialogRef<MessageDialogComponent, DialogButtons>,
        @Inject(MAT_DIALOG_DATA) private data: DialogConfig,
    ) {}
}

/**
 * ボタンの種類
 */
export enum DialogButtons {
    Cancel = 'キャンセル',
    OK = 'OK',
}

/**
 * ダイアログコンフィグ
 */
export interface DialogConfig {
    title: string;
    message: string;
    buttons: DialogButtons[];
    initialFocus: DialogButtons;
}
