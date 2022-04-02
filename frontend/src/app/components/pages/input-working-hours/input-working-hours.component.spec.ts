import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActionComponent } from './header/action/action.component';
import { HeaderComponent } from './header/header.component';
import { SearchComponent } from './header/search/search.component';
import { InputWorkingHoursComponent } from './input-working-hours.component';
import { ListComponent } from './list/list.component';

describe('InputWorkingHoursComponent', () => {
    let component: InputWorkingHoursComponent;
    let fixture: ComponentFixture<InputWorkingHoursComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [InputWorkingHoursComponent, HeaderComponent, ListComponent, SearchComponent, ActionComponent],
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
