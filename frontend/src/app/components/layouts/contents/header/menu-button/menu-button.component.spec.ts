import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatIconModule } from '@angular/material/icon';
import { MenuButtonComponent } from './menu-button.component';

describe('MenuButtonComponent', () => {
    let component: MenuButtonComponent;
    let fixture: ComponentFixture<MenuButtonComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [MenuButtonComponent],
            imports: [MatIconModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(MenuButtonComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
