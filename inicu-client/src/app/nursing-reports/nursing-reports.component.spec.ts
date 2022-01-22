import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingReportsComponent } from './nursing-reports.component';

describe('NursingReportsComponent', () => {
  let component: NursingReportsComponent;
  let fixture: ComponentFixture<NursingReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingReportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
