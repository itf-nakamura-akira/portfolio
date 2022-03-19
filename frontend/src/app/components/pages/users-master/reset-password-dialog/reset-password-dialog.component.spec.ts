import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { ToastrModule } from 'ngx-toastr';
import { UsersPermission } from 'src/app/enums/usersPermission';
import { UsersMasterService } from '../users-master.service';
import { ResetPasswordDialogComponent } from './reset-password-dialog.component';

describe('ResetPasswordDialogComponent', () => {
    let component: ResetPasswordDialogComponent;
    let fixture: ComponentFixture<ResetPasswordDialogComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ResetPasswordDialogComponent],
            imports: [
                NoopAnimationsModule,
                RouterTestingModule,
                HttpClientModule,
                ReactiveFormsModule,
                MatDialogModule,
                MatFormFieldModule,
                MatInputModule,
                MatIconModule,
                ToastrModule.forRoot({
                    progressBar: true,
                }),
            ],
            providers: [
                { provide: MatDialogRef, useValue: {} },
                {
                    provide: MAT_DIALOG_DATA,
                    useValue: {
                        id: 1,
                        account: 'admin',
                        name: '管理者ユーザー',
                        permission: UsersPermission.Admin,
                        isEnabled: true,
                    },
                },
                UsersMasterService,
            ],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ResetPasswordDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
