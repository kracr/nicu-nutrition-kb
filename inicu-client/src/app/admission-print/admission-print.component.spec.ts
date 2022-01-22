import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmissionPrintComponent } from './admission-print.component';

describe('AdmissionPrintComponent', () => {
  let component: AdmissionPrintComponent;
  let fixture: ComponentFixture<AdmissionPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdmissionPrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdmissionPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
