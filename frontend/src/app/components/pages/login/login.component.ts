import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { filter, tap } from 'rxjs';
import { AppService } from 'src/app/app.service';
import { LoginService } from './login.service';
import { TestUserBottomSheetComponent, TestUserOption } from './test-user-bottom-sheet/test-user-bottom-sheet.component';

/**
 * ログイン画面 Component
 */
@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    providers: [LoginService],
})
export class LoginComponent implements OnInit {
    /**
     * パスワード InputElementRef
     */
    @ViewChild('password')
    passwordInputElement!: ElementRef;

    /**
     * フォームループ
     */
    fgLoginForm = new FormGroup({
        fcAccount: new FormControl('', [Validators.required]),
        fcPassword: new FormControl('', [Validators.required]),
    });

    /**
     * エラーメッセージ
     */
    errorMessage$ = this.loginService.errorMessage$.pipe(
        tap(() => {
            const input = this.passwordInputElement.nativeElement as HTMLInputElement;
            input.focus();
            input.select();
        }),
    );

    /**
     * パスワードの非表示ステータス
     */
    passwordHide = true;

    /**
     * コンストラクター
     *
     * @param matBottomSheet MatBottomSheet
     * @param appService AppService
     * @param loginService LoginService
     */
    constructor(private matBottomSheet: MatBottomSheet, private appService: AppService, private loginService: LoginService) {}

    /**
     * 初期化
     */
    ngOnInit(): void {
        this.appService.setIsAuthorized(false);
    }

    /**
     * Submit イベントハンドラー
     *
     * @param formGroup フォームグループ
     */
    loginFormSubmit(formGroup: FormGroup): void {
        this.loginService.login(formGroup.get('fcAccount')?.value, formGroup.get('fcPassword')?.value);
    }

    /**
     * テストユーザーボタンクリックイベントハンドラー
     *
     * @param formGroup フォームグループ
     */
    testUserButtonClick(formGroup: FormGroup): void {
        this.matBottomSheet
            .open(TestUserBottomSheetComponent)
            .afterDismissed()
            .pipe(filter((selection) => !!selection))
            .subscribe((selection: TestUserOption) => {
                formGroup.get('fcAccount')?.setValue(selection.data.account);
                formGroup.get('fcPassword')?.setValue(selection.data.password);
                this.loginFormSubmit(formGroup);
            });
    }
}
