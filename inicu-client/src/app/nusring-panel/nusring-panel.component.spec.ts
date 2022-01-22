import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NusringPanelComponent } from './nusring-panel.component';

describe('NusringPanelComponent', () => {
  let component: NusringPanelComponent;
  let fixture: ComponentFixture<NusringPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NusringPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NusringPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
