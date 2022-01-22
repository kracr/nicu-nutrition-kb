import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingProceduresComponent } from './nursing-procedures.component';

describe('NursingProceduresComponent', () => {
  let component: NursingProceduresComponent;
  let fixture: ComponentFixture<NursingProceduresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingProceduresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingProceduresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
