import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetCardiacComponent } from './assessment-sheet-cardiac.component';

describe('AssessmentSheetCardiacComponent', () => {
  let component: AssessmentSheetCardiacComponent;
  let fixture: ComponentFixture<AssessmentSheetCardiacComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetCardiacComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetCardiacComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
