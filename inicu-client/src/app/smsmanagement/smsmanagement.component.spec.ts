import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmsmanagementComponent } from './smsmanagement.component';

describe('SmsmanagementComponent', () => {
  let component: SmsmanagementComponent;
  let fixture: ComponentFixture<SmsmanagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmsmanagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmsmanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
