import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { UsersMasterHttpService } from './users-master-http.service';

describe('UsersMasterHttpService', () => {
    let service: UsersMasterHttpService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientModule],
        });
        service = TestBed.inject(UsersMasterHttpService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
