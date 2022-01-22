import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetCnsComponent } from './assessment-sheet-cns.component';

describe('AssessmentSheetCnsComponent', () => {
  let component: AssessmentSheetCnsComponent;
  let fixture: ComponentFixture<AssessmentSheetCnsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetCnsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetCnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
