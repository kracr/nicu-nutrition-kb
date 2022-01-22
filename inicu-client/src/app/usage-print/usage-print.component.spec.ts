import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsagePrintComponent } from './usage-print.component';

describe('UsagePrintComponent', () => {
  let component: UsagePrintComponent;
  let fixture: ComponentFixture<UsagePrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsagePrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsagePrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
