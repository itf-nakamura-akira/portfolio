import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { InputWorkingHoursService } from './input-working-hours.service';

describe('InputWorkingHoursService', () => {
    let service: InputWorkingHoursService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientModule],
            providers: [InputWorkingHoursService],
        });
        service = TestBed.inject(InputWorkingHoursService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
