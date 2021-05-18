import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckpatientNurseComponent } from './checkpatient-nurse.component';

describe('CheckpatientNurseComponent', () => {
  let component: CheckpatientNurseComponent;
  let fixture: ComponentFixture<CheckpatientNurseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CheckpatientNurseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckpatientNurseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
