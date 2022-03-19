import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { ToastrModule } from 'ngx-toastr';
import { UsersMasterService } from '../users-master.service';
import { RegistDialogComponent } from './regist-dialog.component';

describe('RegistDialogComponent', () => {
    let component: RegistDialogComponent;
    let fixture: ComponentFixture<RegistDialogComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [RegistDialogComponent],
            imports: [
                ReactiveFormsModule,
                NoopAnimationsModule,
                MatDialogModule,
                RouterTestingModule,
                HttpClientModule,
                ToastrModule.forRoot({
                    progressBar: true,
                }),
                MatInputModule,
                MatSlideToggleModule,
                MatSelectModule,
                MatIconModule,
            ],
            providers: [{ provide: MatDialogRef, useValue: {} }, UsersMasterService],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(RegistDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
