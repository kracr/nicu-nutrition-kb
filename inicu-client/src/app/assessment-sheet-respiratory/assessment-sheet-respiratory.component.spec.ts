import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetRespiratoryComponent } from './assessment-sheet-respiratory.component';

describe('AssessmentSheetRespiratoryComponent', () => {
  let component: AssessmentSheetRespiratoryComponent;
  let fixture: ComponentFixture<AssessmentSheetRespiratoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetRespiratoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetRespiratoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
