import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { DialogButtons, DialogConfig, MessageDialogComponent } from '../components/shared/message-dialog/message-dialog.component';

/**
 * メッセージダイアログ Component
 */
@Injectable({
    providedIn: 'root',
})
export class MessageDialogService {
    /**
     * コンストラクター
     *
     * @param matDialog MatDialog
     */
    constructor(private matDialog: MatDialog) {}

    /**
     * キャンセル OK ボタン メッセージダイアログを表示する
     *
     * @param config ダイアログ設定
     */
    cancelAndOk(config: DefaultArgs): Observable<DialogResult> {
        return this.openDialog(
            config.title,
            config.message,
            [DialogButtons.Cancel, DialogButtons.OK],
            config.initialFocus || DialogButtons.Cancel,
        );
    }

    /**
     * メッセージダイアログを表示する
     *
     * @param title タイトル
     * @param message メッセージ
     * @param buttons 表示するボタン
     * @param initialFocus 初期フォーカス
     *
     * @returns クリックされたボタン
     */
    private openDialog(title: string, message: string, buttons: DialogButtons[], initialFocus: DialogButtons): Observable<DialogResult> {
        const dialogConfig: MatDialogConfig<DialogConfig> = {
            width: '600px',
            data: {
                title,
                message,
                buttons,
                initialFocus,
            },
        };

        return this.matDialog.open<MessageDialogComponent, DialogConfig, DialogButtons>(MessageDialogComponent, dialogConfig).afterClosed();
    }
}

/**
 * ダイアログの結果
 */
export type DialogResult = undefined | DialogButtons;

interface DefaultArgs {
    title: string;
    message: string;
    initialFocus?: DialogButtons;
}
