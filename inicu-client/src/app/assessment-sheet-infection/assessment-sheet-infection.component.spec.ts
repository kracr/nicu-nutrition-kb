import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetInfectionComponent } from './assessment-sheet-infection.component';

describe('AssessmentSheetInfectionComponent', () => {
  let component: AssessmentSheetInfectionComponent;
  let fixture: ComponentFixture<AssessmentSheetInfectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetInfectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetInfectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
