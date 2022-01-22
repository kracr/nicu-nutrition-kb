import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressNotesComponent } from './progress-notes.component';

describe('ProgressNotesComponent', () => {
  let component: ProgressNotesComponent;
  let fixture: ComponentFixture<ProgressNotesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProgressNotesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgressNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
