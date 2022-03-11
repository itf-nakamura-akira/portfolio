import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { UsersMasterService } from './users-master.service';

describe('UsersMasterService', () => {
    let service: UsersMasterService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [RouterTestingModule],
            providers: [UsersMasterService],
        });
        service = TestBed.inject(UsersMasterService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
