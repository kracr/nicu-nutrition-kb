import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugmanagementComponent } from './drugmanagement.component';

describe('DrugmanagementComponent', () => {
  let component: DrugmanagementComponent;
  let fixture: ComponentFixture<DrugmanagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DrugmanagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DrugmanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
