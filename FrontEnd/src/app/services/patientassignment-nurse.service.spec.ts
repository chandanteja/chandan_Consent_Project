import { TestBed } from '@angular/core/testing';

import { PatientassignmentNurseService } from './patientassignment-nurse.service';

describe('PatientassignmentNurseService', () => {
  let service: PatientassignmentNurseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PatientassignmentNurseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
