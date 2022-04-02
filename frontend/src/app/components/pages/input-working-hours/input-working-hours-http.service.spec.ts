import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { InputWorkingHoursHttpService } from './input-working-hours-http.service';

describe('InputWorkingHoursHttpService', () => {
    let service: InputWorkingHoursHttpService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientModule],
        });
        service = TestBed.inject(InputWorkingHoursHttpService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
