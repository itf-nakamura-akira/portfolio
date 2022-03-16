import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { ToastrModule } from 'ngx-toastr';
import { UsersPermission } from 'src/app/enums/usersPermission';
import { UsersMasterService } from '../users-master.service';
import { UpdateDialogComponent } from './update-dialog.component';

describe('UpdateDialogComponent', () => {
    let component: UpdateDialogComponent;
    let fixture: ComponentFixture<UpdateDialogComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UpdateDialogComponent],
            imports: [
                BrowserAnimationsModule,
                ReactiveFormsModule,
                MatDialogModule,
                MatButtonModule,
                MatInputModule,
                MatSelectModule,
                MatSlideToggleModule,
                MatFormFieldModule,
                RouterTestingModule,
                HttpClientModule,
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
        fixture = TestBed.createComponent(UpdateDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
