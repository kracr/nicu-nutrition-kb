import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingPrintComponent } from './nursing-print.component';

describe('NursingPrintComponent', () => {
  let component: NursingPrintComponent;
  let fixture: ComponentFixture<NursingPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingPrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
