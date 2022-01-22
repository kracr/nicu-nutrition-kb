import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScreeningPrintComponent } from './screening-print.component';

describe('ScreeningPrintComponent', () => {
  let component: ScreeningPrintComponent;
  let fixture: ComponentFixture<ScreeningPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScreeningPrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScreeningPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
