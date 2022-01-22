import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LabReportsComponent } from './lab-reports.component';

describe('LabReportsComponent', () => {
  let component: LabReportsComponent;
  let fixture: ComponentFixture<LabReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LabReportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
