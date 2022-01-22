import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryNotesPrintComponent } from './summary-notes-print.component';

describe('SummaryNotesPrintComponent', () => {
  let component: SummaryNotesPrintComponent;
  let fixture: ComponentFixture<SummaryNotesPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummaryNotesPrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummaryNotesPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
