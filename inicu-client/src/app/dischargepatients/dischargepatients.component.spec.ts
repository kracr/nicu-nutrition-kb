import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargepatientsComponent } from './dischargepatients.component';

describe('DischargepatientsComponent', () => {
  let component: DischargepatientsComponent;
  let fixture: ComponentFixture<DischargepatientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargepatientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargepatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
