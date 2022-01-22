import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingNotesComponent } from './nursing-notes.component';

describe('NursingNotesComponent', () => {
  let component: NursingNotesComponent;
  let fixture: ComponentFixture<NursingNotesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingNotesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
