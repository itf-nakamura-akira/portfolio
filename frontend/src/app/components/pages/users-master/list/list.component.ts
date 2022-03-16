import { AfterViewInit, Component, Input, ViewChild, ViewContainerRef } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { filter } from 'rxjs';
import { DialogButtons } from 'src/app/components/shared/message-dialog/message-dialog.component';
import { UsersPermissionMapping } from 'src/app/enums/usersPermission';
import { MessageDialogService } from 'src/app/services/message-dialog.service';
import { UpdateDialogComponent } from '../update-dialog/update-dialog.component';
import { User } from '../users-master-http.service';

/**
 * ユーザーデータの一覧を表示する Component
 */
@Component({
    selector: 'app-list',
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.scss'],
})
export class ListComponent implements AfterViewInit {
    /**
     * テーブルのデータ元
     */
    @Input()
    set data(values: User[]) {
        this.dataSource.data = values;
    }

    /**
     * MatPaginator
     */
    @Input()
    set paginator(value: MatPaginator) {
        this.dataSource.paginator = value;
    }

    /**
     * MatSort
     */
    @ViewChild(MatSort)
    readonly sort!: MatSort;

    /**
     * 表示カラム
     */
    readonly displayedColumns: string[] = ['account', 'name', 'permission', 'isEnabled', 'delete'];

    /**
     * MatTable用のデータソース
     */
    readonly dataSource = new MatTableDataSource<User>([]);

    /**
     * ユーザー権限の文字列マッピング
     */
    readonly UsersPermissionMapping = UsersPermissionMapping;

    /**
     * コンストラクター
     *
     * @param viewContainerRef ViewContainerRef
     * @param matDialog MatDialog
     * @param messageDialogService MessageDialogService
     */
    constructor(
        private viewContainerRef: ViewContainerRef,
        private matDialog: MatDialog,
        private messageDialogService: MessageDialogService,
    ) {}

    /**
     * 初期化
     */
    ngAfterViewInit(): void {
        this.dataSource.sort = this.sort;
    }

    /**
     * 行クリックイベントハンドラー
     *
     * @param row クリックされた行
     */
    rowClick(row: User): void {
        const dialogConfig: MatDialogConfig<User> = {
            data: row,
            width: '400px',
            viewContainerRef: this.viewContainerRef,
        };
        this.matDialog.open(UpdateDialogComponent, dialogConfig);
    }

    /**
     * 行削除ボタンイベントハンドラー
     *
     * @param row クリックされた行
     * @param e クリックイベントパラメーター
     */
    deleteButtonClick(row: User, e: Event): void {
        // 行クリックイベントへの伝搬キャンセル
        e.stopPropagation();

        this.messageDialogService
            .cancelAndOk({
                title: '削除確認',
                message: `${row.name}(${row.account})さんのアカウントを削除してもよろしいですか？削除したデータは元に戻せません。`,
            })
            .pipe(filter((result) => result === DialogButtons.OK))
            .subscribe(() => {
                alert('ok');
            });
    }
}
