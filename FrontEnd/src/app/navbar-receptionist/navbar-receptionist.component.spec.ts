import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarReceptionistComponent } from './navbar-receptionist.component';

describe('NavbarReceptionistComponent', () => {
  let component: NavbarReceptionistComponent;
  let fixture: ComponentFixture<NavbarReceptionistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavbarReceptionistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarReceptionistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
