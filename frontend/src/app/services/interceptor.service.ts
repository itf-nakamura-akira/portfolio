import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { catchError, finalize, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AppService } from '../app.service';
import { ProgressBarService } from './progress-bar.service';

/**
 * HttpClient をインターセプトする Service
 */
@Injectable({
    providedIn: 'root',
})
export class InterceptorService implements HttpInterceptor {
    /**
     * コンストラクター
     *
     * @param toastrService ToastrService
     * @param appService AppService
     * @param progressBarService ProgressBarService
     */
    constructor(private toastrService: ToastrService, private appService: AppService, private progressBarService: ProgressBarService) {}

    /**
     * インターセプトメソッド
     *
     * @param req リクエスト
     * @param next HttpHandler
     *
     * @returns HTTPリクエストイベントストリーム
     */
    intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const cloneReq: HttpRequest<unknown> = req.clone({
            url: `${environment.apiURL}${req.url}`,
            withCredentials: true,
        });

        return next.handle(cloneReq).pipe(
            tap(() => this.progressBarService.start()),
            finalize(() => this.progressBarService.stop()),
            catchError((error: HttpErrorResponse) => {
                if (error.status === HttpStatusCode.InternalServerError) {
                    this.toastrService.error(error.error.message);
                } else if (error.status === HttpStatusCode.Unauthorized) {
                    this.appService.logout();
                }

                throw error;
            }),
        );
    }
}
