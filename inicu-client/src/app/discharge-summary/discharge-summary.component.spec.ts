import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargeSummaryComponent } from './discharge-summary.component';

describe('DischargeSummaryComponent', () => {
  let component: DischargeSummaryComponent;
  let fixture: ComponentFixture<DischargeSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargeSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargeSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
