import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

/**
 * 勤怠入力画面 HTTP Service
 */
@Injectable({
    providedIn: 'root',
})
export class InputWorkingHoursHttpService {
    /**
     * コンストラクター
     *
     * @param httpClient HttpClient
     */
    constructor(private httpClient: HttpClient) {}

    /**
     * ログインユーザーの労働時間データを取得する
     *
     * @param year 年
     * @param month 月
     *
     * @returns 労働時間
     */
    getWorkingHours(year: number, month: number): Observable<GetWorkingHoursResponse> {
        return of({
            workingHours: [],
        });
    }
}

/**
 * getWorkingHours メソッドのレスポンス
 */
interface GetWorkingHoursResponse {
    /**
     * 労働時間データ
     */
    workingHours: WorkingHour[];
}

/**
 * 労働時間データ
 */
export interface WorkingHour {}
