import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SinsheetPrintComponent } from './sinsheet-print.component';

describe('SinsheetPrintComponent', () => {
  let component: SinsheetPrintComponent;
  let fixture: ComponentFixture<SinsheetPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SinsheetPrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SinsheetPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
