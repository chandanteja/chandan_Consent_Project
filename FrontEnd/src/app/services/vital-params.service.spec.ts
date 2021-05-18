import { TestBed } from '@angular/core/testing';

import { VitalParamsService } from './vital-params.service';

describe('VitalParamsService', () => {
  let service: VitalParamsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VitalParamsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
