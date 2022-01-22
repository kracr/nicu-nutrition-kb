import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailManagementComponent } from './email-management.component';

describe('EmailManagementComponent', () => {
  let component: EmailManagementComponent;
  let fixture: ComponentFixture<EmailManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
