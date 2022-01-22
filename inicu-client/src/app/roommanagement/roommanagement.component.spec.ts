import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoommanagementComponent } from './roommanagement.component';

describe('RoommanagementComponent', () => {
  let component: RoommanagementComponent;
  let fixture: ComponentFixture<RoommanagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoommanagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoommanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
