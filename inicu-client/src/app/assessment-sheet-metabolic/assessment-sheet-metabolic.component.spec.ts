import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetMetabolicComponent } from './assessment-sheet-metabolic.component';

describe('AssessmentSheetMetabolicComponent', () => {
  let component: AssessmentSheetMetabolicComponent;
  let fixture: ComponentFixture<AssessmentSheetMetabolicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetMetabolicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetMetabolicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
