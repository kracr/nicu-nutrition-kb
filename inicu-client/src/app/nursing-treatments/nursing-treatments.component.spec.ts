import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingTreatmentsComponent } from './nursing-treatments.component';

describe('NursingTreatmentsComponent', () => {
  let component: NursingTreatmentsComponent;
  let fixture: ComponentFixture<NursingTreatmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingTreatmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingTreatmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
