import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { RegistButtonComponent } from './regist-button.component';

describe('RegistButtonComponent', () => {
    let component: RegistButtonComponent;
    let fixture: ComponentFixture<RegistButtonComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [RegistButtonComponent],
            imports: [MatDialogModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(RegistButtonComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
