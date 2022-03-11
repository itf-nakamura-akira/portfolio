import { TestBed } from '@angular/core/testing';
import { UsersMasterHttpService } from './users-master-http.service';

describe('UsersMasterHttpService', () => {
    let service: UsersMasterHttpService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(UsersMasterHttpService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
