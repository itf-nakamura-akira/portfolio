import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatBottomSheetModule, MatBottomSheetRef, MAT_BOTTOM_SHEET_DATA } from '@angular/material/bottom-sheet';
import { MatListModule } from '@angular/material/list';
import { TestUserBottomSheetComponent } from './test-user-bottom-sheet.component';

describe('TestUserBottomSheetComponent', () => {
    let component: TestUserBottomSheetComponent;
    let fixture: ComponentFixture<TestUserBottomSheetComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [TestUserBottomSheetComponent],
            providers: [
                { provide: MatBottomSheetRef, useValue: {} },
                { provide: MAT_BOTTOM_SHEET_DATA, useValue: {} },
            ],
            imports: [MatBottomSheetModule, MatListModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(TestUserBottomSheetComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
