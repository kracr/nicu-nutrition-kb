import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingChartsComponent } from './nursing-charts.component';

describe('NursingChartsComponent', () => {
  let component: NursingChartsComponent;
  let fixture: ComponentFixture<NursingChartsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingChartsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingChartsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
