import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemographicFormComponent } from './demographic-form.component';

describe('DemographicFormComponent', () => {
  let component: DemographicFormComponent;
  let fixture: ComponentFixture<DemographicFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemographicFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemographicFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
