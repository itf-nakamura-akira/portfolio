import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatMenuModule } from '@angular/material/menu';
import { RouterTestingModule } from '@angular/router/testing';
import { UserButtonComponent } from './user-button.component';

describe('UserButtonComponent', () => {
    let component: UserButtonComponent;
    let fixture: ComponentFixture<UserButtonComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UserButtonComponent],
            imports: [RouterTestingModule, HttpClientModule, MatMenuModule],
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
