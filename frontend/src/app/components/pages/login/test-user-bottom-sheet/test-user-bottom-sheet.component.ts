import { Component } from '@angular/core';
import { MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { MatSelectionListChange } from '@angular/material/list';

/**
 * テストユーザー選択ボトムシート Component
 */
@Component({
    selector: 'app-test-user-bottom-sheet',
    templateUrl: './test-user-bottom-sheet.component.html',
    styleUrls: ['./test-user-bottom-sheet.component.scss'],
})
export class TestUserBottomSheetComponent {
    /**
     * テストユーザー選択肢
     */
    options: TestUserOption[] = [
        { name: '管理者ユーザー', data: { account: 'admin', password: 'admin' } },
        { name: '一般ユーザー(齋藤 綾香)', data: { account: 'saito', password: 'saito' } },
        { name: '一般ユーザー(白浜 隆二)', data: { account: 'shirahama', password: 'shirahama' } },
        { name: '一般ユーザー(一戸 敏雄: 無効ユーザー)', data: { account: 'ichinohe', password: 'ichinohe' } },
        { name: '参照ユーザー', data: { account: 'referencer', password: 'referencer' } },
    ];

    /**
     * コンストラクター
     *
     * @param matBottomSheetRef MatBottomSheetRef<TestUserBottomSheetComponent>
     */
    constructor(private matBottomSheetRef: MatBottomSheetRef<TestUserBottomSheetComponent, TestUserOption>) {}

    /**
     * リスト選択イベントハンドラー
     *
     * @param event イベントパラメーター
     */
    selectionChange(event: MatSelectionListChange): void {
        this.matBottomSheetRef.dismiss(event.options.pop()?.value);
    }
}

/**
 * テストユーザー選択肢
 */
export interface TestUserOption {
    name: string;
    data: { account: string; password: string };
}
