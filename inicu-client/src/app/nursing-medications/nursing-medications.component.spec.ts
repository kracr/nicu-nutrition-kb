import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingMedicationsComponent } from './nursing-medications.component';

describe('NursingMedicationsComponent', () => {
  let component: NursingMedicationsComponent;
  let fixture: ComponentFixture<NursingMedicationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingMedicationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingMedicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
