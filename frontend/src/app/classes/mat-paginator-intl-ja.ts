import { MatPaginatorIntl } from '@angular/material/paginator';

/**
 * MatPaginator の表示ラベルクラス(日本語)
 */
export class MatPaginatorIntlJa extends MatPaginatorIntl {
    /**
     * 1ページあたりの表示件数
     */
    override itemsPerPageLabel = '1ページの表示件数';

    /**
     * 次のページへのボタン
     */
    override nextPageLabel = '次へ';

    /**
     * 前のページへのボタン
     */
    override previousPageLabel = '戻る';

    /**
     * 最初のページへのボタン
     */
    override firstPageLabel = '最初のページ';

    /**
     * 最後のページへのボタン
     */
    override lastPageLabel = '最後のページ';

    /**
     * 現在の表示ステータスのラベル
     *
     * @param page 表示ページ
     * @param pageSize ページサイズ
     * @param length データ合計数
     *
     * @returns 表示ラベル
     */
    override getRangeLabel: (page: number, pageSize: number, length: number) => string = (
        page: number,
        pageSize: number,
        length: number,
    ) => {
        if (length == 0 || pageSize == 0) {
            return `0 / ${length} 件中`;
        }

        length = Math.max(length, 0);

        const startIndex = page * pageSize;

        // If the start index exceeds the list length, do not try and fix the end index to the end.
        const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;

        return `${startIndex + 1} – ${endIndex} 件 / ${length} 件中`;
    };
}
