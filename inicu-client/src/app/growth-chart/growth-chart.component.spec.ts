import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrowthChartComponent } from './growth-chart.component';

describe('GrowthChartComponent', () => {
  let component: GrowthChartComponent;
  let fixture: ComponentFixture<GrowthChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrowthChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrowthChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
