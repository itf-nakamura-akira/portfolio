import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InputWorkingHoursComponent } from './input-working-hours.component';

describe('InputWorkingHoursComponent', () => {
    let component: InputWorkingHoursComponent;
    let fixture: ComponentFixture<InputWorkingHoursComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [InputWorkingHoursComponent],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(InputWorkingHoursComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
