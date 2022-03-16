import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTableModule } from '@angular/material/table';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { ToastrModule } from 'ngx-toastr';
import { HeaderComponent } from './header/header.component';
import { RegistButtonComponent } from './header/regist-button/regist-button.component';
import { SearchComponent } from './header/search/search.component';
import { ListComponent } from './list/list.component';
import { UsersMasterComponent } from './users-master.component';

describe('UsersMasterComponent', () => {
    let component: UsersMasterComponent;
    let fixture: ComponentFixture<UsersMasterComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UsersMasterComponent, SearchComponent, ListComponent, HeaderComponent, RegistButtonComponent],
            imports: [
                NoopAnimationsModule,
                RouterTestingModule,
                HttpClientModule,
                ReactiveFormsModule,
                MatInputModule,
                MatSelectModule,
                MatSlideToggleModule,
                MatDialogModule,
                MatPaginatorModule,
                MatTableModule,
                ToastrModule.forRoot({
                    progressBar: true,
                }),
            ],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(UsersMasterComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
