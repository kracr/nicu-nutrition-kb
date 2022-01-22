import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingNotificationComponent } from './nursing-notification.component';

describe('NursingNotificationComponent', () => {
  let component: NursingNotificationComponent;
  let fixture: ComponentFixture<NursingNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
