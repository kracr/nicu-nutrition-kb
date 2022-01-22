import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryNotesComponent } from './summary-notes.component';

describe('SummaryNotesComponent', () => {
  let component: SummaryNotesComponent;
  let fixture: ComponentFixture<SummaryNotesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummaryNotesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummaryNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
