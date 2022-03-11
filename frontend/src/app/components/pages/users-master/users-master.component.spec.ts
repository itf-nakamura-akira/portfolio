import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
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
                RouterTestingModule,
                BrowserAnimationsModule,
                ReactiveFormsModule,
                MatInputModule,
                MatSelectModule,
                MatSlideToggleModule,
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
