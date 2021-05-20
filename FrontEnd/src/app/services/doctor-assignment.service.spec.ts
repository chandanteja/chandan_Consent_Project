import { TestBed } from '@angular/core/testing';

import { DoctorAssignmentService } from './doctor-assignment.service';

describe('DoctorAssignmentService', () => {
  let service: DoctorAssignmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoctorAssignmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
