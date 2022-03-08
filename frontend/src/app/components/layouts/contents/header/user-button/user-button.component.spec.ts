import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatMenuModule } from '@angular/material/menu';
import { UserButtonComponent } from './user-button.component';

describe('UserButtonComponent', () => {
    let component: UserButtonComponent;
    let fixture: ComponentFixture<UserButtonComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UserButtonComponent],
            imports: [HttpClientModule, MatMenuModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(UserButtonComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
