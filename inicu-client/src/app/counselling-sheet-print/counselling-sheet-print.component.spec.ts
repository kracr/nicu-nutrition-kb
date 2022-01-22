import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CounsellingSheetPrintComponent } from './counselling-sheet-print.component';

describe('CounsellingSheetPrintComponent', () => {
  let component: CounsellingSheetPrintComponent;
  let fixture: ComponentFixture<CounsellingSheetPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CounsellingSheetPrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CounsellingSheetPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
