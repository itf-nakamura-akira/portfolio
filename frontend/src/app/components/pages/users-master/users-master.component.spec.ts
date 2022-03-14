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
import { ListComponent } from './list/list.component';
import { SearchComponent } from './search/search.component';
import { UsersMasterComponent } from './users-master.component';

describe('UsersMasterComponent', () => {
    let component: UsersMasterComponent;
    let fixture: ComponentFixture<UsersMasterComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UsersMasterComponent, SearchComponent, ListComponent],
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
