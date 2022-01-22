import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetMiscellaneousComponent } from './assessment-sheet-miscellaneous.component';

describe('AssessmentSheetMiscellaneousComponent', () => {
  let component: AssessmentSheetMiscellaneousComponent;
  let fixture: ComponentFixture<AssessmentSheetMiscellaneousComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetMiscellaneousComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetMiscellaneousComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
