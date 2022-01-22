import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetJaundiceComponent } from './assessment-sheet-jaundice.component';

describe('AssessmentSheetJaundiceComponent', () => {
  let component: AssessmentSheetJaundiceComponent;
  let fixture: ComponentFixture<AssessmentSheetJaundiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetJaundiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetJaundiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
