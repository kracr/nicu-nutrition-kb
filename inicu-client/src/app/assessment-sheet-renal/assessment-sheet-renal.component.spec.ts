import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetRenalComponent } from './assessment-sheet-renal.component';

describe('AssessmentSheetRenalComponent', () => {
  let component: AssessmentSheetRenalComponent;
  let fixture: ComponentFixture<AssessmentSheetRenalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetRenalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetRenalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
