import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { InputWorkingHoursHttpService, WorkingHour } from './input-working-hours-http.service';

/**
 * 勤怠入力画面 Service
 */
@Injectable()
export class InputWorkingHoursService {
    /**
     * 労働時間データ
     */
    private _inputWorkingHours$ = new BehaviorSubject<WorkingHour[]>([]);

    /**
     * 労働時間データ
     */
    get inputWorkingHours$(): Observable<WorkingHour[]> {
        return this._inputWorkingHours$.asObservable();
    }

    /**
     * コンストラクター
     *
     * @param inputWorkingHoursHttpService InputWorkingHoursHttpService
     */
    constructor(private inputWorkingHoursHttpService: InputWorkingHoursHttpService) {}

    /**
     * ログインユーザーの労働時間データを取得する
     *
     * @param year 年
     * @param month 月
     */
    getWorkingHours(year: number, month: number): void {
        this.inputWorkingHoursHttpService
            .getWorkingHours(year, month)
            .subscribe((response) => this._inputWorkingHours$.next(response.workingHours));
    }
}
