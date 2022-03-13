import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UsersPermissionMapping } from 'src/app/enums/usersPermission';
import { User } from '../users-master-http.service';

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
     * MatSort
     */
    @ViewChild(MatSort)
    readonly sort!: MatSort;

    /**
     * 表示カラム
     */
    readonly displayedColumns: string[] = ['account', 'name', 'permission', 'isEnabled'];

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
     */
    constructor() {}

    /**
     * 初期化
     */
    ngAfterViewInit(): void {
        this.dataSource.sort = this.sort;
    }
}
