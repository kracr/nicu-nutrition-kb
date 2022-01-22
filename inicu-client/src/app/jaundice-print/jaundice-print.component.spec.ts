import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JaundicePrintComponent } from './jaundice-print.component';

describe('JaundicePrintComponent', () => {
  let component: JaundicePrintComponent;
  let fixture: ComponentFixture<JaundicePrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JaundicePrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JaundicePrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
