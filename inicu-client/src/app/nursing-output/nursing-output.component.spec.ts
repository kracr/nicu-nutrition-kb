import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NursingOutputComponent } from './nursing-output.component';

describe('NursingOutputComponent', () => {
  let component: NursingOutputComponent;
  let fixture: ComponentFixture<NursingOutputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NursingOutputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NursingOutputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
