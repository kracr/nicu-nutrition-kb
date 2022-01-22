import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentSheetStableComponent } from './assessment-sheet-stable.component';

describe('AssessmentSheetStableComponent', () => {
  let component: AssessmentSheetStableComponent;
  let fixture: ComponentFixture<AssessmentSheetStableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentSheetStableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentSheetStableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
