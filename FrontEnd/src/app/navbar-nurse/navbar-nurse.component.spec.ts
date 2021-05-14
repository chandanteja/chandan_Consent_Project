import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarNurseComponent } from './navbar-nurse.component';

describe('NavbarNurseComponent', () => {
  let component: NavbarNurseComponent;
  let fixture: ComponentFixture<NavbarNurseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavbarNurseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarNurseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
