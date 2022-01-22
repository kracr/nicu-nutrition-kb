import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingAnthropometryComponent } from './nursing-anthropometry.component';

describe('NursingAnthropometryComponent', () => {
  let component: NursingAnthropometryComponent;
  let fixture: ComponentFixture<NursingAnthropometryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingAnthropometryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingAnthropometryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
